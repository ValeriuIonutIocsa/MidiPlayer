package com.personal.piano.parser;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class MidiParserTest {

	@Test
	void testParse() throws Exception {

		final String midiFilePathString;
		final int input = Integer.parseInt("1");
		if (input == 1) {
			midiFilePathString = "D:\\tmp\\MidiPlayer\\Songs\\personal 001 piano1.mid";

		} else {
			throw new RuntimeException();
		}

		final Path midiFilePath = Paths.get(midiFilePathString);

		MidiParser.parse(midiFilePath);
	}
}
