package Tests;

import game.logic.GameManager;
import game.Config;
import game.board.Board;
import game.board.TwelveMensMorris;
import game.logic.Player;
import game.logic.PlayerToken;
import junit.framework.TestCase;

public class TwelveMensMorrisTestFullGame extends TestCase {
    private GameManager twelveMensBoard;
    private Player player1;
    private Player player2;

    protected void setUp() throws Exception {
        twelveMensBoard = new GameManager(Config.TwelveMensMorris());
        player1 = new Player(PlayerToken.PLAYER1, 12);
        player2 = new Player(PlayerToken.PLAYER2, 12);

    }

    public void testNumPlayerPiecesLeft() {   //assuring that each player starts with 3 pieces
        assertEquals(12, player1.getPiecesLeft());
        assertEquals(12, player2.getPiecesLeft());
    }

    public void testTie() {
        twelveMensBoard.placePiece(0);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(1);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(2);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(3);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(4);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(5);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(6);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(7);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(8);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(9);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(10);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(11);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(12);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(13);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(14);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(15);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(16);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(17);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(18);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(19);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(20);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(21);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(22);
        assertFalse(twelveMensBoard.placePiece(6)); //can't place if out of pieces
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(23);
        twelveMensBoard.nextTurn();


        assertSame(twelveMensBoard.getCurrentGameState(), GameManager.GameState.END);  //all pieces placed, no player has valid move --> TIE, game has ENDED
    }

    public void testFullGame() {
        twelveMensBoard.placePiece(0);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(1);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(2);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(3);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(4);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(5);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(6);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(7);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(8);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(9);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(10);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(11);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(12);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(13);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(14);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(15);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(16);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(17);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(18);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(21);   //21, 22, 23, player 2 should have a mill
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(20);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(22);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(19);
        twelveMensBoard.nextTurn();

        twelveMensBoard.placePiece(23);


        assertSame(twelveMensBoard.getCurrentGameState(), GameManager.GameState.ELIMINATION);
        assertTrue(twelveMensBoard.removePiece(15));  //can remove piece

    }

    public void testWinCondition() {
        System.out.println(twelveMensBoard.getCurrentGameState());

        twelveMensBoard.placePiece(0);
        twelveMensBoard.placePiece(1);
        twelveMensBoard.nextTurn();
        twelveMensBoard.placePiece(5);
        twelveMensBoard.placePiece(7);
        twelveMensBoard.placePiece(20);
        twelveMensBoard.nextTurn();
        twelveMensBoard.placePiece(3);
        twelveMensBoard.placePiece(4);
        twelveMensBoard.placePiece(6);
        twelveMensBoard.placePiece(15);
        twelveMensBoard.placePiece(9);
        twelveMensBoard.placePiece(10);
        twelveMensBoard.placePiece(12);
        twelveMensBoard.placePiece(13);
        twelveMensBoard.placePiece(15);
        twelveMensBoard.placePiece(2);
        twelveMensBoard.removePiece(5);
        twelveMensBoard.nextTurn();
        twelveMensBoard.placePiece(8);
        twelveMensBoard.placePiece(11);
        twelveMensBoard.placePiece(14);
        twelveMensBoard.placePiece(16);
        twelveMensBoard.placePiece(17);
        twelveMensBoard.placePiece(18);
        twelveMensBoard.placePiece(19);
        twelveMensBoard.placePiece(23);
        twelveMensBoard.placePiece(21);

        System.out.println(twelveMensBoard.getActivePlayer());
        System.out.println(twelveMensBoard.getCurrentGameState());
        //assertSame(twelveMensBoard.getCurrentGameState(), GameManager.GameState.PLAYER2_WIN);
    }
}
