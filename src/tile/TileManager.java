package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;
import gameloop.Constants;
import gameloop.GameState;

/**
 * Classe que decide qual mapa seguir, carrega as imagens dos tiles e os desenha na janela do jogo 
 */
public class TileManager {
    Tile[] tile; //Vetor que guarda todos os tipos de tiles
    int[][] TileNumber; //Matriz que contém, em cada posição, o número tile que a representa
    GameState gameState;

    /**
     * Construtor que define a quantidade de tiles diferentes,
     * atribui as imagens de cada tile e carrega um mapa
     */
    public TileManager (GameState gameState) {
        this.gameState = gameState;
        tile = new Tile[30];
        TileNumber = new int[Constants.WORLD_NUM_ROW][Constants.WORLD_NUM_COL];
        getImage();
        load("/resources/maps/map02.txt");   
    }

    /**
     * Método responsável por atribuir as imagens de
     * cada tile em uma posição específica do vetor tile[]
     */
    public void getImage () {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Ground/grass1.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Ground/grass2.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Ground/grass3.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Ground/grass4.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Ground/grass4.png")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Water/TilesetWater_169.png")));
            
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Water/TilesetWater_170.png")));
           
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Water/TilesetWater_171.png")));
            
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Water/TilesetWater_197.png")));
            
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Water/TilesetWater_198.png")));
            
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Water/TilesetWater_199.png")));
            
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Field/TilesetField_17.png")));
            
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Field/TilesetField_21.png")));

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Field/TilesetField_23.png")));

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Tiles/Field/TilesetField_27.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param g2d Ferramente que desenha as imagens na tela
     * 
     * Este método é responsável por implementar a lógica para a renderização dos tiles
     */
    public void draw (Graphics2D g2d) {
        int number;

        for (int worldRow = 0; worldRow < Constants.WORLD_NUM_ROW; worldRow++) {
            for (int worldColumn = 0; worldColumn < Constants.WORLD_NUM_COL; worldColumn++) {
                number = TileNumber[worldRow][worldColumn];
                int worldX = worldColumn * Constants.TILE_SIZE;
                int worldY = worldRow * Constants.TILE_SIZE;
                int screenX = worldX - (int)gameState.player.getWorldPosX() + gameState.player.SCREEN_X;
                int screenY = worldY - (int)gameState.player.getWorldPosY() + gameState.player.SCREEN_Y;
                
                //Somente desenha na tela se a posição do tile estiver dentro dos limites da tela (mais uma borda de tamanho TILE_SIZE)
                if (worldX + Constants.TILE_SIZE> gameState.player.getWorldPosX() - gameState.player.SCREEN_X &&
                    worldX - Constants.TILE_SIZE< gameState.player.getWorldPosX() + gameState.player.SCREEN_X &&
                    worldY + Constants.TILE_SIZE> gameState.player.getWorldPosY() - gameState.player.SCREEN_Y &&
                    worldY - Constants.TILE_SIZE< gameState.player.getWorldPosY() + gameState.player.SCREEN_Y) {
                        g2d.drawImage(tile[number].image, screenX, screenY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
                    }
            }
        }
    }

    /**
     * @param mapPath caminho do arquivo de texto que contém a matriz que representa um mapa
     * Este método é responsável por ler uma matriz de números inteiros, escrita em um txt,
     * e construir uma equivalente em "TileNumber[][]"
     */
    public void load(String mapPath) {
        try {
            InputStream  input = getClass().getResourceAsStream(mapPath);
            assert input != null;
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(input));

            for (int row = 0; row < Constants.WORLD_NUM_ROW; row++) {
                String line = bufferReader.readLine();
                for (int column = 0; column < Constants.WORLD_NUM_COL ; column++) {
                    String[] number = line.split(" ");
                    int index = Integer.parseInt(number[column]);
                    TileNumber[row][column] = index;
                }
            }
            bufferReader.close();
        } catch (Exception ignored) {
        
        }
    }
}