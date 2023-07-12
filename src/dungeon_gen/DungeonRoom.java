package dungeon_gen;

import game_entity.Vector;
import game_entity.static_entities.Door;
import gameloop.Constants;

import java.util.ArrayList;

public class DungeonRoom {
    private final int x, y;
    private final int width, height;
    private static final int tolerance = 16;
    private final int[][] validTileMatrix;

    private final ArrayList<Door> doors;

    public DungeonRoom(int x, int y, int width, int height, int[][] validTileMatrix, ArrayList<Door> doors) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.doors = doors;
        this.validTileMatrix = validTileMatrix;
    }

    public int getMaxY(){
        return (this.y * Constants.TILE_SIZE + (this.height/2)* Constants.TILE_SIZE) + Constants.TILE_SIZE/2 - tolerance;
    }
    public int getMinY(){
        return (this.y * Constants.TILE_SIZE - (this.height/2)* Constants.TILE_SIZE) - Constants.TILE_SIZE/2 + tolerance;
    }
    public int getMaxX(){
        return (this.x * Constants.TILE_SIZE + (this.width/2)* Constants.TILE_SIZE) + Constants.TILE_SIZE/2 - tolerance;
    }

    public int getMinX(){
        return (this.x * Constants.TILE_SIZE - (this.width/2)* Constants.TILE_SIZE) - Constants.TILE_SIZE/2 + tolerance;
    }
    public boolean isInside(Vector position){
        int maxY = this.getMaxY() - 10;
        int minY = this.getMinY() + 10;
        int maxX = this.getMaxX() - 10;
        int minX = this.getMinX() + 10;
        return ((position.x > minX) && (position.y > minY) && (position.y < maxY) && (position.x < maxX));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public int[][] getValidTileMatrix() {
        return validTileMatrix;
    }
}
