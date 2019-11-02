package game.logic;
import java.util.*;

public class PlayerLogic {
    public enum GameState {PLACEMENT, MOVEMENT, ELIMINATION, END, PLAYER1_WIN, PLAYER2_WIN};
    private Board gameBoard = new Board();
    private Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    private Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    private Player activePlayer = Player1;
    private GameState gameState = GameState.PLACEMENT;

    public PlayerLogic() {}
    public PlayerLogic(Player player1, Player player2){
        this.Player1 = player1;
        this.Player2 = player2;
        this.activePlayer = Player1;
    }

    public boolean placePlayerPiece(int index) {   // boolean for if player piece can be placed
        if (gameBoard.getCell(index).isEmpty() && isPhaseOne()) {
              gameBoard.setCell(index, activePlayer.getPlayerToken());
              activePlayer.incrementPiecesOnBoard();   // decrements the pieces left for current player
              return true;
        }
        return false;
    }

    public PlayerToken getActivePlayer() {   // call this to get current player token
      return activePlayer.getPlayerToken();
}

    public GameState getCurrentGameState() { return gameState; }

    public PlayerToken nextTurn() {   // finds next turn
        if(activePlayer == Player1)   // if current player token is player 1, then set to player 2, vice versa
        {
            activePlayer = Player2;
        }
        else
        {
            activePlayer = Player1;
        }
        return activePlayer.getPlayerToken();
    }

    // returns true if phase one is over
    public boolean isPhaseOneOver() {
        return Player1.getPiecesOnBoard() == Player1.getNumPieces() && Player2.getPiecesOnBoard() == Player2.getNumPieces();
    }

    // returns true if still in phase one
    public boolean isPhaseOne(){
        return !isPhaseOneOver();
    }

    public PlayerToken[] getBoardAsPlayerTokens(){ return gameBoard.getBoard(); }

    // Called when a player removes an opponent's piece
    public boolean removePiece(int index) {
        if (activePlayer.getPlayerToken() == gameBoard.getCell(index).getPlayer()) {
            return false;
        }
        if (gameBoard.removePieceFromCell(index)) {
            if (activePlayer == Player1)
                Player2.decrementPiecesLeft();
            else
                Player1.decrementPiecesLeft();
            return true;
        }
        return false;
    }

    /**
     * Test if the given player is able to make any moves on the board
     *
     * @param player
     * @return true if a move is possible, false otherwise
     */
    public boolean canMove(Player player) {
        if (player.getPiecesLeft() <= 3 || isPhaseOne())
            return true;

        // Search board for potential moves the player can make, return true as soon as one is found
        List<Cell> ownedCells = gameBoard.getCellsOccupiedBy(player.getPlayerToken());
        for (Cell cell: ownedCells) {
            for (int indx: cell.getAdjacentCells()) {
                Cell neighbor = gameBoard.getCell(indx);
                if (neighbor.isEmpty())
                    return true;
            }
        }
        // No valid move was found
        return false;
    }

    /**
     * Checks to see if a player has won the game and if a player won, updates the gamestate
     * GameState Outcomes: PLAYER1_WIN, PLAYER2_WIN, END (tie), no change (nobody won or lost)
     *
     */
    public void winCheck(){
        boolean player1HasMoves = canMove(Player1);
        boolean player2HasMoves = canMove(Player2);
        if ((player1HasMoves && !player2HasMoves) || Player2.getPiecesLeft() < 3)
            gameState = GameState.PLAYER1_WIN;
        if ((!player1HasMoves && player2HasMoves) || Player1.getPiecesLeft() < 3)
            gameState = GameState.PLAYER2_WIN;
        if (!player1HasMoves && !player2HasMoves)
            gameState = GameState.END;
    }
}
