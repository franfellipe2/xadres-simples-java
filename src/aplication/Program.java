package aplication;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import ui.UI;

public class Program {
	public static void main(String args[]){
		
		ChessMatch chessMatch  = new ChessMatch();
		ChessPiece[][] pieces = chessMatch.getPieces();
		UI ui = new UI();
		ui.printBoard(pieces);
		
		
	}
}
