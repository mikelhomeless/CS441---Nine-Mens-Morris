package game.board;

import game.logic.PlayerToken;
import java.util.*;

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

    public Board() {
        for (int i = 0; i < numberOfCells(); i++){
            cells.add(new Cell());
        }

        // Once finished creating the board, call the setup function to get other attributes of the board
        setUp();
    }

    protected void addAdjacentCells(int cellIndx, Integer[] cells) {
        this.cells.get(cellIndx).addAdjacentCells(cells);
    }

    /**
     * Returns an array of PlayerTokens stored within each cell on the board
     * NOTE: This function does not return the individual cells of the board, just the PlayerTokens stored within them
     *
     * @return PlayerToken[]
     */
    public PlayerToken[] getBoard() {
        PlayerToken[] board_rep = new PlayerToken[24];
        for (int i = 0; i < cells.size(); i++) {
            board_rep[i] = cells.get(i).getPlayer();
        }
        return board_rep;
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
     * Retrieve a specific cell on the board
     * NOTE: Cells are returned as a reference, any changes you make to the cell will be reflected on the board
     *
     * @param index
     * @return Cell
     */
    public Cell getCell(int index) {
        return cells.get(index);
    }

    public List<Integer> getCellsAsIndexOccupiedBy(PlayerToken player) {
        List<Integer> ownedCells = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
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
     * @return (Array)List of cells occupied by the given player
     */
    public List<Cell> getCellsOccupiedBy(PlayerToken player) {
        List<Cell> ownedCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.isOccupiedBy(player))
                ownedCells.add(cell);
        }
        return ownedCells;
    }

    public int getCount(PlayerToken player) {
        int cnt = 0;
        for (Cell cell : cells) {
            if (cell.isOccupiedBy(player))
                cnt++;
        }
        return cnt;
    }

    public boolean moveFromTo(int srcIndex, int destIndex, PlayerToken player) {
        if (getCell(destIndex).isOccupied())
            return false;
        try {
            setCell(destIndex, player); //set dest = source cell, get index, make sure both indices exist
            setCell(srcIndex, PlayerToken.NOPLAYER);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            return false;
        }
        return true;
    }

    public boolean removePieceFromCell(int index) {
        if (cells.get(index).isEmpty()) {
            return false;
        }
        cells.get(index).setPlayer(PlayerToken.NOPLAYER);
        return true;
    }

    public boolean isCellInMill(int index) {
        for (Mill mill : cells.get(index).getMillCombinations()) {
            if (mill.isMillFormed())
                return true;
        }
        return false;
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

    protected void setMillCombination(int one, int two, int three) {
        Mill newMill = new Mill(cells.get(one), cells.get(two), cells.get(three));
        millCombinations.add(newMill);
        cells.get(one).addMillCombination(newMill);
        cells.get(two).addMillCombination(newMill);
        cells.get(three).addMillCombination(newMill);
    }
}
