package gameloop.sound;
import java.net.URL;

/**
 * Classe que carrega os arquivos de áudio usados pelo Player
 */
public class PlayerSound extends GameSound {
    public PlayerSound () {
        soundURL = new URL[1];
        soundURL[0] = getClass().getResource("/resources/sounds/Retro_FootStep_03.wav");
    }
}
