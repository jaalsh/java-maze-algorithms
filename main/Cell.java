package main;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Cell {

	protected int x;
	protected int y;
	
	protected boolean visited = false;
	protected boolean path = false;
	protected boolean deadEnd = false;
	
	protected boolean[] walls = {true, true, true, true};
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
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
	    } else  if (deadEnd) {
	    	g.setColor(Color.RED);
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
	
	public void setDeadEnd(boolean deadEnd) {
		this.deadEnd = deadEnd;
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
