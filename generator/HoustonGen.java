package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;

/*
 * 
 * # This hybrid algorithm was described to me by Robin Houston. It
# begins with the Aldous-Broder algorithm, to fill out the grid,
# and then switches to Wilson's after the grid is about 1/3
# populated.
#
# This gives you better performance than either algorithm by itself,
# but while intuitively it would seem this preserves the properties
# of the original algorithms, it is not yet certain whether this still
# creates a uniform spanning tree or not.
 */
public class HoustonGen {

	private Stack<Cell> stack = new Stack<Cell>();
	private List<Cell> grid = new ArrayList<Cell>();
	private Cell current;
	private Random r = new Random();

	public HoustonGen(List<Cell> grid, MazeGridPanel panel) {
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
		List<Cell> visited = grid.parallelStream().filter(c -> c.isVisited()).collect(Collectors.toList());
		
		if (visited.size() <= grid.size() / 3) {
			aldousBroder();
		} else {
			wilson();
		}
		
	}
	
	private void aldousBroder() {
		current.setVisited(true);
		List<Cell> neighs = current.getAllNeighbours(grid);
		Cell next = neighs.get(r.nextInt(neighs.size()));
		if (!next.isVisited()) {
			current.removeWalls(next);
		}
		current = next;
	}
	
	private void wilson() {
		if (current.isVisited()) {
			addPathToMaze();
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
