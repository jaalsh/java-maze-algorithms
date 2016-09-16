package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class DijkstraSolve {
	
	private Queue<Cell> gridQueue;
	private Cell current;
	private List<Cell> grid;

	public DijkstraSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		gridQueue = new PriorityQueue<Cell>(new CellDistanceFromGoalComparator());
		current = grid.get(0);
		current.setDistance(0);
		gridQueue.offer(current);
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!current.equals(grid.get(grid.size() - 1))) {
					flood();
				} else {
					drawPath();
					Maze.solved = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
				timer.setDelay(Maze.speed);
			}
		});
		timer.start();
	}
	
	private void flood() {
		current.setDeadEnd(true);
		current = gridQueue.poll();
		List<Cell> adjacentCells = current.getValidMoveNeighbours(grid);
		for (Cell c : adjacentCells) {
			if (c.getDistance() == -1) {
				c.setDistance(current.getDistance() + 1);
				c.setParent(current);
				gridQueue.offer(c);
			}
		}
	}
	
	private void drawPath() {
		while (current != grid.get(0)) {
			current.setPath(true);
			current = current.getParent();
		}
	}
	
	private class CellDistanceFromGoalComparator implements Comparator<Cell> {
		
		Cell goal = grid.get(grid.size() - 1);
		
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


