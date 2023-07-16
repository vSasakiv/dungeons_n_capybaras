package gameloop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Classe responsável por todas as entradas do usuário pelo teclado
 */
public class KeyHandler extends KeyAdapter {
    // Booleans para teclas pressionadas no teclado
    private boolean keyW; // true se tecla W está pressionada
    private boolean keyA; // true se tecla A está pressionada
    private boolean keyS; // true se tecla S está pressionada
    private boolean keyD; // true se tecla D está pressionada
    private boolean keyN; // true se tecla N está pressionada
    private boolean keyEnter;
    private boolean keyEsc;
    private boolean keyF;
    private boolean keyM;

    /**
     * Construtor padrão, que inicializa os atributos boolean como falsos
     */
    public KeyHandler() {
        this.keyW = false;
        this.keyA = false;
        this.keyS = false;
        this.keyD = false;
        this.keyN = false;
        this.keyEnter = false;
        this.keyEsc = false;
        this.keyF = false;
        this.keyM = false;
    }

    /**
     * Atualiza o estado de ativação de cada tecla
     * @param e o evento de KeyPressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> keyW = true;
            case KeyEvent.VK_A -> keyA = true;
            case KeyEvent.VK_S -> keyS = true;
            case KeyEvent.VK_D -> keyD = true;
            case KeyEvent.VK_N -> keyN = true;
            case KeyEvent.VK_ENTER -> keyEnter = true;
            case KeyEvent.VK_ESCAPE -> keyEsc = true;
            case KeyEvent.VK_F -> keyF = true;
            case KeyEvent.VK_M -> keyM = true;
        }
    }

    /**
     * Atualiza o estado de ativação de cada tecla
     * @param e o evento de KeyReleased
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> keyW = false;
            case KeyEvent.VK_A -> keyA = false;
            case KeyEvent.VK_S -> keyS = false;
            case KeyEvent.VK_D -> keyD = false;
            case KeyEvent.VK_N -> keyN = false;
            case KeyEvent.VK_ENTER -> keyEnter = false;
            case KeyEvent.VK_ESCAPE -> keyEsc = false;
            case KeyEvent.VK_F -> keyF = false;
            case KeyEvent.VK_M -> keyM = false;
        }
    }

    /**
     * Verifica se W está sendo pressionado
     * @return true, caso esteja; false, caso contrário
     */
    public boolean isKeyW() {
        return keyW;
    }

    /**
     * Verifica se A está sendo pressionado
     * @return true, caso esteja; false, caso contrário
     */
    public boolean isKeyA() {
        return keyA;
    }

    /**
     * Verifica se S está sendo pressionado
     * @return true, caso esteja; false, caso contrário
     */
    public boolean isKeyS() {
        return keyS;
    }

    /**
     * Verifica se D está sendo pressionado
     * @return true, caso esteja; false, caso contrário
     */
    public boolean isKeyD() {
        return keyD;
    }


    public boolean isKeyN() {
        return keyN;
    }

    public void setKeyN(boolean keyN) {
        this.keyN = keyN;
    }

    public boolean isKeyEnter() { return keyEnter; }

    public void setKeyEnter(boolean keyEnter) {this.keyEnter = keyEnter; }

    public boolean isKeyEsc() {
        return keyEsc;
    }

    public void setKeyEsc(boolean keyEsc) {
        this.keyEsc = keyEsc;
    }

    public void setKeyD(boolean keyD) {
        this.keyD = keyD;
    }

    public boolean isKeyF() {
        return keyF;
    }

    public void setKeyF(boolean keyF) {
        this.keyF = keyF;
    }

    public boolean isKeyM() {
        return keyM;
    }

    public void setKeyM(boolean keyM) {
        this.keyM = keyM;
    }

    /**
     * Verifica se alguma tecla está sendo pressionada
     * @return true, caso esteja; false, caso contrário
     */
    public boolean isPressed() {
        return isKeyD() || isKeyA() || isKeyS() || isKeyW();
    }
}
