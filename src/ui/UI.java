package ui;

import chess.ChessMatch;
import chess.ChessPiece;

public class UI {
	public static void printBoard(ChessPiece[][] pieces) {
		int nLines = pieces.length;
		int nColumns = pieces[0].length;
		for(int i = 0; i < nLines; i ++){
			System.out.print((8 - i)+" ");
			for(int j = 0; j < nColumns; j++){
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}		
		System.out.print("  a b c d e f g h");
		
	}
	
	public static void printPiece(ChessPiece piece){
		if(piece == null){
			System.out.print("-");
		}else{
			System.out.print(piece);
		}
		System.out.print(" ");
	}
}
