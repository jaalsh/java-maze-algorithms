package util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Maze;

public class Cell {

	private int x, y, distance;
	
	private Cell parent;
	
	private boolean visited = false;
	private boolean path = false;
	private boolean deadEnd = false;
	
	protected boolean[] walls = {true, true, true, true};
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.distance = -1;
	}
	
	public void draw(Graphics g) {
		int x2 = x * Maze.W;
	    int y2 = y * Maze.W;
	    
	    if (visited) {
	    	g.setColor(Color.MAGENTA);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }
	   
	    if (path) {
	    	g.setColor(Color.BLUE);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    } else if (deadEnd) {
	    	g.setColor(Color.RED);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }
	    
	    if(x == 0 && y == 0) {
	    	g.setColor(Color.GREEN);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }
	    
	    g.setColor(Color.WHITE);
	    if (walls[0]) {
	    	g.drawLine(x2, y2, x2+Maze.W, y2);
	    }
	    if (walls[1]) {
	    	g.drawLine(x2+Maze.W, y2, x2+Maze.W, y2+Maze.W);
	    }
	    if (walls[2]) {
	    	g.drawLine(x2+Maze.W, y2+Maze.W, x2, y2+Maze.W);
	    }
	    if (walls[3]) {
	    	g.drawLine(x2, y2+Maze.W, x2, y2);
	    } 
	}
	
	public void highlight(Graphics g) {
		int x2 = x * Maze.W;
	    int y2 = y * Maze.W;
	    g.setColor(Color.ORANGE);
    	g.fillRect(x2+1, y2+1, Maze.W-1, Maze.W-1);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean[] getWalls() {
		return walls;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	// used to solve... this needs a rework minds
	private Cell checkNeighbour(List<Cell> grid, Cell neighbour, boolean path) {
		if (grid.contains(neighbour)) {
			Cell c = grid.get(grid.indexOf(neighbour));
			if (path) return c.deadEnd ? null : c;
			else return c.visited ? null : c;
		} else {
			return null;
		}
	}
	
	private Cell randomNeighbour(List<Cell> neighbours) {
		if (neighbours.size() > 0) {
			return neighbours.get(new Random().nextInt(neighbours.size()));
		} else {
			return null;
		}
	}
	
	public Cell checkAllNeighbour(List<Cell> grid, Cell neighbour) {
		if (grid.contains(neighbour)) {
			Cell c = grid.get(grid.indexOf(neighbour));
			return c.path ? null : c;
		} else {
			return null;
		}
	}
	
	public Cell getAllNeighbour(List<Cell> grid) {

		List<Cell> neighbours = new ArrayList<Cell>(4);
		
		Cell top = checkAllNeighbour(grid, new Cell(x, y - 1));
		Cell right = checkAllNeighbour(grid, new Cell(x + 1, y));
		Cell bottom = checkAllNeighbour(grid, new Cell(x, y + 1));
		Cell left = checkAllNeighbour(grid, new Cell(x - 1, y));
		
		if (top != null) neighbours.add(top);
		if (right != null) neighbours.add(right);
		if (bottom != null) neighbours.add(bottom);
		if (left != null) neighbours.add(left);
		
		if (neighbours.size() ==  1) {
			return neighbours.get(0);
		}
		return randomNeighbour(neighbours);
	}

	// this could actually be an array of size 4. 0 = top, 1 = right, 2 = bottom, 3 = right
	public Cell getNeighbour(List<Cell> grid) {
		
		List<Cell> neighbours = getNeighbours(grid);
		
		if (neighbours.size() ==  1) {
			return neighbours.get(0);
		}
		return randomNeighbour(neighbours);
	}
	
	public List<Cell> getNeighbours(List<Cell> grid) {
		
		List<Cell> neighbours = new ArrayList<Cell>(4);
		
		Cell top = checkNeighbour(grid, new Cell(x, y - 1), false);
		Cell right = checkNeighbour(grid, new Cell(x + 1, y), false);
		Cell bottom = checkNeighbour(grid, new Cell(x, y + 1), false);
		Cell left = checkNeighbour(grid, new Cell(x - 1, y), false);
		
		if (top != null) neighbours.add(top);
		if (right != null) neighbours.add(right);
		if (bottom != null) neighbours.add(bottom);
		if (left != null) neighbours.add(left);
		
		return neighbours;
	}
	
	 //do i not have this method already? lol
	private Cell checkValidMoveNeighbour(List<Cell> grid, Cell neighbour) {
		if (grid.contains(neighbour)) {
			return grid.get(grid.indexOf(neighbour));
		} else {
			return null;
		}
	}
	
	public List<Cell> getValidMoveNeighbours(List<Cell> grid) {
		List<Cell> neighbours = new ArrayList<Cell>(4);
		
		Cell top = checkValidMoveNeighbour(grid, new Cell(x, y - 1));
		Cell right = checkValidMoveNeighbour(grid, new Cell(x + 1, y));
		Cell bottom = checkValidMoveNeighbour(grid, new Cell(x, y + 1));
		Cell left = checkValidMoveNeighbour(grid, new Cell(x - 1, y));
		
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
		
		
		return neighbours;
	}
	
	public Cell getPathNeighbour(List<Cell> grid) {
		List<Cell> neighbours = new ArrayList<Cell>();
		
		Cell top = checkNeighbour(grid, new Cell(x, y - 1), true);
		Cell right = checkNeighbour(grid, new Cell(x + 1, y), true);
		Cell bottom = checkNeighbour(grid, new Cell(x, y + 1), true);
		Cell left = checkNeighbour(grid, new Cell(x - 1, y), true);
		
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
		
		if (neighbours.size() ==  1) {
			return neighbours.get(0);
		}
		return randomNeighbour(neighbours);
	}
	
	public void setDeadEnd(boolean deadEnd) {
		this.deadEnd = deadEnd;
	}

	public boolean isPath() {
		return path;
	}

	public void setPath(boolean path) {
		this.path = path;
	}
	
	public void removeWalls(Cell next) {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return the parent
	 */
	public Cell getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Cell parent) {
		this.parent = parent;
	}
}
