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
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOnTheBorad = new ArrayList<>();
	private List<Piece> capturedWhites = new ArrayList<>();
	private List<Piece> capturedBlacks = new ArrayList<>();
	boolean check, checkmate, contaisKingWhite, containsKingBlack;
	List<ChessMatchObserverInterface> chessMatchObservers = new ArrayList<>();
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		intialSetup();
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
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

	public void addObserver(ChessMatchObserverInterface observer) {
		this.chessMatchObservers.add(observer);
	}

	public void removeObserver(ChessMatchObserverInterface observer) {
		this.chessMatchObservers.remove(observer);
	}

	public ChessPiece getPromoted() {
		return promoted;
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

		ChessPiece movedPiece = (ChessPiece) board.piece(targ);
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && targ.getRow() == 0)
					|| (movedPiece.getColor() == Color.BLACK && targ.getRow() == 7)) {
				promoted = movedPiece;
			}
		}

		nextTurn();

		check = testeCheck(currentPlayer);
		checkmate = testeCheckmate(currentPlayer);

		// #En Passant - Verifca se é um peao sujeito a tomar En Passant
		if (movedPiece instanceof Pawn && (src.getRow() == targ.getRow() - 2) || (src.getRow() == targ.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}

	public void replacePromotionPiece(String type) {
		if (promoted == null)
			throw new ChessException("Nao existe peca a ser promovida!");
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q"))
			throw new IllegalArgumentException("Escolha um tipo valido de promocao(B,N,Q,R)");
		
		Position pos = promoted.getChessPosition().toPosition();
		ChessPiece old = (ChessPiece) board.removePiece(pos);		
		piecesOnTheBorad.remove(old);
		ChessPiece newPiece = newPieceFromPromoted(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBorad.add(newPiece);
	}

	private ChessPiece newPieceFromPromoted(String type, Color color) {
		if (type.equals("B"))
			return new Bishop(board, color);
		if (type.equals("N"))
			return new Knight(board, color);
		if (type.equals("Q"))
			return new Queen(board, color);
		return new Rook(board, color);
	}

	public boolean[][] possibleMoves(ChessPosition p) {
		Position pp = p.toPosition();
		validateSourcePosition(pp);
		return board.piece(pp).possibleMoves();
	}

	private Piece makeMove(Position src, Position targ) {
		ChessPiece piece = (ChessPiece) board.removePiece(src);
		Piece capturedPiece = board.removePiece(targ);
		board.placePiece(piece, targ);
		piece.incrementNumberMoves();

		// #jogada especial roque -------------------------------
		if (piece instanceof King && ((King) piece).canCastle()) {
			makeRookCastling((King) piece, src, targ);
			// # jogada especial En Passant
		} else if (piece instanceof Pawn) {
			if (src.getColumn() != targ.getColumn() && capturedPiece == null) {
				Position posPawn;
				if (piece.getColor() == Color.WHITE) {
					posPawn = new Position(targ.getRow() + 1, targ.getColumn());
				} else {
					posPawn = new Position(targ.getRow() - 1, targ.getColumn());
				}
				capturedPiece = board.removePiece(posPawn);
			}
		}

		if (capturedPiece != null) {
			piecesOnTheBorad.remove(capturedPiece);
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

		// # desfaz jogada especial Roque
		King king = piece instanceof King ? (King) piece : null;
		if (king != null && king.canCastle()) {
			undoRookCastling(king, src, target);
			// # desfaz jogada especial especial En Passant
		} else if (piece instanceof Pawn) {
			if (src.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				if (piece.getColor() == Color.WHITE) {
					target.setRow(target.getRow() + 1);
				} else {
					target.setRow(target.getRow() - 1);
				}
			}
		}
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

	private void makeRookCastling(King king, Position srcKing, Position targKing) {
		if (king.canCastle()) {
			// roque pequeno
			if (srcKing.getColumn() + 2 == targKing.getColumn()) {
				ChessPiece rook = (ChessPiece) board
						.removePiece(new Position(srcKing.getRow(), srcKing.getColumn() + 3));
				board.placePiece(rook, new Position(srcKing.getRow(), srcKing.getColumn() + 1));
				rook.incrementNumberMoves();
				// roque grande
			} else if (srcKing.getColumn() - 2 == targKing.getColumn()) {
				ChessPiece rook = (ChessPiece) board
						.removePiece(new Position(srcKing.getRow(), srcKing.getColumn() - 4));
				board.placePiece(rook, new Position(srcKing.getRow(), srcKing.getColumn() - 1));
				rook.incrementNumberMoves();
			}
		}
	}

	private void undoRookCastling(King king, Position srcKing, Position targKing) {
		// roque pequeno
		if (targKing.getColumn() - 2 == srcKing.getColumn()) {
			ChessPiece rook = (ChessPiece) board.removePiece(new Position(srcKing.getRow(), srcKing.getColumn() + 1));
			board.placePiece(rook, new Position(srcKing.getRow(), srcKing.getColumn() + 3));
			rook.decrementNumberMoves();
			// roque grande
		} else if (targKing.getColumn() + 2 == srcKing.getColumn()) {
			ChessPiece rook = (ChessPiece) board.removePiece(new Position(srcKing.getRow(), srcKing.getColumn() - 1));
			board.placePiece(rook, new Position(srcKing.getRow(), srcKing.getColumn() - 4));
			rook.decrementNumberMoves();
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
		for (ChessMatchObserverInterface observer : chessMatchObservers) {
			observer.onNextTuro(turn, currentPlayer);
		}
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

		placeNewPiece('a', 2, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 5, new King(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 4, new King(board, Color.WHITE, this));
	}
}
