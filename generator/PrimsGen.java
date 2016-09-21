package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;

public class PrimsGen {
	
	private final List<Cell> grid;
	private final List<Cell> frontier = new ArrayList<Cell>();
	private Cell current;

	public PrimsGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(0);
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
		
		List<Cell> neighs = current.getUnvisitedNeighboursList(grid);
		frontier.addAll(neighs);
		Collections.shuffle(frontier);
		
		current = frontier.get(0);
		
		List<Cell> inNeighs = current.getAllNeighbours(grid);
		inNeighs.removeIf(c -> !c.isVisited());
		
		if (!inNeighs.isEmpty()) {
			Collections.shuffle(inNeighs);
			current.removeWalls(inNeighs.get(0));
		}
		
		frontier.removeIf(c -> c.isVisited());
	}
}