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
	
	private List<Cell> grid = new ArrayList<Cell>();
	private List<Cell> frontier = new ArrayList<Cell>();
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
	
	/*
	 * It requires storage proportional to the size of the Maze. During creation, each cell is one of three types
	 * (1) "In": The cell is part of the Maze and has been carved into already, 
	 * (2) "Frontier": The cell is not part of the Maze and has not been carved into yet, but is next to a cell that's already "in", and 
	 * (3) "Out": The cell is not part of the Maze yet, and none of its neighbors are "in" either. 
	 * Start by picking a cell, making it "in", and setting all its neighbors to "frontier".
	 *  Proceed by picking a "frontier" cell at random, and carving into it from one of its neighbor cells that are "in". 
	 *  Change that "frontier" cell to "in", and update any of its neighbors that are "out" to "frontier". 
	 *  The Maze is done when there are no more "frontier" cells left (which means there are no more "out" cells left either, so they're all "in").
	 * 
	 */
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