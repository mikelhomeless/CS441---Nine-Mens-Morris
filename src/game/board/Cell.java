package game.board;

import game.board.Board.Mill;
import game.logic.PlayerToken;

import java.util.ArrayList;
import java.util.Arrays;

public class Cell {
    private ArrayList<Integer> adjacentCells = new ArrayList<>();
    private ArrayList<Mill> millCombinations = new ArrayList<>();
    private PlayerToken player = PlayerToken.NOPLAYER;

    public void addMillCombination(Mill mill) {
        millCombinations.add(mill);
    }

    public ArrayList<Integer> getAdjacentCells() {
        return (ArrayList<Integer>) adjacentCells.clone();
    }

    public ArrayList<Mill> getMillCombinations() {
        return (ArrayList<Mill>) millCombinations.clone();
    }

    public PlayerToken getPlayer() {
        return this.player;
    }

    public boolean isAdjacentTo(int index) {
        return this.adjacentCells.contains(index);
    }

    /**
     * Function to determine if a cell is empty
     *
     * @return boolean: true -> cell empty, false -> occupied
     */
    public boolean isEmpty() {
        return this.player == PlayerToken.NOPLAYER;
    }

    /**
     * Function to determine if a cell is occupied. Logical equivalent !isEmpty()
     *
     * @return boolean: true -> cell is occupied, false -> empty
     */
    public boolean isOccupied() {
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
    public void addAdjacentCells(Integer[] newAdjacentCells) {
        this.adjacentCells.addAll(Arrays.asList(newAdjacentCells));
    }

    /**
     * Set value of player occupying the cell
     *
     * @param player - Player to be placed
     */
    public void setPlayer(PlayerToken player) {
        this.player = player;
    }
}
