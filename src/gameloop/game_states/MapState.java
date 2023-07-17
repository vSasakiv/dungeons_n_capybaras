package gameloop.game_states;

import game_entity.GameEntity;
import game_entity.MapPlayer;
import game_entity.MapPlayerStateEnum;
import game_entity.Vector;
import game_entity.npcs.*;
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
        this.mapPlayer = new MapPlayer(200, 200, 5); //player do mapa

        //Mapa do estacionamento
        MapTileManager estacionamentoMap = new MapTileManager(
                "/src/resources/maps/estacionamento/estacionamento.xml",
                mapPlayer,
                new EstacionamentoStrategy());

        MovableNpc convictus1 = new OldManNpc(300, 600, 2);
        convictus1.setStrategy(new PatrolStrategy((GameEntity) convictus1, new Vector(300, 900)));

        Map estacionamento = new Map(estacionamentoMap);
        estacionamento.addNpc(convictus1);
        maps.add(estacionamento);

        //Mapa bienioSup
        MapTileManager bienioSupMap = new MapTileManager(
                "/src/resources/maps/BienioSup/BienioSup.xml",
                mapPlayer,
                new BienioSupStrategy());
        MovableNpc convictus2 = new BoyNpc(512, 2160, 3);
        convictus2.setStrategy(new PatrolStrategy((GameEntity) convictus2, new Vector(890, 2160)));
        MovableNpc convictus3 = new GirlNpc(1000, 1134, 2);
        convictus3.setStrategy(new PatrolStrategy((GameEntity) convictus3, new Vector(1350, 1134)));

        Map bienioSup = new Map(bienioSupMap);
        bienioSup.addNpc(convictus2);
        bienioSup.addNpc(convictus3);
        maps.add(bienioSup);

        this.currentState = MapPlayerStateEnum.DEFAULT; //Estado inicial
        this.keyHandler = keyHandler;
        loadImage();
    }

    @Override
    public void tick() {
        mapPlayer.tick(keyHandler);
        System.out.println("x: " + mapPlayer.getPosition().x + "y: " + mapPlayer.getPosition().y);

        //Verifica colisão com o player
        Layer layer = maps.get(mapNum).getTilemap().getCollisionLayer();
        layer.collisionDetector(mapPlayer, mapPlayer.getHitbox());

        maps.get(mapNum).tick(mapPlayer.getPosition());

        nextState = mapNum;
        mapNum = this.maps.get(mapNum).getTilemap().changeStrategy.changeMap(mapPlayer, mapNum);
        if (this.keyHandler.isKeyEnter()) {
            if (mapNum < 0)
                nextState = mapNum; //Entra na dungeon
            else
                for (MovableNpc npc: maps.get(mapNum).getNpcs()){
                    if (npc.isColliding(this.mapPlayer.getHitbox())){
                        //Interage com npc
                        playSound(0, 0.5F);
                        nextState = -2;
                        this.currentDialogue = npc.getDialogues()[0];
                    }
                }
            this.keyHandler.setKeyEnter(false);
        } else if (mapNum < 0) {
            if (!dungeonEntrance)
                playSound(0, 0.5F); // Dialogo na entrada da dungeon
            mapNum = nextState;
            dungeonEntrance = true;
            currentDialogue = "Você está na entrada de uma dungeon!\nAperte ENTER se quiser entrar.\nCuidado! Uma vez dentro, não há como voltar...";
        } else if (this.keyHandler.isKeyEsc()){
            //Abre menu de dificuldades
            playSound(0, 0.5F);
            this.nextState = -4;
            this.keyHandler.setKeyEsc(false);
        } else
            dungeonEntrance = false;

        this.mapPlayer.setVelocity(currentState.estadoAtual);
    }

    /**
     * Desenha elementos na tela
     * @param g2d Ferramenta para desenho
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (this.mapNum >= 0) {
            Color color = new Color(220, 241, 255);
            g2d.setColor(color);
            g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT); //desenha fundo do mapa

            this.maps.get(mapNum).draw(g2d, this.mapPlayer); // desenha o mapa atual
            this.mapPlayer.draw(g2d); //desenha o player
        }
        //Se está na entrada de uma dungeon, desenha caixa de dialogo
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

    /**
     * Carrega os recursos usados pela interface gráfica: fonte de texto e imagem do menu
     */
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

    /**
     * Toca músicas do mapa em loop
     * @param index index da música
     * @param volume volume
     */
    public void playMusic (int index, float volume) {
        sound.setMusicFile(index);
        sound.setVolume(volume, "MUSIC");
        sound.playMusic();
        sound.loop();
    }

    public int getMapNum() {
        return mapNum;
    }

    /**
     * Toca efeitos sonoros no mapa
     * @param index índice do efeito sonoro na lista
     * @param volume volume
     */
    public void playSound(int index, float volume) {
        sound.setSoundFile(index);
        sound.setVolume(volume, "SOUND");
        sound.playSound();
    }

    /**
     * Interrompe loop da música
     */
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
