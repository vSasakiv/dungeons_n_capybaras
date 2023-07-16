package gameloop.sound;

import java.net.URL;

/**
 * Classe que carrega os arquivos de áudio usados nos mapas
 */
public class MapSound extends GameSound {
    public MapSound () {
        musicURL = new URL[3];
        musicURL[0] = getClass().getResource("/resources/sounds/Adventure_Begin.wav");
        musicURL[1] = getClass().getResource("/resources/sounds/Peaceful.wav");
        musicURL[2] = getClass().getResource("/resources/sounds/End_Theme.wav");

        soundURL = new URL[2];
        soundURL[0] = getClass().getResource("/resources/sounds/Voice1.wav");
    }

}
