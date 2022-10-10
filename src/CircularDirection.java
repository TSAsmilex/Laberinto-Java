import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CircularDirection {
    static ArrayList<Direction> directions = new ArrayList<>(
        Arrays.asList(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)
    );

    static Queue<Direction> getDirections (Direction dir) {
        Queue<Direction> nextDirections = new LinkedList<Direction>();

        int i = 0;

        switch (dir) {
            case UP     -> i = directions.indexOf(Direction.RIGHT);
            case RIGHT  -> i = directions.indexOf(Direction.DOWN);
            case DOWN   -> i = directions.indexOf(Direction.LEFT);
            default     -> i = directions.indexOf(Direction.UP);
        }

        for (int j = 0; j < directions.size(); j++) {
            nextDirections.add(directions.get(i));

            i--;
            if (i < 0) {
                i = directions.size() - 1;
            }
        }

        return nextDirections;
    }
}
