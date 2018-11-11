package multipletimepad;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collector;

import org.junit.Before;
import org.junit.Test;

public class TestKeyProbability {
	
//	private CharProbability charProbabilty;

	@Before
	public void init() {
//		charProbabilty = new CharProbability();
	}

	@Test
	public void testFullOrder() {
//		var chars = PossibleChars.getPossibleValues().toArray((size) -> new Character[size]);
//		var key = 0x7C;
//		Byte[] keys = Arrays.asList(chars).stream().map((c) -> (byte) (c ^ key)).toArray((size) -> new Byte[size]);
//		
////		Arrays.sort(chars, charProbabilty);
//		String charsAsString = Arrays.asList(chars).stream().collect(characterToStringJoiner());
//		
//		assertEquals("This should be almost equal, if it fails assert the extent to which they differ and change the test.", 
//				"!XYZQV?NOGDKFzHJWRLqMUAxBIP'SETjkCv.b,ywfgpmucdlhrsionate ", charsAsString);
	}

	@Test
	public void testOrder() {
//		System.out.println(charProbabilty.compare('a', 'e'));
	}
	
	public Collector<Object, StringJoiner, String> characterToStringJoiner() {
		return Collector.of(
				() -> new StringJoiner(""), 
				(joiner, c) -> joiner.add(String.valueOf(c)), 
				(j1, j2) -> j1.merge(j2),
				StringJoiner::toString);
	}

}
