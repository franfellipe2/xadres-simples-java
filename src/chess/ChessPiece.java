package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int numberMoves;
	private int oponentOnTheWay = 0;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	public int getNumberMoves() {
		return numberMoves;
	}

	public void incrementNumberMoves() {
		numberMoves++;
	}

	public void decrementNumberMoves() {
		if (numberMoves > 0)
			numberMoves--;
	}

	protected boolean isThereOponentPiece(Position p) {
		if (!board.positionExistis(p))
			return false;
		ChessPiece piece = (ChessPiece) getBoard().piece(p);
		return piece != null && (piece.getColor() != this.color);
	}

	protected boolean canMove(Position p) {
		if (board.positionExistis(p)) {
			return getBoard().piece(p) == null;
		}
		return false;
	}

}
