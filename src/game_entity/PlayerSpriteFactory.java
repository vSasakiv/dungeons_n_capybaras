package game_entity;

import game_entity.entity_sprites.PlayerDefaultSprite;
import game_entity.entity_sprites.PlayerNinjaSprite;
import game_entity.entity_sprites.PlayerSprite;

import java.util.Objects;

public class PlayerSpriteFactory{

    public static PlayerSprite create(String type) {
        if (Objects.equals(type, "default")) return new PlayerDefaultSprite();
        else if (Objects.equals(type, "ninja")) return new PlayerNinjaSprite();
        return null;
    }
}
