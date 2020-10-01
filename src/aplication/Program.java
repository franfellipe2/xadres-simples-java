package aplication;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;
import ui.UI;

public class Program {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		UI ui = new UI(chessMatch);

		while (!chessMatch.isCheckmate()) {
			try {

				printBoard(ui, null);

				System.out.print("Origem: ");
				ChessPosition src = UI.readChessPosition(sc);
				printBoard(ui, chessMatch.possibleMoves(src));

				System.out.print("Destino: ");
				ChessPosition targ = UI.readChessPosition(sc);
				chessMatch.performChessMove(src, targ);
				if (chessMatch.getPromoted() != null) {
					System.out.print("Escolha uma promocao(B/N/R/Q): ");
					String type = sc.next().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Escolha um tipo valido de promocao(B,N,Q,R): ");
						type = sc.next().toUpperCase();						
					}
					chessMatch.replacePromotionPiece(type);
				}

			} catch (ChessException e) {
				System.out.print(e.getMessage());
				sc.nextLine();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage());
				sc.nextLine();
				sc.nextLine();
			}
		}

		printBoard(ui, null);
	}

	private static void printBoard(UI ui, boolean[][] possibleMoves) {
		UI.clearScreen();
		System.out.println();
		System.out.println("  XADREZ");
		if (possibleMoves == null) {
			ui.printMatch();
		} else {
			ui.printBoard(possibleMoves);
		}
		System.out.println();
	}
}
