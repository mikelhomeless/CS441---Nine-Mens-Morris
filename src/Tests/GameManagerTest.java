package Tests;

import game.logic.PlayerToken;
import game.logic.*;
import junit.framework.TestCase;

public class GameManagerTest extends TestCase {
    private GameManager gameManager;

    protected void setUp() throws Exception {
        super.setUp();
        gameManager = new GameManager();
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
            gameManager.placePlayerPiece(x);
            gameManager.nextTurn();
        }
        assertTrue(gameManager.isPhaseOneOver());
        assertFalse(gameManager.isPhaseOne());
    }

    public void testPlacePiece() {
        gameManager.placePlayerPiece(5);
        assertFalse(gameManager.placePlayerPiece(5));
    }

    public void testPlacePieceAfterPhaseOne() {
        for (int x = 0; x < 18; x++) {
            gameManager.placePlayerPiece(x);
            gameManager.nextTurn();
        }
        assertFalse(gameManager.placePlayerPiece(19));
    }

    public void testPlacedPieceLocationIsCorrect() {
        gameManager.placePlayerPiece(2);
        gameManager.nextTurn();

        gameManager.placePlayerPiece(6);
        assertSame(gameManager.getBoardAsPlayerTokens()[2], PlayerToken.PLAYER1);
        assertSame(gameManager.getBoardAsPlayerTokens()[6], PlayerToken.PLAYER2);
    }

    /***** SPRINT 2 TESTS *****/
    public void testCannotMoveWhenDestinationOccupied() {   //player 1 move invalid when occupied
        gameManager.placePlayerPiece(5);
        gameManager.placePlayerPiece(4);
        assertFalse(gameManager.move(4, 5));
    }

    public void testFlyLessThanOrEq3() {   //player 2 ability to fly
        Player player1 = new Player(PlayerToken.PLAYER1, 3);
        Player player2 = new Player(PlayerToken.PLAYER2, 3);
        GameManager gameManager = new GameManager(player1, player2);
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(3);
        gameManager.placePlayerPiece(2);
        gameManager.nextTurn();
        gameManager.placePlayerPiece(21);
        gameManager.placePlayerPiece(12);
        gameManager.placePlayerPiece(4);
        assertTrue(gameManager.move(4, 6));
    }

    public void testP1CantMoveP2() {   //player cannot move
        gameManager.nextTurn();
        gameManager.placePlayerPiece(0);
        gameManager.nextTurn();
        assertFalse(gameManager.move(0, 1));
    }

    public void testCanMoveIfAdjacent() {
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 0);  //set pieces to 0 so phase one is registered as over
        GameManager gameManager = new GameManager(player1, player2);
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(7);
        gameManager.placePlayerPiece(6);
        gameManager.placePlayerPiece(2);
        assertTrue(gameManager.move(0, 1));
    }

    public void testCantMoveIfNotAdjacent() {   //cannot move if the index is not adjacent and pieces > 3
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 0);  //set pieces to 0 so phase one is registered as over
        GameManager gameManager = new GameManager(player1, player2);
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(7);
        gameManager.placePlayerPiece(6);
        gameManager.placePlayerPiece(2);
        assertFalse(gameManager.move(0, 8));
    }

    public void testPlayerCantRemoveOwnPiece() {
        gameManager.placePlayerPiece(5);
        assertFalse(gameManager.removePiece(5));
        gameManager.nextTurn();

        gameManager.placePlayerPiece(7);
        assertFalse(gameManager.removePiece(7));
    }

    public void testPiecesDecrementedAfterRemoved() {
        Player player1 = new Player(PlayerToken.PLAYER1, 9);
        Player player2 = new Player(PlayerToken.PLAYER2, 9);
        gameManager = new GameManager(player1, player2);

        gameManager.placePlayerPiece(6);
        gameManager.nextTurn();

        assertTrue(gameManager.removePiece(6));
        assertEquals(8, player1.getPiecesLeft());
    }

    public void testCanMoveInPhaseOne() {
        Player player1 = new Player(PlayerToken.PLAYER1, 9);
        Player player2 = new Player(PlayerToken.PLAYER2, 9);
        gameManager = new GameManager(player1, player2);

        // both players have moves at the beginning
        assertTrue(gameManager.canMove(player1));
        assertTrue(gameManager.canMove(player2));

        // fill up the board
        for (int i = 0; i < 18; i++) {
            gameManager.placePlayerPiece(i);
            gameManager.nextTurn();
        }

        // as well as at the end of placement
        assertTrue(gameManager.canMove(player1));
        assertTrue(gameManager.canMove(player2));
    }

    public void testCanMoveAfterPhaseOne() {
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 4);
        gameManager = new GameManager(player1, player2);

        // set up pieces so that player 1 can't possibly make a move
        gameManager.placePlayerPiece(1);
        gameManager.placePlayerPiece(9);
        gameManager.placePlayerPiece(14);
        gameManager.placePlayerPiece(22);
        gameManager.nextTurn();
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(2);
        gameManager.placePlayerPiece(21);
        gameManager.placePlayerPiece(23);
        assertFalse(gameManager.canMove(player2));

        // remove pieces until player 1 has 3, then should be able to fly, then player 1 can move anywhere
        player2.decrementPiecesLeft();
        assertTrue(gameManager.canMove(player1));
    }

    public void testWinCheckFor2PiecesLeftP1() {
        Player player1 = new Player(PlayerToken.PLAYER1, 9);
        Player player2 = new Player(PlayerToken.PLAYER2, 9);
        gameManager = new GameManager(player1, player2);
        // verify game state at beginning
        assertSame(GameManager.GameState.PLACEMENT, gameManager.getCurrentGameState());
        // force player moves left to be 2
        for (int i = 0; i < 7; i++) {
            player1.decrementPiecesLeft();
        }
        gameManager.winCheck();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER2_WIN);
    }

    public void testWinCheckFor2PiecesLeftP2() {
        Player player1 = new Player(PlayerToken.PLAYER1, 9);
        Player player2 = new Player(PlayerToken.PLAYER2, 9);
        gameManager = new GameManager(player1, player2);
        // verify game state at beginning
        assertSame(GameManager.GameState.PLACEMENT, gameManager.getCurrentGameState());
        // force player moves left to be 2
        for (int i = 0; i < 7; i++) {
            player2.decrementPiecesLeft();
        }
        gameManager.winCheck();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER1_WIN);
    }

    public void testWinCheckP2CannotMove() {
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 4);
        gameManager = new GameManager(player1, player2);

        // set up pieces so that player 2 can't possibly make a move
        gameManager.placePlayerPiece(1);
        gameManager.placePlayerPiece(9);
        gameManager.placePlayerPiece(14);
        gameManager.placePlayerPiece(22);
        gameManager.nextTurn();
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(2);
        gameManager.placePlayerPiece(21);
        gameManager.placePlayerPiece(23);
        gameManager.winCheck();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER1_WIN);
    }

    public void testWinCheckP1CannotMove() {
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 4);
        gameManager = new GameManager(player1, player2);

        // set up pieces so that player 1 can't possibly make a move
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(2);
        gameManager.placePlayerPiece(21);
        gameManager.placePlayerPiece(23);
        gameManager.nextTurn();
        gameManager.placePlayerPiece(1);
        gameManager.placePlayerPiece(9);
        gameManager.placePlayerPiece(14);
        gameManager.placePlayerPiece(22);
        gameManager.winCheck();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLAYER2_WIN);
    }

    public void testWinCheckTie() {
        Player player1 = new Player(PlayerToken.PLAYER1, 12);
        Player player2 = new Player(PlayerToken.PLAYER2, 12);
        gameManager = new GameManager(player1, player2);

        for (int i = 0; i < 24; i++) {
            gameManager.placePlayerPiece(i);
            gameManager.nextTurn();
        }

        gameManager.winCheck();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.END);
    }

    public void testPlayerCannotRemoveMillPiece() {
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(1);
        gameManager.placePlayerPiece(2);
        gameManager.placePlayerPiece(21);
        gameManager.nextTurn();
        assertFalse(gameManager.removePiece(1));
    }

    public void testPlayerCanRemoveMillPiece() {
        gameManager.placePlayerPiece(12);
        gameManager.nextTurn();
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(1);
        gameManager.placePlayerPiece(2);
        gameManager.removePiece(12);
        gameManager.nextTurn();
        assertTrue(gameManager.removePiece(1));
    }

    public void testGameStateChanges() {
        Player player1 = new Player(PlayerToken.PLAYER1, 6);
        Player player2 = new Player(PlayerToken.PLAYER2, 6);
        gameManager = new GameManager(player1, player2);

        gameManager.placePlayerPiece(0);
        gameManager.nextTurn();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLACEMENT);
        gameManager.placePlayerPiece(1);
        gameManager.placePlayerPiece(4);
        gameManager.placePlayerPiece(7);

        // player should not have changed as we should now be in elimination state from mill creation
        assertSame(gameManager.nextTurn(), PlayerToken.PLAYER2);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.ELIMINATION);
        gameManager.removePiece(0);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.PLACEMENT);
        // player should now have changed
        assertSame(gameManager.nextTurn(), PlayerToken.PLAYER1);
        gameManager.placePlayerPiece(0);
        gameManager.placePlayerPiece(2);
        gameManager.placePlayerPiece(23);
        gameManager.placePlayerPiece(21);
        gameManager.placePlayerPiece(10);
        gameManager.nextTurn();
        gameManager.placePlayerPiece(16);
        gameManager.placePlayerPiece(18);
        gameManager.placePlayerPiece(22);
        gameManager.nextTurn();
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.MOVEMENT);
        gameManager.nextTurn();
        gameManager.move(18, 19);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.ELIMINATION);
        gameManager.removePiece(2);
        assertSame(gameManager.getCurrentGameState(), GameManager.GameState.MOVEMENT);
    }
}
