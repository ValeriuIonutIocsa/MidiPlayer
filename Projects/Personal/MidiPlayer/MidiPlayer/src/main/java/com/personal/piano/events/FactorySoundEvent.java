package com.personal.piano.events;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.personal.piano.instruments.FactoryMidiInstrument;
import com.personal.piano.instruments.MidiInstrument;
import com.utils.io.ResourceFileUtils;
import com.utils.log.Logger;
import com.utils.obj.ObjUtils;
import com.utils.string.StrUtils;
import com.utils.xml.dom.XmlDomUtils;

public final class FactorySoundEvent {

	private FactorySoundEvent() {
	}

	public static void fillSoundEventList(
			final List<String> resourceFilePathStringList,
			final List<SoundEvent> soundEventList) {

		final Map<String, Integer> noteNameToNumberMap = new HashMap<>();
		fillNoteNameToNumberMap(noteNameToNumberMap);

		for (final String resourceFilePathString : resourceFilePathStringList) {

			Logger.printProgress("parsing sound events file:");
			Logger.printLine(resourceFilePathString);

			try (InputStream inputStream = ResourceFileUtils.resourceFileToInputStream(resourceFilePathString);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

				String line;
				while ((line = bufferedReader.readLine()) != null) {

					if (!line.startsWith("//")) {

						final String[] lineSplit = StringUtils.split(line);
						if (lineSplit.length > 1) {

							final String firstPart = lineSplit[0];
							final boolean on = "on".equals(firstPart);
							if (on || "off".equals(firstPart)) {

								final String channelString = lineSplit[1];
								final int channel = StrUtils.tryParsePositiveInt(channelString);
								if (!(channel >= 0 && channel < 16)) {
									Logger.printError("invalid channel " + channelString);

								} else {
									final String midiInstrumentString = lineSplit[2];
									final MidiInstrument midiInstrument =
											FactoryMidiInstrument.computeInstance(midiInstrumentString);

									final String noteName = lineSplit[3];
									final Integer noteInteger = noteNameToNumberMap.get(noteName);
									final int note = ObjUtils.nonNullElse(noteInteger, -1);
									if (note < 0) {
										Logger.printError("invalid note: " + noteName);

									} else {
										final String velocityString = lineSplit[4];
										final int velocity = Integer.parseInt(velocityString);

										final SoundEvent soundEvent;
										if (on) {
											soundEvent = new SoundEventNoteOn(
													channel, midiInstrument, note, velocity);
										} else {
											soundEvent = new SoundEventNoteOff(
													channel, midiInstrument, note, velocity);
										}
										soundEventList.add(soundEvent);
									}
								}

							} else if ("wait".equals(firstPart)) {

								final String durationString = lineSplit[1];
								final int duration = Integer.parseInt(durationString);

								final SoundEventWait soundEventWait = new SoundEventWait(duration);
								soundEventList.add(soundEventWait);
							}
						}
					}
				}

			} catch (final Exception exc) {
				Logger.printError("failed to fill the sound event list for file:" +
						System.lineSeparator() + resourceFilePathString);
				Logger.printException(exc);
			}
		}
	}

	private static void fillNoteNameToNumberMap(
			final Map<String, Integer> noteNameToNumberMap) {

		try (InputStream inputStream = ResourceFileUtils.resourceFileToInputStream(
				"com/personal/piano/events/note_numbers.xml")) {

			final Document document = XmlDomUtils.openDocument(inputStream);
			final Element documentElement = document.getDocumentElement();
			final List<Element> noteElementList =
					XmlDomUtils.getElementsByTagName(documentElement, "Note");
			for (final Element noteElement : noteElementList) {

				final String noteName = noteElement.getAttribute("Name");
				final String noteNumberString = noteElement.getAttribute("Number");
				final int noteNumber = StrUtils.tryParsePositiveInt(noteNumberString);
				noteNameToNumberMap.put(noteName, noteNumber);
			}

		} catch (final Exception exc) {
			Logger.printError("failed to fill the note name to number map!");
			Logger.printException(exc);
		}
	}
}
