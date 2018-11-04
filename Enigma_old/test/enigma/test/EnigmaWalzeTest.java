package enigma.test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enigma.Enigma;
import enigma.RotatingEnigmaWalze;
import enigma.IEnigmaWalze;

import static util.CharUtils.toCharacterList;

class EnigmaWalzeTest extends RotatingEnigmaWalze {

	private RotatingEnigmaWalze abc;
	private RotatingEnigmaWalze bac;
	private IEnigmaWalze cba;
	private List<Character> abccba;
	

	private final String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public EnigmaWalzeTest() {
		super(new char[] {});
	}

	@BeforeEach
	void setUp() throws Exception {
		abc = new RotatingEnigmaWalze("ABC".toCharArray());
		bac = new RotatingEnigmaWalze("BAC".toCharArray());
		cba = new RotatingEnigmaWalze("CBA".toCharArray());

		abccba = toCharacterList("ABCCBA");	
	}
	
	@Test
	public void testTransformChar() {
		assertArrayEquals(toCharacterList("ABCCBA").toArray(), abccba.stream().map(abc::transformChar).toArray());
		assertArrayEquals(toCharacterList("BACCAB").toArray(), abccba.stream().map(bac::transformChar).toArray());
		assertArrayEquals(toCharacterList("CBAABC").toArray(), abccba.stream().map(cba::transformChar).toArray());
	}
	
	@Test 
	void testRotation() {
		abc.rotate();
		assertEquals(new RotatingEnigmaWalze("ABC".toCharArray(), 24), abc);
		
		bac.rotate();
		assertEquals(new RotatingEnigmaWalze("ZBD".toCharArray(), 24), bac); //This is the moment i realised that testing with 3 letters doesnt make much sense :)
		
		
		// test cases from task
		Enigma.WALZE_1.rotate();
		assertEquals('A', Enigma.WALZE_1.transformChar('Z'));
		assertEquals('C', Enigma.WALZE_1.transformChar('A'));
		assertEquals('E', Enigma.WALZE_1.transformChar('B'));
		
	}
	
	@Test 
	void testEquals() {
		assertEquals(abc, abc);
		assertEquals(abc, new RotatingEnigmaWalze("ABC".toCharArray()));
	}
	
	@Test 
	void testToString() {
		assertEquals("ABC", abc.toString());
		assertEquals("BAC", bac.toString());
	}
	
	private String getAllValues(Function<? super Character, ? extends Character> encryptionFunction, String aToZ) {
		Collector<Character, StringJoiner, String> characterToStringCollector = Collector.of(
				() -> new StringJoiner(""), 
				(joiner, c) -> joiner.add(String.valueOf(c)), 
				(j1, j2) -> j1.merge(j2),               // combiner
		        StringJoiner::toString);
		
		return toCharacterList(aToZ).stream().map(encryptionFunction).collect(characterToStringCollector);
	}


	@Test
	void testSymetry() {
		String encryption = getAllValues(Enigma.WALZE_1::transformChar, aToZ);
		String decryption = getAllValues(Enigma.WALZE_1::untransformChar, encryption);
		System.out.println();
		
		assertEquals(aToZ, decryption);
	}
	
	@Test
	void testSymetryAfterRotation() {
		Enigma.WALZE_1.rotate();

		String encryption = getAllValues(Enigma.WALZE_1::transformChar, aToZ);
		String decryption = getAllValues(Enigma.WALZE_1::untransformChar, encryption);
		System.out.println();
		
		assertEquals(aToZ, decryption);
	}
	
	@Test
	void testRotateFollowingWalze() {
		fail();
		
		RotatingEnigmaWalze walze = new RotatingEnigmaWalze(aToZ.toCharArray(), 2);
		
		
		walze.setNextWalze(walze);
		
		Enigma.WALZE_1.rotate();

		String encryption = getAllValues(Enigma.WALZE_1::transformChar, aToZ);
		String decryption = getAllValues(Enigma.WALZE_1::untransformChar, encryption);
		System.out.println();
		
		assertEquals(aToZ, decryption);
	}

}
