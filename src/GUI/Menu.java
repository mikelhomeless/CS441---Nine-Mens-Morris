package GUI;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;


class Menu extends JFrame implements ActionListener {
    private Button new_game = new Button("New Game");
    private Button settings = new Button ("Settings");
    private Label title_card = new Label("Nine Men's Morris");
    private Font myFont = new Font("Serif", Font.BOLD, 60);
    private Image background;
    private Timer nvidiaTimer, mainMenuTimer;

    private Menu(){
        MediaTracker mt = new MediaTracker(this);
        nvidiaTimer = new Timer(4000, this);
        mainMenuTimer = new Timer( 5000, this);
        setDefaultLookAndFeelDecorated(true);

        title_card.setBounds(155, 80, 500, 50);
        title_card.setFont(myFont);
        new_game.setBounds(351, 150, 150, 50);
        settings.setBounds(351, 230, 150, 50);

        new_game.addActionListener(this);
        settings.addActionListener(this);
        nvidiaTimer.addActionListener(this);

        background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Nvidia.gif");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });

        setSize(826, 465);
        setLayout(null);
        setVisible(true);
        nvidiaTimer.setRepeats(false);
        mainMenuTimer.setRepeats(false);
        nvidiaTimer.start();
        mainMenuTimer.start();
    }

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

    public void paint(Graphics g){
        if(background != null)
            g.drawImage(background, 0, 30, this);
        else
            g.clearRect(0, 0, getSize().width, getSize().height);
    }

    public static void main(String args[]){
        new Menu();
    }
}
