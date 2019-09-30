package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;

class Board extends JFrame implements ActionListener{

    //JButton b1;
    TextField title_field;
    Image background;
    int player_turn = 0;


    Button b0 = new Button("");
    Button b1 = new Button("");
    Button b2 = new Button("");
    Button b3 = new Button("");
    Button b4 = new Button("");
    Button b5 = new Button("");
    Button b6 = new Button("");
    Button b7 = new Button("");
    Button b8 = new Button("");
    Button b9 = new Button("");
    Button b10 = new Button("");
    Button b11 = new Button("");
    Button b12 = new Button("");
    Button b13 = new Button("");
    Button b14 = new Button("");
    Button b15 = new Button("");
    Button b16 = new Button("");
    Button b17 = new Button("");
    Button b18 = new Button("");
    Button b19 = new Button("");
    Button b20 = new Button("");
    Button b21 = new Button("");
    Button b22 = new Button("");
    Button b23 = new Button("");

    Board(){
        MediaTracker mt = new MediaTracker(this);

        setDefaultLookAndFeelDecorated(true);
        //b1 = new RoundButton("B1");
        Button new_game = new Button("New Game");
        Button settings = new Button("Settings");

        background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Board.png");
        title_field = new TextField();
        
        title_field.setBounds(30, 50, 80, 30);
        new_game.setBounds(150, 150, 80, 30);
        settings.setBounds(150, 200, 80, 30);

        setButtonBounds();

        b0.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b10.addActionListener(this);
        b11.addActionListener(this);
        b12.addActionListener(this);
        b13.addActionListener(this);
        b14.addActionListener(this);
        b15.addActionListener(this);
        b16.addActionListener(this);
        b17.addActionListener(this);
        b18.addActionListener(this);
        b19.addActionListener(this);
        b20.addActionListener(this);
        b21.addActionListener(this);
        b22.addActionListener(this);
        b23.addActionListener(this);

        mt.addImage(background, 0);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });

        add(b0);
        add(b1);
        add(b2);
        add(b3);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);
        add(b10);
        add(b11);
        add(b12);
        add(b13);
        add(b14);
        add(b15);
        add(b16);
        add(b17);
        add(b18);
        add(b19);
        add(b20);
        add(b21);
        add(b22);
        add(b23);

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

    public void setButtonBounds(){
        b0.setBounds(15, 20, 50, 50);
        b1.setBounds(480, 20, 50, 50);
        b2.setBounds(945, 20, 50, 50);
        b3.setBounds(168, 175, 50, 50);
        b4.setBounds(480, 175, 50, 50);
        b5.setBounds(790, 175, 50, 50);
        b6.setBounds(323, 330, 50, 50);
        b7.setBounds(480, 330, 50, 50);
        b8.setBounds(635, 330, 50, 50);
        b9.setBounds(15, 485, 50, 50);
        b10.setBounds(168, 485, 50, 50);
        b11.setBounds(323, 485, 50, 50);
        b12.setBounds(635, 485, 50, 50);
        b13.setBounds(790, 485, 50, 50);
        b14.setBounds(945, 485, 50, 50);
        b15.setBounds(323, 640, 50, 50);
        b16.setBounds(480, 640, 50, 50);
        b17.setBounds(635, 640, 50, 50);
        b18.setBounds(168, 795, 50, 50);
        b19.setBounds(480, 795, 50, 50);
        b20.setBounds(790, 795, 50, 50);
        b21.setBounds(15, 950, 50, 50);
        b22.setBounds(480, 950, 50, 50);
        b23.setBounds(945, 950, 50, 50);
    }

    public void update(Graphics g){
        paint(g);
    }

    public int playerTurn(int player_turn){
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