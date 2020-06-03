import java.io.*;
import javax.sound.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Listing4801{
    private static void playSampleFile(String name, float pan, float gain)
        throws Exception {
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(name));

        AudioFormat format = ais.getFormat();
    }
}
