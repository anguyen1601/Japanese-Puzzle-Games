import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.util.Scanner;
import java.io.*;

public class Hitori implements MyGame {

	private int[][] grid = new int[5][5];
	private MyFrame frame;
	private int[] possibleValues = { 0, 1 };
	private HitoriButton[][] buttons = new HitoriButton[5][5];

	private int[][] scanValues = new int[5][5]; // read in values from file
	private Scanner file;

	private Color grey = new Color(197, 198, 199);
	private Color beige = new Color(227, 220, 192);

	public Hitori(MyFrame f) {
		frame = f;
		file = scanFile(scanValues, "HitoriBoard.txt");
		this.reset();

	}

	//scan file in 
	private Scanner scanFile(int[][] sV, String s) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(s));
			int j = 0;
			while (sc.hasNextLine()) {
				String x = sc.nextLine();
				String[] line = x.split(" "); 
				for (int i = 0; i < sV.length; i++) {
					scanValues[j][i] = Integer.parseInt(line[i]);
				}
				j++;
			}
			sc.close();
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Hitori File name?");
			System.exit(0);
			return null;
		}
		return sc;
	}

	//creates the grid using Nori Nori buttons
	public void makeGrid(Container contentPane, JPanel center, JPanel bottom) {

		int i = 0;
		int j = 0;
		for (int row = 0; row < 7; row++) { // contentPane of center is 7x7 but game grid is 5x5
			for (int col = 0; col < 7; col++) {
				if (row == 0 || row == 6 || col == 0 || col == 6) {
					center.add(Box.createRigidArea(new Dimension(5, 0))); //add space between components
				} else {
					String s = Integer.toString(scanValues[j][i]);
					HitoriButton hb = new HitoriButton(s, this, row - 1, col - 1, beige);
					center.add(hb);
					buttons[j][i] = hb;
					i++;
					if (i > 4) {
						j++;
						i = 0;
					}
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
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				grid[row][col] = -1;
			}
		}
	}

	//called by solve button to display solution
	public void fill() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (selected(row, col)) {
					buttons[row][col].set();
				}
			}
		}
	}

	public void setElement(int row, int col) {
		if (grid[row][col] == 1) {
			grid[row][col] = 0;
		} else {
			grid[row][col] = 1;
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
		return notAdjacent() && isCrossword();
	}
	

	//solves in the background, if user solves, display Nguyen message!
	public boolean isSolved() {
		for (int row = 0; row < grid.length; row++) {
			int[] remaining = new int[grid[row].length + 1];
			for (int col = 0; col < grid[row].length; col++) {
				if (!selected(row, col)) {
					remaining[scanValues[row][col]]++;
				}
			}
			for (int i = 0; i < remaining.length; i++) {
				if (remaining[i] > 1) {
					return false;
				}
			}
		}
		for (int col = 0; col < grid[0].length; col++) {
			int[] remaining = new int[grid.length + 1];
			for (int row = 0; row < grid.length; row++) {
				if (!selected(row, col)) {
					remaining[scanValues[row][col]]++;
				}
			}
			for (int i = 0; i < remaining.length; i++) {
				if (remaining[i] > 1) {
					return false;
				}
			}
		}
		return notAdjacent() && isCrossword();
	}

	public boolean notAdjacent() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				int count = 0;
				if (row == 0 && col == 0) {
					if ((selected(row + 1, col) && selected(row, col))
							|| (selected(row, col + 1) && selected(row, col))) {
						count++;
					}
				} else if (row == 0 && col == grid[row].length - 1) {
					if ((selected(row, col - 1) && selected(row, col))
							|| (selected(row + 1, col) && selected(row, col))) {
						count++;
					}
				} else if (row == grid.length - 1 && col == 0) {
					if ((selected(row - 1, col) && selected(row, col))
							|| (selected(row, col + 1) && selected(row, col))) {
						count++;
					}
				} else if (row == grid.length - 1 && col == grid[row].length - 1) {
					if ((selected(row - 1, col) && selected(row, col))
							|| (selected(row, col - 1) && selected(row, col))) {
						count++;
					}
				} else if (row == 0) {
					if ((selected(row, col - 1) && selected(row, col)) || (selected(row, col + 1) && selected(row, col))
							|| (selected(row + 1, col) && selected(row, col))) {
						count++;
					}
				} else if (row == grid.length - 1) {
					if ((selected(row, col - 1) && selected(row, col)) || (selected(row, col + 1) && selected(row, col))
							|| (selected(row - 1, col) && selected(row, col))) {
						count++;
					}
				} else if (col == 0) {
					if ((selected(row - 1, col) && selected(row, col)) || (selected(row + 1, col) && selected(row, col))
							|| (selected(row, col + 1) && selected(row, col))) {
						count++;
					}
				} else if (col == grid[row].length - 1) {
					if ((selected(row - 1, col) && selected(row, col)) || (selected(row + 1, col) && selected(row, col))
							|| (selected(row, col - 1) && selected(row, col))) {
						count++;
					}
				} else {
					if ((selected(row, col - 1) && selected(row, col)) || (selected(row, col + 1) && selected(row, col))
							|| (selected(row + 1, col) && selected(row, col)
									|| (selected(row - 1, col) && selected(row, col)))) {
						count++;
					}

				}

				if (count >= 1) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isCrossword() {
		int[][] temp = new int[grid.length][grid[0].length];
		for (int row = 0; row < temp.length; row++) {
			for (int col = 0; col < temp[row].length; col++) {
				temp[row][col] = grid[row][col];
			}
		}
		if (grid[0][0] != 1) {
			if (isCrosswordRecursion(temp, 0, 0))
				return true;
			else
				return false;
		} else {
			if (isCrosswordRecursion(temp, 0, 1))
				return true;
			else
				return false;
		}
	}

	private boolean isCrosswordRecursion(int[][] temp, int row, int col) {
		temp[row][col] = 2;
		if (col - 1 >= 0 && temp[row][col - 1] < 1)
			isCrosswordRecursion(temp, row, col - 1);
		if (col + 1 < temp[row].length && temp[row][col + 1] < 1)
			isCrosswordRecursion(temp, row, col + 1);
		if (row - 1 >= 0 && temp[row - 1][col] < 1)
			isCrosswordRecursion(temp, row - 1, col);
		if (row + 1 < temp.length && temp[row + 1][col] < 1)
			isCrosswordRecursion(temp, row + 1, col);

		// compares to grid to see if available spaces filled
		boolean compare = false;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[row].length; j++) {
				if (grid[i][j] != 1 && temp[i][j] != 2) {
					compare = false;
				} else {
					compare = true;
				}
			}
		}
		return compare;

	}

}