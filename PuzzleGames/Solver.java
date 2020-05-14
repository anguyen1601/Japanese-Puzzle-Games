import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Solver extends JButton implements ActionListener {

	private MyGame game;
	private int[][] grid;
	private int[] possibleValues;

	public Solver(String text, MyGame g, int[][] gg, int[] pv) {
		super(text);
		game = g;
		grid = gg;
		possibleValues = pv;
		addActionListener(this);
	}

	public boolean label(int[][] grid, MyGame game, int[] possibleValues, int row, int col) {

		if (row == grid.length) {
			return game.isSolved();
		}

		for (int v : possibleValues) {

			grid[row][col] = v;
			

			if (game.check()) {

				int newCol = col + 1;
				int newRow = row;
				if (newCol == grid[row].length) {

					newCol = 0;
					newRow = row + 1;
				}
				if (label(grid, game, possibleValues, newRow, newCol)) {

					return true;
				}
			}
		}
		grid[row][col] = -1;
		return false;

	}

	public void actionPerformed(ActionEvent e) {

		game.reset();

		boolean result = label(grid, game, possibleValues, 0, 0);

		if (result) {
			game.fill();
			JOptionPane.showMessageDialog(null, "Here is the solution!", "You gave up...",
			JOptionPane.PLAIN_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, "Reset the game first!", "Error...",
			JOptionPane.ERROR_MESSAGE);
		}
	}
}
