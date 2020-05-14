import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class Kakurasu implements MyGame {

	private int[][] grid = new int[5][5];
	private MyFrame frame;
	private int[] possibleValues = { 0, 1 };
	private int[] rowSums = { 12, 6, 14, 3, 6 };
	private int[] colSums = { 1, 11, 7, 11, 4 };

	private KakurasuButton[][] buttons = new KakurasuButton[5][5];

	private Color grey = new Color(197, 198, 199);
	private Color blue = new Color(193, 210, 214);

	public Kakurasu(MyFrame f) {
		frame = f;
		this.reset();

	}

	//creates the grid using Nori Nori buttons
	public void makeGrid(Container contentPane, JPanel center, JPanel bottom) {

		int colValue = 1;
		int rowValue = 1;
		int rowSum = 0;
		int colSum = 0;

		for (int i = 0; i < 7; i++) { // contentPane of center is 7x7 but game grid is 5x5
			for (int j = 0; j < 7; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == 6) || (i == 6 && j == 0) || (i == 6 && j == 6)) {
					center.add(Box.createRigidArea(new Dimension(5, 0))); //add space between components
				} else if (i == 0) {
					JLabel value = new JLabel(Integer.toString(rowValue));
					value.setHorizontalAlignment(JTextField.CENTER);
					center.add(value);
					rowValue++;
				} else if (j == 0) {
					JLabel value = new JLabel(Integer.toString(colValue));
					value.setHorizontalAlignment(JTextField.CENTER);
					center.add(value);
					colValue++;
				} else if (j == 6) {
					JLabel value = new JLabel(Integer.toString(rowSums[rowSum]));
					value.setHorizontalAlignment(JTextField.CENTER);
					center.add(value);
					rowSum++;
				} else if (i == 6) {
					JLabel value = new JLabel(Integer.toString(colSums[colSum]));
					value.setHorizontalAlignment(JTextField.CENTER);
					center.add(value);
					colSum++;
				} else {
					KakurasuButton kb = new KakurasuButton(this, i - 1, j - 1, blue);
					center.add(kb);
					buttons[i - 1][j - 1] = kb;
				}
			}
		}

		//add solve button
		Solver solve = new Solver("solve", this, grid, possibleValues);
		solve.setBackground(grey);
		solve.setOpaque(true);
		bottom.add(solve);

		//add check button
		Checker check = new Checker("check", this);
		check.setBackground(grey);
		check.setOpaque(true);
		bottom.add(check);

		contentPane.add(bottom, BorderLayout.SOUTH);
		contentPane.add(center, BorderLayout.CENTER);

	}

	// initializes all grid values to -1
	public void reset() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = -1;
			}
		}
	}

	//called by solve button to display solution
	public void fill() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 1) {
					buttons[i][j].set();
				}
			}
		}
	}

	public void setElement(int row, int col) {
		if (grid[row][col] == 0 || grid[row][col] == -1) {
			grid[row][col] = 1;
		} else {
			grid[row][col] = 0;
		}
	}

	//has the button been selected?
	public boolean selected(int row, int col) {
		if (grid[row][col] == 1) {
			return true;
		} else {
			return false;
		}
	}

	//check constaints
	public boolean check() {

		boolean correctRows = true;
		for (int i = 0; i < grid.length; i++) {
			int sum = 0;
			for (int j = 0; j < grid[i].length; j++) {
				if (selected(i, j)) {
					sum = sum + j + 1;
				}
			}
			if (sum > rowSums[i])
				correctRows = false;
		}

		boolean correctCols = true;
		for (int j= 0; j < grid[0].length; j++) {
			int sum = 0;
			for (int i = 0; i < grid.length; i++) {
				if (selected(i, j)) {
					sum = sum + i + 1;
				}
			}
			if (sum > colSums[j])
				correctCols = false;
		}
		return correctCols && correctRows;
	}

	//solves in the background, if user solves, display Nguyen message!
	public boolean isSolved() {
		for (int i = 0; i < grid.length; i++) {
			int sum = 0;
			for (int j = 0; j < grid[i].length; j++) {
				if (selected(i, j)) {
					sum = sum + j + 1;
				}
			}
			if (sum != rowSums[i])
				return false;
		}
		for (int j = 0; j < grid[0].length; j++) {
			int sum = 0;
			for (int i = 0; i < grid.length; i++) {
				if (selected(i, j)) {
					sum = sum + i + 1;
				}
			}
			if (sum != colSums[j])
				return false;
		}
		return true;
	}

}