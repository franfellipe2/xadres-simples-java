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
			if (testeRookCastling(new Position(r, c + 3)) && m[r][c + 1] == null && m[r][c + 2] == null) {
				canCastle = true;
				mat[r][c + 2] = true;
			}
			// #roque longo(do lada da rainha)
			if (testeRookCastling(new Position(r, c - 4)) && m[r][c - 1] == null && m[r][c - 2] == null
					&& m[r][c - 3] == null) {
				canCastle = true;
				mat[r][c - 2] = true;
			}
		}

		return mat;
	}

	private boolean testeRookCastling(Position position) {
		if (!board.positionExistis(position))
			return false;
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getNumberMoves() == 0;

	}

	@Override
	protected boolean canMove(Position p) {
		return super.canMove(p) || isThereOponentPiece(p);
	}

}
