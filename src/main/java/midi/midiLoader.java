package midi;

import javax.sound.midi.*;
import java.io.File;

public class midiLoader {
    private static void playMidiFile(String name)
        throws Exception
    {
        //initialise sequencer & synthesizer
        Sequencer sequencer = MidiSystem.getSequencer();
        Transmitter trans = sequencer.getTransmitter();
        Synthesizer synth = MidiSystem.getSynthesizer();
        Receiver rcvr = synth.getReceiver();
        //open & connect both
        sequencer.open();
        synth.open();
        trans.setReceiver(rcvr);
        //read sequence & play
        Sequence seq = MidiSystem.getSequence(new File("./sample/42337.MID"));
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(120);
        sequencer.start();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //nothing
            }
            if (!sequencer.isRunning()) {
                break;
            }
        }


        //stop sequencer and close
        sequencer.stop();
        sequencer.close();
        synth.close();
    }

    public static void main(String[] args) {
        try {
            playMidiFile(args[0]);
        }   catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
