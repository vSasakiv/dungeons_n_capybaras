package tests;

import java.util.ArrayList;

import javax.swing.text.Position;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import game_entity.*;

public class HitboxTest {

    @Test
    @DisplayName("Testa se existe a posição")
    void positionNotNullTest() {

        Vector position = new Vector(200, 300);

        assertNotNull(position, "Position is not null");
    }

    @Test
    @DisplayName("Testa se existe a posição e o hitbox")
    void hitboxNotNullTest() {

        Vector position = new Vector(200, 300);
        Hitbox hitbox = new Hitbox(0, 0, position);
        assertNotNull(hitbox, "Hitbox is not null");
    }

    @Test
    @DisplayName("Testa se acertou")
    void isHittingTest() {

        Vector position = new Vector(200, 300);
        Hitbox hitbox = new Hitbox(10, 10, position);
        assertTrue(hitbox.isHitting(hitbox), "Acertou");
    }

    @Test
    @DisplayName("Testa se não acertou")
    void isNotHittingTest() {

        Vector position = new Vector(-1000, -1000);
        Hitbox hitbox = new Hitbox(-10, -10, position);
        assertFalse(hitbox.isHitting(hitbox), "Não acertou");
    }

}
