package midi;

import javax.sound.midi.*;

public class midiSequenzer {
    private static void playAME()
            throws Exception {
        //score {{pitch, DauerInViertelNoten, AnzahlWiederholungen,..}
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
        //building sequence
        final int PPQS = 16;
        final int STAKKATO = 4;
        Sequence seq = new Sequence(Sequence.PPQ, PPQS);
        Track track = seq.createTrack();
        long currentTick = 0;
        ShortMessage msg;
        //switching channel 0 to "EnsembleStrings" (SoundLibrary)
        msg = new ShortMessage();
        msg.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 48, 0);
        track.add(new MidiEvent(msg, currentTick));
        //add scoreData
        for (int i = 0; i < DATA.length; ++i) {
            for (int j = 0; j < DATA[i][2]; ++j) { //repetition per note
                msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_ON, 0, DATA[i][0], 64);
                track.add(new MidiEvent(msg, currentTick));
                currentTick += PPQS * DATA[i][1] - STAKKATO;
                msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_OFF, 0, DATA[i][0], 0);
                track.add(new MidiEvent(msg, currentTick));
                currentTick += STAKKATO;
            }
        }
        //initialise sequencer & synthesizer
        Sequencer sequencer = MidiSystem.getSequencer();
        Transmitter trans = sequencer.getTransmitter();
        Synthesizer synth = MidiSystem.getSynthesizer();
        Receiver rcvr = synth.getReceiver();
        //open & connect both
        sequencer.open();
        synth.open();
        trans.setReceiver(rcvr);
        //play sequence
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(145);
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
            playAME();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}