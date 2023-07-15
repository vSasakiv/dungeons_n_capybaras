package gameloop.sound;

import java.net.URL;

public class DungeonSound extends GameSound {
    public DungeonSound () {
        musicURL = new URL[4];
        musicURL[0] = getClass().getResource("/resources/sounds/Fight.wav");
        musicURL[1] = getClass().getResource("/resources/sounds/Dark_Castle.wav");
        musicURL[2] = getClass().getResource("/resources/sounds/Curse.wav");
        musicURL[3] = getClass().getResource("/resources/sounds/Story.wav");

        soundURL = new URL[1];
        soundURL[0] = getClass().getResource("/resources/sounds/videogame-death-sound-43894.wav");
    }

}
