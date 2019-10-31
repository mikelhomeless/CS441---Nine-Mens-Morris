package Tests;

import game.logic.*;
import junit.framework.TestCase;

public class CellTest extends TestCase{
    Cell cell;
    protected void setUp(){
        cell = new Cell();
        cell.setAdjacentCells(new Integer[]{1,2,3});
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

    // AC Test
    public void testIsAdjacent(){
        assertTrue(cell.isAdjacentTo(1));
        assertTrue(cell.isAdjacentTo(2));
        assertTrue(cell.isAdjacentTo(3));
        assertFalse(cell.isAdjacentTo(4));
    }

    // Developer Test
    public void testAddAdjacentCell(){
        assertFalse(cell.isAdjacentTo(4));
        cell.addAdjacentCell(4);
        assertTrue(cell.isAdjacentTo(4));
    }
}
