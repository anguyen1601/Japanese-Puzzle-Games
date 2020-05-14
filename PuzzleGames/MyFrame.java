
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;

public class MyFrame extends JFrame {

    Container contentPane;
    Color red = new Color(252, 44, 68);
    Color blue = new Color(9, 191, 228);
    Color yellow = new Color(255, 211, 0);
    Color green = new Color(134, 199, 47);

    // set up the main menu panel
    public void init() { 

        contentPane = this.getContentPane();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane.setLayout(new BorderLayout(100, 50));

        JPanel top = new JPanel(new FlowLayout()); // centers content
        JPanel center = new JPanel(new GridLayout(7, 7)); // 7 rows x 7 columns
        JPanel left = new JPanel(new GridLayout(4, 3)); // 4 rows x 3 columns
        JPanel right = new JPanel(new GridLayout(6, 3)); // 7 rows x 3 columns
        JPanel bottom = new JPanel(new FlowLayout()); // centers content

        contentPane.add(top, BorderLayout.NORTH);
        contentPane.add(center, BorderLayout.CENTER);
        contentPane.add(left, BorderLayout.WEST);
        contentPane.add(right, BorderLayout.EAST);
        contentPane.add(bottom, BorderLayout.SOUTH);

        JLabel image = new JLabel();
        image.setIcon(new ImageIcon("puzzle.jpg"));
        contentPane.add(image);

        JLabel title = new JLabel("<html><h1><strong><i>Constraint Puzzles</i></strong></h1><hr></html>");
        top.add(title);
        JLabel space = new JLabel(" ");
        top.add(space);

        PuzzleButton puzzle1 = new PuzzleButton("Nori Nori", this, contentPane, title, 1, center, bottom, right, image);
        puzzle1.setBackground(green);
        puzzle1.setOpaque(true);
        left.add(puzzle1);

        PuzzleButton puzzle2 = new PuzzleButton("Kakurasu", this, contentPane, title, 2, center, bottom, right, image);
        puzzle2.setBackground(blue);
        puzzle2.setOpaque(true);
        left.add(puzzle2);

        PuzzleButton puzzle3 = new PuzzleButton("Hitori", this, contentPane, title, 3, center, bottom, right, image);
        puzzle3.setBackground(yellow);
        puzzle3.setOpaque(true);
        left.add(puzzle3);

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ExitGame(this));
        exit.setBackground(red);
        exit.setOpaque(true);
        left.add(exit);

        JLabel label = new JLabel("<html><h2><strong><i>Let's solve puzzles!</i></strong></h2></html>");
        bottom.add(label);

    }
}