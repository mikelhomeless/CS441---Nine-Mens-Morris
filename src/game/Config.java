package game;

import game.board.Board;
import game.board.NineMensMorris;
import game.board.ThreeMensMorris;
import game.board.TwelveMensMorris;
import game.board.SixMensMorris;

public class Config {
    public final Board gameBoard;
    public final int piecesPerPlayer;
    public final boolean flyingEnabled;

    public Config(Board boardType, boolean flyingEnabled, int piecesPerPlayer){
        this.gameBoard = boardType;
        this.flyingEnabled = flyingEnabled;
        this.piecesPerPlayer = piecesPerPlayer;
    }
    public static Config SixMensMorris() { return new Config (new SixMensMorris(), false, 6); }

    public static Config NineMensMorris(){
        return new Config(new NineMensMorris(), true, 9);
    }

    public static Config ThreeMensMorris() { return new Config( new ThreeMensMorris(), true, 3); }

    public static Config TwelveMensMorris(){
        return new Config(new TwelveMensMorris(), true, 12);
    }

    public static class Builder {
        public Board gameBoard;
        public int piecesPerPlayer;
        public boolean flyingEnabled = false;

        public Config build() {
            return new Config(gameBoard, flyingEnabled, piecesPerPlayer);
        }
    }
}
