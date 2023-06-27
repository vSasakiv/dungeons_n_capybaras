package tile.dungeon;

public final class EletricaLayers extends LayerInfo{
    public EletricaLayers () {
        fistGid = new int[]{1, 1, 392, 392, 1816, 1816, 2584};
        imagePath = new String[]{
                "/resources/Tiles/Room_Builder_free_16x16_2.png",
                "/resources/Tiles/Room_Builder_free_16x16_2.png",
                "/resources/Tiles/Interiors_free_16x16.png",
                "/resources/Tiles/Interiors_free_16x16.png",
                "/resources/Tiles/atlas_16x.png",
                "/resources/Tiles/atlas_16x.png",
                "/resources/Tiles/collision.png"
        };
        TileWidth = 16;
        TileHeight = 16;
    }
}
