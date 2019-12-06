package game.logic;
import game.board.*;
import game.Config;
import java.util.List;

public class GameManager {
    public enum GameState {PLACEMENT, MOVEMENT, ELIMINATION, END, PLAYER1_WIN, PLAYER2_WIN}

    private Config gameConfig;
    private GameState gameState;
    private Player player1, player2, activePlayer;

    public GameManager(Config gameConfig) {
        this.gameConfig = gameConfig;
        this.player1 = new Player(PlayerToken.PLAYER1, gameConfig.piecesPerPlayer);
        this.player2 = new Player(PlayerToken.PLAYER2, gameConfig.piecesPerPlayer);
        this.activePlayer = player1;
        this.gameState = GameState.PLACEMENT;
    }

    /**
     * Obtain the player who's turn it is
     * @return player token of the currently active player
     */
    public PlayerToken getActivePlayer() { return activePlayer.getPlayerToken(); }

    /**
     * Return the current state of the game
     * @return GameState enum
     */
    public GameState getCurrentGameState() { return gameState; }

    /**
     * checks that it is currently phase one of the game (Piece placement phase)
     * @return true if it is still phase one, false otherwise
     */
    public boolean isPhaseOne() { return !isPhaseOneOver(); }

    /**
     * Checks if phase one is over (no longer piece placement)
     * @return true if phase one is over, false otherwise
     */
    public boolean isPhaseOneOver() {
        return player1.getPiecesOnBoard() == player1.getNumPieces()
            && player2.getPiecesOnBoard() == player2.getNumPieces();
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
        if (!gameConfig.gameBoard.getCell(srcIndex).isOccupiedBy(getActivePlayer()) || isPhaseOne())
            return false;

        // Move anywhere if able to fly
        if (gameConfig.gameBoard.getCount(activePlayer.getPlayerToken()) <= 3)
            return gameConfig.gameBoard.moveFromTo(srcIndex, destIndex);

        // Otherwise must be adjacent to move
        if (gameConfig.gameBoard.getCell(srcIndex).isAdjacentTo(destIndex))
            return gameConfig.gameBoard.moveFromTo(srcIndex, destIndex);

        return false;
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
            activePlayer = (activePlayer == player1) ? player2 : player1;
            winCheck();
        }
        return activePlayer.getPlayerToken();
    }

    /**
     * place a piece on the board at the beginning of the game
     *
     * @param index
     * @return true if the piece was replaced, false otherwise
     */
    public boolean placePiece(int index) {
        if (gameConfig.gameBoard.getCell(index).isEmpty() && isPhaseOne()) {
            gameConfig.gameBoard.setCell(index, activePlayer.getPlayerToken());
            activePlayer.incrementPiecesOnBoard();
            successfulMove(index);
            return true;
        }
        return false;
    }

    /**
     * Remove a player's piece from the board
     *
     * @param index
     * @return true if the piece was removed, false otherwise
     */
    public boolean removePiece(int index) {
        if (activePlayer.getPlayerToken() == gameConfig.gameBoard.getCell(index).getPlayer())
            return false;

        if (canRemovePiece(index) && gameConfig.gameBoard.removePieceFromCell(index)) {
            if (activePlayer == player1)
                player2.decrementPiecesLeft();
            else
                player1.decrementPiecesLeft();

            // If it is phase one, we should return to placement state
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
    private boolean canMove(Player player) {
        if (player.getPiecesLeft() <= 3 || isPhaseOne())
            return true;

        List<Cell> ownedCells = gameConfig.gameBoard.getCellsOccupiedBy(player.getPlayerToken());
        for (Cell cell : ownedCells) {
            for (int indx : cell.getAdjacentCells()) {
                if (gameConfig.gameBoard.getCell(indx).isEmpty())
                    return true;
            }
        }
        return false;
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
    public boolean canRemovePiece(int index) {
        PlayerToken owner = gameConfig.gameBoard.getCell(index).getPlayer();
        if (gameConfig.gameBoard.isCellInMill(index)) {
            List<Integer> ownedCells = gameConfig.gameBoard.getCellsAsIndexOccupiedBy(owner);
            for (Integer indx : ownedCells) {
                if (!gameConfig.gameBoard.isCellInMill(indx)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * PRIVATE: Sets the game state to ELIMINATION if a mill exists on the given cell otherwise sets state to
     * Placement or Movement depending on what phase it is
     *
     * @param placedIndex
     */
    private void successfulMove(int placedIndex) {
        if (gameConfig.gameBoard.isCellInMill(placedIndex))
            gameState = GameState.ELIMINATION;
        else if (isPhaseOne())
            gameState = GameState.PLACEMENT;
        else if (isPhaseOneOver())
            gameState = GameState.MOVEMENT;
    }

    /**
     * Checks to see if a player has won the game and if a player won, updates the gamestate
     * GameState Outcomes: PLAYER1_WIN, PLAYER2_WIN, END (tie), no change (nobody won or lost)
     */
    private void winCheck() {
        boolean player1HasMoves = canMove(player1);
        boolean player2HasMoves = canMove(player2);
        if ((player1HasMoves && !player2HasMoves) || player2.getPiecesLeft() < 3)
            gameState = GameState.PLAYER1_WIN;
        else if ((!player1HasMoves && player2HasMoves) || player1.getPiecesLeft() < 3)
            gameState = GameState.PLAYER2_WIN;
        else if (!player1HasMoves && !player2HasMoves)
            gameState = GameState.END;
    }

    public List<PlayerToken> getBoardAsPlayerTokens() {
        return gameConfig.gameBoard.getBoard();
    }

    // Mostly exposed for testing purposes but can be used other places if needed
    public int getPiecesLeft(PlayerToken player) {
        return (player == PlayerToken.PLAYER1) ? player1.getPiecesLeft() : player2.getPiecesLeft();
    }
}
