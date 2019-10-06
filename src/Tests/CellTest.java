package Tests;

import game.logic.*;
import junit.framework.TestCase;

public class CellTest extends TestCase{
    Cell cell;
    protected void setUp(){
        cell = new Cell();
    }

    public void testBoardIsEmpty(){
        assertTrue(cell.isEmpty());
    }

    public void testBoardisOccupied(){
        assertFalse(cell.isOccupied());
    }

    public void testBoardisOccupiedbyCorrectPlayer(){
        cell.setPlayer(PlayerToken.PLAYER1);
        assertTrue(cell.isOccupiedBy(PlayerToken.PLAYER1));
        assertFalse(cell.isOccupiedBy(PlayerToken.PLAYER2));
    }

    public void testCorrectPlayerSet(){
        cell.setPlayer(PlayerToken.PLAYER1);
        assertEquals(PlayerToken.PLAYER1, cell.getPlayer());
    }
}
