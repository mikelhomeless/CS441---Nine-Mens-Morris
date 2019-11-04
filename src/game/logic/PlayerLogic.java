package game.logic;

import java.util.List;

public class PlayerLogic {
    public enum GameState {PLACEMENT, MOVEMENT, ELIMINATION, END, PLAYER1_WIN, PLAYER2_WIN};
    private Board gameBoard = new Board();
    private Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    private Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    private Player activePlayer = Player1;
    private GameState gameState = GameState.PLACEMENT;

    public PlayerLogic() {
    }

    public PlayerLogic(Player player1, Player player2) {
        this.Player1 = player1;
        this.Player2 = player2;
        this.activePlayer = Player1;
    }

    public boolean placePlayerPiece(int index) {
        if (gameBoard.getCell(index).isEmpty() && isPhaseOne()) {
            gameBoard.setCell(index, activePlayer.getPlayerToken());
            activePlayer.incrementPiecesOnBoard();
            successfulMove(index);
            return true;
        }
        return false;
    }

    public PlayerToken getActivePlayer() {   //call this to get current player token
        return activePlayer.getPlayerToken();
    }

    public GameState getCurrentGameState() {
        return gameState;
    }

    public PlayerToken nextTurn() {
        if (gameState == GameState.PLACEMENT || gameState == GameState.MOVEMENT ) {
            if (activePlayer == Player1)
                activePlayer = Player2;
            else
                activePlayer = Player1;
            winCheck();
        }
        return activePlayer.getPlayerToken();
    }

    // returns true if phase one is over
    public boolean isPhaseOneOver() {
        return Player1.getPiecesOnBoard() == Player1.getNumPieces() && Player2.getPiecesOnBoard() == Player2.getNumPieces();
    }

    // returns true if still in phase one
    public boolean isPhaseOne() {
        return !isPhaseOneOver();
    }

    /**
     * PRIVATE: Sets the game state to ELIMINATION if a mill exists on the given cell otherwise sets state to
     * Placement or Movement depending on what phase it is
     *
     * @param placedIndex
     */
    private void successfulMove(int placedIndex) {
        if (gameBoard.isCellInMill(placedIndex))
            gameState = GameState.ELIMINATION;
        else if (isPhaseOne())
            gameState = GameState.PLACEMENT;
        else if (isPhaseOneOver())
            gameState = GameState.MOVEMENT;
    }

    /**
     * Moves a piece from a source cell to a destination cell. If the piece was moved to the destination cell and
     * the move resulted in a mill, game state will be changed to ELIMINATION.
     *
     * @param srcIndex
     * @param destIndex
     * @return true if the piece was moved, false otherwise
     */
    public boolean move(int srcIndex, int destIndex) {
        if (_move(srcIndex, destIndex)) {
            successfulMove(destIndex);
            return true;
        }

        return false;
    }

    /**
     * PRIVATE: Wrapped function for move(int, int).
     * Checks if a piece can be moved to the desired location, if determined that the piece may be moved then
     * the piece is moved
     *
     * @param srcIndex
     * @param destIndex
     * @return true if piece could be moved, false otherwise
     */
    private boolean _move(int srcIndex, int destIndex) {
        if (!gameBoard.getCell(srcIndex).isOccupiedBy(getActivePlayer()) || isPhaseOne())  //immediately return false if source index player token does not match or if phase one
            return false;
        if (gameBoard.getCount(activePlayer.getPlayerToken()) > 3) {
            if (gameBoard.getCell(srcIndex).isAdjacentTo(destIndex))
                return gameBoard.moveFromTo(srcIndex, destIndex, getActivePlayer());
        } else if (gameBoard.getCount(activePlayer.getPlayerToken()) <= 3) {
            return gameBoard.moveFromTo(srcIndex, destIndex, getActivePlayer());
        }
        return false;
    }

    public PlayerToken[] getBoardAsPlayerTokens() {
        return gameBoard.getBoard();
    }

    // Called when a player removes an opponent's piece
    public boolean removePiece(int index) {
        if (activePlayer.getPlayerToken() == gameBoard.getCell(index).getPlayer())
            return false;
        if (canRemovePiece(index) && gameBoard.removePieceFromCell(index)) {
            if (activePlayer == Player1)
                Player2.decrementPiecesLeft();
            else
                Player1.decrementPiecesLeft();

            // What state the game goes back to depends on what phase of the game it is
            gameState = isPhaseOne() ? GameState.PLACEMENT : GameState.MOVEMENT;
            return true;
        }
        return false;
    }

    /**
     * Test if the given player is able to make any moves on the board. Exits as soon as a valid move is found
     *
     * @param player
     * @return true if a move is possible, false otherwise
     */
    public boolean canMove(Player player) {
        if (player.getPiecesLeft() <= 3 || isPhaseOne())
            return true;

        List<Cell> ownedCells = gameBoard.getCellsOccupiedBy(player.getPlayerToken());
        for (Cell cell : ownedCells) {
            for (int indx : cell.getAdjacentCells()) {
                if (gameBoard.getCell(indx).isEmpty())
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a player has won the game and if a player won, updates the gamestate
     * GameState Outcomes: PLAYER1_WIN, PLAYER2_WIN, END (tie), no change (nobody won or lost)
     */
    public void winCheck() {
        boolean player1HasMoves = canMove(Player1);
        boolean player2HasMoves = canMove(Player2);
        if ((player1HasMoves && !player2HasMoves) || Player2.getPiecesLeft() < 3)
            gameState = GameState.PLAYER1_WIN;
        if ((!player1HasMoves && player2HasMoves) || Player1.getPiecesLeft() < 3)
            gameState = GameState.PLAYER2_WIN;
        if (!player1HasMoves && !player2HasMoves)
            gameState = GameState.END;
    }

    private boolean canRemovePiece(int index) {
        PlayerToken owner = gameBoard.getCell(index).getPlayer();
        if (gameBoard.isCellInMill(index)) { // .isCellInMill  <-- MICHAEL
            List<Integer> ownedCells = gameBoard.getCellsAsIndexOccupiedBy(owner);
            for (Integer indx : ownedCells) {
                if (!gameBoard.isCellInMill(indx)) {
                    return false;
                }
            }
        }
        return true;
    }
}
