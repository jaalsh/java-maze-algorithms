package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.Timer;

import main.Maze;
import util.Cell;

// does not generate a perfect maze - this is mostly for fun.
/*
 * Perfect: A "perfect" Maze means one without any loops or closed circuits, and without any inaccessible areas. 
 * Also called a simply-connected Maze. From each point, there is exactly one path to any other point. 
 * The Maze has exactly one solution. 
 * In Computer Science terms, such a Maze can be described as a spanning tree over the set of cells or vertices
 */

public class QuadDFSGen {

	private Stack<Cell> stack1 = new Stack<Cell>();
	private Stack<Cell> stack2 = new Stack<Cell>();
	private Stack<Cell> stack3 = new Stack<Cell>();
	private Stack<Cell> stack4 = new Stack<Cell>();
	private List<Cell> grid = new ArrayList<Cell>();
	private List<Cell> grid1 = new ArrayList<Cell>();
	private List<Cell> grid2 = new ArrayList<Cell>();
	private List<Cell> grid3 = new ArrayList<Cell>();
	private List<Cell> grid4 = new ArrayList<Cell>();
	private Cell current1, current2, current3, current4;
	private Random r = new Random();

	public QuadDFSGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current1 = grid.get(0);
		current2 = grid.get(grid.size() - 1);
		current3 = grid.get(r.nextInt(grid.size()));
		current4 = grid.get(r.nextInt(grid.size()));
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!grid.parallelStream().allMatch(c -> c.isVisited())) {
					carveCurrent1();
					carveCurrent2();
					carveCurrent3();
					carveCurrent4();
				} else {
					carvePathBetweenStacks(grid1, grid2);
					carvePathBetweenStacks(grid1, grid3);
					carvePathBetweenStacks(grid1, grid4);
					carvePathBetweenStacks(grid2, grid3);
					carvePathBetweenStacks(grid2, grid4);
					carvePathBetweenStacks(grid3, grid4);
					current1 = null;
					current2 = null;
					current3 = null;
					current4 = null;
					Maze.generated = true;
					timer.stop();
				}
				panel.setCurrentCells(Arrays.asList(current1, current2, current3, current4));
				panel.repaint();
			}
		});
		timer.start();
	}

	private void carveCurrent1() {
		current1.setVisited(true);
		Cell next = current1.getUnvisitedNeighbour(grid);
		if (next != null) {
			stack1.push(current1);
			grid1.add(current1);
			current1.removeWalls(next);
			current1 = next;
		} else if (!stack1.isEmpty()) {
			try {
				current1 = stack1.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carveCurrent2() {
		current2.setVisited(true);
		Cell next = current2.getUnvisitedNeighbour(grid);
		if (next != null) {
			stack2.push(current2);
			grid2.add(current2);
			current2.removeWalls(next);
			current2 = next;
		} else if (!stack2.isEmpty()) {
			try {
				current2 = stack2.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carveCurrent3() {
		current3.setVisited(true);
		Cell next = current3.getUnvisitedNeighbour(grid);
		if (next != null) {
			stack3.push(current3);
			grid3.add(current3);
			current3.removeWalls(next);
			current3 = next;
		} else if (!stack3.isEmpty()) {
			try {
				current3 = stack3.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carveCurrent4() {
		current4.setVisited(true);
		Cell next = current4.getUnvisitedNeighbour(grid);
		if (next != null) {
			stack4.push(current4);
			grid4.add(current4);
			current4.removeWalls(next);
			current4 = next;
		} else if (!stack4.isEmpty()) {
			try {
				current4 = stack4.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carvePathBetweenStacks(List<Cell> gridA, List<Cell> gridB) {
		for (Cell c : gridA) {
			for (Cell n : c.getAllNeighbours(grid)) {
				if (gridB.contains(n)) {
					c.removeWalls(n);
					return;
				}
			}
		}
	}
}
