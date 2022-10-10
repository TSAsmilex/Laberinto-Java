import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CircularDirection {
    /*
        Dada una cierta orientación, devuelve una cola con
        las prioridades de las direcciones a seguir.

        Es decir, si estoy mirando hacia arriba, la cola devolverá
            RIGHT, UP, LEFT, DOWN
        Y si estoy mirando hacia la izquierda, sería
            UP, LEFT, DOWN, RIGHT

        En esencia, estoy simulando el comportamiento de Z4, siendo el neutro UP.
                 UP
           LEFT      RIGHT
                DOWN
        Dado un cierto z en Z4, la CircularDirection devolverá
            z+1, z, z-1, z-2

        TL;DR pídele a la clase las direcciones con getDirections(dir) y despreocúpate
        del motivo por el que funciona esto.
    */
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
