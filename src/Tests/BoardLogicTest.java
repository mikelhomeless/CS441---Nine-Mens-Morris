package Tests;

import game.board.Board;
import game.logic.PlayerToken;
import junit.framework.TestCase;

public class BoardLogicTest extends TestCase {
    Board board;

    protected void setUp() throws Exception {
        board = new Board();
    }

    public void testBoardisEmpty() {
        PlayerToken[] newBoard = board.getBoard();
        for (int x = 0; x < newBoard.length; x++) {
            assertEquals(PlayerToken.NOPLAYER, newBoard[x]);
        }
    }

    public void testReturnsExceptionOutofBounds() {
        try {
            board.getCell(25);
            fail("Upper bounds reached");

        } catch (IndexOutOfBoundsException e) {
        }
        try {
            board.getCell(-1);
            fail("Lower bounds reached");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    public void testCorrectCellisSet(){
        board.setCell(1, PlayerToken.PLAYER1);
        board.setCell(2, PlayerToken.PLAYER2);
        assertEquals(PlayerToken.PLAYER1, board.getBoard()[1]);
        assertEquals(PlayerToken.PLAYER2, board.getBoard()[2]);
    }

    public void testCantRemoveIfNoPiece(){
        assertFalse(board.removePieceFromCell(3));
    }

    public void testRemoveIfPieceIsThere(){
        board.setCell(7, PlayerToken.PLAYER1);
        assertTrue(board.removePieceFromCell(7));
        assertTrue(board.getCell(7).isEmpty());
    }

    // AC Test
    public void testSetCellAdjacencies(){
        int[][] to_test = {
                {1, 9},
                {0, 2, 4},
                {1, 14},
                {4, 10},
                {1, 3, 5, 7},
                {4, 13},
                {7, 11},
                {4, 6, 8},
                {7, 12},
                {0, 21},
                {3, 9, 11, 18},
                {6, 10, 15},
                {8, 13, 17},
                {5, 12, 14, 20},
                {2, 13, 23},
                {11, 16},
                {15, 17, 19},
                {12, 16},
                {10, 19},
                {16, 18, 20, 22},
                {13, 19},
                {9, 22},
                {19, 21, 23},
                {14, 22}
        };


        for(int i = 0; i < 24; i++){
            for(int x: to_test[i]){
                assertTrue(board.getCell(i).isAdjacentTo(x));
            }
        }
    }

    public void testDetectsMill(){
        board.setCell(0, PlayerToken.PLAYER1);
        board.setCell(1, PlayerToken.PLAYER1);
        board.setCell(2, PlayerToken.PLAYER1);
        assertTrue(board.isCellInMill(0));
        assertTrue(board.isCellInMill(1));
        assertTrue(board.isCellInMill(2));
    }

    public void testNoMillWhenNotAllSamePlayer(){
        board.setCell(0, PlayerToken.PLAYER1);
        board.setCell(1, PlayerToken.PLAYER1);
        board.setCell(2, PlayerToken.PLAYER2);
        assertFalse(board.isCellInMill(0));
        assertFalse(board.isCellInMill(1));
        assertFalse(board.isCellInMill(2));
    }

    public void testNoMillWhenEmpty(){
        assertFalse(board.isCellInMill(0));
        assertFalse(board.isCellInMill(1));
        assertFalse(board.isCellInMill(2));
    }

    public void testNoMillWhenNotAdjacent(){
        board.setCell(0, PlayerToken.PLAYER1);
        board.setCell(1, PlayerToken.PLAYER1);
        board.setCell(4, PlayerToken.PLAYER1);
        assertFalse(board.isCellInMill(0));
        assertFalse(board.isCellInMill(1));
        assertFalse(board.isCellInMill(4));
    }
}
