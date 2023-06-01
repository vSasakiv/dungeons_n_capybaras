package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import gameloop.Constants;
import gameloop.GameState;


/**
 * Classe que decide qual mapa seguir, carrega as imagens dos tiles e os desenha na janela do jogo 
 */
public class TileManager {

    int[][] TileNumber; //Matriz que contém, em cada posição, o número tile que a representa
    int WorldRolls;// Número de linhas no mundo
    int WorldColumns; //Número de colunas no mundo
    GameState gameState;
    BufferedImage[] tiles; //Vetor com os tiles do mapa
    Sprite sprite;


    /**
     * Construtor da classe TileManager
     * Adiciona um mapa e carrega os tiles
     * @param gameState Estado do jogo
     * @param path Caminho do arquivo de mapa
     * @param imagePath Caminho do spritesheet
     */
    public TileManager (GameState gameState, String path, String imagePath) {
        this.gameState = gameState;
        addTileMap(path, imagePath);
        loadTiles();
    }

    /** Este método é responsável por implementar a lógica para a renderização dos tiles
     * @param g2d Ferramenta que desenha as imagens na tela
     */
    public void draw (Graphics2D g2d) {
        int number;
        for (int worldRow = 0; worldRow < WorldRolls; worldRow++) {
            for (int worldColumn = 0; worldColumn < WorldColumns; worldColumn++) {
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
                    g2d.drawImage(
                            tiles[number - 1],
                            screenX, screenY,
                            Constants.TILE_SIZE,
                            Constants.TILE_SIZE,
                            null
                    );
                }
            }
        }
    }

    /**
     * Método responsável por ler mapas
     * @param path Caminho do arquivo de mapa
     * @param imagePath Caminho do spritesheet
     */
    public void addTileMap (String path, String imagePath) {
        int height = 0;
        int width = 0;
        int tileWidth;
        int tileHeight;
        int layers;
        String[] data = new String[10];
        String[][] block = new String[10][];

        //Pega caminho que o programa está rodando
        String CurrentDirectoryPath = System.getProperty("user.dir");
        File CurrentDirectory = new File(CurrentDirectoryPath);
        String AbsolutePath = CurrentDirectory.getAbsolutePath();

        try {
            File inputFile = new File(AbsolutePath + path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder DocumentBuilder = builderFactory.newDocumentBuilder();
            Document doc = DocumentBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));

            this.sprite = new Sprite(imagePath, tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }
                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent().replaceAll("\\s+", "");
                block[i] = data[i].split(",");

                this.WorldColumns = width;
                this.WorldRolls = height;
                loadTileNumbers(block, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega a matriz de números do mapa
     * @param block Sequência de números, para cada layer
     * @param width largura da matriz
     * @param height altura da matriz
     */
    private void loadTileNumbers (String[][] block, int width, int height) {
        TileNumber = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.TileNumber[i][j] = Integer.parseInt(block[0][i * width + j]);
            }
        }
    }

    /**
     * Carrega vetor de tiles para dado spritesheet
     */
    private void loadTiles () {
        int height = sprite.getSpriteHeight();
        int width = sprite.getSpriteWidth();
        this.tiles = new BufferedImage[height * width];

        //O tile número "x" estará na posição x do vetor
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.tiles[i * width + j] = sprite.getSprite(i, j);
            }
        }
    }

}