package com.personal.piano.events;

import com.personal.piano.player.MidiPlayer;
import com.personal.piano.writer.MidiWriter;

public interface SoundEvent {

	void play(
			MidiPlayer midiPlayer);

	void write(
			MidiWriter midiWriter) throws Exception;
}
