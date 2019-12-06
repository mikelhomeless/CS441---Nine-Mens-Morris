package game.Agent;
import game.board.*;
import game.logic.GameManager;
import game.logic.PlayerToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AI
{
    class Move{
        public int src;
        public int dest;
        public int weight = 0;

        public void perform(){
            if (board.getCell(src).isEmpty())
                board.moveFromTo(src, dest);
        }

        public void undo(){
            if (board.getCell(src).isEmpty())
                board.moveFromTo(dest, src);
        }
    }

    class Placement extends Move {
        public void perform(){
            board.setCell(dest, PlayerToken.PLAYER2);
        }

        public void undo(){
            board.setCell(dest, PlayerToken.NOPLAYER);
        }
    }

    class MoveComparator implements Comparator<Move> {
        public int compare(Move m1, Move m2) {
            if (m1.weight < m2.weight)
                return 1;
            if (m1.weight > m2.weight)
                return -1;
            return 0;
        }
    }

    class RemoveCandidate {
        public int location;
        public int weight = 0;
    }

    class RemoveComparator implements Comparator<RemoveCandidate> {
        public int compare(RemoveCandidate r1, RemoveCandidate r2) {
            if (r1.weight < r2.weight)
                return 1;
            if(r1.weight > r2.weight)
                return -1;
            return 0;
        }
    }

    public Board board;
    public GameManager gameManager;
    GUI.Board gui;

    public AI(Board board, GameManager gameManager, GUI.Board gui){
        this.board = board;
    }

    public void dewIT() throws InterruptedException {
        GameManager.GameState gameState = gameManager.getCurrentGameState();
        if (gameState == GameManager.GameState.PLACEMENT){
            Move m = selectMove();
            gameManager.placePiece(m.dest);
            gui.buttonsArray[m.dest].doClick();
        }
        if (gameState == GameManager.GameState.MOVEMENT){
            Move m = selectMove();
            gameManager.move(m.src, m.dest);
            gui.buttonsArray[m.src].doClick();
            Thread.sleep(1000);
            gui.buttonsArray[m.dest].doClick();
        }
        if (gameState == GameManager.GameState.ELIMINATION){
            int index = selectRemoval();
            gameManager.removePiece(index);
            gui.buttonsArray[index].doClick();
        }
    }

    private boolean blocksOpponentFromMill(Move m){
        return createsMill(m.src, PlayerToken.PLAYER1);
    }

    private Move selectMove(){
        List<Move> moves = getAvailableMoves();
        PriorityQueue<Move> pQueue = new PriorityQueue<>(20, new MoveComparator());
        for(Move move : moves) {
            if (createsMill(move))
                move.weight += 3;
            if (blocksOpponentFromMill(move))
                move.weight += 2;
            if (createsMillNextTurn(move))
                move.weight += 1;

            pQueue.add(move);
        }

        return pQueue.peek();
    }

    private int selectRemoval(){
        List<RemoveCandidate> removeablePieces = getRemoveablePieces();
        PriorityQueue<RemoveCandidate> pQueue = new PriorityQueue<>(12, new RemoveComparator());
        for(RemoveCandidate piece : removeablePieces) {
            if (board.isCellInSemiMill(piece.location, PlayerToken.PLAYER1))
                piece.weight += 1;
            if (removalGivesChanceForMill(piece))
                piece.weight += 1;
            pQueue.add(piece);
        }
        return pQueue.peek().location;
    }

    private List<Move> getAvailableMoves(){
        List<Move> moves = new ArrayList<>();
        if (gameManager.getCurrentGameState() == GameManager.GameState.PLACEMENT) {
            List<Integer> cellIndexes = board.getCellsAsIndexOccupiedBy(PlayerToken.NOPLAYER);
            for(Integer indx : cellIndexes) {
                Placement m = new Placement();
                m.dest = indx;
                moves.add(m);
            }
        }

        else {
            List<Integer> cells = board.getCellsAsIndexOccupiedBy(PlayerToken.PLAYER2);
            for(Integer c : cells) {
                Cell cell = board.getCell(c);
                for(int indx : cell.getAdjacentCells()){
                    if (board.getCell(indx).isEmpty()) {
                        Move m = new Move();
                        m.src = c;
                        m.dest = indx;
                        moves.add(m);
                    }
                }
            }
        }
        return moves;
    }

    private List<RemoveCandidate> getRemoveablePieces(){
        List<Integer> opponentIndexes = board.getCellsAsIndexOccupiedBy(PlayerToken.PLAYER1);
        List<RemoveCandidate> candidates = new ArrayList<>();
        for(int indx : opponentIndexes){
            if (gameManager.canRemovePiece(indx)){
                RemoveCandidate c = new RemoveCandidate();
                c.location = indx;
                candidates.add(c);
            }
        }
        return candidates;
    }

    private boolean createsMill(Move m){
        boolean makesMill = false;
        m.perform();
        makesMill = board.isCellInMill((m.dest));
        m.undo();
        return makesMill;
    }

    private boolean createsMill(int dest, PlayerToken p){
        boolean makesMill = false;
        board.setCell(dest, p);
        makesMill = board.isCellInMill(dest);
        board.setCell(dest, PlayerToken.NOPLAYER);
        return makesMill;
    }

    private boolean createsMillNextTurn(Move m){
        m.perform();
        boolean millNextTurn = board.isCellInSemiMill(m.dest, PlayerToken.PLAYER2);
        m.undo();
        return millNextTurn;
    }

    private boolean removalGivesChanceForMill(RemoveCandidate r){
        board.setCell(r.location, PlayerToken.PLAYER2);
        boolean chanceForMill = board.isCellInMill(r.location);
        board.setCell(r.location, PlayerToken.PLAYER2);
        return chanceForMill;
    }
}
