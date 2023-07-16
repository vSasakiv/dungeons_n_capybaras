package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;
import game_entity.entity_sprites.MovingEntitySprites;
import game_entity.entity_sprites.NpcOldManSprite;
import gameloop.Constants;
import gameloop.render.DrawMovingEntity;

import java.awt.*;
import java.util.ArrayList;

public class OldManNpc extends GameEntity implements MovableNpc{

    // Hitbox do npc
    private final Hitbox hitbox;
    // Estratégia de movimentação do npc
    private NpcStrategy strategy;
    // Método para desenhar npc na tela
    private final DrawMovingEntity drawMethod;
    // Diálogos do npc
    private final String[] dialogues = new String[3];

    /**
     * @param worldPosX posição x no mundo do npc
     * @param worldPosY posição y no mundo do npc
     * @param velocity velocidade do npc
     */
    public OldManNpc(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY, velocity);
        this.strategy = new PatrolStrategy(this, new Vector(0, 0));
        this.hitbox = new Hitbox(Constants.TILE_SIZE, Constants.TILE_SIZE, this.position);
        ArrayList<MovingEntitySprites> sprites = new ArrayList<>();
        sprites.add(new NpcOldManSprite());
        this.drawMethod = new DrawMovingEntity(this, sprites);
        this.setSpriteSizeX(Constants.TILE_SIZE);
        this.setSpriteSizeY(Constants.TILE_SIZE);

    }

    /**
     * @param playerPos vetor posição do player
     */
    @Override
    public void tick(Vector playerPos) {
        this.setDirection(this.strategy.direction(this, playerPos));
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.getDirection(), velocity));
        this.hitbox.setPosition(this.position);
        this.setDialogues();
        this.drawMethod.spriteUpdate(this.getDirection());
        this.drawMethod.spriteCounterUpdate();
        this.setScreenX(this.getWorldPosX() - playerPos.x + (float) Constants.WIDTH /2 - (float) this.getSpriteSizeX() / 2);
        this.setScreenY(this.getWorldPosY() - playerPos.y + (float) Constants.HEIGHT /2 - (float) this.getSpriteSizeX() / 2);
    }

    /**
     * @param strategy modifica a strategy do npc
     */
    public void setStrategy(NpcStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @param hitbox Hitbox de outra entidade
     * @return true caso esteja colidindo
     */
    @Override
    public boolean isColliding(Hitbox hitbox) {
        return (hitbox.isHitting(this.hitbox));
    }

    /**
     * @param g2d    Graphics2D java
     * @param player Entidade referência
     */
    @Override
    public void draw(Graphics2D g2d, GameEntity player) {
        drawMethod.draw(g2d);
    }

    private void setDialogues () {
        this.dialogues[0] = "Alguma coisa estranha está acontecendo, o Biênio foi invadido!\nEu não sou pago o suficiente para isso... \nVocê parece forte, poderia resolver isso? Preciso aplicar um \ntestinho mais tarde...";
        this.dialogues[1] = "Obrigado por resolver o problema do Biênio! \nUma pena que aquele lugar agora está uma bagunça...";
        this.dialogues[2] = "Parece que fecharam a sub de cálculo haha,\nos alunos estão enlouquecendo.";
    }

    /**
     * @return Array de String contendo diálogo
     */
    public String[] getDialogues() {
        return dialogues;
    }

}
