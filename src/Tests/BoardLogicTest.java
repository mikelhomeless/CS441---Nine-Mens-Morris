package Tests;

import game.logic.*;
import junit.framework.TestCase;
import org.junit.Test;

public class BoardLogicTest extends TestCase {
    Board board;

    protected void setUp() {
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
}
