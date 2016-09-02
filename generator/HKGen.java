package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.Timer;

import main.Maze;
import util.Cell;

public class HKGen {
	
	private List<Cell> grid;
	private Cell current;

	public HKGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(0);
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!grid.parallelStream().allMatch(c -> c.isVisited())) {
					carve();
				} else {
					current = grid.get(0);
					Maze.generated = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
			}
		});
		timer.start();
	}

	private void carve() {
		current.setVisited(true);
		Cell next = current.getNeighbour(grid);
		if (next != null) {
			current.removeWalls(next);
			current = next;
		} else {
			// hunt
			Optional<Cell> opt = grid.parallelStream().filter(c -> c.isVisited() && c.getNeighbours(grid).size() > 0)
					.findAny();
			if (opt.isPresent()) {
				current = opt.get();
			}
		}
	}
}
