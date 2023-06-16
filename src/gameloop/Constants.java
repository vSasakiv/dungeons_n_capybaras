package src.gameloop;

import src.game_entity.Vector;

/**
 * Classe que contém constantes utilizadas por todo o projeto
 */
public final class Constants {
    private Constants(){}

    // Screen settings
    public static final int SCALE = 3;
    public static final int TILE_SIZE = 16 * SCALE;
    public static final int NUM_COLUMNS = 29;
    public static final int NUM_ROWS = 16;
    public static final int HEIGHT = 16 * TILE_SIZE;
    public static final int WIDTH = 16 * HEIGHT / 9;

    // Vectors definitions 
    public static final Vector DIRECTION_UP = new Vector(0, -1);
    public static final Vector DIRECTION_DOWN = new Vector(0, 1);
    public static final Vector DIRECTION_LEFT = new Vector(-1, 0);
    public static final Vector DIRECTION_RIGHT = new Vector(1, 0);
    public static final Vector DIRECTION_UP_LEFT = new Vector (-1, -1);
    public static final Vector DIRECTION_UP_RIGHT = new Vector (1, -1);
    public static final Vector DIRECTION_DOWN_LEFT = new Vector (-1, 1);
    public static final Vector DIRECTION_DOWN_RIGHT = new Vector (1, 1);
    public static final Vector NULL_VECTOR = new Vector (0, 0);

    // World settings 
    public static final int WORLD_NUM_COL = 50;
    public static final int WORLD_NUM_ROW = 50;
    public static final int WORLD_WIDTH = Constants.TILE_SIZE * WORLD_NUM_COL;
    public static final int WORLD_HEIGHT = Constants.TILE_SIZE * WORLD_NUM_ROW;
}
