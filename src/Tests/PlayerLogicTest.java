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

  public void testPlayerCantRemoveOwnPiece() {
    playerLogic.placePlayerPiece(5);
    assertFalse(playerLogic.removePiece(5));
    playerLogic.nextTurn();

    playerLogic.placePlayerPiece(7);
    assertFalse(playerLogic.removePiece(7));
  }

  public void testPiecesDecrementedAfterRemoved(){
    Player player1 = new Player(PlayerToken.PLAYER1, 9);
    Player player2 = new Player(PlayerToken.PLAYER2, 9);
    playerLogic = new PlayerLogic(player1, player2);

    playerLogic.placePlayerPiece(6);
    playerLogic.nextTurn();

    assertTrue(playerLogic.removePiece(6));
    assertEquals(0, player1.getPiecesOnBoard());
  }


}
