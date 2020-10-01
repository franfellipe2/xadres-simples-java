package chess.pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessMatchObserverInterface;
import chess.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;
	private boolean vulnerableEnPassant;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			// #En Passant
			if (position.getRow() == 3) {
				ChessPiece left = getOpoentPiece(new Position(position.getRow(), position.getColumn() - 1));
				if (left != null && left == this.chessMatch.getEnPassantVulnerable()) {
					mat[position.getRow() - 1][position.getColumn() - 1] = true;
				}
				ChessPiece right = getOpoentPiece(new Position(position.getRow(), position.getColumn() + 1));
				if (right != null && right == this.chessMatch.getEnPassantVulnerable()) {
					mat[position.getRow() - 1][position.getColumn() + 1] = true;
				}
			}
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
			// #En Passant
			if (position.getRow() == 4) {
				ChessPiece left = getOpoentPiece(new Position(position.getRow(), position.getColumn() - 1));
				if (left != null && left == this.chessMatch.getEnPassantVulnerable()) {
					mat[position.getRow() + 1][position.getColumn() - 1] = true;
				}
				ChessPiece right = getOpoentPiece(new Position(position.getRow(), position.getColumn() + 1));
				if (right != null && right == this.chessMatch.getEnPassantVulnerable()) {
					mat[position.getRow() + 1][position.getColumn() + 1] = true;
				}
			}
		}

		return mat;
	}

	@Override
	public void incrementNumberMoves() {
		super.incrementNumberMoves();
	}

}
