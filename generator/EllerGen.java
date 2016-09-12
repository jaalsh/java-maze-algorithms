package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;
import util.DisjointSets;

// Slightly modified version in that the algorithm implemented here focuses on columns rather than rows.

public class EllerGen {
	
	private List<Cell> grid = new ArrayList<Cell>();
	private List<Cell> currentCol = new ArrayList<Cell>();
	
	private DisjointSets disjointSet = new DisjointSets();
	
	private int colCounter = 0;
	private int rows = Math.floorDiv(Maze.HEIGHT, Maze.W);
	private int fromIndex, toIndex;
	
	private Random r = new Random();
	
	private Cell current;

	public EllerGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(0);
		
		fromIndex = 0;
		toIndex = rows;
		
		for (int i = 0; i < grid.size(); i++) {
			grid.get(i).setId(i);
			disjointSet.create_set(grid.get(i).getId());
		}
		
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

	private void carve() {
		currentCol = grid.subList(fromIndex, toIndex);
		colCounter++;
		fromIndex = toIndex;
		toIndex += rows;
		
		// carve down
		for (Cell c : currentCol) {
			c.setVisited(true);
			current = c;
			
			if (r.nextBoolean() || colCounter == Math.floorDiv(Maze.HEIGHT, Maze.W)) { // or last column
				Cell bottom = c.getBottomNeighbour(grid);
				if (bottom != null) {
					if (disjointSet.find_set(c.getId()) != disjointSet.find_set(bottom.getId())) {
						c.removeWalls(bottom);
						disjointSet.union(c.getId(), bottom.getId());
					}
				}
			}
		}
		
		// carve right
		for (Cell c : currentCol) {
			List<Cell> cells = new ArrayList<Cell>();
			for (Cell c2 : currentCol) {
				if (disjointSet.find_set(c.getId()) == disjointSet.find_set(c2.getId())) {
					cells.add(c2);
				}
			}
			Collections.shuffle(cells);
			Cell c3 = cells.get(0);
			current = c3;
			Cell right = c3.getRightNeighbour(grid);
			if (right != null) {
				c3.removeWalls(right);
				disjointSet.union(c3.getId(), right.getId());
			}
		}
	}	
}