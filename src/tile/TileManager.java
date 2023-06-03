package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;

import gameloop.Constants;
import gameloop.GameState;


/**
 * Classe que decide qual mapa seguir, carrega as imagens dos tiles e os desenha na janela do jogo 
 */
public class TileManager {

    ArrayList<int[][]> tileNumber = new ArrayList<>(); //Matriz que contém, em cada posição, o número tile que a representa
    int WorldRolls;// Número de linhas no mundo
    int WorldColumns; //Número de colunas no mundo
    GameState gameState;
    ArrayList<BufferedImage[]> tiles; //Vetor com os tiles do mapa
    ArrayList<Sprite> sprites;


    /**
     * Construtor da classe TileManager
     * Adiciona um mapa e carrega os tiles
     * @param gameState Estado do jogo
     * @param path Caminho do arquivo de mapa
     */
    public TileManager (GameState gameState, String path) {
        this.gameState = gameState;
        addTileMap(path);
        loadTiles();
    }

    /** Este método é responsável por implementar a lógica para a renderização dos tiles
     * @param g2d Ferramenta que desenha as imagens na tela
     */
    public void draw (Graphics2D g2d) {
        for (int worldRow = 0; worldRow < WorldRolls; worldRow++) {
            for (int worldColumn = 0; worldColumn < WorldColumns; worldColumn++) {
                int worldX = worldColumn * Constants.TILE_SIZE;
                int worldY = worldRow * Constants.TILE_SIZE;
                int screenX = worldX - (int)gameState.player.getWorldPosX() + gameState.player.SCREEN_X;
                int screenY = worldY - (int)gameState.player.getWorldPosY() + gameState.player.SCREEN_Y;
                
                //Somente desenha na tela se a posição do tile estiver dentro dos limites da tela (mais uma borda de tamanho TILE_SIZE)
                if (worldX + Constants.TILE_SIZE> gameState.player.getWorldPosX() - gameState.player.SCREEN_X &&
                    worldX - Constants.TILE_SIZE< gameState.player.getWorldPosX() + gameState.player.SCREEN_X &&
                    worldY + Constants.TILE_SIZE> gameState.player.getWorldPosY() - gameState.player.SCREEN_Y &&
                    worldY - Constants.TILE_SIZE< gameState.player.getWorldPosY() + gameState.player.SCREEN_Y) {
                    for (int i = 0; i < tileNumber.size(); i++) {
                        if (tileNumber.get(i)[worldRow][worldColumn] >= 0)
                            g2d.drawImage(
                                    tiles.get(i)[tileNumber.get(i)[worldRow][worldColumn]],
                                    screenX, screenY,
                                    Constants.TILE_SIZE,
                                    Constants.TILE_SIZE,
                                    null
                            );
                    }

                }
            }
        }
    }

    /**
     * Método responsável por ler mapas
     * @param path Caminho do arquivo de mapa
     */
    public void addTileMap (String path) {
        sprites = new ArrayList<>();
        int height = 0;
        int width = 0;
        ArrayList<String[]> data = new ArrayList<>();
        ArrayList<TilesetDocument> tilesetDocuments = new ArrayList<>();
        NodeList list;
        Node node;
        Element element;

        try {
            MapDocument map = new MapDocument(path);
            list = map.getListByTag("tileset");
            for (int temp = 0; temp < list.getLength(); temp++) {
                node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    System.out.println("atributos: " + element.getAttribute("firstgid") + ", " + element.getAttribute("source"));
                    tilesetDocuments.add(new TilesetDocument(
                            "/" + element.getAttribute("source"),
                            Integer.parseInt(element.getAttribute("firstgid")))
                    );
                    sprites.add(new Sprite(
                            tilesetDocuments.get(temp).getImagePath(),
                            tilesetDocuments.get(temp).getTileWidth(),
                            tilesetDocuments.get(temp).getTileHeight())
                    );
                }
            }

            list = map.getListByTag("layer");
            //list = doc.getElementsByTagName("layer");
            //layers = list.getLength();
            for (int i = 0; i < list.getLength(); i++) {
                node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    if (i == 0) {
                        width = Integer.parseInt(element.getAttribute("width"));
                        height = Integer.parseInt(element.getAttribute("height"));
                    }
                    data.add(element.getElementsByTagName("data").item(0).getTextContent().replaceAll("\\s+", "").split(","));
                    System.out.println(data);
                    tileNumber.add(loadTileNumbers(
                            data.get(i),
                            width,
                            height,
                            tilesetDocuments.get(i).getFirstGrid()
                    ));
                }
            }
            this.WorldColumns = width;
            this.WorldRolls = height;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega a matriz de números do mapa
     * @param data Sequência de números, para cada layer
     * @param width largura da matriz
     * @param height altura da matriz
     */
    private int[][] loadTileNumbers (String[] data, int width, int height, int fistGrid) {
        int [][] tileNumbers = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tileNumbers[i][j] = Integer.parseInt(data[i * width + j]) - fistGrid;
            }
        }
        return tileNumbers;
    }

    /**
     * Carrega vetor de tiles para dado spritesheet
     */
    private void loadTiles () {
        this.tiles = new ArrayList<>();
        for (int layer = 0; layer < sprites.size(); layer++) {
            int height = sprites.get(layer).getSpriteHeight();
            int width = sprites.get(layer).getSpriteWidth();
            //O tile número "x" estará na posição x do vetor
            this.tiles.add(new BufferedImage[height * width]);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    this.tiles.get(layer)[i * width + j] = sprites.get(layer).getSprite(i, j);
                }
            }
        }
    }

}