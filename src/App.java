import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        // Abrir archivos
        var lines = loadFile("./files/laberinto1");

        // Cargar laberinto
        Labyrinth lab = new Labyrinth();
        var maze = parseMaze(lines);
        lab.loadMaze(maze);
        lab.solve();

        // Solución -> salida a pantalla. Print del laberinto
        System.out.println(lab.toString());

        lines = loadFile("./files/laberinto2");
        lab.loadMaze(parseMaze(lines));
        lab.solve();
        System.out.println(lab.toString());

    }

    public static ArrayList<String> loadFile (String path) {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        
        ArrayList<String> lines = new ArrayList<>();

        String curLine;

        try {
            while ((curLine = bufferedReader.readLine()) != null){
                //process the line as required
                lines.add(curLine);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        return lines;
    }

    public static ArrayList<ArrayList<Tile>> parseMaze (ArrayList<String> lines) {
        ArrayList<ArrayList<Tile>> maze = new ArrayList<>();

        for (String line: lines) {
            var mazeLine = new ArrayList<Tile>();

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                switch (c) {
                    case '#':
                        mazeLine.add(Tile.WALL);
                        break;
                    case ' ':
                        mazeLine.add(Tile.SPACE);
                        break;
                    case 'E':
                        mazeLine.add(Tile.ENTRANCE);
                        break;
                    case 'S':
                        mazeLine.add(Tile.EXIT);
                        break;
                }
            }
            
            maze.add(mazeLine);
        }


        return maze;
    }
}
