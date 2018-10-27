package enigma.test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enigma.Enigma;
import enigma.EnigmaWalze;

import static util.CharUtils.toCharacterList;

class EnigmaWalzeTest extends EnigmaWalze {

	private EnigmaWalze abc;
	private EnigmaWalze bac;
	private EnigmaWalze cba;
	private List<Character> abccba;

	public EnigmaWalzeTest() {
		super(new char[] {});
	}

	@BeforeEach
	void setUp() throws Exception {
		abc = new EnigmaWalze("ABC".toCharArray());
		bac = new EnigmaWalze("BAC".toCharArray());
		cba = new EnigmaWalze("CBA".toCharArray());

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
		assertEquals(new EnigmaWalze("ABC".toCharArray(), 24), abc);
		
		bac.rotate();
		assertEquals(new EnigmaWalze("ZBD".toCharArray(), 24), bac); //This is the moment i realised that testing with 3 letters doesnt make much sense :)
		
		
		// test cases from task
		Enigma.WALZE_1.rotate();
		assertEquals('A', Enigma.WALZE_1.transformChar('Z'));
		assertEquals('C', Enigma.WALZE_1.transformChar('A'));
		assertEquals('E', Enigma.WALZE_1.transformChar('B'));
		
	}
	
	@Test 
	void testEquals() {
		assertEquals(abc, abc);
		assertEquals(abc, new EnigmaWalze("ABC".toCharArray()));
	}
	
	@Test 
	void testToString() {
		assertEquals("ABC", abc.toString());
		assertEquals("BAC", bac.toString());
	}

}
