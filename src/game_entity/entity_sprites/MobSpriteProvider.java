package game_entity.entity_sprites;

import game_entity.entity_sprites.mobs.*;
import java.util.HashMap;


public class MobSpriteProvider {
    public static HashMap<String, MovingEntitySprites> spriteMap = new HashMap<>();

    public static MovingEntitySprites getProjectileSprite(String type){
        spriteMap.put("ANT", new AntSprite());
        spriteMap.put("BRAIN", new BrainSprite());
        spriteMap.put("EYE", new EyeSprite());
        spriteMap.put("FLAME", new FlameSprite());
        spriteMap.put("FLY", new FlySprite());
        spriteMap.put("IMP", new ImpSprite());
        spriteMap.put("TWIG", new TwigSprite());
        spriteMap.put("LARVA", new LarvaSprite());
        spriteMap.put("SLIME", new SlimeSprite());

        return spriteMap.get(type);
    }

}
