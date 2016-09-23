package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import generator.*;
import solver.*;
import util.Cell;

public class MazeGridPanel extends JPanel {

	private static final long serialVersionUID = 7237062514425122227L;
	private final List<Cell> grid = new ArrayList<Cell>();
	private List<Cell> currentCells = new ArrayList<Cell>();

	public MazeGridPanel(int rows, int cols) {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				grid.add(new Cell(x, y));
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		// +1 pixel on width and height so bottom and right borders can be drawn.
		return new Dimension(Maze.WIDTH + 1, Maze.HEIGHT + 1);
	}

	public void generate(int index) {
		// switch statement for gen method read from combobox in Maze.java
		switch (index) {
		case 0:
			new DFSGen(grid, this);
			break;
		case 1:
			new HuntAndKillGen(grid, this);
			break;
		case 2:
			new WilsonsGen(grid, this);
			break;
		case 3:
			new GrowingTreeGen(grid, this);
			break;
		case 4:
			new QuadDFSGen(grid, this);
			break;
		case 5:
			new BinaryTreeGen(grid, this);
			break;
		case 6:
			new KruskalGen(grid, this);
			break;
		case 7:
			new EllersGen(grid, this);
			break;
		case 8:
			new AldousBroderGen(grid, this);
			break;
		case 9:
			new GrowingForestGen(grid, this);
			break;
		case 10:
			new SidewinderGen(grid, this);
			break;
		case 11:
			new PrimsGen(grid, this);
			break;
		case 12:
			new HoustonGen(grid, this);
			break;
		case 13:
			new SpiralBacktrackerGen(grid, this);
			break;
		default:
			new GrowingTreeGen(grid, this);
			break;
		}
	}

	public void solve(int index) {
		switch (index) {
		case 0:
			new DFSSolve(grid, this);
			break;
		case 1:
			new BFSSolve(grid, this);
			break;
		case 2: 
			new BiDFSSolve(grid, this);
			break;
		case 3:
			new DijkstraSolve(grid, this);
			break;
		default:
			new BFSSolve(grid, this);
			break;
		}
	}
	
	public void resetSolution() {
		for (Cell c : grid) {
			c.setDeadEnd(false);
			c.setPath(false);
			c.setDistance(-1);
			c.setParent(null);
		}
		repaint();
	}
	
	public void setCurrent(Cell current) {
		if(currentCells.size() == 0) {
			currentCells.add(current);
		} else {
			currentCells.set(0, current);			
		}
	}
	
	public void setCurrentCells(List<Cell> currentCells) {
		this.currentCells = currentCells;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Cell c : grid) {
			c.draw(g);
		}
		for (Cell c : currentCells) {
			if(c != null) c.displayAsColor(g, Color.ORANGE);
		}
		grid.get(0).displayAsColor(g, Color.GREEN); // start cell
		grid.get(grid.size() - 1).displayAsColor(g, Color.YELLOW); // end or goal cell
	}
}
