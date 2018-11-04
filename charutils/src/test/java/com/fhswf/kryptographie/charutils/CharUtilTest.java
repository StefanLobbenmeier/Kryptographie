package com.fhswf.kryptographie.charutils;

import static com.fhswf.kryptographie.charutils.CharUtils.charToNum;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class CharUtilTest {

	@Test
	void testCharToNum() {
		assertEquals(0, charToNum('A'));
		assertEquals(25, charToNum('Z'));
	}

	@Test
	void testCharToNumFails() {
		Arrays.asList('a', '&', ' ').forEach(this::assertFails);
	}

	private void assertFails(char notAtoZ) {
		try {
			charToNum(notAtoZ);
			fail("This shouldve thrown an Exception");
		} catch (final Error e) {
			assertEquals(e.getMessage(), "Only Accepts letters A-Z");
		}
	}

}
