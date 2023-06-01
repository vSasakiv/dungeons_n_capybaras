package gameloop;

import game_entity.Player;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.mobs.PassiveEnemy;
import game_entity.weapons.*;
import tile.TileManager;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Classe que cuida de toda a lógica do jogo
 */
public class GameState {

    public final Player player;
    public final Enemy testEnemy;
    public TileManager tileManager;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final ArrayList<Projectile> projectiles;
    private final ArrayList<Projectile> subProjectiles;

    /**
     * Construtor que inicia o GameState, onde são criados players, os handlers e o tileManager.
     */
    public GameState() {
        player = new Player(150, 150, 4);
        testEnemy = new PassiveEnemy(200, 200, 3);
        ProjectileFactory subSubFactory = new BulletFactory(4);
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, subSubFactory);
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, subFactory);
        player.setWeapon(new MultiShotWeapon(5, 2, factory, 30, 3));
        testEnemy.setWeapon(new AutomaticWeapon(2, 2, subSubFactory));
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        projectiles = new ArrayList<>();
        subProjectiles = new ArrayList<>();
        tileManager = new TileManager(this, "/src/resources/maps/teste2.xml", "/resources/Tiles/TilesetFloor.png");
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        player.tick(player.updateDirection(keyHandler)); //Atualiza as informações do player
        player.updateWeapon(mouseHandler);
        projectiles.addAll(player.updateShoot(mouseHandler));
        testEnemy.tick(new Vector(player.getWorldPosX(), player.getWorldPosY()));
        projectiles.addAll(testEnemy.updateShoot(new Vector(player.getWorldPosX(), player.getWorldPosY())));

        for (Projectile p : projectiles){
            p.tick();
            if (p.shouldDelete())
                subProjectiles.addAll(p.subProjectiles());
        }

        projectiles.addAll(subProjectiles);
        projectiles.removeIf(Projectile::shouldDelete);
        subProjectiles.clear();
    }

    /**
     * obtém os projéteis
     * @return array list tipo <Projectile>
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * obtém o KeyListener utilizado no GameState, para inclusão no GameFrame.
     * @return o KeyListener 
     */
    public KeyListener getKeyHandler() {
        return keyHandler;
    }

    /**
     * obtém o MouseListener
     * @return MouseHandler
     */
    public MouseHandler getMouseListener() {
        return mouseHandler;
    }

    /**
     * obtém o MouseHandler
     * @return MouseHandler
     */
    public MouseHandler getMouseMotionListener() {
        return mouseHandler;
    }
}
