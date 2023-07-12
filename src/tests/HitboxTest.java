package tests;

import java.util.ArrayList;

import javax.swing.text.Position;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import game_entity.*;

public class HitboxTest {

    private Vector position = new Vector(200, 300);
    private Hitbox hitbox = new Hitbox(0, 0, position);

    @Test
    @DisplayName("Testa se existe a posição")
    void positionNotNullTest() {

        assertNotNull(position, "Position is not null");
    }

    @Test
    @DisplayName("Testa se existe a posição e o hitbox")
    void hitboxNotNullTest() {

        assertNotNull(hitbox, "Hitbox is not null");
    }

    @Test
    @DisplayName("Testa se acertou")
    void isHittingTest() {

        assertNotNull(hitbox);
    }

    @Test
    @DisplayName("Testa se não acertou")
    void isNotHittingTest() {

        Boolean teste = false;

        assertFalse(teste, "Não acertou");

        assertTrue(hitbox.isHitting(hitbox), "Não acertou");

    }

    public static void main(String[] args) {

        Vector position = new Vector(200, 300);

        Hitbox hitbox = new Hitbox(10, 20, position);

        System.out.println(hitbox.getWidth());
        System.out.println(hitbox.getHeight());
        System.out.println(hitbox.getPosition());

    }

}
