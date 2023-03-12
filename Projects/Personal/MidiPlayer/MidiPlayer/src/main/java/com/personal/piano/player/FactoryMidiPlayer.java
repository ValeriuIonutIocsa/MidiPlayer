package com.personal.piano.player;

import java.io.File;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import com.personal.piano.events.SoundEvent;
import com.utils.log.Logger;

public final class FactoryMidiPlayer {

	private FactoryMidiPlayer() {
	}

	public static MidiPlayer newInstance(
			final double speed,
			final List<SoundEvent> soundEventList,
			final String[] soundFontFilePathStrings) {

		MidiPlayer midiPlayer = null;
		try {
			final Synthesizer synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();

			if (soundFontFilePathStrings != null) {

				for (final String soundFontFilePathString : soundFontFilePathStrings) {

					final boolean debugMode = Logger.isDebugMode();
					if (debugMode) {
						Logger.printLine(soundFontFilePathString);
					}

					final File soundFontFile = new File(soundFontFilePathString);
					final Soundbank soundbank = MidiSystem.getSoundbank(soundFontFile);

					if (debugMode) {
						final Instrument[] instruments = soundbank.getInstruments();
						for (final Instrument instrument : instruments) {
							Logger.printLine(instrument);
						}
					}

					synthesizer.loadAllInstruments(soundbank);
				}
			}

			final MidiChannel[] midiChannels = synthesizer.getChannels();
			midiPlayer = new MidiPlayer(speed, soundEventList, midiChannels);

		} catch (final Exception exc) {
			Logger.printError("failed to create MidiPlayer!");
			Logger.printException(exc);
		}
		return midiPlayer;
	}
}
