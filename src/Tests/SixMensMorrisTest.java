package Tests;

import game.logic.GameManager;
import game.Config;
import game.board.Board;
import game.board.SixMensMorris;

import junit.framework.TestCase;

import java.util.List;

public class SixMensMorrisTest extends TestCase {
    private GameManager sixMensBoard;

    protected void setUp() throws Exception {
        sixMensBoard = new GameManager(Config.SixMensMorris());
    }

    protected void testSimulatedGame(){
        sixMensBoard.placePiece(0); // player1
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(1); // player2
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(2);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(3);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(4);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(5);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(6);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(7);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(8);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(9);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(10);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(11);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(12);
        sixMensBoard.nextTurn();

        sixMensBoard.placePiece(13);

        assertFalse(sixMensBoard.placePiece(14));

        // cannot fly
        sixMensBoard.removePiece(0); // player2
        sixMensBoard.removePiece(2);
        sixMensBoard.removePiece(4);
        sixMensBoard.nextTurn(); // back to player 1

        assertFalse(sixMensBoard.move(6, 4)); // player1

    }

}
