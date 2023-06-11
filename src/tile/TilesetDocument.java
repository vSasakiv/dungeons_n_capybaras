package tile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TilesetDocument extends MapDocument {
    private int tileWidth;
    private int tileHeight;
    private final int firstgid;
    private String imagePath;

    /**
     * Classe que armazena atributos de um documento xml referente a um tileset
     * @param path caminho para o arquivo xml
     * @param fistgid Número de identificação, no "data", do primeiro sprite
     */
    public TilesetDocument(String path, int fistgid) {
        this.getAttributes(path);
        this.firstgid = fistgid;
    }

    private void getAttributes(String path) {
        Document doc = MapDocument.loadDocument(path);
        NodeList list = getListByTag(doc, "tileset");
        Node node = list.item(0);
        Element element = (Element) node;
        this.tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
        this.tileHeight = Integer.parseInt(element.getAttribute("tileheight"));
        list = getListByTag(doc, "image");
        node = list.item(0);
        element = (Element) node;
        this.imagePath = "/" + element.getAttribute("source");
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public String getImagePath() {
        return imagePath;
    }
}
