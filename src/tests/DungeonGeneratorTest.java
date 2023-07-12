package tests;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import dungeon_gen.DungeonGenerator;

public class DungeonGeneratorTest {

    @Test
    @DisplayName("Testa Generate Dungeon")
    void generateTest() {

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        // ArrayList<int[][]> dungeon = dungeonGenerator.generate("eletrica", 255);
        assertNotNull(dungeonGenerator);
    }

    @Test
    @DisplayName("Testa Generate Dungeon")
    void generateEletricaTest() {

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        // ArrayList<int[][]> dungeon = dungeonGenerator.generate("eletrica", 255);
        assertNotNull(dungeonGenerator);
    }

    public static void main(String[] args) {

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        ArrayList<int[][]> dungeon = dungeonGenerator.generate("eletrica", 255);
        System.out.println(dungeon);
        System.out.println("teste");

    }

}
