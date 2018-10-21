import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VigenereTest {
	
	private Vigenere vigenere;

	@BeforeEach
	void init() {
		vigenere = new Vigenere();
	}

	@Test
	void testKoinzidenzindex() {
		assertEquals(1.0, vigenere.koinzidenzindex("eeeee"), "Koinzidenzindex von gleichen Zeichen sollte 1 sein.");
		assertEquals(0.0, vigenere.koinzidenzindex("abc"), "Koinzidenzindex von nur unterschiedlichen Zeichen sollte 0 sein.");
		assertEquals(0.1, vigenere.koinzidenzindex("zbuud"), 0.1);
	}

	@Test
	void testCountPairs() {
		assertEquals(0, vigenere.countPairs(""));
		assertEquals(2, vigenere.countPairs("aa"));
		assertEquals(6, vigenere.countPairs("aaa"));
	}

	@Test
	void testCountChars() {
		assertEquals(0, vigenere.countChars("")[0]);
		assertEquals(0, vigenere.countChars("flkghsjlg")[0]);
		assertEquals(0, vigenere.countChars(null)[0]);
		assertEquals(1, vigenere.countChars("a")[0]);
		assertEquals(2, vigenere.countChars("aa")[0]);
		assertEquals(4, vigenere.countChars("aasdfjasfja")[0]);
		assertEquals(2, vigenere.countChars("aasdfjasfja")[vigenere.charToNum('f')]);
	}

	@Test
	void testGuessKeySize() {
		assertEquals(1, vigenere.guessKeySize(Vigenere.kRho));
		assertEquals(Double.MAX_VALUE, vigenere.guessKeySize(Vigenere.kRandom));
	}

	@Test
	void testInternalConversion() {
		assertEquals(0, vigenere.charToNum('a'));
		assertEquals(25, vigenere.charToNum('z'));

		assertEquals('a', vigenere.numToChar(0));
		assertEquals('z', vigenere.numToChar(25));
	}
	
	@Test
	void testVigenereEncryption() {
		assertEquals("stefan", vigenere.encrypt("stefan", "a"));
		assertEquals("mbut", vigenere.encrypt("maus", "ab"));
		assertEquals("abc", vigenere.encrypt("aaa", "abc"));
		assertEquals("abc", vigenere.encrypt("abc", "aaa"));
	}
	
	@Test
	void testVigenereDecryption() {
		assertEquals("stefan", vigenere.decrypt("stefan", "a"));
		assertEquals("maus", vigenere.decrypt("mbut", "ab"));
		assertEquals("aaa", vigenere.decrypt("abc", "abc"));
	}

	@Test
	void testStringSlice() {
		assertEquals("acegi", vigenere.slicedCypher("abcdefghij", 2, 0));
		assertEquals("bdfhj", vigenere.slicedCypher("abcdefghij", 2, 1));
	}

	@Test
	void testHighestCountChar() {
		assertEquals('a', vigenere.highestCountChar("basdbfsklfbasdfasdfblskafaaa"));
		assertEquals('a', vigenere.highestCountChar(""));
		assertEquals('t', vigenere.highestCountChar("tjklqtttatwttatt"));
	}

	@Test
	void testGuessKeyChar() {
		assertEquals('a', vigenere.guessKeyChar("hallo", 'l'));
		assertEquals('a', vigenere.guessKeyChar("einnormalertextdervieleesenthelt", 'e'));
		assertEquals('w', vigenere.guessKeyChar(vigenere.encrypt("einnormalertextdervieleesenthelt", "w"), 'e'));
	}

}
