package com.personal.piano.events;

import com.personal.piano.instruments.MidiInstrument;
import com.personal.piano.parser.MidiParser;
import com.personal.piano.player.MidiPlayer;
import com.personal.piano.writer.MidiWriter;

class SoundEventNoteOn extends AbstractSoundEventNote {

	SoundEventNoteOn(
			final int channel,
			final MidiInstrument midiInstrument,
			final int note,
			final int velocity) {
		super(channel, midiInstrument, note, velocity);
	}

	@Override
	public void play(
			final MidiPlayer midiPlayer) {
		midiPlayer.noteOn(channel, midiInstrument, note, velocity);
	}

	@Override
	public void write(
			final MidiWriter midiWriter) throws Exception {

		addInstrument(midiWriter);
		addNote(midiWriter, MidiParser.NOTE_ON);
	}
}
