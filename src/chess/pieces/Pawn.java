package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		// movimentos peça branca
		if (getColor() == Color.WHITE) {
			// top
			p.setValues(position.getRow() - 1, position.getColumn());
			for (int i = getNumberMoves() == 0 ? 0 : 1; i < 2; i++) {
				if (!getBoard().positionExistis(p) || getBoard().piece(p) != null)
					break;
				mat[p.getRow()][p.getColumn()] = true;
				p.setValues(p.getRow() - 1, p.getColumn());
			}
			// nw
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (isThereOponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			// ne
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (isThereOponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			// movimentos peça preta
		} else {
			// top
			p.setValues(position.getRow() + 1, position.getColumn());
			for (int i = getNumberMoves() == 0 ? 0 : 1; i < 2; i++) {
				if (!getBoard().positionExistis(p) || getBoard().piece(p) != null)
					break;
				mat[p.getRow()][p.getColumn()] = true;
				p.setValues(p.getRow() + 1, p.getColumn());
			}
			// nw
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (isThereOponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			// ne
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (isThereOponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}
}
