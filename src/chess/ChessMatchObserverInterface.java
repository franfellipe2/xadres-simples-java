package chess;

import chess.enums.Color;

public interface ChessMatchObserverInterface {
	void onNextTuro(int turno, Color currentPlayer);
}
