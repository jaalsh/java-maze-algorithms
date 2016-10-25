package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;

/**
 * TODO: 
 * - Analyse and increase efficiency.
 * @author jaalsh
 *
 */
public class ZigZagGen {
	
	private final Queue<Cell> queue;
	private Cell current, goal;
	private final List<Cell> grid;
	private List<Cell> notInMaze = new ArrayList<Cell>();
	private final Random r = new Random();

	public ZigZagGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		goal = grid.get(r.nextInt(grid.size()));
		goal.setVisited(true);
		
		queue = new PriorityQueue<Cell>(new CellDistanceFromGoalComparator());
		current = grid.get(r.nextInt(grid.size()));
		queue.offer(current);
		
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
		Cell next = queue.poll();
		Cell prev = current;
		current.removeWalls(next);
		if (next.isVisited()) {
			goal = current;
			notInMaze = grid.parallelStream().filter(c -> !c.isVisited()).collect(Collectors.toList());
			current = notInMaze.get(r.nextInt(notInMaze.size()));
			queue.clear();
		} else {
			current = next;
		}
		
		current.setVisited(true);
		// we never enter carve again after here, need to check if it's last cell before and remove random wall.
		// could either check unvisited (notInMaze) size == 1 or check for 4 walls with all visited neighbours - but this situation may 
		// happen under different circumstances. I think check size == 1 is best.
		if(notInMaze.size() == 1) {
			// last cell to add
			current.removeWalls(current.getAllNeighbours(grid).get(0));
		}
		
		// force zig zag pattern. 
		if (prev == current.getBottomNeighbour(grid) || prev == current.getTopNeighbour(grid)) {
			Cell left = current.getLeftNeighbour(grid);
			Cell right = current.getRightNeighbour(grid);
			
			if (left != null) queue.offer(left);
			if (right != null) queue.offer(right);
		} else {
			Cell top = current.getTopNeighbour(grid);
			Cell bottom = current.getBottomNeighbour(grid);
			
			if (top != null) queue.offer(top);
			if (bottom != null) queue.offer(bottom);
		}
	}

	private class CellDistanceFromGoalComparator implements Comparator<Cell> {
		@Override
		public int compare(Cell arg0, Cell arg1) {
			if (getDistanceFromGoal(arg0) > getDistanceFromGoal(arg1)) {
				return 1;
			} else {
				return getDistanceFromGoal(arg0) < getDistanceFromGoal(arg1) ? -1 : 0;
			}
		}
		
		private double getDistanceFromGoal(Cell c) {
			return Math.hypot(c.getX() - goal.getX(), c.getY() - goal.getY());
		}
	}
}
