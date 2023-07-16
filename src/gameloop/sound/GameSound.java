package gameloop.sound;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Objects;

public class GameSound {
    protected Clip musicClip;
    protected Clip soundClip;
    protected URL[] musicURL;
    protected URL[] soundURL;

    public void setMusicFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[index]);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSoundFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            soundClip = AudioSystem.getClip();
            soundClip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic() {
        musicClip.start();
    }
    public void playSound() {
        soundClip.start();
    }

    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        musicClip.stop();
    }

    public void setVolume(float volume, String type) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);

        FloatControl gainControl = null;
        if (Objects.equals(type, "MUSIC"))
            gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
        else if (Objects.equals(type, "SOUND"))
            gainControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
        assert gainControl != null;
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
