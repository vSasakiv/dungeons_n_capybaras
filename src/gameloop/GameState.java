package gameloop;

import game_entity.Player;
import game_entity.Projectile;
import game_entity.Vector;

import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Classe que cuida de toda a lógica do jogo
 */
public class GameState {

    // Vetores de direção
    private final Vector DIRECTION_UP = new Vector(0, -1);
    private final Vector DIRECTION_DOWN = new Vector(0, 1);
    private final Vector DIRECTION_LEFT = new Vector(-1, 0);
    private final Vector DIRECTION_RIGHT = new Vector(1, 0);


    private final Player player;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    private final ArrayList<Projectile> projectiles;

    /**
     * Construtor que inicia o GameState, onde são criados players e todos os handlers.
     */
    public GameState() {
        player = new Player(150, 150, 4);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        projectiles = new ArrayList<>();
    }


    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        if (this.keyHandler.isKeyW())
            player.tick(DIRECTION_UP);
        if (this.keyHandler.isKeyA())
            player.tick(DIRECTION_LEFT);
        if (this.keyHandler.isKeyS())
            player.tick(DIRECTION_DOWN);
        if (this.keyHandler.isKeyD())
            player.tick(DIRECTION_RIGHT);
        if (this.mouseHandler.isMousePress() && this.getPlayer().canShoot()){
            projectiles.add(player.shoot(this.mouseHandler.getMouseX(), this.mouseHandler.getMouseY()));
        }
        for (Projectile p : projectiles)
            p.tick();
    }
    /**
     * @return objeto player de onde podem ser acessados sua posição para renderização
     */
    public Player getPlayer(){
        return this.player;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
    /**
     * @return retorna o KeyListener sendo utilizado no GameState, para podermos o incluir
     * ao GameFrame.
     */
    public KeyListener getKeyHandler() {
        return keyHandler;
    }
    public MouseHandler getMouseListener() {
        return mouseHandler;
    }
    public MouseHandler getMouseMotionListener() {
        return mouseHandler;
    }
}
