import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Checker extends JButton implements ActionListener {

	private MyGame game;

	public Checker(String text, MyGame g) {
		super(text);
		game = g;
		addActionListener(this);
	}

	//if wrong solution, tell the user 
	public void actionPerformed(ActionEvent e) {
		if (!game.isSolved()) {

			JOptionPane.showMessageDialog(null, "Incorrect Solution! Keep Trying!", "Sorry...",
					JOptionPane.ERROR_MESSAGE);

		}

	}
}
