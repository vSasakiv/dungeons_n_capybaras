package gameloop.game_states;

import gameloop.KeyHandler;
import gameloop.MouseHandler;
import java.awt.event.KeyListener;

/**
 * Classe que cuida de toda a lógica do jogo
 */
public class GameState {

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private StateEnum currentState;
    private final State[] stateList;

    /**
     * Construtor que inicia o GameState, onde são criados todos os estados, e os handlers de entrada
     */
    public GameState() {
        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();
        this.currentState = StateEnum.mapState;
        this.stateList = new State[2];
        this.stateList[0] = new MapState(keyHandler);
        this.stateList[1] = new DungeonState(keyHandler, mouseHandler);
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        updateState();
        this.stateList[currentState.estadoAtual].tick();
    }

    /**
     * Atualiza o estado o gameState caso alguma tecla tenha sido pressionada
     */
    private void updateState(){
        switch (currentState){
            case mapState -> {
                if (this.keyHandler.isKeyP()) this.currentState = StateEnum.dungeonState;
            }
            case dungeonState -> {
                if (this.keyHandler.isKeyQ()) this.currentState = StateEnum.mapState;
            }
        }
    }

    /**
     * Obtém o KeyListener utilizado no GameState, para inclusão no GameFrame.
     * @return o KeyListener 
     */
    public KeyListener getKeyHandler() {
        return keyHandler;
    }

    /**
     * Obtém o MouseListener
     * @return MouseHandler
     */
    public MouseHandler getMouseListener() {
        return mouseHandler;
    }

    /**
     * Obtém o MouseHandler
     * @return MouseHandler
     */
    public MouseHandler getMouseMotionListener() {
        return mouseHandler;
    }

    public State getActualState(){
        return this.stateList[this.currentState.estadoAtual];
    }
}
