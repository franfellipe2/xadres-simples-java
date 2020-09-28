package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		// bellow
		p.setValues(position.getRow() - 1, position.getColumn());
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// bottom
		p.setValues(position.getRow() + 1, position.getColumn());
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		return mat;
	}

	private boolean canMove(Position p) {
		if (getBoard().positionExistis(p)) {
			ChessPiece piece = (ChessPiece) getBoard().piece(p);
			return piece == null || getColor() != piece.getColor();
		}
		return false;
	}
}
