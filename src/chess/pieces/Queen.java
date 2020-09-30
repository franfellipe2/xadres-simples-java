package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class Queen extends ChessPiece {
	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow(), p.getColumn() - 1);
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
		// top
		p.setValues(position.getRow() - 1, position.getColumn());
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn());
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow(), p.getColumn() + 1);
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
		// bottom
		p.setValues(position.getRow() + 1, position.getColumn());
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn());
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if(isThereOponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;

		return mat;
	}
}
