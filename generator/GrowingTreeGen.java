package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class GrowingTreeGen {

	private final List<Cell> grid;
	private Cell current;
	private final List<Cell> cells = new ArrayList<Cell>();
	private final Random r = new Random();

	public GrowingTreeGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(r.nextInt(grid.size()));	
		cells.add(current);
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!grid.parallelStream().allMatch(c -> c.isVisited())) {
					carve();
				} else {
					current = null;
					Maze.generated = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
				timer.setDelay(Maze.speed);
			}
		});
		timer.start();
	}

	private void carve() {
		current.setVisited(true);
		Cell next = current.getUnvisitedNeighbour(grid);
		if (next != null) {
			current.removeWalls(next);
			current = next;
			cells.add(current);
		} else {
			cells.remove(current);
			if (!cells.isEmpty()) {
				current = cells.get(r.nextInt(cells.size())); // get random cell
			}
		}
	}
}
