package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int numberMoves;

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

	public int getNumberMoves(){
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
		if (getBoard().positionExistis(p)) {
			ChessPiece piece = (ChessPiece) getBoard().piece(p);
			return piece != null && (piece.getColor() != this.color);
		}
		return false;
	}
	
	protected boolean canMove(Position p) {
		if (getBoard().positionExistis(p)) {
			ChessPiece piece = (ChessPiece) getBoard().piece(p);
			return piece == null || getColor() != piece.getColor();
		}
		return false;
	}

}
