package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class WilsonsGen {

	private final List<Cell> grid;
	private final Stack<Cell> stack = new Stack<Cell>();
	private Cell current;
	private final Random r = new Random();

	public WilsonsGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(r.nextInt(grid.size()));
		current.setVisited(true);
		current = grid.get(r.nextInt(grid.size()));
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
		if (current.isVisited()) {
			addPathToMaze();
			// TODO: Minor future refinement:
			/* Do not need to run through whole maze with stream filter.
			 * Could maintain a list of all cells not in the maze from beginning and remove them 
			 * from the list as we pop them off the stack in addPathToMaze(). Algorithm should still work as 
			 * current will never be in maze. When this list is empty we have carved the maze.
			 */
			List<Cell> notInMaze = grid.parallelStream().filter(c -> !c.isVisited()).collect(Collectors.toList());
			if (!notInMaze.isEmpty()) {
				current = notInMaze.get(r.nextInt(notInMaze.size()));							
			} else {
				return;
			}
		}
		current.setPath(true);
		Cell next = current.getNonPathNeighbour(grid);
		if (next != null) {
			stack.push(current);
			current.removeWalls(next);
			current = next;
		} else if (!stack.isEmpty()) {
			try {
				current = stack.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addPathToMaze() {
		grid.parallelStream().filter(c -> c.isPath()).forEach(c -> {
			c.setVisited(true); 
			c.setPath(false);
		});
		stack.clear();
	}
}