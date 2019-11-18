package game.board;

import game.logic.Player;
import game.logic.PlayerToken;

import java.util.*;
import java.util.stream.IntStream;

public abstract class Board {
    private List<Cell> cells = new ArrayList<>();
    private List<Mill> millCombinations = new ArrayList<>();

    protected abstract int numberOfCells();

    protected abstract void setUp();

    public class Mill {
        public final Cell[] cells = new Cell[3];

        public Mill(Cell one, Cell two, Cell three) {
            cells[0] = one;
            cells[1] = two;
            cells[2] = three;
        }

        public boolean isMillFormed() {
            PlayerToken player = cells[0].getPlayer();
            return player != PlayerToken.NOPLAYER
                    && this.cells[0].getPlayer() == player
                    && this.cells[1].getPlayer() == player
                    && this.cells[2].getPlayer() == player;
        }
    }

    // Build the board up from the number of cells given.
    // The setUp function is expected to be used to set up adjacencies and mill combinations
    public Board() {
        for (int i = 0; i < numberOfCells(); i++) {
            cells.add(new Cell());
        }

        // Once finished creating the board, call the setup function to get other attributes of the board
        setUp();
    }

    /**
     * Adds a list of cell indexes to the adjacency list of the called upon cell
     *
     * @param cellIndx
     * @param cells
     */
    protected void addAdjacentCells(int cellIndx, Integer[] cells) {
        this.cells.get(cellIndx).addAdjacentCells(cells);
    }

    /**
     * Returns an array of PlayerTokens stored within each cell on the board
     * NOTE: This function does not return the individual cells of the board, just the PlayerTokens stored within them
     *
     * @return List of PlayerTokens
     */
    public List<PlayerToken> getBoard() {
        List<PlayerToken> boardRep = new ArrayList<>();
        cells.forEach(cell -> boardRep.add(cell.getPlayer()));
        return boardRep;
    }

    /**
     * Retrieve a specific cell on the board
     * NOTE: Cells are returned as a reference, any changes you make to the cell will be reflected on the board
     *
     * @param index
     * @return Cell
     */
    public Cell getCell(int index) {
        return cells.get(index);
    }

    /**
     * Searches through the game board to locate all cells the given player occupies
     *
     * @param player
     * @return List of cells occupied by the given player in terms of their indexes on the board
     */
    public List<Integer> getCellsAsIndexOccupiedBy(PlayerToken player) {
        List<Integer> ownedCells = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isOccupiedBy(player)) {
                ownedCells.add(i);
            }
        }
        return ownedCells;
    }

    /**
     * Search through the game board to locate all cells the given player occupies
     *
     * @param player
     * @return List of cells occupied by the given player
     */
    public List<Cell> getCellsOccupiedBy(PlayerToken player) {
        List<Cell> ownedCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.isOccupiedBy(player))
                ownedCells.add(cell);
        }
        return ownedCells;
    }

    /**
     * Get the count of the number of pieces the given player has on the board
     *
     * @param player
     * @return Number of cells occupied by the player
     */
    public int getCount(PlayerToken player) {
        int cnt = 0;
        for (Cell cell : cells) {
            if (cell.isOccupiedBy(player))
                cnt++;
        }
        return cnt;
    }

    /**
     * Search through the game board to locate all cells that are empty
     * <p>
     * NOTE: alias for getCellsOccupiedBy(PlayerToken.NOPLAYER)
     *
     * @return (array)List of all cells that are empty
     */
    public List<Cell> getEmptyCells() {
        return getCellsOccupiedBy(PlayerToken.NOPLAYER);
    }

    /**
     * Check if the given cell is currently located in a mill
     *
     * @param index
     * @return true if in a mill, false otherwise
     */
    public boolean isCellInMill(int index) {
        for (Mill mill : cells.get(index).getMillCombinations()) {
            if (mill.isMillFormed())
                return true;
        }
        return false;
    }

    /**
     * Move a piece from one location to another
     *
     * @param srcIndex
     * @param destIndex
     * @return true if the operation was successfully carried out, false otherwise
     */
    public boolean moveFromTo(int srcIndex, int destIndex) {
        if (cells.get(srcIndex).isEmpty() || cells.get(destIndex).isOccupied())
            return false;

        setCell(destIndex, cells.get(srcIndex).getPlayer());
        setCell(srcIndex, PlayerToken.NOPLAYER);
        return true;
    }

    /**
     * Remove a piece from the board at the given cell index
     *
     * @param index
     * @return true if the piece was removed, false otherwise
     */
    public boolean removePieceFromCell(int index) {
        if (cells.get(index).isEmpty()) {
            return false;
        }
        cells.get(index).setPlayer(PlayerToken.NOPLAYER);
        return true;
    }

    /**
     * Sets the PlayerToken of a specific cell on the board
     *
     * @param index  Integer location of the cell
     * @param player PlayerToken for the desired player to place
     */
    public void setCell(int index, PlayerToken player) {
        cells.get(index).setPlayer(player);
    }

    /**
     * Set a mill combination across three cells (as index)
     *
     * @param one
     * @param two
     * @param three
     */
    protected void setMillCombination(int one, int two, int three) {
        Mill newMill = new Mill(cells.get(one), cells.get(two), cells.get(three));
        millCombinations.add(newMill);
        cells.get(one).addMillCombination(newMill);
        cells.get(two).addMillCombination(newMill);
        cells.get(three).addMillCombination(newMill);
    }
}
