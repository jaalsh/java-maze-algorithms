# Java Mazes

The primary goal of this project is to animate maze generation and solving algorithms. Not every algorithm is included, and the implementations may not be the most efficient
due to the animation involved. 

## Generation Algorithms 

* Aldous-Broder
* Binary Tree
* Depth First Search (Recursive Backtracker)
* Eller's
* Growing Forest
* Growing Tree
* Hunt & Kill
* Houston's
* Kruskal's
* Prim's
* Quad Depth First Search
* Sidewinder
* Wilson's

## Solving Algorithms

* Breadth First Search
* Bidirectional Depth First Search (Recursive Backtracker)
* Depth First Search (Recursive Backtracker)
* Dijkstra's

## Useful Links

* [astrolog.org Think Labyrinth: Maze Algorithms](http://www.astrolog.org/labyrnth/algrithm.htm)
* [wikipedia.org Maze generation algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm)
* [jamisbuck.org Maze Algorithms](http://www.jamisbuck.org/mazes/)

## Notes

### Growing Tree: 
  * Could use stack to always get "newest" cell or queue to always get "oldest" cell

## Eller's:
  * Eller's algorithm is typically implemented to generate one **row** at a time, however I've implemented it to focus on columns. The principles are the still the same.
  If you choose to focus on columns you can generate a maze with a more horizontal bias by choosing to carve down less frequently, likewise if you focus on rows you can       choose to carve right less frequently to generate a vertical biased maze. More help: [neocomputer.org Eller's Algorithm](http://www.neocomputer.org/projects/eller.html)