package Tests;

import game.board.Cell;
import game.logic.PlayerToken;
import junit.framework.TestCase;

public class CellTest extends TestCase{
    Cell cell;
    protected void setUp(){
        cell = new Cell();
        cell.addAdjacentCells(new Integer[]{1,2,3});
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
        cell.addAdjacentCells(new Integer[]{4});
        assertTrue(cell.isAdjacentTo(4));
    }
}
