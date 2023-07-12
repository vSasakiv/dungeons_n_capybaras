package tests;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import dungeon_gen.DungeonGenerator;

public class DungeonGeneratorTest {

    private final static DungeonGenerator dungeonGenerator = new DungeonGenerator();
    // ArrayList<int[][]> dungeon = dungeonGenerator.generate("teste", 1);

    @Test
    @DisplayName("Testa Generate Dungeon")
    void generateNotNullTest() {

        assertNotNull(dungeonGenerator);
    }

    // @Test
    // @DisplayName("Testa Generate Eletrica")
    // void generateEletricaTest() {

    // ArrayList<int[][]> dungeon = dungeonGenerator.generate("eletrica", 255);
    // assertNotNull(dungeonGenerator);
    // }

    public static void main(String[] args) {

    }

}
