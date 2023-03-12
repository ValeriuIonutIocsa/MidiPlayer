package com.personal.piano.events;

import javax.sound.midi.ShortMessage;

import com.personal.piano.instruments.MidiInstrument;
import com.personal.piano.parser.MidiParser;
import com.personal.piano.writer.MidiWriter;

abstract class AbstractSoundEventNote extends AbstractSoundEvent {

	final int channel;
	final MidiInstrument midiInstrument;
	final int note;
	final int velocity;

	AbstractSoundEventNote(
			final int channel,
			final MidiInstrument midiInstrument,
			final int note,
			final int velocity) {

		this.channel = channel;
		this.midiInstrument = midiInstrument;
		this.note = note;
		this.velocity = velocity;
	}

	void addInstrument(
			final MidiWriter midiWriter) throws Exception {

		final ShortMessage shortMessage = new ShortMessage();
		final int bank = midiInstrument.getBank();
		final int program = midiInstrument.getProgram();
		shortMessage.setMessage(MidiParser.PROGRAM_CHANGE, bank, program);

		midiWriter.addMessage(shortMessage);
	}

	void addNote(
			final MidiWriter midiWriter,
			final int status) throws Exception {

		final ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessage(status, note, velocity);

		midiWriter.addMessage(shortMessage);
	}
}
