package src.game_entity;

public enum MapPlayerStateEnum {
    DEFAULT(3), NINJA(7);
    public final int estadoAtual;

    MapPlayerStateEnum(int valor){
        this.estadoAtual = valor;
    }
}
