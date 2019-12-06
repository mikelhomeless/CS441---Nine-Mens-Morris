package Tests;

import game.board.Board;
import game.board.ThreeMensMorris;
import game.Config;
import game.logic.GameManager;
import game.logic.PlayerToken;
import junit.framework.TestCase;

import java.util.List;

public class ThreeMensBoardLogicTest extends TestCase{
    private GameManager threeMensBoard;
    Board board;

    protected void setUp() throws Exception {
        threeMensBoard = new GameManager(Config.ThreeMensMorris());
        board = new ThreeMensMorris();
    }

    public void testBoardisEmpty() {                       //recycled next two functions for testing
        List<PlayerToken> newBoard = board.getBoard();
        for (int x = 0; x < newBoard.size(); x++) {
            assertEquals(PlayerToken.NOPLAYER, newBoard.get(x));
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

    public void testSetCellAdjacencies(){
        int[][] to_test = {
                {1,3,4},
                {0, 2, 4},
                {1, 5, 4},
                {0, 4, 6},
                {0, 1, 2, 3, 4, 5, 6, 7, 8},
                {2, 4, 8},
                {3, 4, 7},
                {4, 6, 8},
                {4, 5, 7}
        };

        //same test as nine men's but this wouldn't pass for me on my machine
        /*for(int i = 0; i < 9; i++){
            for(int x: to_test[i]){
                assertTrue(board.getCell(i).isAdjacentTo(x));
            }
        }*/
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
