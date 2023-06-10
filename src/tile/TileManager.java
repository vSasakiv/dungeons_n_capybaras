package tile;

import java.awt.*;
import game_entity.GameEntity;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Objects;

import gameloop.Constants;


/**
 * Classe que decide qual mapa seguir, carrega as imagens dos tiles e os desenha na janela do jogo 
 */
public class TileManager {
    public ChangeTileStrategy changeStrategy;
    ArrayList<Layer> layers = new ArrayList<>();
    int WorldRolls;// Número de linhas no mundo
    int WorldColumns; //Número de colunas no mundo
    GameEntity player;

    /**
     * Construtor da classe TileManager
     * Adiciona um mapa e carrega os tiles
     * @param path Caminho do arquivo de mapa
     */
    public TileManager (String path, GameEntity player, ChangeTileStrategy strategy) {
        addTileMap(path);
        setLayerCollision("Collision");
        this.player = player;
        this.changeStrategy = strategy;
    }

    /** Este método é responsável por implementar a lógica para a renderização dos tiles
     * @param g2d Ferramenta que desenha as imagens na tela
     */
    public void draw (Graphics2D g2d) {
        for (int worldRow = 0; worldRow < WorldRolls; worldRow++) {
            for (int worldColumn = 0; worldColumn < WorldColumns; worldColumn++) {
                int worldX = worldColumn * Constants.TILE_SIZE;
                int worldY = worldRow * Constants.TILE_SIZE;
                int screenX = worldX - (int)this.player.getWorldPosX() + Constants.WIDTH/2;
                int screenY = worldY - (int)this.player.getWorldPosY() + Constants.HEIGHT/2;
                
                //Somente desenha na tela se a posição do tile estiver dentro dos limites da tela (mais uma borda de tamanho TILE_SIZE)
                if (worldX + Constants.TILE_SIZE > this.player.getWorldPosX() - (float) Constants.WIDTH /2 &&
                    worldX - Constants.TILE_SIZE < this.player.getWorldPosX() + (float) Constants.WIDTH /2 &&
                    worldY + Constants.TILE_SIZE > this.player.getWorldPosY() - (float) Constants.HEIGHT /2 &&
                    worldY - Constants.TILE_SIZE < this.player.getWorldPosY() + (float) Constants.HEIGHT /2) {
                    for (Layer layer : layers) {
                        if (layer.getTileMap()[worldRow][worldColumn] != null && !Objects.equals(layer.getName(), "Collision"))
                            g2d.drawImage(
                                    layer.getTileMap()[worldRow][worldColumn].getImage(),
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
        ArrayList<Sprite> sprites = new ArrayList<>();
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
            for (int i = 0; i < list.getLength(); i++) {
                node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    if (i == 0) {
                        width = Integer.parseInt(element.getAttribute("width"));
                        height = Integer.parseInt(element.getAttribute("height"));
                    }
                    data.add(element.getElementsByTagName("data").item(0).getTextContent().replaceAll("\\s+", "").split(","));
                    layers.add(new Layer(
                            data.get(i),
                            width,
                            height,
                            tilesetDocuments.get(i).getFirstGrid(),
                            sprites.get(i),
                            element.getAttribute("name"))
                    );
                }
            }
            this.WorldColumns = width;
            this.WorldRolls = height;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLayerCollision (String layerName) {
        for (Layer layer: layers) {
            if (Objects.equals(layer.getName(), layerName)) {
                layer.setCollision(true);
            }
        }
    }

    public Layer getCollisionLayer () {
        return this.layers.get(this.layers.size() - 1);
    }
}