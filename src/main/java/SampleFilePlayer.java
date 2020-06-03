import javax.sound.sampled.*;
import java.io.File;

    final class SampleFilePlayer{

        private static void playSampleFile(String name) /*, float pan, float gain*/
                throws Exception {
            //AudioInputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(name));

            AudioFormat format = ais.getFormat();
            //ALAW/ULAW samples zu PCM konvertieren
            if ((format.getEncoding() == AudioFormat.Encoding.ULAW) || (format.getEncoding() == AudioFormat.Encoding.ALAW))
            {
                AudioFormat tmp = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        format.getSampleRate(),
                        format.getSampleSizeInBits(),
                        format.getChannels(),
                        format.getFrameSize() * 2,
                        format.getFrameRate(),
                        true
                );
                ais = AudioSystem.getAudioInputStream(tmp, ais);
                format = tmp;
            }
            //Clip erzeugen und öffnen
            DataLine.Info info = new DataLine.Info(
                    Clip.class,
                    format,
                    ((int) ais.getFrameLength() * format.getFrameSize()));

            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);

            //PAN
//            FloatControl panControl = (FloatControl)clip.getControl(FloatControl.Type.PAN);
//            panControl.setValue(pan);
//
//            //MASTER_GAIN
//            FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
//            gainControl.setValue(gain);

            //Clip erstellen
            clip.start();
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    //nothing
                }
                if (!clip.isRunning()) {
                    break;
                }
            }
            clip.stop();
            clip.close();
        }

        public static void main(String[]args) throws Exception {
//            try {
//                playSampleFile(
//                        args[0],
//                        Float.parseFloat(args[1]),
//                        Float.parseFloat(args[2]));
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.exit(1);
//            }
//            System.exit(0);

            String filepath = "./sample/testAnalogFX.wav";

            SampleFilePlayer test = new SampleFilePlayer();
            test.playSampleFile(filepath);
        }
    }
