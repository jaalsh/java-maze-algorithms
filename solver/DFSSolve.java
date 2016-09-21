package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

import javax.swing.Timer;

import main.*;
import util.Cell;

// Basically a greedy dijkstra's that follows a path until it hits a dead end instead of prioritising the 
// closest cell to the goal.

public class DFSSolve {

	private final Stack<Cell> path = new Stack<Cell>();
	private Cell current;
	private final List<Cell> grid;

	public DFSSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(0);
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!current.equals(grid.get(grid.size() - 1))) {
					path();
				} else {
					drawPath();
					Maze.solved = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
				timer.setDelay(Maze.speed);
			}
		});
		timer.start();
	}

	private void path() {
		current.setDeadEnd(true);
		Cell next = current.getPathNeighbour(grid);
		if (next != null) {
			path.push(current);
			current = next;
		} else if (!path.isEmpty()) {
			try {
				current = path.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void drawPath() {
		while (!path.isEmpty()) {
			try {
				path.pop().setPath(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}