package tests;

import java.lang.Math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import dungeon_gen.DungeonGenerator;
import game_entity.Hitbox;

import org.junit.jupiter.api.DisplayName;

public class HitboxTest {

    private final Hitbox hitbox = new Hitbox(0, 0, null);

    @Test
    @DisplayName("Testa generate")
    void isHittingTest() {
        assertTrue("This will succeed.", hitbox.isHitting(hitbox));

    }

}
