package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;

/*This is basically the simplest and fastest algorithm possible, however Mazes produced by it have a very biased texture. 
 * For each cell carve a passage either leading up or leading left, but not both. 
 * In the wall added version, for each vertex add a wall segment leading down or right, but not both. 
 * Each cell is independent of every other cell, where you don't have to refer to the state of any other cells when creating it.
 *  Hence this is a true memoryless Maze generation algorithm, with no limit to the size of Maze you can create. 
 *  This is basically a computer science binary tree, if you consider the upper left corner the root, 
 *  where each node or cell has one unique parent which is the cell above or to the left of it. 
 *  Binary tree Mazes are different than standard perfect Mazes, since about half the cell types can never exist in them. 
 *  For example there will never be a crossroads, and all dead ends have passages pointing up or left, and never down or right. 
 *  The Maze tends to have passages leading diagonally from upper left to lower right, 
 *  where the Maze is much easier to navigate from lower right to upper left.
 *  You will always be able to travel up or left, but never both,
 *  so you can always deterministically travel diagonally up and to the left without hitting any barriers. 
 *  Traveling down and to the right is when you'll encounter choices and dead ends. 
 *  Note if you flip a binary tree Maze upside down and treat passages as walls and vice versa, the result is basically another binary tree. 
 * 
 */
public class BinaryTreeGen {

	private List<Cell> grid = new ArrayList<Cell>();
	private Cell current;
	private int index;
	private Random r = new Random();

	public BinaryTreeGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		index = grid.size() - 1;
		current = grid.get(index);
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
	
	// if no right carve down AND if no down carve right!
	private void carve() {
		boolean topNeigh = grid.contains(new Cell(current.getX(), current.getY() - 1));
		boolean leftNeigh = grid.contains(new Cell(current.getX() - 1, current.getY()));
		if (topNeigh && leftNeigh) {
			carveDirection(r.nextInt(2));
		} else if (topNeigh) {
			carveDirection(0);
		} else if (leftNeigh) {
			carveDirection(1);
		}
		
		current.setVisited(true);
		
		
		if (index - 1 >= 0) {
			current = grid.get(--index);
		} 
		// else we break out of if statement in timer Action Listener and set maze to generated.
		
	}
	
	// 0 down
	// 1 right
	private void carveDirection(int dir) {
		if (dir == 0) {
			List<Cell> neighs = current.getAllNeighbours(grid);
			for (Cell c : neighs) {
				if (c.getY() + 1 == current.getY()) current.removeWalls(c);
			}
		} else {
			List<Cell> neighs = current.getAllNeighbours(grid);
			for (Cell c : neighs) {
				if (c.getX() + 1 == current.getX()) current.removeWalls(c);
			}
		}
	}
}
	