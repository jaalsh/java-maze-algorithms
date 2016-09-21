package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;
import util.DisjointSets;

public class GrowingForestGen {
	
	private final List<Cell> grid;
	private final List<Cell> active = new ArrayList<Cell>();
	private final DisjointSets disjointSet = new DisjointSets();
	
	private Cell current;
	private final Random r = new Random();

	public GrowingForestGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		for (int i = 0; i < grid.size(); i++) {
			grid.get(i).setId(i);
			disjointSet.create_set(grid.get(i).getId());
		}
		
		current = grid.get(r.nextInt(grid.size()));
		active.add(current);
		
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!active.isEmpty()) {
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
		boolean done = true;
		current.setVisited(true);
		
		List<Cell> neighs = current.getAllNeighbours(grid);
		
		for (Cell n : neighs) {
			if (disjointSet.find_set(current.getId()) != disjointSet.find_set(n.getId())) {
				current.removeWalls(n);
				disjointSet.union(current.getId(), n.getId());
				done = false;
				active.add(n);
				break;
			}
		}
		
		if (done) active.remove(current);
		
		List<Cell> unvisited = grid.parallelStream().filter(c -> !c.isVisited()).collect(Collectors.toList());
		
		// 2% chance to generate a new tree
		if (!unvisited.isEmpty() && r.nextInt(101) > 98) {
			active.add(unvisited.get(r.nextInt(unvisited.size())));
		}
		
		if (!active.isEmpty()) current = active.get(r.nextInt(active.size()));
	}	
}