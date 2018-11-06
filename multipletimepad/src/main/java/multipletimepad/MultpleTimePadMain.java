package multipletimepad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MultpleTimePadMain {

	public static void main(String[] args) {

		final MultpleTimePadMain main = new MultpleTimePadMain();
		
		String[] messages = new String[Data.cypherCount()];
		String key = "";
		Arrays.fill(messages, "");

		for (int charindex = 0; charindex < Data.maxCypherLength(); charindex++) {
			Set<Byte> possibleKeys = main.getPossibleKeys(charindex);
			PossibleKeysTupel tupel = new PossibleKeysTupel(charindex, possibleKeys);
			KeyProbability[] charsOrderedByLikelihood = tupel.charsOrderedByLikelihood();
			System.out.printf("Found %2d keys for position %3d: %s%n", possibleKeys.size(), charindex, Arrays.toString(charsOrderedByLikelihood));

			for (int cypherindex = 0; cypherindex < Data.cypherCount(); cypherindex++) {
				messages [cypherindex] += charsOrderedByLikelihood[0].decrypted()[cypherindex];
			}
			key += (char) charsOrderedByLikelihood[0].getKey();
		}
		
		System.out.println();
		
		for (int cypherindex = 0; cypherindex < Data.cypherCount(); cypherindex++) {
			System.out.printf("Message %d:\t%s%n", cypherindex, messages[cypherindex]);
		}
		System.out.printf("Key:\t\t%s%n", key);

//		System.out.println(main.getPossibleKeys(0));
	}

	Collection<Character> possibleValues = PossibleChars.getPossibleValues();

	public MultpleTimePadMain() {
		
	}

	Set<Byte> getPossibleKeys(int index) {
		int count = Data.cypherCount();

		final var possibleKeysForChiffrat = new HashSet[count];

		for (int testedCypher = 0; testedCypher < count; testedCypher++) {
			possibleKeysForChiffrat[testedCypher] = new HashSet<Byte>();
			for (final Character c : possibleValues ) {
				boolean charWouldWorkForThisChiffrat = true;
				for (int anOtherCypher = 0; anOtherCypher < count; anOtherCypher++) {
					if (testedCypher == anOtherCypher) 
						continue;
					if (isValid(xor(testedCypher, anOtherCypher, index), c)) {
						continue;
					} else {
						charWouldWorkForThisChiffrat = false;
						break;
					}
				}
				if (charWouldWorkForThisChiffrat) {
					possibleKeysForChiffrat[testedCypher].add(key(testedCypher, index, c));
				}
		}
		}

		HashSet<Byte> subset = possibleKeysForChiffrat[0];
//		for (HashSet hashSet : possibleKeysForChiffrat) {
//			System.out.println(hashSet);
//		}
		
		for(int cypherindex = 1; cypherindex < count; cypherindex++) {
			subset.retainAll(possibleKeysForChiffrat[cypherindex]);
		}
		
		return subset;
	}
	
	private Byte key(int testedChiffrat, int index, Character c) {
		return (byte) (Data.get(testedChiffrat, index) ^ c);
	}

	private byte xor(int i, int j, int index) {
		return (byte) (Data.get(i, index) ^ Data.get(j, index));
	}

	protected boolean isValid(byte xor, Character possibleValue) {
		return PossibleChars.getPossibleValues().contains((char) (xor ^ possibleValue));
	}

}
