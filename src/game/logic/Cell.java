package game.logic;

import java.util.*;


public class Cell {
    private PlayerToken player = PlayerToken.NOPLAYER;
    private ArrayList<Integer> adjacentCells = new ArrayList<>();

    /**
     * Add a single cell index to the list of adjacent cells
     *
     * @param index
     */
    public void addAdjacentCell(Integer index) { this.adjacentCells.add(index); }

    /**
     * Give the adjacency list of all neighboring cell indexes
     * @return ArrayList of cell indexes
     */
    public ArrayList<Integer> getAdjacentCells() { return (ArrayList<Integer>) adjacentCells.clone(); }

    /**
     * Obtain player token of currently occupied player
     *
     * @return Player occupying cell - PlayerToken
     */
    public PlayerToken getPlayer(){
        return this.player;
    }

    /**
     * Public function to test if the cell is adjacent to another cell
     *
     * @param index
     * @return true if the index is in the adjacentCells list, false otherwise
     */
    public boolean isAdjacentTo(int index) { return this.adjacentCells.contains(index); }

    /**
     * Function to determine if a cell is empty
     *
     * @return boolean: true -> cell empty, false -> occupied
     */
    public boolean isEmpty(){
        return this.player == PlayerToken.NOPLAYER;
    }

    /**
     * Function to determine if a cell is occupied. Logical equivalent !isEmpty()
     *
     * @return boolean: true -> cell is occupied, false -> empty
     */
    public boolean isOccupied(){
        return this.player != PlayerToken.NOPLAYER;
    }

    /**
     * Test if a player token exists in the cell
     *
     * @param player_token - Player token to match against - Type PlayerToken
     * @return True: player occupies cell, False: player does not occupy cell
     */
    public boolean isOccupiedBy(PlayerToken player_token) {
        return this.player == player_token;
    }

    /**
     * Append a list of cell indexes to the list of adjacent cells
     *
     * @param newAdjacentCells
     */
    public void setAdjacentCells(Integer[] newAdjacentCells) { this.adjacentCells.addAll(Arrays.asList(newAdjacentCells)); }

    /**
     * Set value of player occupying the cell
     *
     * @param player - Player to be placed
     */
    public void setPlayer(PlayerToken player){
        this.player = player;
    }

    /* Main function for if this class is being ran as main. This class should hardly ever be ran as main.
       This is more of a demonstration on how to use the class.
     */
    public static void main(String[] args){
        Cell testCell = new Cell();
    }
}
