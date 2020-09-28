package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public ChessPosition getChessPosition(){
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOponentPiece(Position p) {
		if (getBoard().positionExistis(p)) {
			ChessPiece piece = (ChessPiece) getBoard().piece(p);
			return piece != null && (piece.getColor() != this.color);
		}
		return false;
	}

}
