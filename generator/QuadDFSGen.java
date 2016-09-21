package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class QuadDFSGen {

	private final List<Cell> grid;

	private final List<Cell> currentCells = new ArrayList<Cell>(4);
	private final List<Stack<Cell>> stacks = new ArrayList<Stack<Cell>>(4);
	private final List<List<Cell>> grids = new ArrayList<List<Cell>>(4);
	private final Random r = new Random();

	public QuadDFSGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		currentCells.add(grid.get(0));
		currentCells.add(grid.get(grid.size() - 1));
		currentCells.add(grid.get(r.nextInt(grid.size())));
		currentCells.add(grid.get(r.nextInt(grid.size())));
		
		for (int i = 0; i < 4; i++) {
			stacks.add(new Stack<Cell>());
			grids.add(new ArrayList<Cell>());
		}
		
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!grid.parallelStream().allMatch(c -> c.isVisited())) {
					carve();
				} else {
					createPath();
					currentCells.clear();
					Maze.generated = true;
					timer.stop();
				}
				panel.setCurrentCells(currentCells);
				panel.repaint();
				timer.setDelay(Maze.speed);
			}
		});
		timer.start();
	}

	private void carve() {
		for(int i = 0; i < currentCells.size(); i++) {
			Cell current = currentCells.get(i);
			if (current != null) {
				Stack<Cell> myStack = stacks.get(i);
				List<Cell> myGrid = grids.get(i);
				current.setVisited(true);
				Cell next = current.getUnvisitedNeighbour(grid);
				if (next != null) {
					myStack.push(current);
					myGrid.add(current);
					current.removeWalls(next);
					current = next;
				} else if (!myStack.isEmpty()) {
					try {
						current = myStack.pop();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					current = null;
				}
				currentCells.set(i, current);
			}
		}
	}
	
	private boolean carvePathBetweenGrids(List<Cell> gridA, List<Cell> gridB) {
		for (Cell c : gridA) {
			for (Cell n : c.getAllNeighbours(grid)) {
				if (gridB.contains(n)) {
					c.removeWalls(n);
					return true;
				}
			}
		}
		return false;
	}

	// one and two MUST be connected, otherwise no path from start to end.
	private void createPath() {
		boolean oneTwo = false;
		boolean oneThree = false;
		boolean oneFour = false;
		
		List<Cell> grid1 = grids.get(0);
		List<Cell> grid2 = grids.get(1);
		List<Cell> grid3 = grids.get(2);
		List<Cell> grid4 = grids.get(3);
		
		oneTwo = carvePathBetweenGrids(grid1, grid2);
		oneThree = carvePathBetweenGrids(grid1, grid3);
		oneFour = carvePathBetweenGrids(grid1, grid4);
		
		if (!oneTwo) {
			if (oneThree) {
				carvePathBetweenGrids(grid2, grid3);
			} else if (oneFour) {
				carvePathBetweenGrids(grid2, grid4);
			}
		} else {
			if (!oneThree) {
				carvePathBetweenGrids(grid2, grid3);
			} else if (!oneFour) {
				carvePathBetweenGrids(grid2, grid4);
			}
		}
	}
}