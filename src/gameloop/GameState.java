package gameloop;

import game_entity.Player;
import game_entity.weapons.*;
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
    private final ArrayList<Projectile> subProjectiles;

    /**
     * Construtor que inicia o GameState, onde são criados players, os handlers e o tileManager.
     */
    public GameState() {
        player = new Player(150, 150, 4);
        ProjectileFactory subSubFactory = new BulletFactory(4);
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, subSubFactory);
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, subFactory);
        player.setWeapon(new Shotgun(5, 2, factory, 30, 3));
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        projectiles = new ArrayList<>();
        subProjectiles = new ArrayList<>();
        tileManager = new TileManager(this);
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        player.tick(player.updateDirection(keyHandler)); //Atualiza as informações do player
        projectiles.addAll(player.updateShoot(mouseHandler));
        System.out.println(projectiles);
        for (Projectile p : projectiles){
            p.tick();
            if (p.shouldDelete())
                subProjectiles.addAll(p.subProjectiles());
        }
        projectiles.addAll(subProjectiles);
        projectiles.removeIf(Projectile::shouldDelete);
        subProjectiles.clear();
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
