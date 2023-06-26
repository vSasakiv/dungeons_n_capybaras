package dungeon_gen;

import tile.dungeon.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class DungeonGenerator {
    ArrayList<int[][]> dungeon;
    Random rand = new Random();

    public DungeonGenerator(){}

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);

        DungeonGenerator gen = new DungeonGenerator();
        ArrayList<int[][]> test = gen.generate("bienio", 105);
        for (int[][] matrix: test) {
            DungeonGenerator.printMatrix(matrix);
            System.out.println("\n\n");
        }
    }

    public ArrayList<int[][]> generate(String type, int side){
        DungeonAbstractFactory dungeonFactory = Objects.requireNonNull(DungeonFactoryProvider.getFactory(type));
        int[] center = this.getCenter(side);
        ArrayList<DungeonTile> rooms = dungeonFactory.createRoom();
        this.dungeon = new ArrayList<>();
        for (int i = 0; i < dungeonFactory.maxLayers(); i++)
            this.dungeon.add(new int[side][side]);

        this.generateCorridor(dungeonFactory, center);
        this.addRoom(rooms.get(0).getMapTileNumbers(), center[0], center[0]); // initial room
        addEntrance(dungeonFactory, rooms.get(0).getMapTileNumbers(), center, 0, 0);
        this.addRoom(rooms.get(0).getMapTileNumbers(), center[2], center[2]); // boss room
        addEntrance(dungeonFactory, rooms.get(0).getMapTileNumbers(), center, 2, 2);
        genRooms(dungeonFactory, rooms, center);

        return this.dungeon;
    }

    private void generateCorridor(DungeonAbstractFactory dungeonFactory, int[] center){
        ArrayList<int[][]> horCorridor = dungeonFactory.createHorizontalCorridor().getMapTileNumbers();
        ArrayList<int[][]> verCorridor = dungeonFactory.createVerticalCorridor().getMapTileNumbers();
        for (int k = 0; k < horCorridor.size(); k++)
            for (int i = 0; i < 3; i++)
                for (int j = center[0]; j < center[2]; j+= 15){
                    putMatrix(this.dungeon.get(k), horCorridor.get(k), center[i], j);
                    putMatrix(this.dungeon.get(k), verCorridor.get(k), j, center[i]);
                }
    }
    private int[] getCenter(int side){
        int[] center = new int[3];
        for (int i = 0; i < 3; i++)
            center[i] = (side / 3) * i + (side / 6);
        return center;
    }
    private void addRoom(ArrayList<int[][]> room, int x, int y){
        for (int i = 0; i < room.size(); i++)
            DungeonGenerator.putMatrix(this.dungeon.get(i), room.get(i), x, y);
    }
    private void addEntrance(DungeonAbstractFactory dungeonFactory, ArrayList<int[][]> room, int[] center, int centerX, int centerY){
        ArrayList<int[][]> horizontalRightEntrance = dungeonFactory.createHorizontalRightEntrance().getMapTileNumbers();
        ArrayList<int[][]> verticalUpEntrance = dungeonFactory.createVerticalUpEntrance().getMapTileNumbers();
        ArrayList<int[][]> horizontalLeftEntrance = dungeonFactory.createHorizontalLeftEntrance().getMapTileNumbers();
        ArrayList<int[][]> verticalDownEntrance = dungeonFactory.createVerticalUpEntrance().getMapTileNumbers();

        if (centerX == 0 || centerX == 1)
            addRoom(verticalDownEntrance, center[centerX] + 1 + room.get(0).length / 2, center[centerY]);
        if (centerY == 0 || centerY == 1)
            addRoom(horizontalRightEntrance, center[centerX], center[centerY] + 1 + room.get(0).length / 2);
        if (centerX == 2 || centerX == 1)
            addRoom(verticalUpEntrance, center[centerX] - room.get(0).length / 2, center[centerY]);
        if (centerY == 2 || centerY == 1)
            addRoom(horizontalLeftEntrance, center[centerX] , center[centerY]  - 1 - room.get(0).length / 2);
    }

    private void genRooms(DungeonAbstractFactory dungeonFactory, ArrayList<DungeonTile> rooms, int[] center){
        int[][] placed = new int[3][3];
        placed[0][0] = 1;
        placed[2][2] = 1;
        for (int i = 0; i < 7;) {
            int x = rand.nextInt(3);
            int y = rand.nextInt(3);
            if (placed[x][y] == 0) {
                ArrayList<int[][]> room = rooms.get(rand.nextInt(rooms.size())).getMapTileNumbers();
                this.addRoom(room, center[x], center[y]);
                this.addEntrance(dungeonFactory, room, center, x, y);
                placed[x][y] = 1;
                i++;
            }
        }
    }

    private static void putMatrix(int[][] sourceMatrix, int[][] insertMatrix, int x, int y){
        for (int i = 0, i2 = x - insertMatrix.length/2; i < insertMatrix.length; i++, i2++)
            for (int j = 0, j2 = y - insertMatrix[0].length/2; j < insertMatrix[0].length; j++, j2++)
                sourceMatrix[i2][j2] = insertMatrix[i][j];
    }

    private static void printMatrix(int[][] matrix){
        for (int[] iv: matrix) {
            for (int i: iv)
                System.out.print(i + " ");
            System.out.print("\n");
        }
    }
}
