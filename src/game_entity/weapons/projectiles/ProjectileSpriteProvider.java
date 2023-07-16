package game_entity.weapons.projectiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

/**
 * Fábrica que retorna o sprite de um projétil selecionado por "type"
 */
public class ProjectileSpriteProvider {
    public static HashMap<String, BufferedImage> spriteMap = new HashMap<>();
    public static BufferedImage getProjectileSprite(String type){
        try {
            spriteMap.put("ARROW", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/weapons/bow/Arrow.png"))));
            spriteMap.put("WEB", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/ant/antProjectile.png"))));
            spriteMap.put("EYE", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/eye/eyeProjectile.png"))));
            spriteMap.put("SLIME", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/slime/slimeProjectile.png"))));
            spriteMap.put("BRAIN", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/brain/brainProjectile.png"))));
            spriteMap.put("FLY", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/fly/flyProjectile.png"))));
            spriteMap.put("IMP", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/imp/impProjectile.png"))));
            spriteMap.put("TWIG", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/twig/twigProjectile.png"))));
            spriteMap.put("FLAME", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/mobs/flame/flameProjectile.png"))));
            spriteMap.put("SHURIKEN", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/weapons/Shuriken.png"))));
            spriteMap.put("ENERGY", ImageIO.read(Objects.requireNonNull(ProjectileSpriteProvider.class.getResourceAsStream("/resources/weapons/staffProjectile.png"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spriteMap.get(type);
    }
}
