package game.board;

public class ThreeMensMorris extends Board{

    /*
     * This constructor builds a board with empty cells
     * The board is an array of cells where each index corresponds to a specific position on the board,
     * indexed from 0 to 8
     *
     * 0 - - - - - - - - 1 - - - - - - - - 2     <---- All of these are also connected by the diagonal.
     * |                 |                 |
     * |                 |                 |
     * |                 |                 |
     * 3 - - - - - - - - 4 - - - - - - - - 5
     * |                 |                 |
     * |                 |                 |
     * |                 |                 |
     * 6 - - - - - - - - 7 - - - - - - - - 8
     */

    protected int numberOfCells() {return 9; }

    protected void setUp() {
        //Set up adjacencies
        addAdjacentCells(0, new Integer[]{1, 3, 4});
        addAdjacentCells(1, new Integer[]{0, 2, 3, 4, 5});
        addAdjacentCells(2, new Integer[]{1, 4, 5});
        addAdjacentCells(3, new Integer[]{0, 1, 4, 6, 7});
        addAdjacentCells(4, new Integer[]{0, 1, 2, 3, 5, 6, 7, 8});
        addAdjacentCells(5, new Integer[]{1, 2, 4, 7, 8});
        addAdjacentCells(6, new Integer[]{3, 4, 7});
        addAdjacentCells(7, new Integer[]{3, 4, 5, 6, 8});
        addAdjacentCells(8, new Integer[]{4, 5, 7});

        /* Horizontal Mills */
        setMillCombination(0, 1, 2);
        setMillCombination(3, 4, 5);
        setMillCombination(6, 7, 8);
        /* Vertical Mills */
        setMillCombination(0, 3, 6);
        setMillCombination(1, 4, 7);
        setMillCombination(2, 5, 8);
        /* Diagonal Mills*/
        setMillCombination(0, 4, 8);
        setMillCombination(2, 4, 6);
    }
}
