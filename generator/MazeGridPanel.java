package generator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import main.Maze;
import solver.DFSSolver;
import util.Cell;


public class MazeGridPanel extends JPanel {

	private static final long serialVersionUID = 7237062514425122227L;
	private List<Cell> grid = new ArrayList<Cell>();
	private Cell current;

	public MazeGridPanel(int rows, int cols) {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				grid.add(new Cell(x, y));
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		// +1 pixel on width and height so bottom and right borders can be
		// drawn.
		return new Dimension(Maze.WIDTH + 1, Maze.HEIGHT + 1);
	}

	public void generate(int index) {
		// switch statement for gen method read from combobox in Maze.java
		switch (index) {
		case 0:
			new DFSGen(grid, this);
			break;
		case 1:
			new HKGen(grid, this);
			break;
		case 2:
			new WilsonGen(grid, this);
			break;
		default:
			new WilsonGen(grid, this);
			break;
		}
	}

	public void solve(int index) {
		switch (index) {
		case 0:
			new DFSSolver(grid, this);
			break;
		default:
			new DFSSolver(grid, this);
			break;
		}
	}
	
	public void setCurrent(Cell current) {
		this.current = current;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Cell c : grid) {
			c.draw(g);
		}
		if (current != null) {
			current.highlight(g);
		}
	}
}
