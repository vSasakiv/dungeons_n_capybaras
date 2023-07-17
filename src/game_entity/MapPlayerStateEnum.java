package game_entity;

/**
 * Enum para representar todos os estados do player do mapa.
 */
public enum MapPlayerStateEnum {
    DEFAULT(5), NINJA(20);
    public final int estadoAtual;

    MapPlayerStateEnum(int valor){
        this.estadoAtual = valor;
    }
}
