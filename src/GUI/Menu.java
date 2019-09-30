package GUI;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;


class Menu extends JFrame implements ActionListener {

    /*
    This is the main class of the program. Everything runs from here. The main menu is launched initializing the needed variables to access
    the rest of the code. 
    */

    //creates all variables and settings for main menu
    private Button new_game = new Button("New Game");
    private Button settings = new Button ("Settings");
    private Label title_card = new Label("Nine Men's Morris");
    private Font myFont = new Font("Serif", Font.BOLD, 60);
    private Image background;
    private Timer nvidiaTimer, mainMenuTimer;

    private Menu(){
        /*edits settings for initialized variables, media tracker allows the background to be painted, nvdia timer knows when to cut off
        the nvida logo and change background, mainMenuTimer allows for the main menu to initialize its main components.
        */
        MediaTracker mt = new MediaTracker(this);
        nvidiaTimer = new Timer(3950, this);
        mainMenuTimer = new Timer( 5000, this);
        setDefaultLookAndFeelDecorated(true);

        //creates settings for buttons and text fields
        title_card.setBounds(155, 80, 500, 50);
        title_card.setFont(myFont);
        new_game.setBounds(351, 150, 150, 50);
        settings.setBounds(351, 230, 150, 50);

        //creates action listeners for not only the timers but for the buttons as well
        new_game.addActionListener(this);
        settings.addActionListener(this);
        mainMenuTimer.addActionListener(this);
        nvidiaTimer.addActionListener(this);

        //paints the initial background image onto the frame
        background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Nvidia.gif");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });

        //adds settings for the frame and timers
        setSize(826, 465);
        setLayout(null);
        setVisible(true);
        nvidiaTimer.setRepeats(false);
        mainMenuTimer.setRepeats(false);
        nvidiaTimer.start();
        mainMenuTimer.start();
    }


    //using the same listener, different actions are performed based on which timer/button is received. s
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == new_game){
            new Board();
        }
        if(e.getSource() == nvidiaTimer){
            background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/black_background.jpg");
        }
        if(e.getSource() == mainMenuTimer){
            background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Battlefield.jpg");
            add(title_card);
            add(new_game);
            add(settings);
        }
    }

    public void update(Graphics g){
        paint(g);
    }

    //draws background images to the frame
    public void paint(Graphics g){
        if(background != null)
            g.drawImage(background, 0, 30, this);
        else
            g.clearRect(0, 0, getSize().width, getSize().height);
    }

    //the main class to initialize the program
    public static void main(String args[]){
        new Menu();
    }
}
