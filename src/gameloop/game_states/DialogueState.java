package gameloop.game_states;

import gameloop.KeyHandler;

import java.awt.*;

public class DialogueState implements State{

    private final KeyHandler keyHandler;
    private int nextState;
    private boolean exit = false;
    public DialogueState(KeyHandler keyHandler){
        this.keyHandler = keyHandler;
    }
    @Override
    public void tick() {
        this.exit = this.keyHandler.isKeyEnter();
        if (this.exit){
            keyHandler.setKeyEnter(false);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        int x = 300;
        int y = 100;
        int width = 800;
        int height = 500;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, width, height);

    }

    @Override
    public int nextState() {
        if (exit){
            return nextState;
        }
        return 2;
    }

    public void setNextState(int next){
        this.nextState = next;
    }
    @Override
    public void setMapNum(int mapNum) {}

    @Override
    public void setDefaultPosition(int x, int y) {}
}
