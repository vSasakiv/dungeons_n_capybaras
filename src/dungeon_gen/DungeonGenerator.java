package dungeon_gen;

import game_entity.static_entities.Door;
import gameloop.Constants;
import tile.dungeon.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class DungeonGenerator {
    // Array lista de matrizes, cada uma sendo uma layer, representando a dungeon final
    ArrayList<int[][]> dungeon;
    // Gerador aleatório para geração única
    Random rand = new Random();

    DungeonRoom[] combatRooms = new DungeonRoom[7];
    public DungeonGenerator(){}

    /**
     * @param type Tipo de dungeon a ser gerado
     * @param side tamanho do mapa
     * @return ArrayList de matrizes para a construção da dungeon
     */
    public ArrayList<int[][]> generate(String type, int side){
        // utiliza fábrica abstrata para obter os componentes para construção da dungeon
        DungeonAbstractFactory dungeonFactory = Objects.requireNonNull(DungeonFactoryProvider.getFactory(type));
        // array que representa oas 9 centros do mapa, sendo cada centro o ponto central de uma sala
        int[] center = this.getCenter(side);
        ArrayList<DungeonTile> rooms = dungeonFactory.createRoom();
        this.dungeon = new ArrayList<>();
        // inicializamos os valores da dungeon
        for (int i = 0; i < dungeonFactory.maxLayers(); i++)
            this.dungeon.add(new int[side][side]);

        // geramos os corredores da dungeon
        this.generateCorridor(dungeonFactory, center);
        // adicionamos as salas e as entradas
        this.addRoom(rooms.get(0).getMapTileNumbers(), center[0], center[0]); // initial room
        addEntrance(dungeonFactory, rooms.get(0).getMapTileNumbers(), center, 0, 0);
        this.addRoom(rooms.get(rooms.size() - 1).getMapTileNumbers(), center[2], center[2]); // boss room
        addEntrance(dungeonFactory, rooms.get(rooms.size() - 1).getMapTileNumbers(), center, 2, 2);
        rooms.remove(0);
        rooms.remove(rooms.size() - 1);
        // geramos as salas intermediárias
        genRooms(dungeonFactory, rooms, center);

        return this.dungeon;
    }

    /**
     * @param dungeonFactory Fábrica abstrata para geração das salas
     * @param center array com os centros do mapa
     */
    private void generateCorridor(DungeonAbstractFactory dungeonFactory, int[] center){
        // geramos os corredores horizontais e verticais
        ArrayList<int[][]> horCorridor = dungeonFactory.createHorizontalCorridor().getMapTileNumbers();
        ArrayList<int[][]> verCorridor = dungeonFactory.createVerticalCorridor().getMapTileNumbers();
        // iteramos pela matriz da dungeon, copiando e colando os tiles necessários
        for (int k = 0; k < horCorridor.size(); k++)
            for (int i = 0; i < 3; i++)
                for (int j = center[0]; j < center[2]; j+= 15){
                    putMatrix(this.dungeon.get(k), verCorridor.get(k), center[i], j);
                    putMatrix(this.dungeon.get(k), horCorridor.get(k), j, center[i]);
                }
    }

    /**
     * @param side tamanho da dungeon
     * @return array contendo os centros da dungeon
     */
    private int[] getCenter(int side){
        int[] center = new int[3];
        for (int i = 0; i < 3; i++)
            center[i] = (side / 3) * i + (side / 6);
        return center;
    }

    /**
     * @param room ArrayList contendo as layers de uma sala
     * @param x posição x do centro da sala
     * @param y posição y do centro da sala
     */
    private void addRoom(ArrayList<int[][]> room, int x, int y){
        // para cada layer, inserirmos os tiles na dungeon total
        for (int i = 0; i < room.size(); i++)
            DungeonGenerator.putMatrix(this.dungeon.get(i), room.get(i), x, y);
    }

    /**
     * @param dungeonFactory fábrica para geração dos componentes da dungeon
     * @param room sala na qual queremos colocar as entradas
     * @param center array com os centros da dungeon
     * @param centerX posição centro x da sala
     * @param centerY posição centro y da sala
     */
    private ArrayList<Door> addEntrance(DungeonAbstractFactory dungeonFactory, ArrayList<int[][]> room, int[] center, int centerX, int centerY){
        ArrayList<int[][]> horizontalRightEntrance = dungeonFactory.createHorizontalRightEntrance().getMapTileNumbers();
        ArrayList<int[][]> verticalUpEntrance = dungeonFactory.createVerticalUpEntrance().getMapTileNumbers();
        ArrayList<int[][]> horizontalLeftEntrance = dungeonFactory.createHorizontalLeftEntrance().getMapTileNumbers();
        ArrayList<int[][]> verticalDownEntrance = dungeonFactory.createVerticalDownEntrance().getMapTileNumbers();

        ArrayList<Door> doors = new ArrayList<>();

        int entranceX, entranceY;
        // dependendo da posição da sala, colocamos ou não a entrada
        if (centerX == 0 || centerX == 1) {
            entranceX = center[centerX] + 1 + room.get(0)[0].length / 2;
            entranceY = center[centerY];
            addRoom(horizontalRightEntrance, entranceX, entranceY);
            doors.add(new Door(
                    entranceX * Constants.TILE_SIZE + Constants.TILE_SIZE,
                    entranceY * Constants.TILE_SIZE + Constants.TILE_SIZE/2.0f,
                     Constants.TILE_SIZE,
                     7 * Constants.TILE_SIZE));
        }
        if (centerY == 0 || centerY == 1) {
            entranceX = center[centerX];
            entranceY = center[centerY] + 1 + room.get(0).length / 2;
            addRoom(verticalDownEntrance, entranceX, entranceY);
            doors.add(new Door(
                    entranceX * Constants.TILE_SIZE + Constants.TILE_SIZE/2.0f,
                    entranceY * Constants.TILE_SIZE + Constants.TILE_SIZE,
                    7 * Constants.TILE_SIZE,
                    Constants.TILE_SIZE));
        }
        if (centerX == 2 || centerX == 1) {
            entranceX = center[centerX] - 1 - room.get(0)[0].length / 2;
            entranceY = center[centerY];
            addRoom(horizontalLeftEntrance, entranceX, entranceY);
            doors.add(new Door(
                    entranceX * Constants.TILE_SIZE - Constants.TILE_SIZE       ,
                    entranceY * Constants.TILE_SIZE + Constants.TILE_SIZE/2.0f,
                    Constants.TILE_SIZE,
                    7 * Constants.TILE_SIZE));
        }
        if (centerY == 2 || centerY == 1) {
            entranceX = center[centerX];
            entranceY = center[centerY] - room.get(0).length / 2;
            addRoom(verticalUpEntrance, entranceX, entranceY);
            doors.add(new Door(
                    entranceX * Constants.TILE_SIZE + Constants.TILE_SIZE/2.0f,
                    entranceY * Constants.TILE_SIZE - Constants.TILE_SIZE,
                    7 * Constants.TILE_SIZE,
                    Constants.TILE_SIZE));
        }
        return doors;
    }

    /**
     * @param dungeonFactory fábrica de componentes da dungeon
     * @param rooms lista de todas as salas possíveis
     * @param center array de centros
     */
    private void genRooms(DungeonAbstractFactory dungeonFactory, ArrayList<DungeonTile> rooms, int[] center){
        // matriz para verificar se uma sala já foi colocada na posição
        int[][] placed = new int[3][3];
        placed[0][0] = 1;
        placed[2][2] = 1;
        for (int i = 0; i < 7;) {
            int x = rand.nextInt(3);
            int y = rand.nextInt(3);
            // caso a sala não tenha sido colocada, colocamos uma nova sala
            if (placed[x][y] == 0) {
                ArrayList<int[][]> room = rooms.get(rand.nextInt(rooms.size())).getMapTileNumbers();
                int [][] validTileMatrix = DungeonGenerator.validSpawnMatrix(room.get(0), room.get(room.size()-1));
                this.addRoom(room, center[x], center[y]);
                ArrayList<Door> doors = this.addEntrance(dungeonFactory, room, center, x, y);
                this.combatRooms[i] = new DungeonRoom(center[x], center[y], room.get(0)[0].length, room.get(0).length, validTileMatrix, doors);
                placed[x][y] = 1;
                i++;
            }
        }
    }

    public DungeonRoom[] getCombatRooms() {
        return combatRooms;
    }

    /**
     * @param sourceMatrix matriz que irá receber a matrix inserida
     * @param insertMatrix matriz que será inserida na matrix fonte
     * @param x posição na qual a matriz inserida será colocada na fonte
     * @param y posição na qual a matriz inserida será colocada na fonte
     */
    private static void putMatrix(int[][] sourceMatrix, int[][] insertMatrix, int x, int y){
        for (int i = 0, i2 = x - insertMatrix[0].length/2; i < insertMatrix[0].length; i++, i2++)
            for (int j = 0, j2 = y - insertMatrix.length/2; j < insertMatrix.length; j++, j2++)
                sourceMatrix[j2][i2] = insertMatrix[j][i];
    }

    private static int[][] validSpawnMatrix(int[][] matrixRoom, int[][] matrixCollisions){
        int [][] matrixValida = new int[matrixRoom.length][matrixRoom[0].length];
        int [][] matrixCollisionExtended = DungeonGenerator.extendCollisionMatrix(matrixCollisions);
        for (int i = 0; i < matrixRoom.length; i++){
            for (int j = 0; j < matrixRoom[0].length; j++){
                if (matrixRoom[i][j] != 0 && matrixCollisionExtended[i][j] == 0){
                    matrixValida[i][j] = 1;
                }
            }
        }
        return matrixValida;
    }

    private static int[][] extendCollisionMatrix(int[][] matrixCollisions) {
        int[][] matrixSurrounded = new int[matrixCollisions.length][matrixCollisions[0].length];
        int[][] direcoes = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int cx, cy;
        for (int i = 0; i < matrixCollisions.length; i++) {
            for (int j = 0; j < matrixCollisions[0].length; j++) {
                if (matrixCollisions[i][j] != 0) {
                    matrixSurrounded[i][j] = 1;
                    for (int[] direcao : direcoes) {
                        cx = i + direcao[0];
                        cy = j + direcao[1];
                        if (cx >= 0 && cx < matrixCollisions.length && cy >= 0 && cy < matrixCollisions[0].length) {
                            matrixSurrounded[cx][cy] = 1;
                        }
                    }
                }
            }
        }
        return matrixSurrounded;
    }
}
