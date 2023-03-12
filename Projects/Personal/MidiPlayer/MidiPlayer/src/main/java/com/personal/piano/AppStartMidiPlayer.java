package com.personal.piano;

import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.personal.piano.events.FactorySoundEvent;
import com.personal.piano.events.SoundEvent;
import com.personal.piano.player.FactoryMidiPlayer;
import com.personal.piano.player.MidiPlayer;
import com.personal.piano.writer.MidiWriter;
import com.utils.io.PathUtils;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.log.Logger;

final class AppStartMidiPlayer {

	private static final String ROOT_PATH_STRING =
			PathUtils.computePath(PathUtils.createRootPath(), "tmp", "MidiPlayer");

	private AppStartMidiPlayer() {
	}

	public static void main(
			final String[] args) {

		final Instant start = Instant.now();

		Logger.setDebugMode(true);

		final int repeatCount = 1;
		final double speed = 1;
		final boolean writeMid = Integer.parseInt("0") == 1;
		final boolean play = Integer.parseInt("1") == 1;

		String songName = null;
		final String[] resourceFilePathStringArray;
		final int inputNotesFile = Integer.parseInt("101");
		if (inputNotesFile == 0) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training000_test.txt"
			};
		} else if (inputNotesFile == 1) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training001_sequence_piano1.txt"
			};
		} else if (inputNotesFile == 2) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training001_sequence_organ1.txt"
			};
		} else if (inputNotesFile == 3) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training001_sequence_violin.txt"
			};
		} else if (inputNotesFile == 4) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training001_sequence_flute.txt"
			};

		} else if (inputNotesFile == 11) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training002_accords_piano1.txt"
			};

		} else if (inputNotesFile == 21) {
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/training/training003_multiple_instruments.txt"
			};

			// personal composition no.1
		} else if (inputNotesFile == 100) {
			songName = "personal 001 piano1";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/personal/personal001_piano1.txt"
			};

		} else if (inputNotesFile == 101) {
			songName = "personal 001 organ1";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/personal/personal001_organ1.txt"
			};

		} else if (inputNotesFile == 102) {
			songName = "personal 001 violin";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/personal/personal001_violin.txt"
			};

			// personal composition no.2
		} else if (inputNotesFile == 200) {
			songName = "personal 002 piano1";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/personal/personal002_piano1.txt"
			};

			// A Million Dreams
		} else if (inputNotesFile == 1000) {
			songName = "A Million Dreams piano1";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_01.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_02.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_03.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_04.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_05.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_06.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_07.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_08.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_09.txt",
					"com/personal/piano/songs/a_million_dreams/a_million_dreams_piano1_10.txt"
			};

			// Maria (West Side Story)
		} else if (inputNotesFile == 1010) {
			songName = "Maria (West Side Story) piano1";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/songs/maria_west_side_story/maria_west_side_story_piano1_01.txt",
					"com/personal/piano/songs/maria_west_side_story/maria_west_side_story_piano1_02.txt",
					"com/personal/piano/songs/maria_west_side_story/maria_west_side_story_piano1_03.txt",
					"com/personal/piano/songs/maria_west_side_story/maria_west_side_story_piano1_04.txt",
					"com/personal/piano/songs/maria_west_side_story/maria_west_side_story_piano1_05.txt"
			};

			// You should be sad
		} else if (inputNotesFile == 1020) {
			songName = "You should be sad";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/songs/you_should_be_sad/you_should_be_sad_piano1.txt"
			};

			// Epic sax guy
		} else if (inputNotesFile == 1030) {
			songName = "Epic sax guy";
			resourceFilePathStringArray = new String[] {
					"com/personal/piano/songs/epic_sax_guy/epic_sax_guy_piano1_001.txt"
			};

		} else {
			throw new RuntimeException();
		}

		final String[] soundFontFilePathStrings;
		final int inputSoundFonts = Integer.parseInt("1");
		if (inputSoundFonts == 1) {
			soundFontFilePathStrings = new String[] {
					Paths.get(ROOT_PATH_STRING, "SoundFonts", "Motif_ES6_Concert_Piano12Mb.sf2").toString(),
					Paths.get(ROOT_PATH_STRING, "SoundFonts", "Arianna's Violin.sf2").toString()
			};
		} else if (inputSoundFonts == 2) {
			soundFontFilePathStrings = new String[] {
					Paths.get(ROOT_PATH_STRING, "SoundFonts", "Sax Compilation.sf2").toString()
			};
		} else if (inputSoundFonts == 3) {
			soundFontFilePathStrings = new String[] {
					Paths.get(ROOT_PATH_STRING, "SoundFonts", "FreePatsGM-20190813.sf2").toString()
			};
		} else {
			soundFontFilePathStrings = null;
		}

		final List<String> resourceFilePathStringList = new ArrayList<>();
		for (int i = 0; i < repeatCount; i++) {
			Collections.addAll(resourceFilePathStringList, resourceFilePathStringArray);
		}

		final List<SoundEvent> soundEventList = new ArrayList<>();
		FactorySoundEvent.fillSoundEventList(resourceFilePathStringList, soundEventList);

		if (!soundEventList.isEmpty()) {

			if (writeMid && StringUtils.isNotBlank(songName)) {

				final String outputPathString =
						Paths.get(ROOT_PATH_STRING, "Songs", songName + ".mid").toString();
				FactoryFolderCreator.getInstance().createParentDirectories(outputPathString, true);

				final MidiWriter midiWriter = new MidiWriter(soundEventList, outputPathString);
				midiWriter.work();
			}

			if (play) {

				final MidiPlayer midiPlayer = FactoryMidiPlayer.newInstance(
						speed, soundEventList, soundFontFilePathStrings);
				if (midiPlayer != null) {
					midiPlayer.play();
				}
			}
		}

		Logger.printFinishMessage(start);
	}
}
