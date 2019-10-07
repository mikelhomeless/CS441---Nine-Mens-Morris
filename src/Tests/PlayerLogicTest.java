package Tests;

import game.logic.*;
import junit.framework.TestCase;

public class PlayerLogicTest extends TestCase {
  private PlayerLogic playerLogic;
  protected void setUp() throws Exception{
      super.setUp();
      playerLogic = new PlayerLogic();
  }
  public void testActivePlayerChange(){
      assertEquals(PlayerToken.PLAYER2, playerLogic.nextTurn());
      assertEquals(PlayerToken.PLAYER1, playerLogic.nextTurn());
  }
  public void testStartPlayer1(){
      assertEquals(PlayerToken.PLAYER1, playerLogic.getActivePlayer());
  }
  public void testPhaseOneEnds(){
      for(int x = 0; x < 18; x++){
          playerLogic.placePlayerPiece(x);
          playerLogic.nextTurn();
      }
      assertTrue(playerLogic.isPhaseOneOver());
      assertFalse(playerLogic.isPhaseOne());
  }
  public void testPlacePiece(){
      playerLogic.placePlayerPiece(5);
      assertFalse(playerLogic.placePlayerPiece(5));
  }
  public void testPlacePieceAfterPhaseOne(){
      for(int x = 0; x < 18; x++){
          playerLogic.placePlayerPiece(x);
          playerLogic.nextTurn();
      }
      assertFalse(playerLogic.placePlayerPiece(19));
  }

  public void testPlacedPieceLocationIsCorrect() {
      playerLogic.placePlayerPiece(2);
      playerLogic.nextTurn();

      playerLogic.placePlayerPiece(6);
      assertSame(playerLogic.getBoardAsPlayerTokens()[2], PlayerToken.PLAYER1);
      assertSame(playerLogic.getBoardAsPlayerTokens()[6], PlayerToken.PLAYER2);
  }
}
