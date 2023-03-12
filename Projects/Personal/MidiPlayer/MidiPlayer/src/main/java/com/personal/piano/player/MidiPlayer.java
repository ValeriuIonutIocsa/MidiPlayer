package com.personal.piano.player;

import java.util.List;

import javax.sound.midi.MidiChannel;

import com.personal.piano.events.SoundEvent;
import com.personal.piano.events.SoundEventWait;
import com.personal.piano.instruments.MidiInstrument;
import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public class MidiPlayer {

	private final double speed;
	private final List<SoundEvent> soundEventList;
	private final MidiChannel[] midiChannels;

	MidiPlayer(
			final double speed,
			final List<SoundEvent> soundEventList,
			final MidiChannel[] midiChannels) {

		this.speed = speed;
		this.soundEventList = soundEventList;
		this.midiChannels = midiChannels;
	}

	public void play() {

		Logger.printProgress("playing sound events");
		new SoundEventWait(1000).play(this);
		for (final SoundEvent soundEvent : soundEventList) {
			soundEvent.play(this);
		}
		new SoundEventWait(500).play(this);
	}

	@ApiMethod
	public void noteOn(
			final int channel,
			final MidiInstrument midiInstrument,
			final int note,
			final int velocity) {

		final MidiChannel midiChannel = midiChannels[channel];
		final int bank = midiInstrument.getBank();
		final int program = midiInstrument.getProgram();
		midiChannel.programChange(bank, program);
		midiChannel.noteOn(note, velocity);
	}

	@ApiMethod
	public void noteOff(
			final int channel,
			final MidiInstrument midiInstrument,
			final int note,
			final int velocity) {

		final MidiChannel midiChannel = midiChannels[channel];
		final int bank = midiInstrument.getBank();
		final int program = midiInstrument.getProgram();
		midiChannel.programChange(bank, program);
		midiChannel.noteOff(note, velocity);
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public double getSpeed() {
		return speed;
	}
}
