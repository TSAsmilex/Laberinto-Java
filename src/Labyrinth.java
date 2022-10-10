import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

enum Tile {
    WALL,
    SPACE,
    PATH,
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
    private HashSet<Point> visited = new HashSet<>();

    private Point entrance = new Point();
    private Point exit = new Point();


    // ───────────────────────────────────────────────────────── CONSTRUCTORES ─────


    public Labyrinth() {
        maze = new ArrayList<>();
    }


    // ──────────────────────────────────────────────────────────── RESOLUCION ─────


    public void solve() {
        var atExit = false;

        while (!atExit) {
            var lastPosition = new Point();
            lastPosition = this.currentPosition;

            var priorities = CircularDirection.getDirections(currentOrientation);

            // Escoge una dirección y muévete hacia allí.
            // Orden: Derecha - Arriba - Izquierda - Detrás.
            while (!priorities.isEmpty()) {
                var nextDir = priorities.poll();

                if (canMove(nextDir)) {
                    var posiblePosition = nextPosition(currentPosition, nextDir);

                    if (!visited.contains(posiblePosition)) {
                        move(nextDir);
                        priorities.clear();
                    }
                }
            }

            atExit = currentPosition.equals(exit);

            // No nos hemos movido y no estamos en la salida => dar marcha atrás.
            if (!atExit && lastPosition.equals(currentPosition)) {
                if (maze.get(currentPosition.x).get(currentPosition.y) == Tile.PATH) {
                    maze.get(currentPosition.x).set(currentPosition.y, Tile.SPACE);
                }
                currentPosition = stack.pop();
            }
        }
    }


    public void move (Direction dir) {
        this.stack.push(currentPosition);

        this.currentOrientation = dir;
        this.currentPosition    = nextPosition(currentPosition, dir);

        this.visited.add(currentPosition);
        this.maze.get(currentPosition.x).set(currentPosition.y, Tile.PATH);
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

        switch (dir) {
            case UP     -> x--;
            case DOWN   -> x++;
            case LEFT   -> y--;
            case RIGHT  -> y++;
        }

        var next = new Point();
        next.x = x;
        next.y = y;

        return next;
    }


    // ─────────────────────────────────────────────────── PARSING AND LOADING ─────


    public static ArrayList<ArrayList<Tile>> parseMaze (ArrayList<String> lines) {
        ArrayList<ArrayList<Tile>> maze = new ArrayList<>();

        for (String line: lines) {
            var mazeLine = new ArrayList<Tile>();

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                switch (c) {
                    case '#' -> mazeLine.add(Tile.WALL);
                    case ' ' -> mazeLine.add(Tile.SPACE);
                    case 'E' -> mazeLine.add(Tile.ENTRANCE);
                    case 'S' -> mazeLine.add(Tile.EXIT);
                }
            }

            maze.add(mazeLine);
        }

        return maze;
    }


    public void loadMaze(ArrayList<ArrayList<Tile>> maze) {
        this.maze.clear();
        this.stack.clear();
        this.visited.clear();
        this.maze = maze;

        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).size(); j++) {
                if (maze.get(i).get(j) == Tile.ENTRANCE) {
                    this.entrance.x = i;
                    this.entrance.y = j;

                    this.currentPosition = entrance;

                    this.currentOrientation = Direction.DOWN;
                }
                else if (maze.get(i).get(j) == Tile.EXIT) {
                    this.exit.x = i;
                    this.exit.y = j;
                }
            }
        }
    }


    // ────────────────────────────────────────────────────────────── PRINTING ─────


    public String toString() {
        String salida = new String();

        for (var line: maze) {
            for (var tile: line) {
                switch (tile) {
                    case WALL     -> salida += "#";
                    case SPACE    -> salida += " ";
                    case PATH     -> salida += ".";
                    case ENTRANCE -> salida += "E";
                    case EXIT     -> salida += "S";
                }
            }
            salida += "\n";
        }

        return salida;
    }
}
