import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

// Walls are top, right, bottom, left

public class Cell {

	private int x, y;
	private boolean visited = false;
	private boolean path = false;
	private boolean deadEnd = false;
	public boolean isDeadEnd() {
		return deadEnd;
	}

	public void setDeadEnd(boolean deadEnd) {
		this.deadEnd = deadEnd;
	}

	private boolean[] walls = {true, true, true, true};
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	// also rename methods! i.e. checkNeighbours only gives one neighbour etc. rename it.
	
	private Cell getNeighbour(List<Cell> grid, Cell neighbour) {
		if (grid.contains(neighbour)) {
			Cell c = grid.get(grid.indexOf(neighbour));
			return c.visited ? null : c;
		} else {
			return null;
		}
	}

	// this could actually be an array of size 4. 0 = top, 1 = right, 2 = bottom, 3 = right
	public Cell checkNeighbours(List<Cell> grid) {
			
		List<Cell> neighbours = new ArrayList<Cell>();
		
		Cell top = getNeighbour(grid, new Cell(x, y - 1));
		Cell right = getNeighbour(grid, new Cell(x + 1, y));
		Cell bottom = getNeighbour(grid, new Cell(x, y + 1));
		Cell left = getNeighbour(grid, new Cell(x - 1, y));
		
		if (top != null) neighbours.add(top);
		if (right != null) neighbours.add(right);
		if (bottom != null) neighbours.add(bottom);
		if (left != null) neighbours.add(left);
		
		if (neighbours.size() > 0) {
			return neighbours.get(new Random().nextInt(neighbours.size()));
		} else {
			return null;
		}
	}
	
	private Cell getPathNeighbour(List<Cell> grid, Cell neighbour) {
		if (grid.contains(neighbour)) {
			Cell c = grid.get(grid.indexOf(neighbour));
			return c.deadEnd ? null : c;
		} else {
			return null;
		}
	}
	
	public Cell checkPathNeighbours(List<Cell> grid) {
		List<Cell> neighbours = new ArrayList<Cell>();
		
		Cell top = getPathNeighbour(grid, new Cell(x, y - 1));
		Cell right = getPathNeighbour(grid, new Cell(x + 1, y));
		Cell bottom = getPathNeighbour(grid, new Cell(x, y + 1));
		Cell left = getPathNeighbour(grid, new Cell(x - 1, y));
		// SOMETHING WRONG WITH WALLS! COULD BE THAT IT'S REMOVED ON NEIGHBOUR BUT NOT ON CURRENT OR SOMETHING?? DONT THINK SO!
		if (top != null) {
			if (!walls[0] && !top.walls[2]) neighbours.add(top);
		}
		if (right != null) {
			if (!walls[1] && !right.walls[3]) neighbours.add(right);
		}
		
		if (bottom != null) {
			if (!walls[2] && !bottom.walls[0]) neighbours.add(bottom);
		}
		
		if (left != null) {
			if (!walls[3] && !left.walls[1]) neighbours.add(left);
		}
		
		// neighbours should always be <= 2?
		
		if (neighbours.size() > 0) {
			return neighbours.get(new Random().nextInt(neighbours.size()));
		} else {
			return null;
		}
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
	
	public void draw(Graphics g) {
		int x2 = x * Maze.W;
	    int y2 = y * Maze.W;
	    
	    
	    if (visited) {
	    	g.setColor(Color.magenta);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }

	    // these cam be changed don't need to draw both?
	    if (deadEnd) {
	    	g.setColor(Color.RED);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }
	    
	    if (path) {
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

	public boolean isPath() {
		return path;
	}

	public void setPath(boolean path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (visited ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(walls);
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
}