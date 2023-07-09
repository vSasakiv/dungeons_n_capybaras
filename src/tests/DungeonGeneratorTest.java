package tests;

import java.lang.Math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import dungeon_gen.DungeonGenerator;

import org.junit.jupiter.api.DisplayName;

public class DungeonGeneratorTest {

    private final DungeonGenerator dungeon_gen = new DungeonGenerator();

    @Test
    @DisplayName("Testa generate")
    void generateTest() {
        assertEquals(7, dungeon_gen.generate(null, 2));

    }

    @Test
    @DisplayName("Testa generate")
    void generateCorridorTest() {
        // assertEquals(7, dungeon_gen.generateCorridor(null, null));

    }

}
