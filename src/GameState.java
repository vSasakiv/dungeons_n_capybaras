import java.awt.KeyEventDispatcher;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class GameState {

    private boolean keyW, keyA, keyS, keyD;
    public int x, y;   

    private KeyHandler keyHandler;

    public GameState() {
        this.x = 150;
        this.y = 150;
        this.keyW = false;
        this.keyA = false;
        this.keyS = false;
        this.keyD = false;
        keyHandler = new KeyHandler();
    }

    public void tick() {
        System.out.println("Ticking");
        if (this.keyW)
            y -= 8;
        if (this.keyA)
            x -= 8;
        if (this.keyS)
            y += 8;
        if (this.keyD)
            x += 8;
        
        x = Math.max(x, 0);
        x = Math.min(x, GameFrame.WIDTH - 40);
        y = Math.max(y, 30);
        y = Math.min(y, GameFrame.HEIGHT - 20);

    }

    public KeyListener getKeyHandler() {
        return keyHandler;
    }

    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    keyW = true;
                    break;
                case KeyEvent.VK_A:
                    keyA = true;
                    break;
                case KeyEvent.VK_S:
                    keyS = true;
                    break;
                case KeyEvent.VK_D:
                    keyD = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    keyW = false;       
                    break;
                case KeyEvent.VK_A:
                    keyA = false;
                    break;
                case KeyEvent.VK_S:
                    keyS = false;
                    break;
                case KeyEvent.VK_D:
                    keyD = false;
                    break;
            }
        }
    }
}
