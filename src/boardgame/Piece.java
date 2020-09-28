package boardgame;

public abstract class Piece {

	protected Position position;
	protected Board board;

	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position p) {
		return possibleMoves()[p.getRow()][p.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		Position p = new Position(0, 0);
		int rows = possibleMoves().length;
		int columns = possibleMoves()[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				p.setRow(i);
				p.setColumn(j);
				if (this.possibleMove(p))
					return true;
			}
		}
		return false;
	}
}
