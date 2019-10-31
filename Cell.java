package game.logic;

public class Cell {
    PlayerToken player;

    public Cell(){
        this.player = PlayerToken.NOPLAYER;
    }

    public Cell(PlayerToken player){
        this.player = player;
    }

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
     * Set value of player occupying the cell
     *
     * @param player - Player to be placed
     */
    public void setPlayer(PlayerToken player){
        this.player = player;
    }

    /**
     * Obtain player token of currently occupied player
     *
     * @return Player occupying cell - PlayerToken
     */
    public PlayerToken getPlayer(){
        return this.player;
    }

    /* Main function for if this class is being ran as main. This class should hardly ever be ran as main.
       This is more of a demonstration on how to use the class.
     */
    public static void main(String[] args){
        Cell testCell = new Cell();
    }
}
