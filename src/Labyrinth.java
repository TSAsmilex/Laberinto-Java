import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

enum Tile {
    WALL, 
    SPACE, 
    VISITED, 
    ENTRANCE, 
    EXIT
};

enum Direction {
    UP, 
    DOWN, 
    LEFT, 
    RIGHT
}

public class Labyrinth {
    private ArrayList<ArrayList<Tile>> maze;
    
    private Point currentPosition = new Point(); 
    private Direction currentOrientation;

    private Stack<Point> stack = new Stack<>();

    private Point entrance = new Point();
    private Point exit = new Point(); 


    // ───────────────────────────────────────────────────────── CONSTRUCTORES ─────

    public Labyrinth() {
        maze = new ArrayList<>();
    }

    // ──────────────────────────────────────────────────────── ENTRADA SALIDA ─────


    public void loadMaze(ArrayList<ArrayList<Tile>> maze) {
        this.maze = maze;

        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).size(); j++) {
                if (maze.get(i).get(j) == Tile.ENTRANCE) {
                    this.entrance.x = i;
                    this.entrance.y = j;

                    this.currentPosition.x = entrance.x;
                    this.currentPosition.y = entrance.y;

                    this.currentOrientation = Direction.DOWN;
                }
                else if (maze.get(i).get(j) == Tile.EXIT) {
                    this.exit.x = i; 
                    this.exit.y = j;
                }
            }
        }
    }

    public void solve() {
        var atExit = false;

        while (!atExit) {      
            var lastPosition = new Point();
            lastPosition.x = this.currentPosition.x;
            lastPosition.y = this.currentPosition.y;

            var priorities = CircularDirection.getDirections(currentOrientation);
            
            while (!priorities.isEmpty()) {
                var nextDir = priorities.poll(); 

                if (canMove(nextDir)) {
                    var posiblePosition = nextPosition(currentPosition, nextDir);

                    if (maze.get(posiblePosition.x).get(posiblePosition.y) != Tile.VISITED) {
                        move(nextDir);
                        stack.add(currentPosition);
                        priorities.clear();
                    }
                }
            }

            atExit = currentPosition.x == exit.x && currentPosition.y == exit.y;
            
            if (!atExit && lastPosition.x == currentPosition.x && currentPosition.y == lastPosition.y) {
                currentPosition = stack.pop();
            }
        }
    }

    public String toString() {
        String salida = new String(); 

        for (var line: maze) {
            for (var tile: line) {
                switch (tile) {
                    case WALL:
                        salida += "#";
                        break;
                    case SPACE:
                        salida += " ";
                        break;
                    case VISITED:
                        salida += ".";
                        break;
                    case ENTRANCE:
                        salida += "E";
                        break;
                    case EXIT:
                        salida += "S";
                        break;
                }
            }
            salida += "\n";
        }

        return salida;
    }

    // ──────────────────────────────────────────────────────────── MOVIMIENTO ─────
    

    public void move (Direction dir) {
        this.stack.push(currentPosition);
        this.currentOrientation = dir;
        this.currentPosition = nextPosition(currentPosition, dir);
        this.maze.get(currentPosition.x).set(currentPosition.y, Tile.VISITED);
    }

    public boolean canMove (Direction dir) {
        var next = nextPosition(currentPosition, dir);

        if (next.x < 0 || next.x >= maze.size()) {
            return false;
        }
        else if (next.y < 0 || next.y >= maze.get(next.x).size()) {
            return false;
        }
        else if (maze.get(next.x).get(next.y) == Tile.WALL) {
            return false;
        }
        else {
            return true;
        }
    }


    public Point nextPosition(Point position, Direction dir) {
        int x = position.x, y = position.y;

        if (Direction.UP == dir) {
            x -= 1;
        }
        else if (Direction.RIGHT == dir) {
            y += 1;
        }
        else if (Direction.LEFT == dir) {
            y -= 1;
        }
        else if (Direction.DOWN == dir) {
            x += 1;
        }

        var next = new Point();
        next.x = x;
        next.y = y;

        return next;
    }
}
