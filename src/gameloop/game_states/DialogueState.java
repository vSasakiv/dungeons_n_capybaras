package gameloop.game_states;

import gameloop.Constants;
import gameloop.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Objects;

/**
 * Estado para representar um diálogo ocorrendo.
 */
public class DialogueState implements State{

    private final KeyHandler keyHandler;
    private int nextState;
    private boolean exit = false;
    private String currentDialogue;
    private BufferedImage dialogueWindow;

    private Font pixeled;

    public DialogueState(KeyHandler keyHandler){
        this.keyHandler = keyHandler;
        loadImage();
    }

    /**
     * Caso Enter seja pressionado, sai do diálogo
     */
    @Override
    public void tick() {
        this.exit = this.keyHandler.isKeyEnter();
        if (this.exit){
            keyHandler.setKeyEnter(false);
        }
    }

    private void loadImage () {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/UI/fonts/VCRosdNEUE.ttf");
            assert is != null;
            pixeled = Font.createFont(Font.TRUETYPE_FONT, is);
            dialogueWindow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/UI/UI_Flat_Button_Medium_Press_02a3.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        int x = Constants.TILE_SIZE * 4;
        int y = Constants.TILE_SIZE ;
        int scale = 10;
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
        x += Constants.TILE_SIZE * 2;
        y += Constants.TILE_SIZE * 2.5;
        for (String line: currentDialogue.split("\n")) {
            g2d.drawString(line, x, y);
            y += 40;
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

    @Override
    public void setCurrentDialogue(String text) {
        currentDialogue = text;
    }

    @Override
    public String getCurrentDialogue() {
        return currentDialogue;
    }

}
