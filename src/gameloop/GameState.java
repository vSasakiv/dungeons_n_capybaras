package gameloop;

import game_entity.Attributes;
import game_entity.Hitbox;
import game_entity.Player;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.mobs.EnemyStrategy;
import game_entity.mobs.PassiveStrategy;
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
    private final ArrayList<Enemy> enemies;

    /**
     * Construtor que inicia o GameState, onde são criados players, os handlers e o tileManager.
     */
    public GameState() {
        player = new Player(150, 150, 4);

        Hitbox enemyHitbox = new Hitbox(50, 50, new Vector(200, 200));
        Attributes enemyAttributes = new Attributes(5, 0, 0);
        EnemyStrategy enemyStrategy = new PassiveStrategy(500, 200, 150, 30, 60);
        Enemy enemyTemplate = new Enemy(200, 200, 4, enemyStrategy);

        ProjectileFactory subSubFactory = new BulletFactory(4);
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, subSubFactory);
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, subFactory);
        player.setWeapon(new MeleeWeapon(20, 4, 50, 50, 30));
        //player.setWeapon(new MultiShotWeapon(5, 4, factory, 30, 3));

        enemyTemplate.setWeapon(new AutomaticWeapon(5, 4, subSubFactory));
        enemyTemplate.setHitbox(enemyHitbox);
        enemyTemplate.setAttributes(enemyAttributes);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        tileManager = new TileManager(this, "/src/resources/maps/teste2.xml", "/resources/Tiles/TilesetFloor.png");
        enemies = new ArrayList<>();
        enemies.add(enemyTemplate.clone(200, 200));
        enemies.add(enemyTemplate.clone(500, 500));

    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        player.tick(keyHandler, mouseHandler); //Atualiza as informações do player

        for (Enemy e: enemies)
            e.tick(new Vector(player.getWorldPosX(), player.getWorldPosY()));


        for (Enemy e: enemies) {
            if (e.hitbox.isHitting(player.getHitbox())) {
                player.gotHit(1);
                e.gotHit(1);
            }
            for (MeleeWeaponAttack hitbox: player.getMeleeAttacks())
                if (e.hitbox.isHitting(hitbox))
                    e.gotHit(hitbox.getDamage());
        }

        enemies.removeIf(Enemy::isDead);
    }

    /**
     * obtém os projéteis
     * @return array list tipo <Projectile>
     */
    public ArrayList<Projectile> getProjectiles() {
        ArrayList<Projectile> projectiles = new ArrayList<>(this.player.getRangedAttacks());
        for (Enemy e: enemies)
            projectiles.addAll(e.getRangedAttacks());
        return projectiles;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<MeleeWeaponAttack> getWeaponHitbox() {
        ArrayList<MeleeWeaponAttack> attacks = new ArrayList<>(this.player.getMeleeAttacks());
        for (Enemy e: enemies)
            attacks.addAll(e.getMeleeAttacks());
        return attacks;
    }

    /**
     * Obtém o KeyListener utilizado no GameState, para inclusão no GameFrame.
     * @return o KeyListener 
     */
    public KeyListener getKeyHandler() {
        return keyHandler;
    }

    /**
     * Obtém o MouseListener
     * @return MouseHandler
     */
    public MouseHandler getMouseListener() {
        return mouseHandler;
    }

    /**
     * Obtém o MouseHandler
     * @return MouseHandler
     */
    public MouseHandler getMouseMotionListener() {
        return mouseHandler;
    }
}
