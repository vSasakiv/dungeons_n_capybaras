package dungeon_gen;

import game_entity.Vector;
import gameloop.Constants;

public class DungeonRoom {
    private final int x, y;
    private final int width, height;
    private static final int tolerance = 16;
    private  final int[][] validTileMatrix;

    public DungeonRoom(int x, int y, int width, int height, int[][] validTileMatrix) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        int maxY = this.getMaxY();
        int minY = this.getMinY();
        int maxX = this.getMaxX();
        int minX = this.getMinX();
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

    public int[][] getValidTileMatrix() {
        return validTileMatrix;
    }
}
