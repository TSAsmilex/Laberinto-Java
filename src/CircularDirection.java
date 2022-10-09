import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CircularDirection {
    static ArrayList<Direction> directions = new ArrayList<>(
        Arrays.asList(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)
    );

    static Queue<Direction> getDirections (Direction dir) {
        Queue<Direction> nextDirections = new LinkedList<Direction>();

        int i = 0;
        
        switch (dir) {
            case UP:    
                i = directions.indexOf(Direction.RIGHT); 
                break; 
            case RIGHT:
                i = directions.indexOf(Direction.DOWN);
                break; 
            case DOWN:
                i = directions.indexOf(Direction.LEFT);
                break;
            default:
                i = directions.indexOf(Direction.UP);
                break;
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
