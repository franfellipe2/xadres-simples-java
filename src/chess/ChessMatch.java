package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOnTheBorad = new ArrayList<>();
	private List<Piece> capturedWhites = new ArrayList<>();
	private List<Piece> capturedBlacks = new ArrayList<>();
	boolean check, checkmate, contaisKingWhite, containsKingBlack;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		intialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckmate() {
		return checkmate;
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * Passa a peça para o tabuleiro
	 * 
	 * @param column
	 * @param row
	 * @param piece
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		if (piece instanceof King) {
			if (piece.getColor() == Color.BLACK) {
				if (!containsKingBlack) {
					containsKingBlack = true;
				} else {
					throw new ChessException("Nao e possivel adcionar 2 " + piece.getColor() + " reis no tabuleiro");
				}
			} else {
				if (!contaisKingWhite) {
					contaisKingWhite = true;
				} else {
					throw new ChessException("Nao e possivel adcionar 2 " + piece.getColor() + " reis no tabuleiro");
				}
			}
		}
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBorad.add(piece);

	}

	public ChessPiece performChessMove(ChessPosition srcPosition, ChessPosition targPosition) {
		Position src = srcPosition.toPosition();
		Position targ = targPosition.toPosition();
		validateSourcePosition(src);
		validateTargetPosition(src, targ);
		Piece capturedPiece = makeMove(src, targ);
		if (testeCheck(currentPlayer)) {
			undoMove(src, targ, capturedPiece);
			throw new ChessException("voce nao pode realizar uma jogada que o deixe em CHECK!");
		}
		nextTurn();
		check = testeCheck(currentPlayer);
		checkmate = testeCheckmate(currentPlayer);
		return (ChessPiece) capturedPiece;
	}

	public boolean[][] possibleMoves(ChessPosition p) {
		Position pp = p.toPosition();
		validateSourcePosition(pp);
		return board.piece(pp).possibleMoves();
	}

	private Piece makeMove(Position src, Position targ) {
		ChessPiece piece = (ChessPiece) board.removePiece(src);
		Piece capturedPiece = board.removePiece(targ);
		piecesOnTheBorad.remove(capturedPiece);
		board.placePiece(piece, targ);
		piece.incrementNumberMoves();
		if (capturedPiece != null) {
			if (((ChessPiece) capturedPiece).getColor() == Color.WHITE) {
				capturedWhites.add(capturedPiece);
			} else {
				capturedBlacks.add(capturedPiece);
			}
		}
		return capturedPiece;
	}

	private void undoMove(Position src, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece) board.removePiece(target);
		board.placePiece(piece, src);
		piece.decrementNumberMoves();
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			piecesOnTheBorad.add(capturedPiece);
			if (((ChessPiece) capturedPiece).getColor() == Color.WHITE) {
				capturedWhites.remove(capturedPiece);
			} else {
				capturedBlacks.remove(capturedPiece);
			}
		}
	}

	public ChessPiece king(Color color) {
		List<Piece> list = this.piecesOnTheBorad.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Nao foi possível encontraro o rei " + color + " no tabuleiro!");
	}

	private Color oponent(Color color) {
		return color == Color.WHITE ? Color.BLACK : Color.WHITE;
	}

	/**
	 * Verifica se o jogador está em check
	 * 
	 * @param player
	 * @return
	 */
	private boolean testeCheck(Color player) {
		Position kingPosition = king(player).getChessPosition().toPosition();
		List<Piece> oponents = this.piecesOnTheBorad.stream()
				.filter(x -> ((ChessPiece) x).getColor() == oponent(player)).collect(Collectors.toList());
		for (Piece o : oponents) {
			boolean[][] oponentPosibleMoves = o.possibleMoves();
			if (oponentPosibleMoves[kingPosition.getRow()][kingPosition.getColumn()] == true) {
				return true;

			}
		}
		return false;
	}

	private boolean testeCheckmate(Color player) {
		if (!this.check)
			return false;
		List<Piece> pieces = piecesOnTheBorad.stream().filter(x -> ((ChessPiece) x).getColor() == player)
				.collect(Collectors.toList());
		for (Piece piece : pieces) {
			boolean[][] mat = piece.possibleMoves();
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < mat[i].length; j++) {
					if (mat[i][j]) {
						Position src, target;
						src = ((ChessPiece) piece).getChessPosition().toPosition();
						target = new Position(i, j);
						Piece capturedPiece = makeMove(src, target);
						boolean isCheck = testeCheck(player);
						undoMove(src, target, capturedPiece);
						if (!isCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void validateTargetPosition(Position src, Position targ) {
		if (!board.piece(src).possibleMove(targ)) {
			throw new ChessException("Esta peca nao pode ser movida para a posicao de destino!");
		}
	}

	/**
	 * Verifica se tem peça na posiçao e se ela tem movimentos válidos
	 * 
	 * @param p
	 */
	private void validateSourcePosition(Position p) {
		if (!board.thereIsAPiece(p))
			throw new ChessException("Nao existe peça na posiçao de origem!");
		if (currentPlayer != ((ChessPiece) board.piece(p)).getColor())
			throw new ChessException("A peca escolhida nao e sua!");
		if (!board.piece(p).isThereAnyPossibleMove())
			throw new ChessException("Nao existe movimento possivel para esta peca!");

	}

	public void nextTurn() {
		this.turn++;
		this.currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
	}

	public Board getBoard() {
		return board;
	}

	public List<Piece> getPiecesOnTheBorad() {
		return piecesOnTheBorad;
	}

	public List<Piece> getCapturedWhites() {
		return capturedWhites;
	}

	public List<Piece> getCapturedBlacks() {
		return capturedBlacks;
	}

	private void intialSetup() {
		// WHITE
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));
		// BLACK
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
