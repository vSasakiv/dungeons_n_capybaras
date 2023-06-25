package tile.dungeon;

public final class BienioLayers extends LayerInfo {
    public BienioLayers () {
        fistGid = new int[]{1, 1, 392, 392, 1816};
        imagePath = new String[]{
                "/resources/Tiles/Room_Builder_free_16x16.png",
                "/resources/Tiles/Room_Builder_free_16x16.png",
                "/resources/Tiles/Interiors_free_16x16.png",
                "/resources/Tiles/Interiors_free_16x16.png",
                "/resources/Tiles/collision.png"
        };
        TileWidth = 16;
        TileHeight = 16;
    }
}
