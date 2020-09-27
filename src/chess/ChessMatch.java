package chess;

import boardgame.Board;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		intialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	private void intialSetup() {
		board.placePiece(new Rook(board, Color.BLACK), new Position(4, 1));
		board.placePiece(new King(board, Color.WHITE), new Position(3, 5));
		board.placePiece(new King(board, Color.BLACK), new Position(7, 6));
	}
}
