package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;
import util.DisjointSets;

public class KruskalGen {

	private Stack<Cell> stack = new Stack<Cell>();
	private DisjointSets disjointSet = new DisjointSets();
	private List<Cell> grid = new ArrayList<Cell>(); // may not need
	private Cell current;

	public KruskalGen(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(0);
		
		for (int i = 0; i < grid.size(); i++) {
			grid.get(i).setId(i);
			disjointSet.create_set(grid.get(i).getId());
		}
		
		stack.addAll(grid);
		
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
		current = stack.pop();
		current.setVisited(true);
		
		List<Cell> neighs = current.getAllNeighbours(grid);
		
		for (Cell n : neighs) {
			if (disjointSet.find_set(current.getId()) != disjointSet.find_set(n.getId())) {
				current.removeWalls(n);
				disjointSet.union(current.getId(), n.getId());
			}
		}
				
		Collections.shuffle(stack);
	}
}
