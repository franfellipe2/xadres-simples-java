package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

	ChessMatch match;
	private boolean canCastle;

	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}

	@Override
	public String toString() {
		return "K";
	}

	public boolean canCastle() {
		return canCastle;
	}

	@Override
	public boolean[][] possibleMoves() {
		canCastle = false;
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
		// #Roque: Jogada especial
		if (this.getNumberMoves() == 0 && !this.match.isCheck()) {
			int r = position.getRow();// linha
			int c = position.getColumn();// coluna
			ChessPiece[][] m = match.getPieces();
			// #Roque curto
			ChessPiece rook = m[r][c + 3];
			if (rook instanceof Rook && rook.getNumberMoves() == 0 && m[r][c + 1] == null && m[r][c + 2] == null) {
				canCastle = true;
				mat[r][c + 2] = true;
			}
			// #roque longo(do lada da rainha)
			rook = m[r][c - 4];
			if (rook instanceof Rook && rook.getNumberMoves() == 0 && m[r][c - 1] == null && m[r][c - 2] == null
					&& m[r][c - 3] == null) {
				canCastle = true;
				mat[r][c - 2] = true;
			}
		}

		return mat;
	}

	@Override
	protected boolean canMove(Position p) {
		return super.canMove(p) || isThereOponentPiece(p);
	}

}
