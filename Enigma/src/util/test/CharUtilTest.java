package util.test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import static util.CharUtils.charToNum;

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
			fail();
		} catch(Error e) {
			assertEquals(e.getMessage(), "Only Accepts letters A-Z");
		}
	}

}
