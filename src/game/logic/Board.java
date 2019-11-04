package game.logic;
import java.util.*;

public class Board {
    public class Mill{
        public final Cell[] cells = new Cell[3];
        public Mill(Cell one, Cell two, Cell three){
            cells[0] = one;
            cells[1] = two;
            cells[2] = three;
        }

        public boolean isMillFormed(){
            PlayerToken player = cells[0].getPlayer();
            return player != PlayerToken.NOPLAYER
                    && this.cells[0].getPlayer() == player
                    && this.cells[1].getPlayer() == player
                    && this.cells[2].getPlayer() == player;
        }
    }

    private static final int NUMBER_OF_CELLS = 24;
    private Cell[] cells = new Cell[NUMBER_OF_CELLS];
    private List<Mill> millCombinations = new ArrayList<>();

    /**
     * This constructor builds a board with empty cells
     * The board is an array of cells where each index corresponds to a specific position on the board,
     * indexed from 0 to 23
     *<blockquote>
     *   0 - - - - - - - - 1 - - - - - - - - 2
     *   |                 |                 |
     *   |     3 - - - - - 4 - - - - - 5     |
     *   |     |           |           |     |
     *   |     |     6 - - 7 - - 8     |     |
     *   |     |     |           |     |     |
     *   9 - - 10- - 11         12 - - 13- - 14
     *   |     |     |           |     |     |
     *   |     |     15- - 16- - 17    |     |
     *   |     |           |           |     |
     *   |     18- - - - - 19- - - - - 20    |
     *   |                 |                 |
     *   21- - - - - - - - 22- - - - - - - - 23
     *</blockquote>
     */
    public Board(){
        // fill up the cells array with empty cells
        for (int i = 0; i < NUMBER_OF_CELLS; i++) { cells[i] = new Cell(); }
        setCellAdjacencies();
        setMills();
    }

    /**
     * Returns an array of PlayerTokens stored within each cell on the board
     * NOTE: This function does not return the individual cells of the board, just the PlayerTokens stored within them
     *
     * @return PlayerToken[]
     */
    public PlayerToken[] getBoard(){
        PlayerToken[] board_rep = new PlayerToken[24];
        for(int i = 0; i < NUMBER_OF_CELLS; i++){
            board_rep[i] = cells[i].getPlayer();
        }
        return board_rep;
    }

    /**
     * Search through the game board to locate all cells the given player occupies
     *
     * @param player
     * @return (Array)List of cells occupied by the given player
     */
    public List<Cell> getCellsOccupiedBy(PlayerToken player){
        List<Cell> ownedCells = new ArrayList<>();
        for (Cell cell: cells) {
            if (cell.isOccupiedBy(player))
                ownedCells.add(cell);
        }
        return ownedCells;
    }

    /**
     * Search through the game board to locate all cells that are empty
     *
     * NOTE: alias for getCellsOccupiedBy(PlayerToken.NOPLAYER)
     *
     * @return (array)List of all cells that are empty
     */
    public List<Cell> getEmptyCells(){
        return getCellsOccupiedBy(PlayerToken.NOPLAYER);
    }

    /**
     * Retrieve a specific cell on the board
     * NOTE: Cells are returned as a reference, any changes you make to the cell will be reflected on the board
     *
     * @param index
     * @return Cell
     */
    public Cell getCell(int index){
        return cells[index];
    }

    public List<Integer> getCellsAsIndexOccupiedBy(PlayerToken player){
        List<Integer> ownedCells = new ArrayList<>();
        for (int i = 0; i < 24; i++){
            if (cells[i].isOccupiedBy(player)){
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
    public List<Cell> getCellsOccupiedBy(PlayerToken player){
        List<Cell> ownedCells = new ArrayList<>();
        for (Cell cell: cells) {
            if (cell.isOccupiedBy(player))
                ownedCells.add(cell);
        }
        return ownedCells;
    }

    public int getCount(PlayerToken player) {
        int cnt = 0;
        for(Cell cell: cells) {
            if(cell.isOccupiedBy(player))
                cnt++;
        }
        return cnt;
    }

    public boolean moveFromTo(int srcIndex, int destIndex, PlayerToken player){
        if (getCell(destIndex).isOccupied())
            return false;
        try {
            setCell(destIndex, player ); //set dest = source cell, get index, make sure both indices exist
            setCell(srcIndex, PlayerToken.NOPLAYER); }
        catch(Exception ArrayIndexOutOfBoundsException)  {
            return false;
        }
        return true;
    }

    public boolean removePieceFromCell(int index) {
        if(cells[index].isEmpty()) {
            return false;
        }
        cells[index].setPlayer(PlayerToken.NOPLAYER);
        return true;
    }

    public boolean isCellInMill(int index){
        for (Mill mill: cells[index].getMillCombinations()){
            if (mill.isMillFormed())
                return true;
        }
        return false;
    }

    /**
     * Sets the PlayerToken of a specific cell on the board
     *
     * @param index Integer location of the cell
     * @param player PlayerToken for the desired player to place
     */
    public void setCell(int index, PlayerToken player){
        cells[index].setPlayer(player);
    }

    public void setCellAdjacencies(){
        this.cells[0].setAdjacentCells(new Integer[]{1,9});
        this.cells[1].setAdjacentCells(new Integer[]{0,2,4});
        this.cells[2].setAdjacentCells(new Integer[]{1,14});
        this.cells[3].setAdjacentCells(new Integer[]{4,10});
        this.cells[4].setAdjacentCells(new Integer[]{1,3,5,7});
        this.cells[5].setAdjacentCells(new Integer[]{4,13});
        this.cells[6].setAdjacentCells(new Integer[]{7,11});
        this.cells[7].setAdjacentCells(new Integer[]{4,6,8});
        this.cells[8].setAdjacentCells(new Integer[]{7,12});
        this.cells[9].setAdjacentCells(new Integer[]{0,21});
        this.cells[10].setAdjacentCells(new Integer[]{3,9,11,18});
        this.cells[11].setAdjacentCells(new Integer[]{6,10,15});
        this.cells[12].setAdjacentCells(new Integer[]{8,13,17});
        this.cells[13].setAdjacentCells(new Integer[]{5,12,14,20});
        this.cells[14].setAdjacentCells(new Integer[]{2,13,23});
        this.cells[15].setAdjacentCells(new Integer[]{11,16});
        this.cells[16].setAdjacentCells(new Integer[]{15,17,19});
        this.cells[17].setAdjacentCells(new Integer[]{12,16});
        this.cells[18].setAdjacentCells(new Integer[]{10,19});
        this.cells[19].setAdjacentCells(new Integer[]{16,18,20,22});
        this.cells[20].setAdjacentCells(new Integer[]{13,19});
        this.cells[21].setAdjacentCells(new Integer[]{9,22});
        this.cells[22].setAdjacentCells(new Integer[]{19,21,23});
        this.cells[23].setAdjacentCells(new Integer[]{14,22});
    }

    public void setMills(){
        /* Horizontal Mills */
        setMillCombination(0,1,2);
        setMillCombination(3,4,5);
        setMillCombination(6,7,8);
        setMillCombination(9,10,11);
        setMillCombination(12,13,14);
        setMillCombination(15,16,17);
        setMillCombination(18,19,20);
        setMillCombination(21,22,23);
        /* Vertical Mills */
        setMillCombination(0,9,21);
        setMillCombination(1,4,7);
        setMillCombination(2,14,23);
        setMillCombination(3,10,18);
        setMillCombination(6,11,15);
        setMillCombination(8,12,17);
        setMillCombination(5,13,20);
        setMillCombination(16,19,22);
    }

    private void setMillCombination(int one, int two, int three){
        Mill newMill = new Mill(cells[one], cells[two], cells[three]);
        millCombinations.add(newMill);
        cells[one].addMillCombination(newMill);
        cells[two].addMillCombination(newMill);
        cells[three].addMillCombination(newMill);
    }

    // main method for when this class is being executed as main
    public static void main(String[] args){
        Board board = new Board();
        board.setCell(0, PlayerToken.PLAYER1);
        board.setCell(20, PlayerToken.PLAYER2);
        board.setCell(22, PlayerToken.PLAYER1);
        board.getCell(1).setPlayer(PlayerToken.PLAYER2);
        board.moveFromTo(1, 2, PlayerToken.PLAYER1);
        PlayerToken[] board_rep = board.getBoard();
        for(int i = 0; i < board_rep.length; i++) {
            System.out.printf("%d, %s \n", i, board_rep[i]);
        }
    }
}
