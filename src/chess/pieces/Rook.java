package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);

	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		// below
		p.setValues(position.getRow() - 1, position.getColumn());
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if (isThereOponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;		
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if (isThereOponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;
		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if (isThereOponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;
		// bottom
		p.setValues(position.getRow() + 1, position.getColumn());
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if (isThereOponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;

		return mat;
	}
}
