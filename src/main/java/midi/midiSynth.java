package midi;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import java.nio.channels.ScatteringByteChannel;

public class midiSynth {
    private static void playAME()
            throws Exception {
        //Partitur {{pitch, DauerInViertelNoten, AnzahlWiederholungen,..}
        final int DATA[][] = {
                {60, 1, 1},
                {62, 1, 1},
                {64, 1, 1},
                {65, 1, 1},
                {67, 2, 2},
                {69, 1, 4},
                {67, 4, 1},
                {69, 1, 4},
                {67, 4, 1},
                {65, 1, 4},
                {64, 2, 2},
                {62, 1, 4},
                {60, 4, 1}
        };
        //open synth and get receiver
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        Receiver rcvr = synth.getReceiver();
        //play melody
        ShortMessage msg = new ShortMessage();
        for (int i = 0; i < DATA.length; ++i) {
            for (int j = 0; j < DATA[i][2]; ++j) { //repetition per note
                //note on
                msg.setMessage(ShortMessage.NOTE_ON, 0, DATA[i][0], 64);
                rcvr.send(msg, -1);
                //pause
                try {
                    Thread.sleep(DATA[i][1] * 400);
                } catch (Exception e) {
                    //nothing
                }
                //note off
                msg.setMessage(ShortMessage.NOTE_OFF, 0, DATA[i][0], 0);
                rcvr.send(msg, -1);
            }
        }
        //close synth
        synth.close();
    }

    public static void main(String[] args) {
        try {
            playAME();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
