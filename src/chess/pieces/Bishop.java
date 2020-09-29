package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);

	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		// nw
		p.setValues(position.getRow() - 1, position.getColumn()-1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()-1, p.getColumn()-1);
		}		
		// ne
		p.setValues(position.getRow()-1, position.getColumn() + 1);		
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()-1, p.getColumn() +1);
		}		
		// se
		p.setValues(position.getRow()+1, position.getColumn() + 1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()+1, p.getColumn() + 1);
		}
		// sw
		p.setValues(position.getRow() + 1, position.getColumn()-1);
		while (canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()+1, p.getColumn() - 1);
		}
		return mat;
	}
}
