package GUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.JFrame;

class Board extends JFrame implements ActionListener{

    //JButton b1;
    private Image background;
    private int player_turn = 0;

    private Button[] buttonsArray = new Button[24];

    Board(){
        MediaTracker mt = new MediaTracker(this);

        setDefaultLookAndFeelDecorated(true);
        //b1 = new RoundButton("B1");
        Button new_game = new Button("New Game");
        Button settings = new Button("Settings");

        background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Board.png");

        settings.setBounds(150, 200, 80, 30);

        for(int x = 0; x < buttonsArray.length; x++){
            buttonsArray[x] = new Button("");
            buttonsArray[x].addActionListener(this);
            add(buttonsArray[x]);
        }

        setButtonBounds();

        mt.addImage(background, 0);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });

        setDefaultLookAndFeelDecorated(true);
        setSize(1030, 1050);
        setLayout(null);
        setVisible(true);
    }

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

    private int playerTurn(int player_turn){
        if(player_turn == 0){
            return 1;
        }
        else{
            return 0;
        }
    }

    public void paint(Graphics g){
        if(background != null)
        g.drawImage(background, 0, 30, this);
        else
        g.clearRect(0, 0, getSize().width, getSize().height);
    }

}