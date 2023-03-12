package com.personal.piano.instruments;

import org.apache.commons.lang3.StringUtils;

public final class FactoryMidiInstrument {

	private static final MidiInstrument[] VALUES = MidiInstrument.values();

	private FactoryMidiInstrument() {
	}

	public static MidiInstrument computeInstance(
			final String nameParam) {

		MidiInstrument midiInstrument = null;
		for (final MidiInstrument aMidiInstrument : VALUES) {

			final String name = aMidiInstrument.name();
			if (StringUtils.equals(name, nameParam)) {
				midiInstrument = aMidiInstrument;
				break;
			}
		}
		return midiInstrument;
	}

	public static MidiInstrument computeInstance(
			final int bankParam,
			final int programParam) {

		MidiInstrument midiInstrument = null;
		for (final MidiInstrument aMidiInstrument : VALUES) {

			final int bank = aMidiInstrument.getBank();
			final int program = aMidiInstrument.getProgram();
			if (bank == bankParam && program == programParam) {
				midiInstrument = aMidiInstrument;
				break;
			}
		}
		return midiInstrument;
	}
}
