import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    private Clip clip;  // Define clip at the class level

    public void playIntroSound() {
        playSound("/resources/intro.wav");
    }

    public void playClickSound() {
        playSound("/resources/click.wav");
    }

    public void playBogshSound() {
        playSound("/resources/bomb.wav");
    }

    private void playSound(String resourcePath) {
        try {
            URL soundURL = getClass().getResource(resourcePath);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + resourcePath);
                return;
            }

            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL)) {
                if (clip != null && clip.isRunning()) {
                    clip.stop();  // Stop any currently playing clip
                }
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
