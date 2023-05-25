package gameloop;

import game_entity.Player;
import game_entity.Projectile;
import tile.TileManager;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Classe que cuida de toda a lógica do jogo
 */
public class GameState {

    public final Player player;
    public final TileManager tileManager;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final ArrayList<Projectile> projectiles;     

    /**
     * Construtor que inicia o GameState, onde são criados players, os handlers e o tileManager.
     */
    public GameState() {
        player = new Player(150, 150, 4);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        projectiles = new ArrayList<>();
        tileManager = new TileManager(this);
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        player.tick(player.updateDirection(keyHandler)); //Atualiza as informações do player

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
