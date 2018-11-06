package multipletimepad;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.junit.*;

public class TestMultipleTimePadMain  {

	private MultpleTimePadMain main;

	@Before
	public void init() {
		main = new MultpleTimePadMain();
	}
	
	@Test
	public void testPossibleKeys() {
		
		for(int charIndex = 0; charIndex < Data.minCypherLength(); charIndex++) {
			Set<Byte> possibleKeys = main.getPossibleKeys(charIndex);
			for (byte key: possibleKeys) {
				for (int cypherIndex = 0; cypherIndex < Data.cypherCount(); cypherIndex++) {
					byte[] cypherText = Data.get(cypherIndex);
					byte cypher = cypherText[charIndex];
	
					assertTrue(String.format("%s should be supported", getDecryptionDescription(key, cypherIndex, charIndex), key),
							PossibleChars.contains((char) (cypher ^ key)));
				}
				
			}
			
			Set<Byte> impossibleKeys = allBytes();
			impossibleKeys.removeAll(possibleKeys);
			for (byte key: impossibleKeys) {
				boolean possible = true;
				for (int cypherIndex = 0; cypherIndex < Data.cypherCount(); cypherIndex++) {
					byte[] cypherText = Data.get(cypherIndex);
					byte cypher = cypherText[charIndex];
	
					if(PossibleChars.contains((char) (cypher ^ key))) {
						// One is ok;
					} else {
						possible = false;
					}
				}
				
				if (possible) {
					fail(String.format("%s should failed", getDecryptionDescription(key, charIndex)));
				}
				
			}
		}
	}
	
	private String getDecryptionDescription(byte key, int charIndex) {
		StringJoiner joiner = new StringJoiner("\n");
		for (int cypherIndex = 0; cypherIndex < Data.cypherCount(); cypherIndex++) {
			joiner.add(getDecryptionDescription(key, cypherIndex, charIndex));
		}
		return "Multiple Cyphers:\n" + joiner.toString();
	}
	
	private String getDecryptionDescription(byte key, int cypherIndex, int charIndex) {
		byte cypher = Data.cyphers[cypherIndex][charIndex];
		char decryptedChar = (char) (key ^ cypher);
		return String.format("The Key %02X that decrypts Data.cyphers[%d][%d] = %02X to %02X|%c", 
				key, cypherIndex, charIndex, cypher, (byte) decryptedChar, decryptedChar);
	}

	private Set<Byte> allBytes() {
		var allBytesSet = new HashSet<Byte>(256, 1f);
		for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE;  b++) {
			allBytesSet.add(b);
		}
		allBytesSet.add(Byte.MAX_VALUE);
		return allBytesSet;
	}

	@Test
	public void testIsValid() {
		assertTrue(main.isValid((byte) ('a' ^ 'b'), 'a'));
		assertTrue(main.isValid((byte) ('a' ^ 'b'), 'b'));
		assertFalse(main.isValid((byte) ('a' ^ 'c'), 'b'));
	}

}
