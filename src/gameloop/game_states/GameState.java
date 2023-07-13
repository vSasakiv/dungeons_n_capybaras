package gameloop.game_states;

import gameloop.Constants;
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
        this.stateList = new State[3];
        this.stateList[0] = new MapState(keyHandler);
        this.stateList[1] = new DungeonState(keyHandler, mouseHandler);
        this.stateList[2] = new DialogueState(keyHandler);
    }

    /**
     * Método chamado a cada tick do GameLoop, onde devemos atualizar os estados do GameState
     */
    public void tick() {
        updateState();
        this.stateList[currentState.estadoAtual].tick();
    }

    private void updateState() {
        switch (currentState) {
            case mapState -> {
                if (stateList[0].nextState() == -1) {
                    this.currentState = StateEnum.dungeonState;
                    DungeonState state = (DungeonState) stateList[1];
                    state.generateDungeon("bienio", 255);
                    stateList[0].setMapNum(0);
                    stateList[1].setMapNum(0);
                    stateList[currentState.estadoAtual].setDefaultPosition(Constants.TILE_SIZE * 43, Constants.TILE_SIZE * 41);
                } else if (stateList[0].nextState() == -2) {
                    this.currentState = StateEnum.dialogueState;
                    stateList[2].setCurrentDialogue(stateList[0].getCurrentDialogue());
                    stateList[0].setMapNum(0);
                } else if (stateList[0].nextState() == -3) {
                    this.currentState = StateEnum.dungeonState;
                    DungeonState state = (DungeonState) stateList[1];
                    state.generateDungeon("eletrica", 255);
                    stateList[0].setMapNum(0);
                    stateList[1].setMapNum(1);
                    stateList[currentState.estadoAtual].setDefaultPosition(Constants.TILE_SIZE * 42, Constants.TILE_SIZE * 42);

                }
            }
            case dungeonState -> {
                if (stateList[1].nextState() == -1) {
                    this.currentState = StateEnum.mapState;
                    stateList[1].setMapNum(0);
                    stateList[currentState.estadoAtual].setDefaultPosition(42 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE);
                }
            }
            case dialogueState -> {
                if (stateList[2].nextState() != 2){
                    this.currentState = StateEnum.mapState;
                }
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
