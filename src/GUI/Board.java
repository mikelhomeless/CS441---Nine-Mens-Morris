package GUI;
import game.Config;
import game.logic.PlayerToken;
import game.logic.GameManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JFrame implements ActionListener{

    /**
     * This constructor builds a board on the gui using an array of buttons
     * indexed from 0 to 24
     *<blockquote>
     *   0 - - - - - - - - 1 - - - - - - - - 2
     *   |                 |                 |
     *   |     3 - - - - - 4 - - - - - 5     |
     *   |     |           |           |     |
     *   |     |     6 - - 7 - - 8     |     |
     *   |     |     |           |     |     |
     *   9 - - 10- - 11         12 - - 13- - 14
     *   |     |     |           |     |     |
     *   |     |     15- - 16- - 17    |     |
     *   |     |           |           |     |
     *   |     18- - - - - 19- - - - - 20    |
     *   |                 |                 |
     *   21- - - - - - - - 22- - - - - - - - 23
     *</blockquote>
     */

    private PlayerToken playerToken = PlayerToken.PLAYER1;
    private GameManager.GameState gameState;
    public GameManager players;

    public JFrame frame = new JFrame();
    public BufferedImage Jbackground;
    public JButton[] buttonsArray; //array of buttons to be accessed by all functions within the class

    private boolean moves = false;
    private boolean cpu_player = false;
    private int firstPiece, secondPiece;
    public Color player_one_color;
    public Color player_two_color;

    public Board() {
    }

    Board(boolean cpu, String player_one_string, String player_two_string){
        players = new GameManager(game.Config.NineMensMorris());
        MediaTracker mt = new MediaTracker(this); //allows background to be added to the frame
        buttonsArray = new JButton[24];

        try {
            Jbackground = ImageIO.read(new File("src/GUI/Textures/Nine_Mens_Board.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        //closes window when the exit button is selected
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });


        //settings for the frame
        setDefaultLookAndFeelDecorated(true);
        frame.setTitle("Nine Men's Morris");
        frame.setContentPane(new ImagePanel(Jbackground));
        frame.setSize(1040, 1050);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        for(int x = 0; x < buttonsArray.length; x++){
            buttonsArray[x] = new RoundButton("");
            buttonsArray[x].addActionListener(this);
            frame.add(buttonsArray[x]);
        }
        player_one_color = setPlayerColor(player_one_string);
        player_two_color = setPlayerColor(player_two_string);
        setButtonBounds();//calls the bounds setting to set buttons in place, moved down to save space in constructor
    }

    //sets bounds for individual buttons
    public void setButtonBounds(){
        buttonsArray[0].setBounds(22, 20, 50, 50);
        buttonsArray[1].setBounds(487, 20, 50, 50);
        buttonsArray[2].setBounds(952, 20, 50, 50);
        buttonsArray[3].setBounds(175, 175, 50, 50);
        buttonsArray[4].setBounds(487, 175, 50, 50);
        buttonsArray[5].setBounds(797, 175, 50, 50);
        buttonsArray[6].setBounds(330, 330, 50, 50);
        buttonsArray[7].setBounds(487, 330, 50, 50);
        buttonsArray[8].setBounds(642, 330, 50, 50);
        buttonsArray[9].setBounds(22, 485, 50, 50);
        buttonsArray[10].setBounds(175, 485, 50, 50);
        buttonsArray[11].setBounds(330, 485, 50, 50);
        buttonsArray[12].setBounds(642, 485, 50, 50);
        buttonsArray[13].setBounds(797, 485, 50, 50);
        buttonsArray[14].setBounds(952, 485, 50, 50);
        buttonsArray[15].setBounds(330, 640, 50, 50);
        buttonsArray[16].setBounds(487, 640, 50, 50);
        buttonsArray[17].setBounds(642, 640, 50, 50);
        buttonsArray[18].setBounds(175, 795, 50, 50);
        buttonsArray[19].setBounds(487, 795, 50, 50);
        buttonsArray[20].setBounds(797, 795, 50, 50);
        buttonsArray[21].setBounds(22, 950, 50, 50);
        buttonsArray[22].setBounds(487, 950, 50, 50);
        buttonsArray[23].setBounds(952, 950, 50, 50);
    }

    //the listener for events from all buttons
    public void actionPerformed(ActionEvent e) {
        GameManager.GameState gameState = checkGameState();
        JButton b = (JButton) e.getSource();

        boolean placed = false;
        boolean moved;
        int piece = -1;

        for(int x = 0; x < buttonsArray.length; x++){
            if(b == buttonsArray[x]){
                placed = getButtonPressed(x);
                piece = x;
            }
        }
        if(gameState == GameManager.GameState.PLACEMENT) {
            System.out.println("placed == " + placed);
            if (placed) {
                if (playerToken == PlayerToken.PLAYER1)
                    b.setBackground(player_one_color);
                if (playerToken == PlayerToken.PLAYER2)
                    b.setBackground(player_two_color);
                System.out.println("Player " + playerToken + " placed piece at " + piece + " during PLACEMENT");
                playerToken = players.nextTurn();
            }
        }
        if(gameState == GameManager.GameState.MOVEMENT){
            System.out.println("moves == " + moves);
            if(!moves){
                firstPiece = piece;
                System.out.println("Player " + playerToken + " selected piece to move at " + piece + " during SELECTION");
            }
            if(moves){
                secondPiece = piece;
                moved = getButtonMoved(firstPiece, secondPiece);
                System.out.println("moved == " + moved);
                if (moved) {
                    if (playerToken == PlayerToken.PLAYER1) {
                        b.setBackground(player_one_color);
                    }
                    if (playerToken == PlayerToken.PLAYER2) {
                        b.setBackground(player_two_color);
                    }
                    buttonsArray[firstPiece].setBackground(Color.WHITE);
                    System.out.println("Player " + playerToken + " placed piece at " + piece + " during MOVEMENT");
                    playerToken = players.nextTurn();
                }
            }
            switchMoves();
        }
        if (gameState == GameManager.GameState.ELIMINATION) {
            if(players.removePiece(piece)) {
                b.setBackground(Color.WHITE);
                System.out.println("Player " + playerToken + " removed place piece at " + piece + " during ELIMINATION");
                playerToken = players.nextTurn();
            }
        }
        checkGameState();
    }

    private void switchMoves(){ moves = !moves; }

    public void setCpu_player(boolean cpu_player) {
        this.cpu_player = cpu_player;
    }

    public boolean getCpu_player(){
        return cpu_player;
    }

    public GameManager.GameState getGameState(){
        return this.gameState;
    }

    public GameManager getPlayers(){
        return this.players;
    }

    private boolean getButtonPressed(int b){
        boolean placed;
        placed = players.placePiece(b);
        return placed;
    }

    private boolean getButtonMoved(int b, int c){
        boolean moved;
        moved = players.move(b, c);
        return moved;
    }

    public Color setPlayerColor(String colorS){
        char color = colorS.charAt(0);
        switch (color){
            case 'R':
                return Color.RED;
            case 'O':
                return Color.ORANGE;
            case 'Y':
                return Color.YELLOW;
            case 'G':
                return Color.GREEN;
            case 'B':
                return Color.BLUE;
            case 'P':
                return Color.magenta;
            default:
                return Color.WHITE;
        }
    }

    private GameManager.GameState checkGameState(){
        gameState = players.getCurrentGameState();
        if(gameState == GameManager.GameState.END || gameState == GameManager.GameState.PLAYER1_WIN || gameState == GameManager.GameState.PLAYER2_WIN) {
            String infoMessage;
            switch (gameState) {
                case END:
                    infoMessage = "This was a Tie";
                    break;
                case PLAYER1_WIN:
                    infoMessage = "Player One has Won the Game!!!";
                    break;
                case PLAYER2_WIN:
                    infoMessage = "Player Two has Won the Game!!!";
                    break;
                default:
                    infoMessage = "What?";
            }
            for(int x = 0; x < buttonsArray.length; x++){
                buttonsArray[x].removeActionListener(this::actionPerformed);
            }
            JOptionPane.showMessageDialog(null, infoMessage, "The Game Has Ended", JOptionPane.PLAIN_MESSAGE);
        }
        return gameState;
    }

    //draws background images to the frame
    public void paint(Graphics g){
        if(Jbackground != null)
            g.drawImage(Jbackground, 0, 30, this);
        else
            g.clearRect(0, 0, getSize().width, getSize().height);
    }


    public void paintComponents(Graphics g){
        super.paintComponents(g);
        g.drawImage(Jbackground, 0, 30, this);
    }

}

//---------------------------------------------------------------------------------------------------------------

class boardThree extends Board{

    boardThree(boolean cpu, String player_one_string, String player_two_string){

        players = new GameManager(Config.ThreeMensMorris());
        buttonsArray = new JButton[9];

        try {
            Jbackground = ImageIO.read(new File("src/GUI/Textures/3_Mens_Board.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        //closes window when the exit button is selected
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });

        //settings for the frame
        setDefaultLookAndFeelDecorated(true);
        frame.setContentPane(new ImagePanel(Jbackground));
        frame.setSize(1030, 1050);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Three Men's Morris");
        for(int x = 0; x < buttonsArray.length; x++){
            buttonsArray[x] = new RoundButton("");
            buttonsArray[x].addActionListener(this);
            frame.add(buttonsArray[x]);
        }
        player_one_color = setPlayerColor(player_one_string);
        player_two_color = setPlayerColor(player_two_string);
        setButtonBounds(); //calls the bounds setting to set buttons in place, moved down to save space in constructor
    }

    public void setButtonBounds(){
        buttonsArray[0].setBounds(175, 175, 50, 50);
        buttonsArray[1].setBounds(487, 175, 50, 50);
        buttonsArray[2].setBounds(797, 175, 50, 50);
        buttonsArray[3].setBounds(175, 485, 50, 50);
        buttonsArray[4].setBounds(487, 485, 50, 50);
        buttonsArray[5].setBounds(797, 485, 50, 50);
        buttonsArray[6].setBounds(175, 795, 50, 50);
        buttonsArray[7].setBounds(487, 795, 50, 50);
        buttonsArray[8].setBounds(797, 795, 50, 50);
    }
}

//------------------------------------------------------------------------------------------------------------------------------

class boardSix extends Board{

    boardSix(boolean cpu, String player_one_string, String player_two_string){
        players = new GameManager(Config.SixMensMorris());
        MediaTracker mt = new MediaTracker(this); //allows background to be added to the frame
        buttonsArray = new JButton[16];

        try {
            Jbackground = ImageIO.read(new File("src/GUI/Textures/Six_Mens_Board.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        //closes window when the exit button is selected
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });

        //settings for the frame
        setDefaultLookAndFeelDecorated(true);
        frame.setContentPane(new ImagePanel(Jbackground));
        frame.setSize(1030, 1050);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Six Men's Morris");
        for(int x = 0; x < buttonsArray.length; x++){
            buttonsArray[x] = new RoundButton("");
            buttonsArray[x].addActionListener(this);
            frame.add(buttonsArray[x]);
        }
        player_one_color = setPlayerColor(player_one_string);
        player_two_color = setPlayerColor(player_two_string);
        setButtonBounds(); //calls the bounds setting to set buttons in place, moved down to save space in constructor
    }

    public void setButtonBounds(){
        buttonsArray[0].setBounds(175, 175, 50, 50);
        buttonsArray[1].setBounds(487, 175, 50, 50);
        buttonsArray[2].setBounds(797, 175, 50, 50);
        buttonsArray[3].setBounds(330, 330, 50, 50);
        buttonsArray[4].setBounds(487, 330, 50, 50);
        buttonsArray[5].setBounds(642, 330, 50, 50);
        buttonsArray[6].setBounds(175, 485, 50, 50);
        buttonsArray[7].setBounds(330, 485, 50, 50);
        buttonsArray[8].setBounds(642, 485, 50, 50);
        buttonsArray[9].setBounds(797, 485, 50, 50);
        buttonsArray[10].setBounds(330, 640, 50, 50);
        buttonsArray[11].setBounds(487, 640, 50, 50);
        buttonsArray[12].setBounds(642, 640, 50, 50);
        buttonsArray[13].setBounds(175, 795, 50, 50);
        buttonsArray[14].setBounds(487, 795, 50, 50);
        buttonsArray[15].setBounds(797, 795, 50, 50);
    }
}

//-----------------------------------------------------------------------------------------------------------------

class boardTwelve extends Board{

    boardTwelve(boolean cpu, String player_one_string, String player_two_string){
        players = new GameManager(Config.TwelveMensMorris());
        MediaTracker mt = new MediaTracker(this); //allows background to be added to the frame
        buttonsArray = new JButton[24];

        try {
            Jbackground = ImageIO.read(new File("src/GUI/Textures/Twelve_Mens_Board.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        //closes window when the exit button is selected
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });

        //settings for the frame
        setDefaultLookAndFeelDecorated(true);
        frame.setContentPane(new ImagePanel(Jbackground));
        frame.setSize(1030, 1050);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Twelve Men's Morris");
        for(int x = 0; x < buttonsArray.length; x++){
            buttonsArray[x] = new RoundButton("");
            buttonsArray[x].addActionListener(this);
            frame.add(buttonsArray[x]);
        }
        player_one_color = setPlayerColor(player_one_string);
        player_two_color = setPlayerColor(player_two_string);
        setButtonBounds(); //calls the bounds setting to set buttons in place, moved down to save space in constructor
    }
}
