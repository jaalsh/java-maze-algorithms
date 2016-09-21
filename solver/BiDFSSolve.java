package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class BiDFSSolve {

	private final Stack<Cell> path1 = new Stack<Cell>();
	private final Stack<Cell> path2 = new Stack<Cell>();
	private Cell current1, current2;
	private final List<Cell> grid;

	public BiDFSSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current1 = grid.get(0);
		current2 = grid.get(grid.size() - 1);
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!pathFound()) {	
					pathFromEnd();
					pathFromStart();
				} else {
					current1 = null;
					current2 = null;
					Maze.solved = true;
					drawPath();
					timer.stop();
				}
				panel.setCurrentCells(Arrays.asList(current1, current2));
				panel.repaint();
				timer.setDelay(Maze.speed);
			}
		});
		timer.start();
	}

	private void pathFromStart() {
		current1.setDeadEnd(true);
		Cell next = current1.getPathNeighbour(grid);
		if (next != null) {
			path1.push(current1);
			current1 = next;
		} else if (!path1.isEmpty()) {
			try {
				current1 = path1.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void pathFromEnd() {
		current2.setDeadEnd(true);
		Cell next = current2.getPathNeighbour(grid);
		if (next != null) {
			path2.push(current2);
			current2 = next;
		} else if (!path2.isEmpty()) {
			try {
				current2 = path2.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean pathFound() {
		List<Cell> neighs1 = current1.getValidMoveNeighbours(grid);
		List<Cell> neighs2 = current2.getValidMoveNeighbours(grid);
		for (Cell c : neighs1) {
			if (path2.contains(c)) {
				// path from beginning.
				path1.push(current1);
				path1.push(c);
				joinPaths(c, path2, current2);
				return true;
			}
		}
		for (Cell c : neighs2) {
			if (path1.contains(c)) {
				// path from end.
				path2.push(current2);
				path2.push(c);
				joinPaths(c, path1, current1);
				return true;
			}
		}
		return false;
	}
	
	private void joinPaths(Cell c, Stack<Cell> path, Cell current) {
		while (!path.isEmpty() && !current.equals(c)) {
			try {
				current = path.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		path1.addAll(path2);
	}
	

	private void drawPath() {
		while (!path1.isEmpty()) {
			try {
				path1.pop().setPath(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}