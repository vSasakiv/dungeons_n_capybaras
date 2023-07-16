package gameloop.sound;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Objects;

public class GameSound {
    protected Clip musicClip;
    protected Clip soundClip;
    protected URL[] musicURL; //Caminho para as músicas
    protected URL[] soundURL; //Caminho para os sons

    /**
     * Atribui ao clip a música  número "index" da lista "musicURl".
     * @param index número da música na lista
     */
    public void setMusicFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[index]);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Atribui ao clip o som número "index" da lista "soundURl".
     * @param index número do som na lista
     */
    public void setSoundFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            soundClip = AudioSystem.getClip();
            soundClip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Métodos que tocam os clips
     */
    public void playMusic() {
        musicClip.start();
    }
    public void playSound() {
        soundClip.start();
    }

    /**
     * Deixa a música tocando em loop
     */
    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Interrompe o loop da música
     */
    public void stop() {
        musicClip.stop();
    }

    /**
     * Altera volume de uma música ou um som
     * @param volume novo volume
     * @param type parâmetro para alterar música ("MUSIC") ou som ("SOUND")
     */
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
