package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class BFSSolve {

	// Shortest path finder: As the name indicates, this algorithm finds the
	// shortest solution, picking one if there are multiple shortest solutions.
	// It focuses on you multiple times, is fast for all types of Mazes, and
	// requires quite a bit of extra memory proportional to the size of the
	// Maze. Like the collision solver, this basically floods the Maze with
	// "water", such that all distances from the start are filled in at the same
	// time (a breadth first search in Computer Science terms) however each
	// "drop" or pixel remembers which pixel it was filled in by. Once the
	// solution is hit by a "drop", trace backwards from it to the beginning and
	// that's a shortest path. 
	
	// This algorithm works well given any input,
	// because unlike most of the others, this doesn't require the Maze to have
	// any one pixel wide passages that can be followed. Note this is basically
	// the A* path finding algorithm without a heuristic so all movement is
	// given equal weight.
	
	private Queue<Cell> gridQueue = new LinkedList<Cell>();
	private Cell current;
	private List<Cell> grid;

	public BFSSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
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
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
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
}
