package com.personal.piano.writer;

import java.io.File;
import java.util.List;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

import com.personal.piano.events.SoundEvent;
import com.utils.log.Logger;

public class MidiWriter {

	private final List<SoundEvent> soundEventList;
	private final String outputPathString;

	private Track track;
	private long tick;

	public MidiWriter(
			final List<SoundEvent> soundEventList,
			final String outputPathString) {

		this.soundEventList = soundEventList;
		this.outputPathString = outputPathString;
	}

	public void work() {

		try {
			Logger.printProgress("writing file:");
			Logger.printLine(outputPathString);

			final int resolution = 250;
			final Sequence sequence = new Sequence(Sequence.PPQ, resolution);
			track = sequence.createTrack();

			addSoundSet();
			addTempo();
			addTrackName();
			addOmni();
			addPoly();

			for (final SoundEvent soundEvent : soundEventList) {
				soundEvent.write(this);
			}

			addEnd();

			MidiSystem.write(sequence, 1, new File(outputPathString));

		} catch (final Exception exc) {
			Logger.printError("failed to write MID file");
			Logger.printException(exc);
		}
	}

	private void addSoundSet() throws Exception {

		final SysexMessage sysexMessage = new SysexMessage();
		final byte[] soundSetData =
				{ (byte) 0xF0, (byte) 0x7E, (byte) 0x7F, (byte) 0x09, (byte) 0x01, (byte) 0xF7 };
		sysexMessage.setMessage(soundSetData, soundSetData.length);

		addMessage(sysexMessage);
	}

	private void addTempo() throws Exception {

		final MetaMessage metaMessage = new MetaMessage();
		final byte[] tempoData = { (byte) 0x02, (byte) 0x00, (byte) 0x00 };
		metaMessage.setMessage(0x51, tempoData, tempoData.length);

		addMessage(metaMessage);
	}

	private void addTrackName() throws Exception {

		final MetaMessage metaMessage = new MetaMessage();
		final String trackName = "midi file track";
		metaMessage.setMessage(0x03, trackName.getBytes(), trackName.length());

		addMessage(metaMessage);
	}

	private void addOmni() throws Exception {

		final ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessage(0xB0, 0x7D, 0x00);

		addMessage(shortMessage);
	}

	private void addPoly() throws Exception {

		final ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessage(0xB0, 0x7F, 0x00);

		addMessage(shortMessage);
	}

	private void addEnd() throws Exception {

		final MetaMessage metaMessage = new MetaMessage();
		final byte[] endData = {};
		metaMessage.setMessage(0x2F, endData, endData.length);

		incrementTick(500);
		addMessage(metaMessage);
	}

	public void addMessage(
			final MidiMessage midiMessage) {

		final MidiEvent midiEvent = new MidiEvent(midiMessage, tick);
		track.add(midiEvent);
	}

	public void incrementTick(
			final int tickIncrement) {
		tick += tickIncrement;
	}
}
