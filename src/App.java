import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Labyrinth lab = new Labyrinth();

        // ──────────────────────────────────────────── LABERINTO 1 ─────

        var lines = loadFile("./files/laberinto1");
        var maze = Labyrinth.parseMaze(lines);
        lab.loadMaze(maze);

        System.out.println("Laberinto 1: \n" + lab.toString());
        lab.solve();
        System.out.println("Solución: \n" + lab.toString());

        // ──────────────────────────────────────────── LABERINTO 2 ─────

        lines = loadFile("./files/laberinto2");
        maze = Labyrinth.parseMaze(lines);
        lab.loadMaze(maze);

        System.out.println("Laberinto 2 cargado: \n" + lab.toString());
        lab.solve();
        System.out.println("Solución: \n" + lab.toString());
    }

    public static ArrayList<String> loadFile (String path) {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        ArrayList<String> lines = new ArrayList<>();

        try {
            String current;

            while ((current = bufferedReader.readLine()) != null){
                //process the line as required
                lines.add(current);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
