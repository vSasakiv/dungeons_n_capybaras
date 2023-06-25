package gameloop.render;

import game_entity.MapPlayer;
import gameloop.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawMapPlayer implements Draw {
    MapPlayer mapPlayer;
    public DrawMapPlayer (MapPlayer mapPlayer) {
        this.mapPlayer = mapPlayer;
    }
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = mapPlayer.getPlayerImage(mapPlayer.getPlayerSprites().get(mapPlayer.playerSelected));
        //Desenha o player
        if (mapPlayer.playerSelected == 0) {
            g2d.drawImage(
                    playerImage,
                    (int)mapPlayer.getScreenX(),
                    (int)mapPlayer.getScreenY(),
                    mapPlayer.getSpriteSizeX(),
                    mapPlayer.getSpriteSizeY(),
                    null
            );
        } else {
            g2d.drawImage(
                    playerImage,
                    (int) mapPlayer.getScreenX()+ Constants.TILE_SIZE/2,
                    (int) mapPlayer.getScreenY()+ Constants.TILE_SIZE/2,
                    mapPlayer.getSpriteSizeX()/2,
                    mapPlayer.getSpriteSizeY()/2,
                    null
            );
        }
    }
}
