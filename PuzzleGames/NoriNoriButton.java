import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class NoriNoriButton extends JButton implements ActionListener {

	private NoriNori n;

	private int row;
	private int col;
	
	private ImageIcon sushi = new ImageIcon("sushi.png");

	public NoriNoriButton(NoriNori nori, int r, int c, Color color) {
		n = nori;
		row = r;
		col = c;
		this.setBackground(color);
		this.setOpaque(true);
		this.setBorderPainted(true);
		addActionListener(this);
	}

	// sets element in grid checks if puzzle is solved

	public void actionPerformed(ActionEvent e) {
		n.setElement(row, col);
		if (n.selected(row, col)) {
			this.setIcon(sushi);
		} else {
			this.setIcon(null);
		}
		if (n.isSolved()) {
			JOptionPane.showMessageDialog(null, "Nguyener Nguyener! Puzzle Solved", "Congratulations!",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	// called when solve button is used to display solution
	public void set() {
		if (n.selected(row, col)) {
			this.setIcon(sushi);
		} else {
			this.setIcon(null);
		}
	}

}
