package GUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.JFrame;

class Board extends JFrame implements ActionListener{

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

    private Image background;
    private int player_turn = 0;
    private Button[] buttonsArray = new Button[24]; //array of buttons to be accessed by all funcitons within the class

    Board(){
        MediaTracker mt = new MediaTracker(this); //allows background to be added to the frame

        setDefaultLookAndFeelDecorated(true); 
        Button new_game = new Button("New Game");
        Button settings = new Button("Settings");

        background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Board.png"); //grabs image from textures file

        //for loop to create the button instances, add listeners, and add them to the frame
        for(int x = 0; x < buttonsArray.length; x++){ 
            buttonsArray[x] = new Button("");
            buttonsArray[x].addActionListener(this);
            add(buttonsArray[x]);
        }

        setButtonBounds(); //calls the bounds setting to set buttons in place, moved down to save space in constructor

        mt.addImage(background, 0);
        
        //closes window when the exit button is selected
        addWindowListener(new WindowAdapter(){ 
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });

        //settings for the frame
        setDefaultLookAndFeelDecorated(true);
        setSize(1030, 1050);
        setLayout(null);
        setVisible(true);
    }

    //the listener for events from all buttons    
    public void actionPerformed(ActionEvent e) {
        Button b = (Button) e.getSource();
        player_turn = playerTurn(player_turn);
        if(player_turn == 0){
            b.setBackground(Color.RED);
        }
        else{
            b.setBackground(Color.BLUE);
        }
    }

    //sets bounds for individual buttons
    private void setButtonBounds(){
        buttonsArray[0].setBounds(15, 20, 50, 50);
        buttonsArray[1].setBounds(480, 20, 50, 50);
        buttonsArray[2].setBounds(945, 20, 50, 50);
        buttonsArray[3].setBounds(168, 175, 50, 50);
        buttonsArray[4].setBounds(480, 175, 50, 50);
        buttonsArray[5].setBounds(790, 175, 50, 50);
        buttonsArray[6].setBounds(323, 330, 50, 50);
        buttonsArray[7].setBounds(480, 330, 50, 50);
        buttonsArray[8].setBounds(635, 330, 50, 50);
        buttonsArray[9].setBounds(15, 485, 50, 50);
        buttonsArray[10].setBounds(168, 485, 50, 50);
        buttonsArray[11].setBounds(323, 485, 50, 50);
        buttonsArray[12].setBounds(635, 485, 50, 50);
        buttonsArray[13].setBounds(790, 485, 50, 50);
        buttonsArray[14].setBounds(945, 485, 50, 50);
        buttonsArray[15].setBounds(323, 640, 50, 50);
        buttonsArray[16].setBounds(480, 640, 50, 50);
        buttonsArray[17].setBounds(635, 640, 50, 50);
        buttonsArray[18].setBounds(168, 795, 50, 50);
        buttonsArray[19].setBounds(480, 795, 50, 50);
        buttonsArray[20].setBounds(790, 795, 50, 50);
        buttonsArray[21].setBounds(15, 950, 50, 50);
        buttonsArray[22].setBounds(480, 950, 50, 50);
        buttonsArray[23].setBounds(945, 950, 50, 50);
    }

    public void update(Graphics g){
        paint(g);
    }

    //handles player turns to change color based on whose turn it is
    private int playerTurn(int player_turn){
        if(player_turn == 0){
            return 1;
        }
        else{
            return 0;
        }
    }

    //paints the background onto the frame
    public void paint(Graphics g){
        if(background != null)
        g.drawImage(background, 0, 30, this);
        else
        g.clearRect(0, 0, getSize().width, getSize().height);
    }

}