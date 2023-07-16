package tile.dungeon;


import java.util.Objects;

/**
 * Fábrica que retorna a classe com informações de uma dungeon
 */
public class LayerInfoProvider {
    public static LayerInfo getLayerInfo (String type) {
        if (Objects.equals(type, "bienio")) return new BienioLayers();
        else if (Objects.equals(type, "eletrica")) return new EletricaLayers();
        return null;
    }
}
