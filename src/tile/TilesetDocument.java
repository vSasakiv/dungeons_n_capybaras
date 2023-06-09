package tile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TilesetDocument extends MapDocument {
    private int tileWidth;
    private int tileHeight;
    private int firstGrid;
    private String imagePath;
    public TilesetDocument(String path, int fistGrid) {
        super(path);
        this.getAttributes();
        this.firstGrid = fistGrid;
    }

    private void getAttributes() {
        NodeList list = this.getListByTag("tileset");
        Node node = list.item(0);
        Element element = (Element) node;
        this.tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
        this.tileHeight = Integer.parseInt(element.getAttribute("tileheight"));
        list = this.getListByTag("image");
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

    public int getFirstGrid() {
        return firstGrid;
    }

    public String getImagePath() {
        return imagePath;
    }
}
