package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.Bishop;
import chess.pieces.Horse;
import chess.pieces.King;
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
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBorad.add(piece);
	}

	public ChessPiece performChessMove(ChessPosition srcPosition, ChessPosition targPosition) {
		Position src = srcPosition.toPosition();
		Position targ = targPosition.toPosition();
		validateSourcePosition(src);
		validateTargetPosition(src, targ);
		Piece capturedPiece = makeMove(src, targ);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	public boolean[][] possibleMoves(ChessPosition p) {
		Position pp = p.toPosition();
		validateSourcePosition(pp);
		return board.piece(pp).possibleMoves();
	}

	private Piece makeMove(Position src, Position targ) {
		Piece piece = board.removePiece(src);
		Piece capturedPiece = board.removePiece(targ);
		board.placePiece(piece, targ);
		if (capturedPiece != null) {
			if (((ChessPiece) capturedPiece).getColor() == Color.WHITE) {
				capturedWhites.add(capturedPiece);
			} else {
				capturedBlacks.add(capturedPiece);
			}
		}
		return capturedPiece;
	}

	private void validateTargetPosition(Position src, Position targ) {
		// TODO Auto-generated method stub
		if (!board.piece(src).possibleMove(targ)) {
			throw new ChessException("Esta peca nao pode ser movida para a posicao de destino!");
		}
	}

	/**
	 * Verifica se tem peça na posição e se ela tem movimentos válidos
	 * 
	 * @param p
	 */
	private void validateSourcePosition(Position p) {
		if (!board.thereIsAPiece(p))
			throw new ChessException("Não existe peça na posição de origem!");
		if (currentPlayer != ((ChessPiece) board.piece(p)).getColor())
			throw new ChessException("A peca escolhida nao e sua!");
		if (!board.piece(p).isThereAnyPossibleMove())
			throw new ChessException("Não existe movimento possivel para esta peca!");

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
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Horse(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('f', 1, new King(board, Color.WHITE));
		placeNewPiece('g', 1, new Horse(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		// placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		// placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Horse(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('f', 8, new King(board, Color.BLACK));
		placeNewPiece('g', 8, new Horse(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		// placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		// placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		//placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
