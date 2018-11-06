package multipletimepad;

import java.util.Arrays;
import java.util.HashMap;

public class KeyProbability implements Comparable<KeyProbability> {
	
	private static final HashMap<Integer, Byte> knownKeys;

	static void addKnownKey(int index, int key) {
		knownKeys.put(index, (byte) key);
	}
	static void addKnownKeys(int index, int... keys) {
		for (int i = 0; i < keys.length; i++) {
			addKnownKey(index + i, keys[i]);
		}
	}
	
	static {
		knownKeys = new HashMap<>();
		addKnownKeys(37, 0x20, 0x61, 0x72, 0x65, 0x20, 0x62, 0x61, 0x64, 0x20, 0x6B, 0x65); // yot do thh -> you do the (2, harmonic average)
		addKnownKeys(99, 0x74, 0x20, 0x6C, 0x65, 0x61, 0x73, 0x74, 0x20, 0x74, 0x68); //we scn't -> we don't
		addKnownKeys(94, 0x64, 0x73, 0x2E, 0x20, 0x41); //ill over again. -> all 
		addKnownKeys(90, 0x65);//  fAt my -> . At my
	}
	
	
	private int index;
	private byte key;

	public KeyProbability(int index, byte key) {
		this.index = index;
		this.key = key;
	}

	@Override
	public int compareTo(KeyProbability o) {
		return -1 * Float.compare(getProbabilty(), o.getProbabilty());
	}

	private float getProbabilty() {
		if (knownKeys.containsKey(index)) {
			if (knownKeys.get(index) == key) return 1f;
//			else return 0f; this line would make sense if you know all the keys are correct, but not returning 0 does not hurt either, so you are safer if you give some alternatives in a good order
		}
		
		float probabilty = 0f;
		for (char decrypted: decrypted()) {
			float charProbability = CharProbability.getProbabilityOfChar(decrypted);
			
			probabilty += 1 / charProbability;
		}
		
		return 1 / probabilty;
	}
	
	public void setPrevious() {
		
	}

	public char[] decrypted() {
		char[] decrypted = new char[Data.cypherCount()];
		for (int i = 0; i < Data.cypherCount(); i++) {
			byte encrypted = Data.cyphers[i][index];
			decrypted[i] = (char) (encrypted ^ key);
		}
		return decrypted;
	}
	
	@Override
	public String toString() {
		return String.format("<%s, %2X, %.4f>", Arrays.toString(decrypted()), key, getProbabilty());
	}

}
