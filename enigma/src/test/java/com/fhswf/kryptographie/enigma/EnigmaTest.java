package com.fhswf.kryptographie.enigma;

import static com.fhswf.kryptographie.charutils.CharUtils.toCharacterList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.StringJoiner;
import java.util.stream.Collector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fhswf.kryptographie.enigma.enigma.Enigma;

class EnigmaTest extends Enigma {

	private Enigma enigma;

	@BeforeEach
	void setup() {
		enigma = new Enigma();
	}

	@Test
	void testBasePosition() {
		final String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(aToZ);

		final String encryption = getAllValues(aToZ);
		System.out.println(encryption);
		System.out.println();

		assertEquals("UEJOBTPZWCNSRKDGVMLFAQIYXH", encryption);
	}

	@Test
	void testValuesAfterRotation() {
		enigma.rotate();

		final String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(aToZ);

		final String encryption = getAllValues(aToZ);
		System.out.println(encryption);
		System.out.println();

		assertEquals("PGLVAUHXYMIDQNEOSJRBTCWZKF", encryption);

	}

	private String getAllValues(String aToZ) {
		final Collector<Character, StringJoiner, String> characterToStringCollector = Collector.of(
				() -> new StringJoiner(""), (joiner, c) -> joiner.add(String.valueOf(c)), (j1, j2) -> j1.merge(j2), // combiner
				StringJoiner::toString);

		return toCharacterList(aToZ).stream().map(enigma::getValueWithoutRotation).collect(characterToStringCollector);
	}

	@Test
	void testSymetry() {
		final String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final String encryption = getAllValues(aToZ);
		final String decryption = getAllValues(encryption);
		System.out.println();

		assertEquals(aToZ, decryption);
	}

	@Test
	void testSymetryAfterRotation() {
		final String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		enigma.rotate();

		final String encryption = getAllValues(aToZ);
		final String decryption = getAllValues(encryption);
		System.out.println();

		assertEquals(aToZ, decryption);
	}

}
