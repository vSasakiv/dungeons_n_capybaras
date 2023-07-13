package gameloop.game_states;

import dungeon_gen.Dungeon;
import dungeon_gen.MonsterRoom;
import game_entity.Hitbox;
import game_entity.DungeonPlayer;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.static_entities.CollidableObject;
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
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private String currentDialogue;
    private int mapNum;
    private final Dungeon dungeon = new Dungeon();
    private TileDungeonManager tileManager;
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    private final ArrayList<CollidableObject> collidableObjects = new ArrayList<>();

    public DungeonState(KeyHandler keyHandler, MouseHandler mouseHandler) {
        dungeonPlayer = new DungeonPlayer(600, 600, 7);

        ProjectileFactory subSubFactory = new BulletFactory(4, 6);
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, 6, subSubFactory );
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, 6, subFactory);
        //player.setWeapon(new MeleeWeapon(20, 4, 50, 50, 30));
        dungeonPlayer.setWeapon(new MultiShotWeapon(5, 4, factory, 30, 3));

        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    public void generateDungeon(String tipo, int size){
        this.dungeon.geraDungeon(tipo, size, 4, 2, 10);
        this.tileManager = new TileDungeonManager(
                this.dungeon.getDungeonTiles(),
                tipo,
                dungeonPlayer,
                new ZonaAbertaStrategy()
        );

    }
    @Override
    public void tick() {
        dungeonPlayer.tick(keyHandler, mouseHandler); //Atualiza as informações do player

        enemies.clear();
        for (MonsterRoom monsterRoom: this.dungeon.getCombatRooms()){
            monsterRoom.startWaves(dungeonPlayer);
            if (monsterRoom.hasWaves()){
                monsterRoom.nextWave();
                this.enemies.addAll(monsterRoom.getCurrentWave());
            }
            monsterRoom.killEnemies();
            this.collidableObjects.addAll(monsterRoom.getActiveDoors());
            //System.out.println(this.collidableObjects);
        }

        Layer layer = tileManager.getCollisionLayer();
        layer.collisionDetector(dungeonPlayer, dungeonPlayer.getHitbox());

        for (CollidableObject collidable: this.collidableObjects){
            collidable.checkCollision(dungeonPlayer, dungeonPlayer.getHitbox());
        }

        for (Enemy e: enemies) {
            //layer.collisionDetector(e, );
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
        collidableObjects.clear();
        mapNum = this.tileManager.changeStrategy.changeMap(dungeonPlayer, mapNum);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // exemplo
        g2d.setColor(Color.BLACK);

        if (mapNum >= 0)
            this.tileManager.draw(g2d);
        //gameState.tm.render(g2d);

        for (MonsterRoom room: this.dungeon.getCombatRooms()){
            room.drawDoors(g2d, this.dungeonPlayer);
        }

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

    public int nextState() {
        return mapNum;
    }
    public void setMapNum(int mapNum) {
        this.mapNum = mapNum;
    }

    public void setDefaultPosition(int x, int y) {
        this.dungeonPlayer.setPosition(new Vector(x, y));
    }

    @Override
    public void setCurrentDialogue(String text) {
        this.currentDialogue = text;
    }

    @Override
    public String getCurrentDialogue() {
        return this.currentDialogue;
    }

}
