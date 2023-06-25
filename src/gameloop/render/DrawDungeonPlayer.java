package gameloop.render;

import game_entity.DungeonPlayer;


import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawDungeonPlayer implements Draw {
    DungeonPlayer dungeonPlayer;
    public DrawDungeonPlayer (DungeonPlayer dungeonPlayer) {
        this.dungeonPlayer = dungeonPlayer;
    }
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = dungeonPlayer.getPlayerImage();
        //Desenha o player
        g2d.drawImage(
                playerImage,
                (int)dungeonPlayer.getScreenX(),
                (int)dungeonPlayer.getScreenY(),
                dungeonPlayer.getSpriteSizeX(),
                dungeonPlayer.getSpriteSizeY(),
                null
        );
    }
}
