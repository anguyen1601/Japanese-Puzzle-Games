import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PuzzleButton extends JButton implements ActionListener {

	private MyGame g;
	private MyFrame frame;

	private int code;

	private Container contentPane;
	private JPanel center;
	private JPanel bottom;
	private JPanel right;
	private JLabel image;
	private JLabel label;

	public PuzzleButton(String text, MyFrame f, Container cp, JLabel l, int n, JPanel c, JPanel b, JPanel r, JLabel i) {
		super(text);
		frame = f;
		contentPane = cp;
		label = l;
		code = n;
		center = c;
		bottom = b;
		right = r;
		image = i;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// removes all components to allow for components of new game

		contentPane.remove(label);
		center.removeAll();
		contentPane.remove(image);
		right.removeAll();
		bottom.removeAll();

		// creates a new instance of MyGame depending on score
		if (code == 1) {
			JLabel title = new JLabel("<html><h2><strong><i>Nori Nori! Green like nori...  </i></strong></h2></html>");
			right.add(title);

			JLabel rule1 = new JLabel("Squares can be filled with or without sushi.");
			JLabel rule2 = new JLabel("Fill in squares in pairs in adjacent squares.");
			JLabel rule3 = new JLabel("Cannot have more than 2 sushi together.");
			JLabel rule4 = new JLabel("Each shaded area must have exactly 2 sushi.");
			JLabel rule5 = new JLabel("Reselect the puzzle button to reset.");

			right.add(rule1);
			right.add(rule2);
			right.add(rule3);
			right.add(rule4);
			right.add(rule5);

			MyGame g = new NoriNori(frame);
			g.makeGrid(contentPane, center, bottom);
		}

		if (code == 2) {
			JLabel title = new JLabel("<html><h2><strong><i>Kakurasu!  </i></strong></h2></html>");
			right.add(title);

			JLabel rule1 = new JLabel("Squares can be marked or not marked.");
			JLabel rule2 = new JLabel("The sums on right and bottom are for respective rows and columns.");
			JLabel rule3 = new JLabel("The numbers across the top and left are the values of each square.");
			JLabel rule4 = new JLabel("A marked square adds it's value to the row and columns sums.");
			JLabel rule5 = new JLabel("Reselect the puzzle button to reset.");

			right.add(rule1);
			right.add(rule2);
			right.add(rule3);
			right.add(rule4);
			right.add(rule5);

			MyGame g = new Kakurasu(frame);
			g.makeGrid(contentPane, center, bottom);
		}

		if (code == 3) {
			JLabel title = new JLabel("<html><h2><strong><i>Hitori!  </i></strong></h2></html>");
			right.add(title);

			JLabel rule1 = new JLabel("Click to shade a square.");
			JLabel rule2 = new JLabel("Shading 'removes' a number from the row or column.");
			JLabel rule3 = new JLabel("Unshaded numbers should not repeat in rows or columns.");
			JLabel rule4 = new JLabel("Shaded squares can only touch diagonally.");
			JLabel rule5 = new JLabel("Reselect the puzzle button to reset.");

			right.add(rule1);
			right.add(rule2);
			right.add(rule3);
			right.add(rule4);
			right.add(rule5);

			MyGame g = new Hitori(frame);
			g.makeGrid(contentPane, center, bottom);
		}

		frame.validate();
		frame.repaint();
	}

}
