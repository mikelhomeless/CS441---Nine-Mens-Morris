package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Rules extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel container;
    private JButton backButton;
    private JScrollBar scrollBar;
    private JTextField rules;
    String sRules = "The board consist of a grid with 24 points. Each player has nine pieces, or 'men'. Players\n" +
            "Players try to form 'mills', or three of their own men lined horizontally or vertically\n" +
            "allowing a player to remove an opponent's man from the game. A player wins by reducing the opponent\n" +
            "to two pieces (where they could no longer form mills and thus be unable to win), or by leaving them\n" +
            "without a legal move";

    public Rules() {
        frame = new JFrame();
        container = new JPanel();
        scrollBar = new JScrollBar();
        rules = new JTextField(sRules);

        MediaTracker mt = new MediaTracker(this);
        setDefaultLookAndFeelDecorated(false);

        backButton.setBounds(1000, 350, 100, 50);
        scrollBar.setBounds(0, 0, 10, 1100);
        rules.setBounds(20, 20, 760, 980);


        frame.add(rules);
        frame.add(scrollBar);
        frame.add(backButton);
        frame.add(scrollBar);


        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                frame.dispose();
            }
        });

        frame.setSize(800, 1100);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e){

    }
}
