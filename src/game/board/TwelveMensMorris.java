package game.board;

public class TwelveMensMorris extends Board {

    protected int numberOfCells() {
        return 24;
    }

    protected void setUp() {
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
    }
}
