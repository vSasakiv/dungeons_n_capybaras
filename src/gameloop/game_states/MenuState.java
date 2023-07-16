package gameloop.game_states;

import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.game_states.difficulty.DifficultyState;
import gameloop.game_states.difficulty.EasyState;
import gameloop.game_states.difficulty.HardState;
import gameloop.game_states.difficulty.MediumState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Objects;

public class MenuState implements State {
    private final KeyHandler keyHandler;
    private boolean exit = false;
    private DifficultyState difficulty;
    private BufferedImage dialogueWindow;
    private Font pixeled;
    private String currentDialogue;

    public MenuState(KeyHandler keyHandler) {
       this.keyHandler = keyHandler;
       this.difficulty = new EasyState();
        this.currentDialogue = "Dificuldade atual: fácil.";
       loadImage();
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
            this.currentDialogue = "Dificuldade atual: fácil.";
        }
        if (this.keyHandler.isKeyM()){
            this.difficulty = new MediumState();
            this.keyHandler.setKeyM(false);
            this.currentDialogue = "Dificuldade atual: médio.";
        }
        if (this.keyHandler.isKeyD()){
            this.difficulty = new HardState();
            this.keyHandler.setKeyD(false);
            this.currentDialogue = "Dificuldade atual: difícil.";
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        String defaultText = "Seleciona a dificuldade desejada apertando no telcado o botão indicado:\nF - Fácil\nM - Médio\nD - Difícil";
        int x = 0;
        int y = Constants.TILE_SIZE * 2 ;
        int scale = 15;
        int width = scale * 96;
        int height = scale * 32;

        g2d.drawImage(
                dialogueWindow,
                x,
                y,
                width,
                height,
                null
        );

        Color color = new Color(0, 0, 0);
        g2d.setColor(color);
        g2d.setFont(pixeled);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 25));
        x += Constants.TILE_SIZE * 6.5;
        y += Constants.TILE_SIZE * 3.5;
        for (String line: defaultText.split("\n")) {
            g2d.drawString(line, x, y);
            y += 40;
        }
        g2d.drawString(currentDialogue, x, y);
    }

    private void loadImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/UI/fonts/VCRosdNEUE.ttf");
            assert is != null;
            pixeled = Font.createFont(Font.TRUETYPE_FONT, is);
            dialogueWindow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/UI/UI_Flat_Banner_02_Downward.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playMusic(int index, float volume) {
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
