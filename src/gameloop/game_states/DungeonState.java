package gameloop.game_states;

import dungeon_gen.DungeonGenerator;
import game_entity.Attributes;
import game_entity.Hitbox;
import game_entity.DungeonPlayer;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.mobs.EnemyStrategy;
import game_entity.mobs.PassiveStrategy;
import game_entity.weapons.AutomaticWeapon;
import game_entity.weapons.MeleeWeaponAttack;
import game_entity.weapons.MultiShotWeapon;
import game_entity.weapons.projectiles.BulletFactory;
import game_entity.weapons.projectiles.ClusterBulletFactory;
import game_entity.weapons.projectiles.Projectile;
import game_entity.weapons.projectiles.ProjectileFactory;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.MouseHandler;
import tile.Layer;
import tile.ZonaAbertaStrategy;
import tile.dungeon.TileDungeonManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que cuida de toda a lógica do jogo numa dungeon
 */
public class  DungeonState implements State{
    public final DungeonPlayer dungeonPlayer;
    public final TileDungeonManager tileManager;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final ArrayList<Enemy> enemies;
    private final DungeonGenerator dungeonGenerator;

    private int mapNum;


    public DungeonState(KeyHandler keyHandler, MouseHandler mouseHandler) {
        dungeonPlayer = new DungeonPlayer(150, 150, 20);

        Hitbox enemyHitbox = new Hitbox(50, 50, new Vector(200, 200));
        Attributes enemyAttributes = new Attributes(5, 0, 0);
        EnemyStrategy enemyStrategy = new PassiveStrategy(500, 200, 150, 30, 60);
        Enemy enemyTemplate = new Enemy(200, 200, 4, enemyStrategy);


        ProjectileFactory subSubFactory = new BulletFactory(4, 6);
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, 6, subSubFactory );
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, 6, subFactory);
        //player.setWeapon(new MeleeWeapon(20, 4, 50, 50, 30));
        dungeonPlayer.setWeapon(new MultiShotWeapon(5, 4, factory, 30, 3));

        enemyTemplate.setWeapon(new AutomaticWeapon(5, 4, subSubFactory));
        enemyTemplate.setHitbox(enemyHitbox);
        enemyTemplate.setAttributes(enemyAttributes);

        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        dungeonGenerator = new DungeonGenerator();
        ArrayList<int[][]> dungeon = dungeonGenerator.generate("eletrica", 255);
        tileManager = new TileDungeonManager(
                dungeon,
                "eletrica",
                this.dungeonPlayer,
                new ZonaAbertaStrategy());
        enemies = new ArrayList<>();
        enemies.add(enemyTemplate.clone(400, 400));
        enemies.add(enemyTemplate.clone(500, 500));
    }
    @Override
    public void tick() {
        dungeonPlayer.tick(keyHandler, mouseHandler); //Atualiza as informações do player

        Layer layer = tileManager.getCollisionLayer();
        // layer.collisionDetector(dungeonPlayer);

        for (Enemy e: enemies) {
            // layer.collisionDetector(e);
            e.tick(new Vector(dungeonPlayer.getWorldPosX(), dungeonPlayer.getWorldPosY()));
            if (e.hitbox.isHitting(dungeonPlayer.getHitbox())) {
                dungeonPlayer.gotHit(1);
                e.gotHit(1);
            }
            for (Projectile p: e.getRangedAttacks()){
                if (p.getHitbox().isHitting(dungeonPlayer.getHitbox())) {
                    dungeonPlayer.gotHit(e.getWeapon().getDamage());
                    p.setCollided(true);
                }
            }
            for (MeleeWeaponAttack hitbox: dungeonPlayer.getMeleeAttacks())
                if (e.hitbox.isHitting(hitbox))
                    e.gotHit(hitbox.getDamage());

            for (Projectile p: dungeonPlayer.getRangedAttacks())
                if (p.getHitbox().isHitting(e.hitbox)) {
                    e.gotHit(dungeonPlayer.getWeapon().getDamage());
                    p.setCollided(true);
                }
        }
        enemies.removeIf(Enemy::isDead);

        mapNum = this.tileManager.changeStrategy.changeMap(dungeonPlayer, mapNum);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // exemplo
        g2d.setColor(Color.BLACK);

        this.tileManager.draw(g2d);
        //gameState.tm.render(g2d);

        for (Projectile p : this.getProjectiles()){
            p.draw(g2d, this.dungeonPlayer);
            p.getHitbox().draw(g2d, this.dungeonPlayer);
        }

        this.dungeonPlayer.draw(g2d);
        g2d.setColor(Color.red);
        for (Enemy e: this.enemies) {
            g2d.fillOval(
                    (int) (e.getWorldPosX() - this.dungeonPlayer.getWorldPosX() + Constants.WIDTH / 2.0 - 5),
                    (int) (e.getWorldPosY() - this.dungeonPlayer.getWorldPosY() + Constants.HEIGHT / 2.0 - 5),
                    10, 10);
            e.hitbox.draw(g2d, this.dungeonPlayer);
        }
        for (Hitbox h: this.getWeaponHitbox())
            h.draw(g2d, this.dungeonPlayer);
        this.dungeonPlayer.getHitbox().draw(g2d, this.dungeonPlayer);
        this.dungeonPlayer.getAttributes().draw(g2d);
    }


    /**
     * obtém os projéteis
     * @return array list tipo <Projectile>
     */
    private ArrayList<Projectile> getProjectiles() {
        ArrayList<Projectile> projectiles = new ArrayList<>(this.dungeonPlayer.getRangedAttacks());
        for (Enemy e: enemies)
            projectiles.addAll(e.getRangedAttacks());
        return projectiles;
    }

    private ArrayList<MeleeWeaponAttack> getWeaponHitbox() {
        ArrayList<MeleeWeaponAttack> attacks = new ArrayList<>(this.dungeonPlayer.getMeleeAttacks());
        for (Enemy e: enemies)
            attacks.addAll(e.getMeleeAttacks());
        return attacks;
    }

    public int getMapNum() {
        return mapNum;
    }
    public void setMapNum(int mapNum) {
        this.mapNum = mapNum;
    }

    public void setDefaultPosition(int x, int y) {
        this.dungeonPlayer.setPosition(new Vector(x, y));
    }
}
