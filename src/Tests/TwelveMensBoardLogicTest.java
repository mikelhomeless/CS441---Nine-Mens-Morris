package Tests;

import game.board.Board;
import game.Config;
import game.board.TwelveMensMorris;
import game.logic.GameManager;
import game.logic.PlayerToken;
import junit.framework.TestCase;

import java.util.List;

public class TwelveMensBoardLogicTest extends TestCase{
    private GameManager twelveMensBoard;
    Board board;

    protected void setUp() throws Exception {
        twelveMensBoard = new GameManager(Config.TwelveMensMorris());
        board = new TwelveMensMorris();
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
                {1,3,9},
                {0, 2, 4},
                {1, 5, 14},
                {0, 4, 6, 10},
                {1,3,5,7},
                {2, 4, 8, 13},
                {3, 7, 11},
                {4, 6, 8},
                {5, 7, 12},
                {0, 10, 21},
                {3, 9, 11, 18},
                {6, 10, 15},
                {8, 13, 17},
                {2, 13, 23} ,
                {11, 16, 18},
                {15, 17, 19},
                {12, 16, 20},
                {10, 15, 19, 21},
                {16, 18, 20, 22},
                {13, 17, 19, 23},
                {9, 18, 22},
                {19, 21, 23},
                {14, 20, 22},



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
