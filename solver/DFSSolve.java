package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

import javax.swing.Timer;

import generator.MazeGridPanel;
import main.Maze;
import util.Cell;

public class DFSSolve {

	private Stack<Cell> path = new Stack<Cell>();
	private Cell current;
	private List<Cell> grid;

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
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
			}
		});
		timer.start();
	}

	public void path() {
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