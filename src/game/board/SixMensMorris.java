package game.board;

public class SixMensMorris extends Board {


    protected int numberOfCells(){
        return 16;
    }

    protected void setUp(){
        // set up adjasencies
        addAdjacentCells(0, new Integer[]{1, 6});
        addAdjacentCells(1, new Integer[]{0, 2, 4});
        addAdjacentCells(2, new Integer[]{1, 9});
        addAdjacentCells(3, new Integer[]{4, 7});
        addAdjacentCells(4, new Integer[]{1, 3, 5});
        addAdjacentCells(5, new Integer[]{4, 8});
        addAdjacentCells(6, new Integer[]{0, 7, 13});
        addAdjacentCells(7, new Integer[]{3, 6, 10});
        addAdjacentCells(8, new Integer[]{5, 9, 12});
        addAdjacentCells(9, new Integer[]{2, 8, 15});
        addAdjacentCells(10, new Integer[]{7, 11});
        addAdjacentCells(11, new Integer[]{10, 12, 14});
        addAdjacentCells(12, new Integer[]{8, 11});
        addAdjacentCells(13, new Integer[]{6, 14});
        addAdjacentCells(14, new Integer[]{11, 13, 15});
        addAdjacentCells(15, new Integer[]{9, 14});

        // Horizontal mills
        setMillCombination(0, 1, 2);
        setMillCombination(3, 4, 5);
        setMillCombination(10,11,12);
        setMillCombination(13, 14,15);

        // Vertical mills
        setMillCombination(0, 6, 13);
        setMillCombination(3, 7, 10);
        setMillCombination(5, 8, 12);
        setMillCombination(2, 9, 15);
        
    }

}
