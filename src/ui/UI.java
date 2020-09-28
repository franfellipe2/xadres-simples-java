package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.enums.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	private ChessMatch chessMatch;

	public UI(ChessMatch chessMatch) {
		this.chessMatch = chessMatch;
	}

	/**
	 * {@link} https://stackoverflow.com/questions/2979383/java-clear-the-
	 * console
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void printBoard() {
		performPrintBoard(new OnPrintBoard() {
			@Override
			public void printCell(ChessPiece piece, int row, int column) {
				printPiece(piece, false);
			}
		});
	}

	public void printBoard(final boolean[][] possibleMoves) {
		performPrintBoard(new OnPrintBoard() {
			@Override
			public void printCell(ChessPiece piece, int row, int column) {
				if (possibleMoves[row][column]) {
					printPiece(piece, true);
				} else {
					printPiece(piece, false);
				}
			}
		});
	}

	private void performPrintBoard(OnPrintBoard callback) {
		System.out.println("  " + ANSI_YELLOW_BACKGROUND + ANSI_BLACK + "  a b c d e f g h " + ANSI_RESET);
		ChessPiece[][] pieces = this.chessMatch.getPieces();
		int nLines, nColumns;
		nLines = nColumns = pieces.length;
		for (int i = 0; i < nLines; i++) {
			System.out.print("  " + (8 - i) + " ");
			for (int j = 0; j < nColumns; j++) {
				callback.printCell(pieces[i][j], i, j);
			}
			System.out.println();
		}
		System.out.print("  " + ANSI_WHITE_BACKGROUND + ANSI_BLACK + "  a b c d e f g h " + ANSI_RESET);
		System.out.println();
	}

	public interface OnPrintBoard {
		void printCell(ChessPiece piece, int row, int column);
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.next();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler posicao: escolha uma posicao de a1 a h8");
		}
	}

	public static void printPiece(ChessPiece piece, boolean background) {
		if (piece == null) {
			if (background) {
				System.out.print(ANSI_BLUE_BACKGROUND + "-" + ANSI_RESET);
			} else {
				System.out.print("-");
			}
		} else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

}
