package game_entity.weapons;

import game_entity.weapons.projectiles.ProjectileSpriteProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

/**
 * Fábrica que retorna os sprites das armas com base no tipo selecionado
 */
public class WeaponSpriteProvider {
    public static HashMap<String, BufferedImage> spriteMap = new HashMap<>();
    public static BufferedImage getWeaponSprite (String type) {
        try {
            spriteMap.put("BOW", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/weapons/bow/Bow.png"))));
            spriteMap.put("SHURIKEN", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/weapons/Shuriken01.png"))));
            spriteMap.put("STAFF", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/weapons/Staff.png"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spriteMap.get(type);
    }
}
