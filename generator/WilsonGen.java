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

/*Wilson's algorithm: This is an improved version of the Aldous-Broder algorithm, 
 * in that it produces Mazes with exactly the same texture as that algorithm (the algorithms are uniform with all possible Mazes generated with equal probability), 
 * however Wilson's algorithm runs much faster. It requires storage up to the size of the Maze. Begin by making a random starting cell part of the Maze. 
 * 
 * Proceed by picking a random cell not already part of the Maze, and doing a random walk until a cell is found which is already part of the Maze. 
 * Once the already created part of the Maze is hit, go back to the random cell that was picked, and carve along the path that was taken, adding those cells to the Maze. 
 * More specifically, when retracing the path, at each cell carve along the direction that the random walk most recently took when it left that cell. 
 * That avoids adding loops along the retraced path, resulting in a single long passage being appended to the Maze. 
 * The Maze is done when all cells have been appended to the Maze. 
 * This has similar performance issues as Aldous-Broder, where it may take a long time for the first random path to find the starting cell,
 * however once a few paths are in place, the rest of the Maze gets carved quickly. On average this runs five times faster than Aldous-Broder,
 * and takes less than twice as long as the top algorithms. Note this runs twice as fast when implemented as a wall adder,
 * because the whole boundary wall starts as part of the Maze, so the first walls are connected much quicker. 
 * */

public class WilsonGen {

	private List<Cell> grid;
	private Stack<Cell> stack = new Stack<Cell>();
	private Cell current;
	private Random r = new Random();

	public WilsonGen(List<Cell> grid, MazeGridPanel panel) {
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
			}
		});
		timer.start();
	}
	
	private void carve() {
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
