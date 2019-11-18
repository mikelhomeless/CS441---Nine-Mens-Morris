package game.board;

public class NineMensMorris extends Board {
    /*
     * This constructor builds a board with empty cells
     * The board is an array of cells where each index corresponds to a specific position on the board,
     * indexed from 0 to 23
     *
     * 0 - - - - - - - - 1 - - - - - - - - 2
     * |                 |                 |
     * |     3 - - - - - 4 - - - - - 5     |
     * |     |           |           |     |
     * |     |     6 - - 7 - - 8     |     |
     * |     |     |           |     |     |
     * 9 - - 10- - 11         12 - - 13- - 14
     * |     |     |           |     |     |
     * |     |     15- - 16- - 17    |     |
     * |     |           |           |     |
     * |     18- - - - - 19- - - - - 20    |
     * |                 |                 |
     * 21- - - - - - - - 22- - - - - - - - 23
     */

    // This function is provided so that the parent class knows how many cells to set up
    protected int numberOfCells() {
        return 24;
    }

    protected void setUp() {
        // Set up Adjacencies
        addAdjacentCells(0, new Integer[]{1, 9});
        addAdjacentCells(1, new Integer[]{2, 4});
        addAdjacentCells(2, new Integer[]{1, 14});
        addAdjacentCells(3, new Integer[]{4, 10});
        addAdjacentCells(4, new Integer[]{1, 3, 5, 7});
        addAdjacentCells(5, new Integer[]{4, 13});
        addAdjacentCells(6, new Integer[]{7, 11});
        addAdjacentCells(7, new Integer[]{4, 6, 8});
        addAdjacentCells(8, new Integer[]{7, 12});
        addAdjacentCells(9, new Integer[]{0, 21});
        addAdjacentCells(10, new Integer[]{3, 9, 11, 18});
        addAdjacentCells(11, new Integer[]{6, 10, 15});
        addAdjacentCells(12, new Integer[]{8, 13, 17});
        addAdjacentCells(13, new Integer[]{5, 12, 14, 20});
        addAdjacentCells(14, new Integer[]{2, 13, 23});
        addAdjacentCells(15, new Integer[]{11, 16});
        addAdjacentCells(16, new Integer[]{15, 17, 19});
        addAdjacentCells(17, new Integer[]{12, 16});
        addAdjacentCells(18, new Integer[]{10, 19});
        addAdjacentCells(19, new Integer[]{16, 18, 20, 22});
        addAdjacentCells(20, new Integer[]{13, 19});
        addAdjacentCells(21, new Integer[]{9, 22});
        addAdjacentCells(22, new Integer[]{19, 21, 23});
        addAdjacentCells(23, new Integer[]{14, 22});

        /* Horizontal Mills */
        setMillCombination(0, 1, 2);
        setMillCombination(3, 4, 5);
        setMillCombination(6, 7, 8);
        setMillCombination(9, 10, 11);
        setMillCombination(12, 13, 14);
        setMillCombination(15, 16, 17);
        setMillCombination(18, 19, 20);
        setMillCombination(21, 22, 23);
        /* Vertical Mills */
        setMillCombination(0, 9, 21);
        setMillCombination(1, 4, 7);
        setMillCombination(2, 14, 23);
        setMillCombination(3, 10, 18);
        setMillCombination(6, 11, 15);
        setMillCombination(8, 12, 17);
        setMillCombination(5, 13, 20);
        setMillCombination(16, 19, 22);
    }
}
