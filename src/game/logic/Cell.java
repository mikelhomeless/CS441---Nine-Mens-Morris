package game.logic;

public class Cell {
    PlayerToken player;

    public Cell(){
        this.player = PlayerToken.NOPLAYER;
    }

    public Cell(PlayerToken player){
        this.player = player;
    }

    public boolean isEmpty(){
        return this.player == PlayerToken.NOPLAYER;
    }

    public boolean isOccupied(){
        return this.player != PlayerToken.NOPLAYER;
    }

    public boolean isOccupiedBy(PlayerToken player_token) {
        return this.player == player_token;
    }

    public void setPlayer(PlayerToken player){
        this.player = player;
    }

    public PlayerToken getPlayer(){
        return this.player;
    }

    /* Main function for if this class is being ran as main. This class should hardly ever be ran as main.
       This is more of a demonstration on how to use the class.
     */
    public static void main(String[] args){
        System.out.println("Hello World");
    }
}
