package GUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;


class Menu extends JFrame implements ActionListener {

    /*
    This is the main class of the program. Everything runs from here. The main menu is launched initializing the needed variables to access
    the rest of the code. 
    */

    //creates all variables and settings for main menu
    public Board boardInstance;
    public Rules rulesInstance;
    private JButton new_game = new JButton("New Game");
    private JButton settings = new JButton ("Settings");
    private JButton rules = new JButton("Rules");
    private JTextField title_card = new JTextField("Nine Men's Morris");
    private Font myFont = new Font("Serif", Font.BOLD, 60);
    private Image background;
    private BufferedImage Jbackground;
    private Timer nvidiaTimer, mainMenuTimer;
    private JFrame frame;

    private Menu() throws IOException {
        /*edits settings for initialized variables, media tracker allows the background to be painted, nvdia timer knows when to cut off
        the nvida logo and change background, mainMenuTimer allows for the main menu to initialize its main components.
        */

        frame = new JFrame();
        MediaTracker mt = new MediaTracker(this);
        nvidiaTimer = new Timer(3950, this);
        mainMenuTimer = new Timer( 1000, this);
        setDefaultLookAndFeelDecorated(false);

        //creates settings for buttons and text fields
        title_card.setBounds(155, 80, 500, 50);
        title_card.setFont(myFont);
        title_card.setOpaque(true);
        new_game.setBounds(351, 150, 150, 50);
        settings.setBounds(351, 230, 150, 50);
        rules.setBounds(351, 310, 150, 50);

        //creates action listeners for not only the timers but for the buttons as well
        new_game.addActionListener(this);
        settings.addActionListener(this);
        rules.addActionListener(this);
        mainMenuTimer.addActionListener(this);
        nvidiaTimer.addActionListener(this);

        //paints the initial background image onto the frame
        try {
            Jbackground = ImageIO.read(new File("src/GUI/Textures/Battlefield.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });

        //adds settings for the frame and timers
        frame.setSize(826, 465);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setContentPane(new ImagePanel(Jbackground));
        nvidiaTimer.setRepeats(false);
        mainMenuTimer.setRepeats(false);
        nvidiaTimer.start();
        mainMenuTimer.start();
    }


    //using the same listener, different actions are performed based on which timer/button is received. s
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == new_game){
            boardInstance = new Board();
        }
        if(e.getSource() == rules){
            rulesInstance = new Rules();
        }
        /*
        if(e.getSource() == nvidiaTimer){
            try {
                Jbackground = ImageIO.read(new File("src/GUI/Textures/black_background.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.repaint();
        }
         */
        if(e.getSource() == mainMenuTimer){
            /*
            try {
                Jbackground = ImageIO.read(new File("src/GUI/Textures/Battlefield.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
             */
            frame.add(title_card);
            frame.add(new_game);
            frame.add(settings);
            frame.add(rules);
            frame.repaint();
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


    public void paintComponents(Graphics g){
        super.paintComponents(g);
        g.drawImage(Jbackground, 0, 30, this);
    }

    //the main function to initialize the program
    public static void main(String args[]) throws IOException {
        new Menu();
    }
}
