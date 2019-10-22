package game.logic;

public class Board {
    public static int NUMBEROFCELLS = 24;
    Cell[] cells = new Cell[NUMBEROFCELLS];

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
        for(int i = 0; i < NUMBEROFCELLS; i++){
            cells[i] = new Cell();
        }
    }

    /**
     * Returns an array of PlayerTokens stored within each cell on the board
     * NOTE: This function does not return the individual cells of the board, just the PlayerTokens stored within them
     *
     * @return PlayerToken[]
     */
    public PlayerToken[] getBoard(){
        PlayerToken[] board_rep = new PlayerToken[24];
        for(int i = 0; i < NUMBEROFCELLS; i++){
            board_rep[i] = cells[i].getPlayer();
        }
        return board_rep;
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

    /**
     * Sets the PlayerToken of a specific cell on the board
     *
     * @param index Integer location of the cell
     * @param player PlayerToken for the desired player to place
     */
    public void setCell(int index, PlayerToken player){
        cells[index].setPlayer(player);
    }

    // main method for when this class is being executed as main
    public static void main(String[] args){
        Board board = new Board();
        board.setCell(0, PlayerToken.PLAYER1);
        board.setCell(20, PlayerToken.PLAYER2);
        board.setCell(22, PlayerToken.PLAYER1);
        board.getCell(1).setPlayer(PlayerToken.PLAYER2);
        PlayerToken[] board_rep = board.getBoard();
        for(int i = 0; i < board_rep.length; i++) {
            System.out.printf("%d, %s \n", i, board_rep[i]);
        }
    }
}