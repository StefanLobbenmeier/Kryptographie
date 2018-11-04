package enigma.test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enigma.Enigma;

import static util.CharUtils.toCharacterList;

import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class EnigmaTest extends Enigma {
	
	private Enigma enigma;

	@BeforeEach
	void setup() {
		enigma = new Enigma();
	}

	@Test
	void testBasePosition() {
		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(aToZ);
		
		String encryption = getAllValues(aToZ);
		System.out.println(encryption);
		System.out.println();
		
		assertEquals("UEJOBTPZWCNSRKDGVMLFAQIYXH", encryption);
	}

	@Test
	void testValuesAfterRotation() {
		enigma.rotate();

		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(aToZ);
		
		String encryption = getAllValues(aToZ);
		System.out.println(encryption);
		System.out.println();
		
		assertEquals("PGLVAUHXYMIDQNEOSJRBTCWZKF", encryption);
		
	}
	
	private String getAllValues(String aToZ) {
		Collector<Character, StringJoiner, String> characterToStringCollector = Collector.of(
				() -> new StringJoiner(""), 
				(joiner, c) -> joiner.add(String.valueOf(c)), 
				(j1, j2) -> j1.merge(j2),               // combiner
		        StringJoiner::toString);
		
		return toCharacterList(aToZ).stream().map(enigma::getValueWithoutRotation).collect(characterToStringCollector);
	}


	@Test
	void testSymetry() {
		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		String encryption = getAllValues(aToZ);
		String decryption = getAllValues(encryption);
		System.out.println();
		
		assertEquals(aToZ, decryption);
	}
	
	@Test
	void testSymetryAfterRotation() {
		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		enigma.rotate();

		String encryption = getAllValues(aToZ);
		String decryption = getAllValues(encryption);
		System.out.println();
		
		assertEquals(aToZ, decryption);
	}

}
