package gameloop;

import game_entity.Attributes;
import game_entity.Hitbox;
import game_entity.Player;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.mobs.PassiveEnemyFactory;
import game_entity.weapons.*;

import tile.Layer;
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
    private final ArrayList<Enemy> enemies;

    private final ArrayList<MeleeWeaponAttack> weaponHitbox;

    /**
     * Construtor que inicia o GameState, onde são criados players, os handlers e o tileManager.
     */
    public GameState() {
        player = new Player(150, 150, 4);
        Hitbox enemyHitbox = new Hitbox(50, 50, new Vector(200, 200));
        Attributes enemyAttributes = new Attributes(5, 0, 0);

        ProjectileFactory subSubFactory = new BulletFactory(4);
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, subSubFactory);
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, subFactory);
        //player.setWeapon(new MeleeWeapon(20, 4, 50, 50, 30));
        player.setWeapon(new MultiShotWeapon(5, 4, factory, 30, 3));
        PassiveEnemyFactory enemyFactory = new PassiveEnemyFactory(
                4,
                enemyAttributes,
                enemyHitbox,
                new AutomaticWeapon(2, 2, subSubFactory));

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        projectiles = new ArrayList<>();
        subProjectiles = new ArrayList<>();
        tileManager = new TileManager(this, "/src/resources/maps/mapaTeste.xml", player);
        enemies = new ArrayList<>();
        weaponHitbox = new ArrayList<>();

        enemies.add(enemyFactory.criaEnemy(200, 200));
        enemies.add(enemyFactory.criaEnemy(500, 500));
        enemies.add(enemyFactory.criaEnemy(1200, 100));
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        AttackResults playerAttackResults;
        AttackResults enemyAttackResults;

        player.tick(keyHandler, mouseHandler); //Atualiza as informações do player
        playerAttackResults = player.updateAttack(mouseHandler);

        projectiles.addAll(playerAttackResults.getProjectiles());
        weaponHitbox.addAll(playerAttackResults.getHitboxes());
        for (Layer l: tileManager.getLayers()) {
            if (l.isCollision()) {
                l.collisionDetector(player);
                //player.setPosition(new Vector (150, 150));
            }
        }
        for (Enemy e: enemies) {
            e.tick(new Vector(player.getWorldPosX(), player.getWorldPosY()));
            enemyAttackResults = e.updateShoot(new Vector(player.getWorldPosX(), player.getWorldPosY()));
            projectiles.addAll(enemyAttackResults.getProjectiles());
        }

        for (Projectile p : projectiles){
            p.tick();
            if (p.shouldDelete())
                subProjectiles.addAll(p.subProjectiles());
        }

        projectiles.addAll(subProjectiles);
        for (Enemy e: enemies) {
            if (e.hitbox.isHitting(player.getHitbox())) {
                player.gotHit(1);
                e.gotHit(1);
            }
            for (MeleeWeaponAttack hitbox: weaponHitbox)
                if (e.hitbox.isHitting(hitbox))
                    e.gotHit(hitbox.getDamage());
        }
        enemies.removeIf(Enemy::isDead);
        projectiles.removeIf(Projectile::shouldDelete);

        for (MeleeWeaponAttack hitbox : weaponHitbox)
            hitbox.tick();

        weaponHitbox.removeIf(MeleeWeaponAttack::isFinished);
        subProjectiles.clear();
    }

    /**
     * obtém os projéteis
     * @return array list tipo <Projectile>
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<MeleeWeaponAttack> getWeaponHitbox() {
        return weaponHitbox;
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
