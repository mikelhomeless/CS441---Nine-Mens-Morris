package Tests;

import game.logic.*;
import junit.framework.TestCase;

public class PlayerTest extends TestCase{
    Player player1;
    Player player2;
    protected void setUp(){
        player1 = new Player(PlayerToken.PLAYER1, 9);
        player2 = new Player(PlayerToken.PLAYER2, 9);
    }
    public void testCorrectPlayerReturned(){
        assertEquals(PlayerToken.PLAYER1, player1.getPlayerToken());
        assertEquals(PlayerToken.PLAYER2, player2.getPlayerToken());
    }
    public void testNumPlayerTokensLeft(){
        assertEquals(9, player1.getPiecesLeft());
        assertEquals(9, player2.getPiecesLeft());
        player1.incrementPiecesOnBoard();
        player2.incrementPiecesOnBoard();
        assertEquals(8, player1.getPiecesLeft());
        assertEquals(8, player2.getPiecesLeft());
    }
    public void testNumPlayerTokensOnBoard(){
        assertEquals(0, player1.getPiecesOnBoard());
        assertEquals(0, player2.getPiecesOnBoard());
        player1.incrementPiecesOnBoard();
        player2.incrementPiecesOnBoard();
        assertEquals(1, player1.getPiecesOnBoard());
        assertEquals(1, player2.getPiecesOnBoard());
    }
}
