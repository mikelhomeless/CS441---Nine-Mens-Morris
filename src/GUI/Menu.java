package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Timer;


class Menu extends JFrame implements ActionListener {
    Button new_game = new Button("New Game");
    Button settings = new Button ("Settings");
    Image background;
    Timer nvidiaTimer;

    Menu(){
        MediaTracker mt = new MediaTracker(this);
        nvidiaTimer = new Timer(500, this);
        setDefaultLookAndFeelDecorated(true);

        new_game.setBounds(103, 50, 80, 30);
        settings.setBounds(103, 100, 80, 30);

        new_game.addActionListener(this);
        settings.addActionListener(this);
        nvidiaTimer.addActionListener(this);

        background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Nvidia.gif");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });

        add(new_game);
        add(settings);
        setSize(826, 465);
        setLayout(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == new_game){
            new Board();
        }
        if(e.getSource() == nvidiaTimer){
            background = Toolkit.getDefaultToolkit().createImage("src/GUI/Textures/Battlefield.jpg");
            setSize(1000, 1000);
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
