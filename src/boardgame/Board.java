package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows <= 0 || columns <= 0) {
			throw new BoardException("Erro ao criar tabuleiro: � necess�rio que haja pelo menos 1 linha e 1 coluna!!");
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
			throw new BoardException("N�o existe esta posi��o no tabuleiro ");
		return pieces[row][column];
	}

	public Piece removePiece(Position p) {
		if (!positionExistis(p)) {
			throw new BoardException("N�o existe esta posi��o no tabuleiro ");
		}
		if (piece(p) == null)
			return null;

		Piece aux = piece(p);
		aux.position = null;
		pieces[p.getRow()][p.getColumn()] = null;
		return aux;
	}
	public Piece removePiece(Piece p) {		
		Piece aux = p;
		aux.position = null;
		pieces[p.position.getRow()][p.position.getColumn()] = null;
		return aux;
	}

	private boolean positionExistis(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExistis(Position position) {
		return positionExistis(position.getRow(), position.getColumn());
	}

	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("J� existe uma pe�a nessa posi��o");
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExistis(position.getRow(), position.getColumn())) {
			throw new BoardException("N�o existe esta posi��o no tabuleiro ");
		}
		return piece(position) != null;
	}
}
