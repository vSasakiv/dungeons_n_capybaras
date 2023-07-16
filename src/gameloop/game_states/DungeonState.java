package gameloop.game_states;

import dungeon_gen.Dungeon;
import dungeon_gen.MonsterRoom;
import game_entity.Hitbox;
import game_entity.DungeonPlayer;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.static_entities.CollidableObject;
import game_entity.weapons.MeleeWeaponAttack;
import game_entity.weapons.projectiles.Projectile;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.MouseHandler;
import gameloop.game_states.difficulty.DifficultyState;
import gameloop.game_states.difficulty.EasyState;
import gameloop.sound.DungeonSound;
import gameloop.sound.GameSound;
import tile.Layer;
import tile.ZonaAbertaStrategy;
import tile.dungeon.TileDungeonManager;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que cuida de toda a lógica do jogo numa dungeon
 */
public class  DungeonState implements State{
    public DungeonPlayer dungeonPlayer; // player da dungeon
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private String currentDialogue;
    private int mapNum;
    private final Dungeon dungeon = new Dungeon();
    private TileDungeonManager tileManager;
    private final ArrayList<Enemy> enemies = new ArrayList<>(); // lista de inimigos ativos

    private final ArrayList<CollidableObject> collidableObjects = new ArrayList<>();

    private DifficultyState difficultyState = new EasyState();

    private final GameSound sound = new DungeonSound();

    public DungeonState(KeyHandler keyHandler, MouseHandler mouseHandler) {
       /* dungeonPlayer = new DungeonPlayer(600, 600, 10);

        ProjectileFactory subSubFactory = new BulletFactory(4, 6, "ENERGY");
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, 6, subSubFactory, "ENERGY" );
        ProjectileFactory factory = new ClusterBulletFactory(4, 50, 4, 6, subFactory, "ENERGY");
        //player.setWeapon(new MeleeWeapon(20, 4, 50, 50, 30));
        dungeonPlayer.setWeapon(new AutomaticWeapon(5, 4, factory, "STAFF"));*/
        dungeonPlayer = difficultyState.getPlayer();
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    /**
     * @param tipo de dungeon a ser gerada, "eletrica" ou "bienio"
     * @param size tamanho total do mapa da dungeon
     */
    public void generateDungeon(String tipo, int size){
        // gera a dungeon com os atributos passados
        this.dungeon.geraDungeon(tipo, size, 4, 2, 10);
        this.tileManager = new TileDungeonManager(
                this.dungeon.getDungeonTiles(),
                tipo,
                dungeonPlayer,
                new ZonaAbertaStrategy()
        );
        this.dungeonPlayer.getAttributes().restore();
        this.dungeonPlayer.setPosition(new Vector(600, 600));
        // atualiza os monstros de acordo com a dificuldade
        for (MonsterRoom monsterRoom: this.dungeon.getCombatRooms()){
            for (ArrayList<Enemy> enemyList : monsterRoom.getEnemyWaves()){
                for (Enemy enemy: enemyList){
                    this.difficultyState.updateAttributes(enemy);
                }
            }
        }
    }

    /**
     * Atualiza a sala, ativa os monstros e portas
     */
    public void updateRooms(){
        boolean enemyKilled;
        for (MonsterRoom monsterRoom: this.dungeon.getCombatRooms()){
            monsterRoom.startWaves(dungeonPlayer);
            if (monsterRoom.hasWaves()){
                monsterRoom.nextWave();
                this.enemies.addAll(monsterRoom.getCurrentWave());
            }
            enemyKilled = monsterRoom.killEnemies();
            if (enemyKilled)
                playSound(1, 0.05F);
            if (enemyKilled && monsterRoom.getEnemyWaves().get(monsterRoom.getCurrentWaveNumber()).isEmpty())
                playSound(3, 0.1F);

            this.collidableObjects.addAll(monsterRoom.getActiveDoors());
            //System.out.println(this.collidableObjects);
        }
    }

    /**
     * Verifica todas as colisões entre entidades e objetos estáticos collectives
     */
    private void checkCollidable(){
        for (CollidableObject collidable: this.collidableObjects){
            collidable.checkCollision(dungeonPlayer, dungeonPlayer.getHitbox());
            for (Enemy e: enemies){
                collidable.checkCollision(e, e.hitbox);
            }
        }
    }

    /**
     * verifica colisão do player e seus projéteis com o resto do mapa
     * @param layer layer de colisão
     */
    private void checkPlayer(Layer layer){
        layer.collisionDetector(dungeonPlayer, dungeonPlayer.getHitbox());
        for (Projectile p : dungeonPlayer.getRangedAttacks()) {
            for (CollidableObject collidable : this.collidableObjects) {
                if (p.getHitbox().isHitting(collidable.hitbox)) {
                    p.setCollided(true);
                }
            }
            layer.collisionDetectorProjectile(p);
        }
    }

    /**
     * Verifica colisão de todos os inimigos e seus projéteis com o resto do mapa,e e com o player e seus projéteis
     * @param layer layer de colisão
     */
    private void checkEnemies(Layer layer){
        for (Enemy e: enemies) {
            // verifica colisão com o mapa
            layer.collisionDetector(e, e.hitbox);
            e.tick(new Vector(dungeonPlayer.getWorldPosX(), dungeonPlayer.getWorldPosY()));
            // verifica colisão direta com o player
            if (e.hitbox.isHitting(dungeonPlayer.getHitbox())) {
                dungeonPlayer.gotHit(1);
                e.gotHit(1);
                playSound(2, 0.1F);
            }
            for (Projectile p: e.getRangedAttacks()){
                // verifica colisão de projéteis com o player
                if (p.getHitbox().isHitting(dungeonPlayer.getHitbox())) {
                    dungeonPlayer.gotHit(e.getWeapon().getDamage());
                    playSound(2, 0.1F);
                    p.setCollided(true);
                }
                // verifica colisão de projéteis com objetos estáticos
                for (CollidableObject collidable: this.collidableObjects){
                    if (p.getHitbox().isHitting(collidable.hitbox)){
                        p.setCollided(true);
                    }
                }
                // verifica colisão de projéteis com o mapa
                layer.collisionDetectorProjectile(p);
            }
            for (MeleeWeaponAttack hitbox: dungeonPlayer.getMeleeAttacks())
                // verifica colisão de inimigos com ataques melees do player
                if (e.hitbox.isHitting(hitbox))
                    e.gotHit(hitbox.getDamage());

            for (Projectile p: dungeonPlayer.getRangedAttacks()) {
                // verifica colisão de projéteis do player com os inimigos
                if (p.getHitbox().isHitting(e.hitbox)) {
                    e.gotHit(dungeonPlayer.getWeapon().getDamage());
                    p.setCollided(true);
                }
            }
        }
    }
    @Override
    public void tick() {
        dungeonPlayer.tick(keyHandler, mouseHandler); //Atualiza as informações do player
        if (dungeonPlayer.getAttributes().isDead()){
            this.playSound(0, 0.5F);
            this.setMapNum(-1);
        }

        if (mouseHandler.isMousePress() && dungeonPlayer.getWeapon().canShoot()) {
            playSound(4, 0.1F);
        }
        enemies.clear();

        this.updateRooms();

        Layer layer = tileManager.getCollisionLayer();

        this.checkPlayer(layer);
        this.checkCollidable();
        this.checkEnemies(layer);

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
            room.drawSpawnable(g2d, this.dungeonPlayer);
        }

        for (Projectile p : this.getProjectiles()){
            p.draw(g2d, this.dungeonPlayer);
            p.getHitbox().draw(g2d, this.dungeonPlayer);
        }

        this.dungeonPlayer.draw(g2d);
        g2d.setColor(Color.red);

        for (Enemy e: this.enemies) {
            e.draw(g2d);
            e.hitbox.draw(g2d, this.dungeonPlayer);
        }
        for (Hitbox h: this.getWeaponHitbox())
            h.draw(g2d, this.dungeonPlayer);
        this.dungeonPlayer.getHitbox().draw(g2d, this.dungeonPlayer);
        this.dungeonPlayer.getAttributes().draw(g2d);
    }

    public void playMusic (int index, float volume) {
        sound.setMusicFile(index);
        sound.playMusic();
        sound.loop();
        sound.setVolume(volume, "MUSIC");
    }

    public void playSound(int index, float volume) {
        sound.setSoundFile(index);
        sound.setVolume(volume, "SOUND");
        sound.playSound();
    }

    public void stopMusic () {
        sound.stop();
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

    public void setDifficultyState(DifficultyState difficultyState) {
        this.difficultyState = difficultyState;
        this.dungeonPlayer = difficultyState.getPlayer();
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
