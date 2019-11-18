package game.logic;
import game.board.*;
import java.util.List;

public class GameManager {
    public enum GameState {PLACEMENT, MOVEMENT, ELIMINATION, END, PLAYER1_WIN, PLAYER2_WIN}

    private Board gameBoard = new NineMensMorris();
    private Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    private Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    private Player activePlayer = Player1;
    private GameState gameState = GameState.PLACEMENT;

    public GameManager() {
    }

    public GameManager(Player player1, Player player2) {
        this.Player1 = player1;
        this.Player2 = player2;
        this.activePlayer = Player1;
    }

    /**
     * place a piece on the board at the beginning of the game
     *
     * @param index
     * @return true if the piece was replaced, false otherwise
     */
    public boolean placePiece(int index) {
        if (gameBoard.getCell(index).isEmpty() && isPhaseOne()) {
            gameBoard.setCell(index, activePlayer.getPlayerToken());
            activePlayer.incrementPiecesOnBoard();
            successfulMove(index);
            return true;
        }
        return false;
    }

    /**
     * Obtain the player who's turn it is
     * @return player token of the currently active player
     */
    public PlayerToken getActivePlayer() {   //call this to get current player token
        return activePlayer.getPlayerToken();
    }

    /**
     * Return the current state of the game
     * @return GameState enum
     */
    public GameState getCurrentGameState() {
        return gameState;
    }

    /**
     * Changes the turn of the game. If the current phase is not PLACEMENT or MOVEMENT, then the turn
     * will not be changed and the current active player will be returned. A win check is also performed
     * and the end of every turn
     *
     * @return The player token of the current active player
     */
    public PlayerToken nextTurn() {
        if (gameState == GameState.PLACEMENT || gameState == GameState.MOVEMENT) {
            if (activePlayer == Player1)
                activePlayer = Player2;
            else
                activePlayer = Player1;
            winCheck();
        }
        return activePlayer.getPlayerToken();
    }

    /**
     * Checks if phase one is over (no longer piece placement)
     * @return true if phase one is over, false otherwise
     */
    public boolean isPhaseOneOver() {
        return Player1.getPiecesOnBoard() == Player1.getNumPieces() && Player2.getPiecesOnBoard() == Player2.getNumPieces();
    }

    /**
     * checks that it is currently phase one of the game (Piece placement phase)
     * @return true if it is still phase one, false otherwise
     */
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

        if (gameBoard.getCount(activePlayer.getPlayerToken()) <= 3)
            return gameBoard.moveFromTo(srcIndex, destIndex);

        if (gameBoard.getCell(srcIndex).isAdjacentTo(destIndex))
            return gameBoard.moveFromTo(srcIndex, destIndex);

        return false;
    }

    /**
     * Remove a player's piece from the board
     *
     * @param index
     * @return true if the piece was removed, false otherwise
     */
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

    /**
     * Searches through the board to verify it is legal to removed the piece selected.
     * <p>
     * Conditions for legal removal:
     * IF the piece is not located within a formed mill, then the piece may be removed
     * IF the piece IS in a mill, then it may only be removed given the owner does not have any other pieces on the board
     * that are not located within a formed mill
     *
     * @param index
     * @return true if the removal is legal, false otherwise
     */
    private boolean canRemovePiece(int index) {
        PlayerToken owner = gameBoard.getCell(index).getPlayer();
        if (gameBoard.isCellInMill(index)) {
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
