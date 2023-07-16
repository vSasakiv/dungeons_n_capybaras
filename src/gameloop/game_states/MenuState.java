package gameloop.game_states;

import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.game_states.difficulty.DifficultyState;
import gameloop.game_states.difficulty.EasyState;
import gameloop.game_states.difficulty.HardState;
import gameloop.game_states.difficulty.MediumState;

import java.awt.*;

public class MenuState implements State {
    private final KeyHandler keyHandler;
    private boolean exit = false;
    private DifficultyState difficulty;
    public MenuState(KeyHandler keyHandler) {
       this.keyHandler = keyHandler;
       this.difficulty = new EasyState();
    }

    @Override
    public void tick() {
        this.updateDifficulty();
        this.exit = this.keyHandler.isKeyEsc();
        if (this.exit){
            this.keyHandler.setKeyEsc(false);
        }
    }

    private void updateDifficulty(){
        if (this.keyHandler.isKeyF()){
            this.difficulty = new EasyState();
            this.keyHandler.setKeyF(false);
        }
        if (this.keyHandler.isKeyM()){
            this.difficulty = new MediumState();
            this.keyHandler.setKeyM(false);
        }
        if (this.keyHandler.isKeyD()){
            this.difficulty = new HardState();
            this.keyHandler.setKeyD(false);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        int x = Constants.TILE_SIZE * 4;
        int y = Constants.TILE_SIZE;
        int scale = 10;
        int width = scale * 96;
        int height = scale * 32;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(
                x,
                y,
                width,
                height
        );
    }

    @Override
    public void playMusic(int index) {

    }

    @Override
    public void stopMusic() {

    }

    @Override
    public int nextState() {
        if (this.exit){
            return 0;
        }
        return 3;
    }

    @Override
    public void setMapNum(int mapNum) {

    }

    @Override
    public void setDefaultPosition(int x, int y) {

    }

    @Override
    public void setCurrentDialogue(String text) {

    }

    @Override
    public String getCurrentDialogue() {
        return null;
    }

    public DifficultyState getDifficulty (){
        return difficulty;
    }
}
