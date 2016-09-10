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


	private List<Cell> grid;
	private Cell current;
	private List<Cell> cells = new ArrayList<Cell>();
	private Random r = new Random();

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
			}
		});
		timer.start();
	}
	
	// Growing tree algorithm: This is a general algorithm, capable of creating
		// Mazes of different textures. It requires storage up to the size of the
		// Maze. Each time you carve a cell, add that cell to a list. 
	
	//Proceed by  picking a cell from the list, and carving into an unmade cell next to it.
	
		// If there are no unmade cells next to the current cell, remove the current
		// cell from the list. The Maze is done when the list becomes empty. The
		// interesting part that allows many possible textures is how you pick a
		// cell from the list. 
	
		//For example, if you always pick the most recent cell
		// added to it, this algorithm turns into the recursive backtracker. If you
		// always pick cells at random, this will behave similarly but not exactly
		// to Prim's algorithm. If you always pick the oldest cells added to the
		// list, this will create Mazes with about as low a "river" factor as
		// possible, even lower than Prim's algorithm. If you usually pick the most
		// recent cell, but occasionally pick a random cell, the Maze will have a
		// high "river" factor but a short direct solution. If you randomly pick
		// among the most recent cells, the Maze will have a low "river" factor but
		// a long windy solution.

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
				if (cells.get(0).getUnvisitedNeighboursList(grid).size() != 0) {
					current = cells.get(0);
				} else {
					current = cells.get(r.nextInt(cells.size()));
				}
			}
		}
	}
}
