package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;

public class SpiralBacktrackerGen {
	
	private static final int STEP_COUNT = 4; // change this to generate different mazes.

	private final Stack<Cell> stack = new Stack<Cell>();
	private final List<Cell> grid;
	private final Random r = new Random();
	
	private int direction;
	private Cell current;
	private int count = STEP_COUNT; // so we can pick direction in carve().

	public SpiralBacktrackerGen(List<Cell> grid, MazeGridPanel panel) {
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
		
		if (neighs.isEmpty()) {
			current = stack.pop();
			return;
		}
		
		if (count < STEP_COUNT) {
			Cell next = getNeighbour();
			if (next != null) {
				if (!next.isVisited()) {
					stack.push(current);
					current.removeWalls(next);
					current = next;
				}
				
			} else {
				count = STEP_COUNT + 1;
			}
			
			count++;
		} else {
			count = 0; // reset count
			List<Integer> directions = new ArrayList<Integer>();
			// get valid directions
			if(neighs.contains(current.getTopNeighbour(grid))) {
				directions.add(0);
			}
			
			if(neighs.contains(current.getRightNeighbour(grid))) {
				directions.add(1);
			}
			
			if(neighs.contains(current.getBottomNeighbour(grid))) {
				directions.add(2);
			}
			
			if(neighs.contains(current.getLeftNeighbour(grid))) {
				directions.add(3);
			}
			
			
			int prevDir = direction;
			// optional, this prevents the algorithm going in the same direction multiple times.
			if (neighs.size() > 1) {
				while (direction == prevDir) direction = directions.get(r.nextInt(directions.size()));
			} else {
				// can just do this line and go in the same direction multiple times.
				direction = directions.get(r.nextInt(directions.size()));
			}
		}
	}
	
	private Cell getNeighbour() {
		switch (direction) {
		case 0:
			return current.getTopNeighbour(grid);
		case 1:
			return current.getRightNeighbour(grid);
		case 2:
			return current.getBottomNeighbour(grid);
		case 3:
			return current.getLeftNeighbour(grid);
		default:
			return null;
		}
	}
}
