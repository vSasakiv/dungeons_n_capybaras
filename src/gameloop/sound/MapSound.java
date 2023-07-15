package gameloop.sound;

import java.net.URL;

public class MapSound extends GameSound {
    public MapSound () {
        musicURL = new URL[3];
        musicURL[0] = getClass().getResource("/resources/sounds/Adventure_Begin.wav");
        musicURL[1] = getClass().getResource("/resources/sounds/Peaceful.wav");
        musicURL[2] = getClass().getResource("/resources/sounds/End_Theme.wav");
    }

}
