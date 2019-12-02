package Tests;

import game.logic.GameManager;
import game.Config;
import game.board.Board;
import game.board.ThreeMensMorris;
import junit.framework.TestCase;

public class ThreeMensMorrisTestFullGame extends TestCase{
    private GameManager threeMensBoard;

    protected void setUp() throws Exception {
        threeMensBoard = new GameManager(Config.ThreeMensMorris());
    }
    public void testSimulatedGame() {
        threeMensBoard.placePiece(0);
        threeMensBoard.nextTurn();

        threeMensBoard.placePiece(1);
        threeMensBoard.nextTurn();

        threeMensBoard.placePiece(2);
        threeMensBoard.nextTurn();

        threeMensBoard.placePiece(3);
        threeMensBoard.nextTurn();

        threeMensBoard.placePiece(4);
        threeMensBoard.nextTurn();

        threeMensBoard.placePiece(5);
        threeMensBoard.nextTurn();

        assertFalse(threeMensBoard.placePiece(6)); //can't place if out of pieces

        assertFalse(threeMensBoard.move(3,6));  //can't move other player's piece
        threeMensBoard.nextTurn();
        assertTrue(threeMensBoard.move(3,6));  //CAN move own piece if all pieces placed

        assertFalse(threeMensBoard.move(0,3));  //same
        threeMensBoard.nextTurn();
        assertTrue(threeMensBoard.move(0,3));
        threeMensBoard.nextTurn();

        assertTrue(threeMensBoard.move(6, 0));  //player2 can fly
        threeMensBoard.nextTurn();
        assertTrue(threeMensBoard.move(3, 8));  //player1 can fly

        threeMensBoard.nextTurn();
        threeMensBoard.move(5, 6);
        threeMensBoard.nextTurn();
        threeMensBoard.move(4,7);
        threeMensBoard.nextTurn();
        threeMensBoard.move(1, 3);  //this forms a mill, player should be able to remove piece
        assertSame(threeMensBoard.getCurrentGameState(), GameManager.GameState.ELIMINATION);
        threeMensBoard.removePiece(2);  //remove piece and go to next turn... game should be over
        threeMensBoard.nextTurn();
        assertSame(threeMensBoard.getCurrentGameState(), GameManager.GameState.PLAYER2_WIN);
    }
}
