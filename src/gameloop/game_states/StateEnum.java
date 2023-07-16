package gameloop.game_states;

/**
 * Enum para representar todos os estados do jogo.
 */
public enum StateEnum {
    mapState(0), dungeonState(1), dialogueState(2), menuState(3);
    public final int estadoAtual;
    StateEnum(int valor){
        this.estadoAtual = valor;
    }
}
