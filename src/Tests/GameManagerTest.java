package Tests;

import game.board.NineMensMorris;
import game.logic.PlayerToken;
import game.logic.*;
import junit.framework.TestCase;
import game.Config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameManagerTest extends TestCase {
    private GameManager gameManager;

    protected void setUp() throws Exception {
        super.setUp();
        gameManager = new GameManager(game.Config.NineMensMorris());
    }

    public void testActivePlayerChange() {
        assertEquals(PlayerToken.PLAYER2, gameManager.nextTurn());
        assertEquals(PlayerToken.PLAYER1, gameManager.nextTurn());
    }

    public void testStartPlayer1() {
        assertEquals(PlayerToken.PLAYER1, gameManager.getActivePlayer());
    }

    public void testPhaseOneEnds() {
        for (int x = 0; x < 18; x++) {
            gameManager.placePiece(x);
            gameManager.nextTurn();
        }
        assertTrue(gameManager.isPhaseOneOver());
        assertFalse(gameManager.isPhaseOne());
    }

    public void testPlacePiece() {
        gameManager.placePiece(5);
        assertFalse(gameManager.placePiece(5));
    }

    public void testPlacePieceAfterPhaseOne() {
        for (int x = 0; x < 18; x++) {
            gameManager.placePiece(x);
            gameManager.nextTurn();
        }
        assertFalse(gameManager.placePiece(19));
    }

    public void testPlacedPieceLocationIsCorrect() {
        gameManager.placePiece(2);
        gameManager.nextTurn();

        gameManager.placePiece(6);
        assertSame(gameManager.getBoardAsPlayerTokens().get(2), PlayerToken.PLAYER1);
        assertSame(gameManager.getBoardAsPlayerTokens().get(6), PlayerToken.PLAYER2);
    }

    /***** SPRINT 2 TESTS *****/
    public void testCannotMoveWhenDestinationOccupied() {   //player 1 move invalid when occupied
        gameManager.placePiece(5);
        gameManager.placePiece(4);
        assertFalse(gameManager.move(4, 5));
    }

    public void testFlyLessThanOrEq3() {   //player 2 ability to fly
        Config testThreePieces = new Config(new NineMensMorris(), true, 3);
        GameManager gameManager = new GameManager(testThreePieces);
        gameManager.placePiece(0);
        gameManager.placePiece(3);
        gameManager.placePiece(2);
        gameManager.nextTurn();
        gameManager.placePiece(21);
        gameManager.placePiece(12);
        gameManager.placePiece(4);
        assertTrue(gameManager.move(4, 6));
    }

    public void testP1CantMoveP2() {   //player cannot move
        gameManager.nextTurn();
        gameManager.placePiece(0);
        gameManager.nextTurn();
        assertFalse(gameManager.move(0, 1));
    }

    public void testCanMoveIfAdjacent() {
        Config testFourPieces = new Config(new NineMensMorris(), true, 4);
        GameManager gameManager = new GameManager(testFourPieces);
        gameManager.placePiece(0);
        gameManager.placePiece(7);
        gameManager.placePiece(6);
        gameManager.placePiece(2);
        gameManager.nextTurn();
        gameManager.placePiece(22);
        gameManager.placePiece(18);
        gameManager.placePiece(20);
        gameManager.placePiece(16);
        gameManager.nextTurn();
        assertTrue(gameManager.move(0, 1));
    }

    public void testCantMoveIfNotAdjacent() {   //cannot move if the index is not adjacent and pieces > 3
        Config testFourPieces = new Config(new NineMensMorris(), true, 4);
        GameManager gameManager = new GameManager(testFourPieces);
        gameManager.placePiece(0);
        gameManager.placePiece(7);
        gameManager.placePiece(6);
        gameManager.placePiece(2);
        gameManager.nextTurn();
        gameManager.placePiece(22);
        gameManager.placePiece(18);
        gameManager.placePiece(20);
        gameManager.placePiece(16);
        gameManager.nextTurn();
        assertFalse(gameManager.move(0, 8));
    }

    public void testPlayerCantRemoveOwnPiece() {
        gameManager.placePiece(5);
        assertFalse(gameManager.removePiece(5));
        gameManager.nextTurn();

        gameManager.placePiece(7);
        assertFalse(gameManager.removePiece(7));
    }

    public void testPiecesDecrementedAfterRemoved() {
        gameManager.placePiece(6);
        gameManager.nextTurn();

        assertTrue(gameManager.removePiece(6));
        assertEquals(8, gameManager.getPiecesLeft(PlayerToken.PLAYER1));
    }

//    public void testCanMoveInPhaseOne() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Object p1 = gameManager.getClass().getDeclaredField("player1");
//        Object p2 = gameManager.getClass().getDeclaredField("player2");
//        Method m = gameManager.getClass().getDeclaredMethod("canMove", Player.class);
//        m.setAccessible(true);
//
//        // both players have moves at the beginning
//        assertTrue((boolean) m.invoke(gameManager, (Player) p1));
//        assertTrue((boolean) m.invoke(gameManager, (Player) p2));
//
//        // fill up the board
//        for (int i = 0; i < 18; i++) {
//            gameManager.placePiece(i);
//            gameManager.nextTurn();
//        }
//
//        // as well as at the end of placement
//        assertTrue((boolean) m.invoke(gameManager, (Player) p1));
//        assertTrue((boolean) m.invoke(gameManager, (Player) p2));
//    }

//    public void testCanMoveAfterPhaseOne() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
//        Config testFourPieces = new Config(new NineMensMorris(), true, 4);
//        Object p1 = gameManager.getClass().getDeclaredField("player1");
//        Object p2 = gameManager.getClass().getDeclaredField("player2");
//        Method m = gameManager.getClass().getDeclaredMethod("canMove", Player.class);
//        m.setAccessible(true);
//
//        gameManager = new GameManager(testFourPieces);
//
//        // set up pieces so that player 1 can't possibly make a move
//        gameManager.placePiece(1);
//        gameManager.placePiece(9);
//        gameManager.placePiece(14);
//        gameManager.placePiece(22);
//        gameManager.nextTurn();
//        gameManager.placePiece(0);
//        gameManager.placePiece(2);
//        gameManager.placePiece(21);
//        gameManager.placePiece(23);
//        assertFalse((boolean) m.invoke(gameManager, (Player) p2));
//
//        // remove pieces until player 1 has 3, then should be able to fly, then player 1 can move anywhere
//        gameManager.removePiece(1);
//        assertTrue((boolean) m.invoke(gameManager, (Player) p1));
//    }

    public void testWinCheckFor2PiecesLeftP1() {
        Config testThreePieces = new Config(new NineMensMorris(), true, 3);
        gameManager = new GameManager(testThreePieces);
        // verify game state at beginning
        assertSame(GameManager.GameState.PLACEMENT, gameManager.getCurrentGameState());
        // force player moves left to be 2
        gameManager.placePiece(0);
        gameManager.placePiece(1);
        gameManager.placePiece(4);
        gameManager.nextTurn();
        gameManager.placePiece(6);
        gameManager.placePiece(7);
        gameManager.placePiece(11);
        gameManager.removePiece(0);
        gameManager.nextTurn();

        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER2_WIN);
    }

    public void testWinCheckFor2PiecesLeftP2() {
        Config testThreePieces = new Config(new NineMensMorris(), true, 3);
        gameManager = new GameManager(testThreePieces);
        // verify game state at beginning
        assertSame(GameManager.GameState.PLACEMENT, gameManager.getCurrentGameState());
        // force player moves left to be 2
        gameManager.placePiece(0);
        gameManager.placePiece(1);
        gameManager.placePiece(4);
        gameManager.nextTurn();
        gameManager.placePiece(6);
        gameManager.placePiece(7);
        gameManager.placePiece(11);
        gameManager.nextTurn();
        gameManager.removePiece(6);
        gameManager.nextTurn();

        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER1_WIN);
    }

    public void testWinCheckP2CannotMove() {
        Config testFourPieces = new Config(new NineMensMorris(), true, 4);
        gameManager = new GameManager(testFourPieces);

        // set up pieces so that player 2 can't possibly make a move
        gameManager.placePiece(1);
        gameManager.placePiece(9);
        gameManager.placePiece(14);
        gameManager.placePiece(22);
        gameManager.nextTurn();
        gameManager.placePiece(0);
        gameManager.placePiece(2);
        gameManager.placePiece(21);
        gameManager.placePiece(23);
        gameManager.nextTurn();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER1_WIN);
    }

    public void testWinCheckP1CannotMove() {
        Config testFourPieces = new Config(new NineMensMorris(), true, 4);
        gameManager = new GameManager(testFourPieces);

        // set up pieces so that player 1 can't possibly make a move
        gameManager.placePiece(0);
        gameManager.placePiece(2);
        gameManager.placePiece(21);
        gameManager.placePiece(23);
        gameManager.nextTurn();
        gameManager.placePiece(1);
        gameManager.placePiece(9);
        gameManager.placePiece(14);
        gameManager.placePiece(22);
        gameManager.nextTurn();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER2_WIN);
    }

    public void testWinCheckTie() {
        Config twelvePiece = new Config(new NineMensMorris(), true, 12);
        gameManager = new GameManager(twelvePiece);

        for (int i = 0; i < 24; i++) {
            gameManager.placePiece(i);
            gameManager.nextTurn();
        }

        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.END);
    }

    public void testPlayerCannotRemoveMillPiece() {
        gameManager.placePiece(0);
        gameManager.placePiece(1);
        gameManager.placePiece(2);
        gameManager.placePiece(21);
        gameManager.nextTurn();
        assertFalse(gameManager.removePiece(1));
    }

    public void testPlayerCanRemoveMillPiece() {
        gameManager.placePiece(12);
        gameManager.nextTurn();
        gameManager.placePiece(0);
        gameManager.placePiece(1);
        gameManager.placePiece(2);
        gameManager.removePiece(12);
        gameManager.nextTurn();
        assertTrue(gameManager.removePiece(1));
    }

    public void testGameStateChanges() {
        Config sixPiece = new Config(new NineMensMorris(), true, 6);
        gameManager = new GameManager(sixPiece);

        gameManager.placePiece(0);
        gameManager.nextTurn();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLACEMENT);
        gameManager.placePiece(1);
        gameManager.placePiece(4);
        gameManager.placePiece(7);

        // player should not have changed as we should now be in elimination state from mill creation
        assertSame(gameManager.nextTurn(), PlayerToken.PLAYER2);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.ELIMINATION);
        gameManager.removePiece(0);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLACEMENT);
        // player should now have changed
        assertSame(gameManager.nextTurn(), PlayerToken.PLAYER1);
        gameManager.placePiece(0);
        gameManager.placePiece(2);
        gameManager.placePiece(23);
        gameManager.placePiece(21);
        gameManager.placePiece(10);
        gameManager.nextTurn();
        gameManager.placePiece(16);
        gameManager.placePiece(18);
        gameManager.placePiece(22);
        gameManager.nextTurn();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.MOVEMENT);
        gameManager.nextTurn();
        gameManager.move(18, 19);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.ELIMINATION);
        gameManager.removePiece(2);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.MOVEMENT);
    }
}
