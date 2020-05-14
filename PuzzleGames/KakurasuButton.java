import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class KakurasuButton extends JButton implements ActionListener {

	private Kakurasu k;

	private int row;
	private int col;
	
	private Color gray = Color.lightGray;
	private Color color;

	public KakurasuButton(Kakurasu kaku, int r, int c, Color rgb) {
		k = kaku;
		row = r;
		col = c;
		color = rgb;
		this.setBackground(color);
		this.setOpaque(true);
		this.setBorderPainted(true);
		addActionListener(this);
	}

	// sets element in grid checks if puzzle is solved

	public void actionPerformed(ActionEvent e) {
		k.setElement(row, col);
		if (k.selected(row, col)) {
			this.setBackground(gray);

		} else {
			this.setBackground(color);
		}

		if (k.isSolved()) {
			JOptionPane.showMessageDialog(null, "Nguyener Nguyener! Puzzle Solved", "Congratulations!",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	// called when solve button is used to display solution
	public void set() {
		if (k.selected(row, col)) {
			this.setBackground(gray);
		}
	}

}
