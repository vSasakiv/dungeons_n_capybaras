package tile;

import java.awt.*;
import game_entity.GameEntity;
import gameloop.render.Draw;
import gameloop.render.DrawTileManager;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;


/**
 * Classe que decide qual mapa seguir, carrega as imagens dos tiles e os desenha na janela do jogo 
 */
public class MapTileManager implements TileManager{
    public ChangeTileStrategy changeStrategy; // Estratégia para mudanças de mapa
    ArrayList<Layer> layers = new ArrayList<>(); // Conjunto de camadas (layers) que formam o mapa
    int WorldRolls;// Número de linhas no mundo
    int WorldColumns; //Número de colunas no mundo

    private ArrayList<int[][]> mapTileNumbers;
    Draw drawMethod;

    /**
     * Construtor da classe TileManager
     * Adiciona um mapa e carrega os tiles
     * @param path Caminho do arquivo de mapa
     */
    public MapTileManager(String path, GameEntity player, ChangeTileStrategy strategy) {
        addTileMap(path);
        this.changeStrategy = strategy;
        drawMethod = new DrawTileManager(layers, WorldRolls, WorldColumns, player);
    }

    public MapTileManager(String path) {
        addTileMap(path);
    }

    /** Este método é responsável por implementar a lógica para a renderização dos tiles
     * Implementa também a lógica da câmera do jogo (centrada no player)
     * @param g2d Ferramenta que desenha as imagens na tela
     */
    public void draw (Graphics2D g2d) {
        drawMethod.draw(g2d);
    }

    /**
     * Método responsável por ler mapas
     * @param path Caminho do arquivo de mapa
     */
    public void addTileMap (String path) {
        ArrayList<Sprite> sprites = new ArrayList<>();
        mapTileNumbers = new ArrayList<>();
        String name;
        int height = 0;
        int width = 0;
        ArrayList<String[]> data = new ArrayList<>();
        ArrayList<TilesetDocument> tilesetDocuments = new ArrayList<>();
        NodeList list;
        Node node;
        Element element;

        try {
            Document map = MapDocument.loadDocument(path);
            list = MapDocument.getListByTag(map,"tileset");
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
            list = MapDocument.getListByTag(map,"layer");
            for (int i = 0; i < list.getLength(); i++) {
                node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    name =  element.getAttribute("name");
                    if (i == 0) {
                        width = Integer.parseInt(element.getAttribute("width"));
                        height = Integer.parseInt(element.getAttribute("height"));
                    }
                    data.add(element.getElementsByTagName("data").item(0).getTextContent().replaceAll("\\s+", "").split(","));
                    layers.add(new Layer(
                            data.get(i),
                            width,
                            height,
                            tilesetDocuments.get(i).getFirstgid(),
                            sprites.get(i))

                    );
                    mapTileNumbers.add(getLayerMatrix(data.get(i), width, height, tilesetDocuments.get(i).getFirstgid()));
                }
            }
            this.WorldColumns = width;
            this.WorldRolls = height;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Layer que tem tiles sólidos (deve possuir colisão)
     */
    public Layer getCollisionLayer () {
        return this.layers.get(this.layers.size() - 1);
    }

    private int[][] getLayerMatrix (String[] data, int width, int height, int firstGid) {
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = Integer.parseInt(data[i * width + j]);
            }
        }
    return matrix;
    }

    public ArrayList<int[][]> getMapTileNumbers() {
        return mapTileNumbers;
    }


    public ChangeTileStrategy getChangeStrategy() {
        return changeStrategy;
    }
}