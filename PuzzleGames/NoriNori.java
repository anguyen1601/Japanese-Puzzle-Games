import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class NoriNori implements MyGame {

	private int[][] grid = new int[6][6];
	private Color c1 = new Color(145, 210, 144); // green cause nori is green lol
	private Color c2 = new Color(219, 213, 185); // pale brown cause nori is more brown tbh
	private Color c3 = new Color(224, 243, 176); // medium green 
	private Color c4 = Color.white; // white

	//this array holds the color for button in each row 
	private Color[][] color = { { c1, c1, c2, c2, c3, c1 },		//row 0
								{ c1, c4, c4, c3, c3, c1 }, 	//row 1
								{ c1, c4, c4, c3, c1, c1 },		//row 2
								{ c1, c4, c4, c4, c1, c2 }, 	//row 3
								{ c3, c3, c3, c2, c2, c2 }, 	//row 4
								{ c3, c3, c4, c4, c4, c4 } }; 	//row 5

	private NoriNoriButton[][] buttons = new NoriNoriButton[6][6];
	private int[] possibleValues = { 0, 1 };
	private MyFrame frame;

	//this array holds the colored areas 
	private int[][][] areas = { { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 }, { 3, 0 } },	//area 0
								{ { 0, 2 }, { 0, 3 } },		//area 1
								{ { 0, 4 }, { 1, 3 }, { 1, 4 }, { 2, 3 } },  //area 2
								{ { 0, 5 }, { 1, 5 }, { 2, 4 }, { 2, 5 }, { 3, 4 } },	//area 3
								{ { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 }, { 3, 1 }, { 3, 2 }, { 3, 3 } },	//area 4
								{ { 4, 0 }, { 4, 1 }, { 4, 2 }, { 5, 0 }, { 5, 1 } },	//area 5
								{ { 3, 5 }, { 4, 3 }, { 4, 4 }, { 4, 5 } },		//area 6
								{ { 5, 2 }, { 5, 3 }, { 5, 4 }, { 5, 5 } } }; 	//area 7

	private Color grey = new Color(197, 198, 199);

	public NoriNori(MyFrame f) {
		frame = f;
		this.reset();
	}

	//creates the grid using Nori Nori buttons
	public void makeGrid(Container contentPane, JPanel center, JPanel bottom) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				NoriNoriButton nb = new NoriNoriButton(this, i, j, color[i][j]);
				center.add(nb);
				buttons[i][j] = nb;
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

	// checks constraints
	public boolean check() {
		for (int i = 0; i < areas.length; i++) {
			if (!hasAdjacentPairs(areas[i])) {
				return false;
			
			}
		}

		for (int i = 0; i < areas.length; i++) {
			int j = 0;
			int count = 0;
			while (j < areas[i].length) {
				if (grid[areas[i][j][0]][areas[i][j][1]] == 1) {
					count++;
				}
				j++;
			}
			if (count > 2) {
				return false;
			}
		}
		return isPairedOrSingle();
	}

	//solves in the background, if user solves, display Nguyen message!
	public boolean isSolved() {

		return isPaired() && pairInArea();

	}

	// do the areas have pairs?
	public boolean isPaired() {

		boolean isPaired = true;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int count = 0;
				if (i == 0 && j == 0 && selected(i, j)) {
					count++;
					if (selected(i + 1, j) || selected(i, j + 1)) {
						count++;
					}
				} else if (i == 0 && j == grid[i].length - 1 && selected(i, j)) {
					count++;
					if (selected(i, j - 1) || selected(i + 1, j)) {
						count++;
					}
				} else if (i == grid.length - 1 && j == 0 && selected(i, j)) {
					count++;
					if (selected(i - 1, j) || selected(i, j + 1)) {
						count++;
					}
				} else if (i == grid.length - 1 && j == grid[i].length - 1 && selected(i, j)) {
					count++;
					if (selected(i - 1, j) || selected(i, j - 1)) {
						count++;
					}
				} else if (i == 0 && selected(i, j)) {
					count++;
					if (selected(i, j - 1) || selected(i, j + 1) || selected(i + 1, j)) {
						count++;
					}
				} else if (i == grid.length - 1 && selected(i, j)) {
					count++;
					if (selected(i, j - 1) || selected(i, j + 1) || selected(i - 1, j)) {
						count++;
					}
				} else if (j == 0 && selected(i, j)) {
					count++;
					if (selected(i - 1, j) || selected(i + 1, j) || selected(i, j + 1)) {
						count++;
					}
				} else if (j == grid[i].length - 1 && selected(i, j)) {
					count++;
					if (selected(i - 1, j) || selected(i + 1, j) || selected(i, j - 1)) {
						count++;
					}
				} else if (selected(i, j)) {
					count++;
					if (selected(i, j - 1) || selected(i, j + 1) || selected(i + 1, j) || selected(i - 1, j)) {
						count++;
					}
				}
				if (count != 2 && count != 0) {
					isPaired = false;
				}
			}
		}
		return isPaired;
	}

	// checks if each area has a pair
	public boolean pairInArea() {
		boolean pairInArea = true;
		for (int i = 0; i < areas.length; i++) {
			int j = 0;
			int count = 0;
			while (j < areas[i].length) {
				if (grid[areas[i][j][0]][areas[i][j][1]] == 1) {
					count++;
				}
				j++;
			}
			if (count != 2) {
				pairInArea = false;
				return pairInArea;
			}
		}
		pairInArea = true;

		return pairInArea;

	}


	// method checks if area has available spaces
	private boolean hasAdjacentPairs(int[][] area) {
		int emptySpace = 0;
		int filledSpace = 0;

		for (int i = 0; i < area.length; i++) {
			if (grid[area[i][0]][area[i][1]] == -1) {
				emptySpace++;
			}
			if (grid[area[i][0]][area[i][1]] == 1) {
				filledSpace++;
			}
		}
		if (emptySpace + filledSpace >= 2) {
			return true;
		}
		return false;
	}

	// do the areas have one pair or singles?
	private boolean isPairedOrSingle() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int count = 0;
				if (i == 0 && j == 0 && selected(i, j)) {
					count++;
					if (selected(i + 1, j)){
						count++;
					}
					if (selected(i, j + 1)){
						count++;
					}
				} else if (i == 0 && j == grid[i].length - 1 && selected(i, j)) {
					count++;
					if (selected(i, j - 1)) {
						count++;
					}
					if(selected(i + 1, j)) {
						count++;
					}
				} else if (i == grid.length - 1 && j == 0 && selected(i, j)) {
					count++;
					if (selected(i - 1, j)) {
						count++;
					}
					if (selected(i, j + 1)){
						count++;
					}
				} else if (i == grid.length - 1 && j == grid[i].length - 1 && selected(i, j)) {
					count++;
					if (selected(i - 1, j)) {
						count++;
					}

					if(selected(i, j - 1)){
						count++;
					}
				} else if (i == 0 && selected(i, j)) {
					count++;
					if (selected(i, j - 1)) {
						count++;
					}
					if(selected(i, j + 1)){
						count++;
					}
					if(selected(i + 1, j)){
						count++;
					}
				} else if (i == grid.length - 1 && selected(i, j)) {
					count++;
					if (selected(i, j - 1)) {
						count++;
					}
					if(selected(i, j + 1)){
						count++;
					}
					if(selected(i - 1, j)){
						count++;
					}
				} else if (j == 0 && selected(i, j)) {
					count++;
					if (selected(i - 1, j)) {
						count++;
					}
					if(selected(i + 1, j) ){
						count++;
					}
					if(selected(i, j + 1)){
						count++;
					}
				} else if (j == grid[i].length - 1 && selected(i, j)) {
					count++;
					if (selected(i - 1, j)) {
						count++;
					}
					if(selected(i + 1, j)){
						count++;
					}
					if(selected(i, j - 1)){
						count++;
					}
				} else if (selected(i, j)) {
					count++;
					if (selected(i, j - 1) ) {
						count++;
					}
					if(selected(i, j + 1) ){
						count++;
					}
					if(selected(i + 1, j)){
						count++;
					}
					if(selected(i - 1, j)){
						count++;
					}
				}
				if (count > 2) {
					return false;
				}
			}
		}
		return true;
	}

}
