import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

// Walls are top, right, bottom, left

public class Cell {

	private int x, y;
	private boolean visited = false;
	private boolean[] walls = {true, true, true, true};
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	// could check for neighbour visited here and remove the 4 if statements in checkNeighbours
	private Cell getNeighbour(List<Cell> grid, Cell neighbour) {
		if (grid.contains(neighbour)) {
			return grid.get(grid.indexOf(neighbour));
		} else {
			return null;
		}
	}

	// this could actually be an array of size 4. 0 = top, 1 = right, 2 = bottom, 3 = right
	public Cell checkNeighbours(List<Cell> grid, Cell current) {
			
		List<Cell> neighbours = new ArrayList<Cell>();
		
		Cell top = getNeighbour(grid, new Cell(x, y - 1));
		Cell right = getNeighbour(grid, new Cell(x + 1, y));
		Cell bottom = getNeighbour(grid, new Cell(x, y + 1));
		Cell left = getNeighbour(grid, new Cell(x - 1, y));
		
		if (top != null) {
			if (!top.visited) neighbours.add(top);
		}
		if (right != null) {
			 if (!right.visited) neighbours.add(right);
		}
		if (bottom != null ) {
			if (!bottom.visited) neighbours.add(bottom);
		}
		if (left != null) {
			 if (!left.visited) {
				 neighbours.add(left);
			 }
		}
		
		if (neighbours.size() > 0) {
			return neighbours.get(new Random().nextInt(neighbours.size()));
		} else {
			return null;
		}
	}
	
	public void removeWalls(Cell current, Cell next) {
		int x = current.x - next.x;
		 // top 0, right 1, bottom 2, left 3
		
		if(x == 1) {
			current.walls[3] = false;
			next.walls[1] = false;
		} else if (x == -1) {
			current.walls[1] = false;
			next.walls[3] = false;
		}
		
		int y = this.y - next.y;
		
		if(y == 1) {
			current.walls[0] = false;
			next.walls[2] = false;
		} else if (y == -1) {
			current.walls[2] = false;
			next.walls[0] = false;
		}
	}
	
	public void draw(Graphics g) {
		int x2 = x * MazePanel.W;
	    int y2 = y * MazePanel.W;
	    
	    if (visited) {
	    	g.setColor(Color.magenta);
	    	g.fillRect(x2, y2, MazePanel.W, MazePanel.W);
	    }
	 
	    g.setColor(Color.WHITE);
	    if (walls[0]) {
	    	g.drawLine(x2, y2, x2+MazePanel.W, y2);
	    }
	    if (walls[1]) {
	    	g.drawLine(x2+MazePanel.W, y2, x2+MazePanel.W, y2+MazePanel.W);
	    }
	    if (walls[2]) {
	    	g.drawLine(x2+MazePanel.W, y2+MazePanel.W, x2, y2+MazePanel.W);
	    }
	    if (walls[3]) {
	    	g.drawLine(x2, y2+MazePanel.W, x2, y2);
	    } 
	}
	
	public void highlight(Graphics g) {
		int x2 = x * MazePanel.W;
	    int y2 = y * MazePanel.W;
	    g.setColor(Color.ORANGE);
    	g.fillRect(x2+1, y2+1, MazePanel.W-1, MazePanel.W-1);
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
		if (visited != other.visited)
			return false;
		if (!Arrays.equals(walls, other.walls))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}