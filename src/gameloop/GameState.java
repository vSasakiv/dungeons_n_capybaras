package gameloop;

import game_entity.Player;
import game_entity.Projectile;
import game_entity.Shotgun;
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
        player.setWeapon(new Shotgun(5, 2, 8, 2, 20));
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        projectiles = new ArrayList<>();
        tileManager = new TileManager(this);
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        player.tick(player.updateDirection(keyHandler, mouseHandler)); //Atualiza as informações do player

        projectiles.addAll(player.updateShoot(mouseHandler));
        projectiles.removeIf(Projectile::outOfBounds);
        System.out.println(projectiles);
        for (Projectile p : projectiles)
            p.tick();
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
