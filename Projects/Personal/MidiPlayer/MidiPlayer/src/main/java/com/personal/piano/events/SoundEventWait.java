package com.personal.piano.events;

import com.personal.piano.player.MidiPlayer;
import com.personal.piano.writer.MidiWriter;
import com.utils.concurrency.ThreadUtils;

public class SoundEventWait extends AbstractSoundEvent {

	private final int durationMs;

	public SoundEventWait(
			final int durationMs) {

		this.durationMs = durationMs;
	}

	@Override
	public void play(
			final MidiPlayer midiPlayer) {

		final double speed = midiPlayer.getSpeed();
		final int calibratedDurationMs = (int) Math.round(durationMs * speed);
		ThreadUtils.trySleep(calibratedDurationMs);
	}

	@Override
	public void write(
			final MidiWriter midiWriter) {
		midiWriter.incrementTick(durationMs);
	}
}
