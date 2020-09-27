package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows <= 0 || columns <= 0) {
			throw new BoardException("Erro ao criar tabuleiro: é necessário que haja pelo menos 1 linha e 1 coluna!!");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExistis(row, column))
			throw new BoardException("Não existe esta posição(" + row + "," + column + ") no tabuleiro ");
		return pieces[row][column];
	}

	private boolean positionExistis(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExistis(Position position) {
		if (!positionExistis(position.getRow(), position.getColumn())) {
			throw new BoardException(
					"Não existe esta posição(" + position.getRow() + "," + position.getColumn() + ") no tabuleiro ");
		}
		return positionExistis(position.getRow(), position.getColumn());
	}

	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException(
					"Já existe uma peça nessa posição(" + position.getRow() + "," + position.getColumn() + ")");
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExistis(position.getRow(), position.getColumn())) {
			throw new BoardException(
					"Não existe esta posição(" + position.getRow() + "," + position.getColumn() + ") no tabuleiro ");
		}
		return piece(position) != null;
	}
}
