package GUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.util.EventListener;
import javax.imageio.ImageIO;
import javax.swing.*;


class Menu extends JFrame implements ActionListener {

    /*
    This is the main class of the program. Everything runs from here. The main menu is launched initializing the needed variables to access
    the rest of the code. 
    */

    //creates all variables and settings for main menu
    private Board boardInstance;
    private Rules rulesInstance;

    private JFrame frame;
    private JButton new_game = new JButton("New Game");
    private JButton settings = new JButton ("Settings");
    private JButton rules = new JButton("Rules");
    private JButton threeMens = new JButton("Three Mens Morris");
    private JButton sixMens = new JButton("Six Mens Morris");
    private JButton nineMens = new JButton("Nine Mens Morris");
    private JButton twelveMens = new JButton("Twelve Mens Morris");
    private JButton backButton = new JButton("Back");
    private JButton small = new JButton("Small");
    private JButton medium = new JButton("Medium");
    private JButton large = new JButton("Large");
    private JCheckBox playerVplayer = new JCheckBox("Player V. Player");
    private JCheckBox playerVcpu = new JCheckBox("Player V. CPU");
    private JComboBox<String> colors1;
    private JComboBox<String> colors2;
    private JLabel title_card = new JLabel("Men's Morris");
    private JLabel board_size = new JLabel("Board Size");
    private JOptionPane warning = new JOptionPane();
    private Font myFont = new Font("Serif", Font.BOLD, 60);
    private Font settingsFont = new Font("Serif", Font.BOLD, 40);
    private BufferedImage Jbackground;

    private boolean player = false;
    private boolean cpu = false;
    private int boardSizeChoice = 1; //0 = small, 1 = medium, 2 = large
    private String[] choices = {"Red", "Orange", "Yellow", "Green", "Blue", "Purple"};
    private String player_one_color = "Red";
    private String player_two_color = "Blue";

    private Menu(){

        frame = new JFrame();

        title_card.setFont(myFont);
        board_size.setFont(settingsFont);
        title_card.setForeground(Color.WHITE);
        board_size.setForeground(Color.WHITE);
        title_card.setOpaque(false);
        board_size.setOpaque(false);
        colors1 = new JComboBox<String>(choices);
        colors2 = new JComboBox<String>(choices);
        colors1.setSelectedIndex(0);
        colors2.setSelectedIndex(4);


        //creates settings for buttons and text fields
        frame.setSize(826, 465);
        title_card.setBounds(250, 80, 500, 50);
        board_size.setBounds(160, 40, 300, 50);
        new_game.setBounds(351, 150, 150, 50);
        settings.setBounds(351, 230, 150, 50);
        rules.setBounds(351, 310, 150, 50);
        threeMens.setBounds(30, 60, 150, 300);
        sixMens.setBounds(230, 60, 150, 300);
        nineMens.setBounds(430, 60, 150, 300);
        twelveMens.setBounds(630, 60, 150, 300);
        playerVplayer.setBounds(430, 390, 130, 20);
        playerVcpu.setBounds(230, 390,  130, 20);
        backButton.setBounds(0, 0, 70, 40);
        small.setBounds(151, 100, 200, 80);
        medium.setBounds(151, 200, 200, 80);
        large.setBounds(151, 300, 200, 80);
        colors1.setBounds(451, 150, 150, 50);
        colors2.setBounds(451, 250, 150, 50);

        //creates action listeners for not only the timers but for the buttons as well
        new_game.addActionListener(this);
        settings.addActionListener(this);
        rules.addActionListener(this);
        threeMens.addActionListener(this);
        sixMens.addActionListener(this);
        nineMens.addActionListener(this);
        twelveMens.addActionListener(this);
        playerVcpu.addActionListener(this);
        playerVplayer.addActionListener(this);
        backButton.addActionListener(this);
        small.addActionListener(this);
        medium.addActionListener(this);
        large.addActionListener(this);
        colors1.addActionListener(this);
        colors2.addActionListener(this);

        addMouseListeners();

        //paints the initial background image onto the frame
        try {
            Jbackground = ImageIO.read(new File("src/GUI/Textures/Battlefield.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        //makes sure frame closes on click of exit button
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });

        //adds settings for the frame and timers
        setDefaultLookAndFeelDecorated(false);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setContentPane(new ImagePanel(Jbackground));
        frame.add(title_card);
        frame.add(new_game);
        frame.add(settings);
        frame.add(rules);
    }


    //using listener for all instances of the menu, also to change the menu when a certain button is chosen.
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == rules){
            rulesInstance = new Rules();
        }
        if(e.getSource() == new_game){
            removeMainMenu();
            addBoardChoice();
            frame.repaint();
        }
        if(e.getSource() == backButton){
            removeSettings();
            removeBoardChoice();
            addMainMenu();
            frame.repaint();
        }
        if(e.getSource() == settings){
            removeMainMenu();
            addSettings();
            frame.repaint();
        }
        if(e.getSource() == small){
            boardSizeChoice = 0;
        }
        if(e.getSource() == medium){
            boardSizeChoice = 1;
        }
        if(e.getSource() == large){
            boardSizeChoice = 2;
        }
        if(e.getSource() == colors1){
            player_one_color = (String)colors1.getSelectedItem();
        }
        if(e.getSource() == colors2){
            player_two_color = (String)colors2.getSelectedItem();
        }
        if(!player && !cpu){
            if(e.getSource() == playerVcpu){
                playerVplayer.setSelected(false);
                cpu = true;
            }
            if(e.getSource() == playerVplayer){
                playerVcpu.setSelected(false);
                player = true;
            }
            if(e.getSource() == threeMens || e.getSource() == sixMens || e.getSource() == nineMens || e.getSource() == twelveMens){
                    warning.showMessageDialog(frame, "Please select player type first", "Player Type Not Selected", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            if(e.getSource() == playerVcpu){
                playerVplayer.setSelected(false);
                player = false;
                cpu = true;
            }
            if(e.getSource() == playerVplayer){
                playerVcpu.setSelected(false);
                cpu = false;
                player = true;
            }
            if (e.getSource() == threeMens) {
                boardInstance = new boardThree();
                boardInstance.setCpu_player(cpu);
            }
            if (e.getSource() == sixMens) {
                boardInstance = new boardSix();
                boardInstance.setCpu_player(cpu);
            }
            if (e.getSource() == nineMens) {
                boardInstance = new Board();
                boardInstance.setCpu_player(cpu);
            }
            if (e.getSource() == twelveMens) {
                boardInstance = new boardTwelve();
                boardInstance.setCpu_player(cpu);
            }
        }
    }

    private void addMouseListeners(){
        small.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                small.setText("Coming Soon");
            }

            public void mouseExited(MouseEvent e){
                small.setText("Small");
            }
        });

        medium.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                medium.setText("Coming Soon");
            }

            public void mouseExited(MouseEvent e){
                medium.setText("Medium");
            }
        });

        large.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                large.setText("Coming Soon");
            }

            public void mouseExited(MouseEvent e){
                large.setText("Large");
            }
        });
    }

    private void addMainMenu(){
        frame.add(title_card);
        frame.add(new_game);
        frame.add(settings);
        frame.add(rules);
    }

    private void addBoardChoice(){
        frame.add(backButton);
        frame.add(playerVplayer);
        frame.add(playerVcpu);
        frame.add(threeMens);
        frame.add(sixMens);
        frame.add(nineMens);
        frame.add(twelveMens);
    }

    private void removeMainMenu(){
        frame.remove(title_card);
        frame.remove(new_game);
        frame.remove(settings);
        frame.remove(rules);
    }

    private void removeBoardChoice(){
        frame.remove(backButton);
        frame.remove(playerVplayer);
        frame.remove(playerVcpu);
        frame.remove(threeMens);
        frame.remove(sixMens);
        frame.remove(nineMens);
        frame.remove(twelveMens);
    }

    private void addSettings(){
        frame.add(board_size);
        frame.add(small);
        frame.add(medium);
        frame.add(large);
        frame.add(backButton);
        frame.add(colors1);
        frame.add(colors2);
    }

    private void removeSettings(){
        frame.remove(board_size);
        frame.remove(small);
        frame.remove(medium);
        frame.remove(large);
        frame.remove(colors1);
        frame.remove(colors2);
    }

    public void update(Graphics g){
        paint(g);
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

    //the main function to initialize the program
    public static void main(String args[]) throws IOException {
        new Menu();
    }
}
