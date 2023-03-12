package com.personal.piano.parser;

import java.io.File;
import java.nio.file.Path;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.personal.piano.instruments.FactoryMidiInstrument;
import com.personal.piano.instruments.MidiInstrument;
import com.utils.string.StrUtils;

public final class MidiParser {

	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final int PROGRAM_CHANGE = 0xC0;

	public static final String[] NOTE_NAME_ARRAY =
			{ "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B" };

	private MidiParser() {
	}

	public static void parse(
			final Path midiFilePath) throws Exception {

		final File midiFile = midiFilePath.toFile();
		final Sequence sequence = MidiSystem.getSequence(midiFile);

		int trackNumber = 0;
		final Track[] trackArray = sequence.getTracks();
		for (final Track track : trackArray) {

			trackNumber++;

			final int trackSize = track.size();
			System.out.println("Track " + trackNumber + ": size = " + trackSize);
			System.out.println();

			for (int i = 0; i < trackSize; i++) {

				final MidiEvent midiEvent = track.get(i);

				final long tick = midiEvent.getTick();
				System.out.print(StrUtils.createLeftPaddedString(String.valueOf(tick), 8));
				System.out.print("   ");

				final MidiMessage message = midiEvent.getMessage();
				if (message instanceof ShortMessage) {

					final ShortMessage shortMessage = (ShortMessage) message;
					System.out.print("Channel: " + shortMessage.getChannel() + " ");
					final int command = shortMessage.getCommand();
					if (command == NOTE_ON) {

						final int key = shortMessage.getData1();
						final int octave = (key / 12) - 1;
						final int note = key % 12;
						final String noteName = NOTE_NAME_ARRAY[note];

						final int velocity = shortMessage.getData2();

						System.out.println("Note on, " + noteName + octave + " velocity: " + velocity);

					} else if (command == NOTE_OFF) {

						final int key = shortMessage.getData1();
						final int octave = (key / 12) - 1;
						final int note = key % 12;
						final String noteName = NOTE_NAME_ARRAY[note];

						final int velocity = shortMessage.getData2();

						System.out.println("Note off, " + noteName + octave + " velocity: " + velocity);

					} else if (command == PROGRAM_CHANGE) {

						final int bank = shortMessage.getData1();
						final int program = shortMessage.getData2();
						final MidiInstrument midiInstrument =
								FactoryMidiInstrument.computeInstance(bank, program);

						System.out.println("Program change, " + midiInstrument.name());

					} else {
						System.out.println("Command:" + command);
					}

				} else {
					System.out.println("Other message: " + message.getClass());
				}
			}

			System.out.println();
		}
	}
}
