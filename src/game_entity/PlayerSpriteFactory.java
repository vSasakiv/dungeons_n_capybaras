package game_entity;

import java.util.Objects;

public class PlayerSpriteFactory{

    public static PlayerSprite create(String type) {
        if (Objects.equals(type, "default")) return new PlayerDefaultSprite();
        else if (Objects.equals(type, "ninja")) return new PlayerNinjaSprite();
        return null;
    }
}
