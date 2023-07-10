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

    private final Hitbox hitbox;
    private NpcStrategy strategy;
    private final DrawMovingEntity drawMethod;
    private final String[] dialogues = new String[3];

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

    public void setStrategy(NpcStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean isColliding(Hitbox hitbox) {
        return (hitbox.isHitting(this.hitbox));
    }

    @Override
    public void draw(Graphics2D g2d, GameEntity player) {
        drawMethod.draw(g2d);
        this.hitbox.draw(g2d, player);
    }

    private void setDialogues () {
        this.dialogues[0] = "Alguma coisa estranha está acontecendo, o Biênio foi invadido!\nEu não sou pago o suficiente para isso... \nVocê parece forte, poderia resolver isso? Preciso aplicar um \ntestinho mais tarde...";
        this.dialogues[1] = "Obrigado por resolver o problema do Biênio! \nUma pena que aquele lugar agora está uma bagunça...";
        this.dialogues[2] = "Parece que fecharam a sub de cálculo haha,\nos alunos estão enlouquecendo.";
    }

    public String[] getDialogues() {
        return dialogues;
    }

}
