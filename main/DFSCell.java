package main;

import java.util.*;

public class DFSCell extends Cell {
	
	public DFSCell(int x, int y) {
		super(x, y);
	}
	
	private DFSCell checkNeighbour(List<DFSCell> grid, DFSCell neighbour, boolean path) {
		if (grid.contains(neighbour)) {
			DFSCell c = grid.get(grid.indexOf(neighbour));
			if (path) return c.deadEnd ? null : c;
			else return c.visited ? null : c;
		} else {
			return null;
		}
	}
	
	private DFSCell randomNeighbour(List<DFSCell> neighbours) {
		if (neighbours.size() > 0) {
			return neighbours.get(new Random().nextInt(neighbours.size()));
		} else {
			return null;
		}
	}

	// this could actually be an array of size 4. 0 = top, 1 = right, 2 = bottom, 3 = right
	public DFSCell getNeighbour(List<DFSCell> grid) {
			
		List<DFSCell> neighbours = new ArrayList<DFSCell>();
		
		DFSCell top = checkNeighbour(grid, new DFSCell(x, y - 1), false);
		DFSCell right = checkNeighbour(grid, new DFSCell(x + 1, y), false);
		DFSCell bottom = checkNeighbour(grid, new DFSCell(x, y + 1), false);
		DFSCell left = checkNeighbour(grid, new DFSCell(x - 1, y), false);
		
		if (top != null) neighbours.add(top);
		if (right != null) neighbours.add(right);
		if (bottom != null) neighbours.add(bottom);
		if (left != null) neighbours.add(left);
		
		return randomNeighbour(neighbours);
	}
	
	public DFSCell getPathNeighbour(List<DFSCell> grid) {
		List<DFSCell> neighbours = new ArrayList<DFSCell>();
		
		DFSCell top = checkNeighbour(grid, new DFSCell(x, y - 1), true);
		DFSCell right = checkNeighbour(grid, new DFSCell(x + 1, y), true);
		DFSCell bottom = checkNeighbour(grid, new DFSCell(x, y + 1), true);
		DFSCell left = checkNeighbour(grid, new DFSCell(x - 1, y), true);
		
		if (top != null) {
			if (!walls[0]) neighbours.add(top);
		}
		if (right != null) {
			if (!walls[1]) neighbours.add(right);
		}
		
		if (bottom != null) {
			if (!walls[2]) neighbours.add(bottom);
		}
		
		if (left != null) {
			if (!walls[3]) neighbours.add(left);
		}
		
		return randomNeighbour(neighbours);
	}
	
	public void removeWalls(DFSCell next) {
		int x = this.x - next.x;
		 // top 0, right 1, bottom 2, left 3
		
		if(x == 1) {
			walls[3] = false;
			next.walls[1] = false;
		} else if (x == -1) {
			walls[1] = false;
			next.walls[3] = false;
		}
		
		int y = this.y - next.y;
		
		if(y == 1) {
			walls[0] = false;
			next.walls[2] = false;
		} else if (y == -1) {
			walls[2] = false;
			next.walls[0] = false;
		}
	}
}