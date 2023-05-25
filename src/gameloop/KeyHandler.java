package gameloop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Classe responsável por todas as entradas do usuário pelo teclado
 */
public class KeyHandler extends KeyAdapter {
    // Booleans para teclas apertadas no teclado que implicam em ações.
    private boolean keyW;
    private boolean keyA;
    private boolean keyS;
    private boolean keyD;

    /**
     * Construtor que inicializa todos os atributos boolean para false
     */
    public KeyHandler() {
        this.keyW = false;
        this.keyA = false;
        this.keyS = false;
        this.keyD = false;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> keyW = true;
            case KeyEvent.VK_A -> keyA = true;
            case KeyEvent.VK_S -> keyS = true;
            case KeyEvent.VK_D -> keyD = true;
        }
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> keyW = false;
            case KeyEvent.VK_A -> keyA = false;
            case KeyEvent.VK_S -> keyS = false;
            case KeyEvent.VK_D -> keyD = false;
        }
    }
    public boolean isKeyW() {
        return keyW;
    }

    public boolean isKeyA() {
        return keyA;
    }

    public boolean isKeyS() {
        return keyS;
    }

    public boolean isKeyD() {
        return keyD;
    }

    public boolean isPressed() {
        return isKeyD() || isKeyD() || isKeyS() || isKeyW();
    }
}
