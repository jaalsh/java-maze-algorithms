package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import generator.GeneratorPanel;
import main.DFSCell;
import main.Stack;

public class DFSSolver {

	private Stack<DFSCell> path;
	private DFSCell current;
	private List<DFSCell> grid;

	public DFSSolver(int rows, int cols, List<DFSCell> grid, GeneratorPanel panel) {
		this.grid = grid;
		path = new Stack<DFSCell>(rows * cols);
		current = grid.get(0);
		final Timer timer = new Timer(1, null);
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
		DFSCell next = current.getPathNeighbour(grid);
		if (next != null) {
			path.push(current);
			current = next;
		} else if (!path.isStackEmpty()) {
			try {
				current = path.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void drawPath() {
		while (!path.isStackEmpty()) {
			try {
				path.pop().setPath(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}