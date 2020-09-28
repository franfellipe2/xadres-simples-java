package aplication;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.UI;

public class Program {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		UI ui = new UI();

		boolean runGame = true;
		while (runGame == true) {

			System.out.println();
			System.out.println("XADREZ");
			ui.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.print("Origem: ");
			ChessPosition src = ui.readChessPosition(sc);			
			System.out.print("Destino: ");
			ChessPosition targ = ui.readChessPosition(sc);
			ChessPiece capturedPiece = chessMatch.performChessMove(src, targ);

		}
		if (runGame == false)
			sc.close();
	}
}
