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

  /***** SPRINT 2 TESTS *****/
  public void testCannotMoveWhenDestinationOccupied() {   //player 1 move invalid when occupied
      playerLogic.placePlayerPiece(5);
      playerLogic.placePlayerPiece(4);
      assertFalse(playerLogic.move(4, 5));
  }

  public void testFlyLessThanOrEq3() {   //player 2 ability to fly
      Player player1 = new Player(PlayerToken.PLAYER1, 3);
      Player player2 = new Player(PlayerToken.PLAYER2, 3);
      PlayerLogic playerLogic = new PlayerLogic(player1, player2);
      playerLogic.placePlayerPiece(0);
      playerLogic.placePlayerPiece(1);
      playerLogic.placePlayerPiece(2);
      playerLogic.nextTurn();
      playerLogic.placePlayerPiece(3);
      playerLogic.placePlayerPiece(4);
      playerLogic.placePlayerPiece(5);
      assertTrue(playerLogic.move(3, 6));
  }

    public void testP1CantMoveP2() {   //player cannot move
        playerLogic.nextTurn();
        playerLogic.placePlayerPiece(0);
        playerLogic.nextTurn();
        assertFalse(playerLogic.move(0,1));
    }

    public void testCanMoveIfAdjacent() {
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 0);  //set pieces to 0 so phase one is registered as over
        PlayerLogic playerLogic = new PlayerLogic(player1, player2);
        playerLogic.placePlayerPiece(0);
        playerLogic.placePlayerPiece(7);
        playerLogic.placePlayerPiece(6);
        playerLogic.placePlayerPiece(2);
        assertTrue(playerLogic.move(0,1));
    }

    public void testCantMoveIfNotAdjacent() {   //cannot move if the index is not adjacent and pieces > 3
        Player player1 = new Player(PlayerToken.PLAYER1, 4);
        Player player2 = new Player(PlayerToken.PLAYER2, 0);  //set pieces to 0 so phase one is registered as over
        PlayerLogic playerLogic = new PlayerLogic(player1, player2);
        playerLogic.placePlayerPiece(0);
        playerLogic.placePlayerPiece(7);
        playerLogic.placePlayerPiece(6);
        playerLogic.placePlayerPiece(2);
        assertFalse(playerLogic.move(0,8));
    }
}