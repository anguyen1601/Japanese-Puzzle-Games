import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class HitoriButton extends JButton implements ActionListener {

	private Hitori h;

	private int row;
	private int col;
	
	private Color gray = Color.lightGray;
	private Color color;

	public HitoriButton(String text, Hitori hit, int r, int c, Color rgb) {
		super(text);
		h = hit;
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

		h.setElement(row, col);
		if (h.selected(row, col)) {
			this.setBackground(gray);
		} else {
			this.setBackground(color);
		}

		if (h.isSolved()) {
			JOptionPane.showMessageDialog(null, "Nguyener Nguyener! Puzzle Solved", "Congratulations!",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void set() {
		if (h.selected(row, col)) {
			this.setBackground(gray);
		}
	}

}
