package com.personal.piano.player;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;

import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class MidiPlayerTest {

	@Test
	void testPrintAllInstruments() throws Exception {

		final StringBuilder sbInstruments = new StringBuilder();

		final Synthesizer synthesizer = MidiSystem.getSynthesizer();
		final Instrument[] availableInstruments = synthesizer.getAvailableInstruments();
		for (final Instrument instrument : availableInstruments) {

			final Patch patch = instrument.getPatch();

			sbInstruments.append("INST_");
			final String instrumentName = instrument.getName().trim();
			for (int j = 0; j < instrumentName.length(); j++) {

				final char ch = instrumentName.charAt(j);
				if (Character.isLetterOrDigit(ch)) {
					sbInstruments.append(Character.toUpperCase(ch));
				} else if (ch == ' ') {
					sbInstruments.append('_');
				}
			}

			final int bank = patch.getBank();
			if (bank != 0) {
				sbInstruments.append('_').append(bank);
			}

			sbInstruments.append("(\"");
			sbInstruments.append(instrumentName);
			sbInstruments.append("\", ");

			sbInstruments.append(bank);
			sbInstruments.append(", ");

			final int program = patch.getProgram();
			sbInstruments.append(program);
			sbInstruments.append("),");

			sbInstruments.append(System.lineSeparator());
		}
		synthesizer.close();

		Logger.printLine(sbInstruments);
	}
}
