package gameloop.game_states;

import gameloop.sound.GameSound;

import java.net.URL;

public class PlayerSound extends GameSound {
    public PlayerSound () {
        soundURL = new URL[1];
        soundURL[0] = getClass().getResource("/resources/sounds/Retro_FootStep_03.wav");
    }
}
