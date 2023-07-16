package gameloop.game_states;

import game_entity.GameEntity;
import game_entity.MapPlayer;
import game_entity.MapPlayerStateEnum;
import game_entity.Vector;
import game_entity.npcs.OldManNpc;
import game_entity.npcs.MovableNpc;
import game_entity.npcs.PatrolStrategy;
import game_entity.static_entities.CollidableObject;
import game_entity.static_entities.Door;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.Map;
import gameloop.sound.MapSound;
import gameloop.sound.GameSound;
import tile.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * CLasse que cuida do state no mapa geral do jogo
 */
public class MapState implements State{
    private final MapPlayer mapPlayer;
    public ArrayList<Map> maps;
    private final KeyHandler keyHandler;
    private int mapNum = 0;
    private int nextState = 0;
    private final MapPlayerStateEnum currentState;
    private String currentDialogue;
    private final GameSound sound = new MapSound();

    private BufferedImage dialogueWindow;

    private Font pixeled;

    private boolean dungeonEntrance;

    public MapState(KeyHandler keyHandler) {
        this.dungeonEntrance = false;
        maps = new ArrayList<>();
        this.mapPlayer = new MapPlayer(200, 200, 3);
        MapTileManager estacionamentoMap = new MapTileManager(
                "/src/resources/maps/estacionamento/estacionamento.xml",
                mapPlayer,
                new EstacionamentoStrategy());

        CollidableObject randomDoor = new Door(275, 566, 50 ,50);
        MovableNpc convictus1 = new OldManNpc(300, 600, 2);
        convictus1.setStrategy(new PatrolStrategy((GameEntity) convictus1, new Vector(300, 900)));

        Map estacionamento = new Map(estacionamentoMap);
        estacionamento.addCollidable(randomDoor);
        estacionamento.addNpc(convictus1);
        maps.add(estacionamento);

        MapTileManager bienioSupMap = new MapTileManager(
                "/src/resources/maps/BienioSup/BienioSup.xml",
                mapPlayer,
                new BienioSupStrategy());

        CollidableObject randomDoor2 = new Door(1361, 1243, 50 ,50);

        Map bienioSup = new Map(bienioSupMap);
        bienioSup.addCollidable(randomDoor2);
        maps.add(bienioSup);

        this.currentState = MapPlayerStateEnum.DEFAULT;
        this.keyHandler = keyHandler;
        loadImage();
    }

    @Override
    public void tick() {
        mapPlayer.tick(keyHandler);

        Layer layer = maps.get(mapNum).getTilemap().getCollisionLayer();
        layer.collisionDetector(mapPlayer, mapPlayer.getHitbox());

        maps.get(mapNum).tick(mapPlayer.getPosition());
        nextState = mapNum;
        mapNum = this.maps.get(mapNum).getTilemap().changeStrategy.changeMap(mapPlayer, mapNum);

        if (this.keyHandler.isKeyEnter()) {
            if (mapNum < 0)
                nextState = mapNum;
            else
                for (MovableNpc npc: maps.get(mapNum).getNpcs()){
                    if (npc.isColliding(this.mapPlayer.getHitbox())){
                        nextState = -2;
                        this.currentDialogue = npc.getDialogues()[0];
                    }
                }
            this.keyHandler.setKeyEnter(false);
        } else if (mapNum < 0) {
            mapNum = nextState;
            dungeonEntrance = true;
            currentDialogue = "Você está na entrada de uma dungeon!\nAperte ENTER se quiser entrar.\nCuidado! Uma vez dentro, não há como voltar...";
        } else
            dungeonEntrance = false;


        this.mapPlayer.setVelocity(currentState.estadoAtual);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.mapNum >= 0) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
            // exemplo
            g2d.setColor(Color.BLACK);

            this.maps.get(mapNum).draw(g2d, this.mapPlayer);
            this.mapPlayer.draw(g2d);
            g2d.setColor(Color.red);
            this.mapPlayer.getHitbox().draw(g2d, this.mapPlayer);
        }
        if (dungeonEntrance) {
            int x = Constants.TILE_SIZE * 4;
            int y = Constants.TILE_SIZE ;
            int scale = 10;
            int width = scale * 96;
            int height = scale * 32;

            g2d.drawImage(
                    dialogueWindow,
                    x,
                    y,
                    width,
                    height,
                    null
            );
            Color color = new Color(0, 0, 0);
            g2d.setColor(color);
            g2d.setFont(pixeled);
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 25));
            x += Constants.TILE_SIZE * 2;
            y += Constants.TILE_SIZE * 2.5;
            for (String line: currentDialogue.split("\n")) {
                g2d.drawString(line, x, y);
                y += 40;
            }
        }
    }


    private void loadImage () {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/UI/fonts/VCRosdNEUE.ttf");
            assert is != null;
            pixeled = Font.createFont(Font.TRUETYPE_FONT, is);
            dialogueWindow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/UI/UI_Flat_Button_Medium_Press_02a3.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic (int index) {
        sound.setMusicFile(index);
        sound.setVolume(0.01F, "MUSIC");
        sound.playMusic();
        sound.loop();
    }

    public void playSound(int index) {
        sound.setSoundFile(index);
        sound.setVolume(0.01F, "SOUND");
        sound.playSound();
    }

    public void stopMusic () {
        sound.stop();
    }

    public int nextState() {
        return nextState;
    }

    public void setMapNum(int mapNum) {
        this.mapNum = mapNum;
    }

    public void setDefaultPosition(int x, int y) {
        this.mapPlayer.setPosition(new Vector(x, y));
    }

    @Override
    public void setCurrentDialogue(String text) {
        this.currentDialogue = text;
    }

    public String getCurrentDialogue() {
        return currentDialogue;
    }
}
