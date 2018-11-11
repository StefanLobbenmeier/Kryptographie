package multipletimepad;

import java.util.Arrays;
import java.util.HashMap;

public class KeyProbability implements Comparable<KeyProbability> {
	
	private static final HashMap<Integer, Byte> knownKeys;

	private static void addKnownKey(int index, int key) {
		knownKeys.put(index, (byte) key);
	}
	private static void addKnownKeys(int index, int... keys) {
		for (int i = 0; i < keys.length; i++) {
			addKnownKey(index + i, keys[i]);
		}
	}
	
	private static void addKnownText(int cypherIndex, int charIndex, String decrypted) {
		for(int i = 0; i < decrypted.length(); i++) {
			char c = decrypted.charAt(i);
			int currentIndex = charIndex + i;
			addKnownKey(currentIndex, c ^ Data.get(cypherIndex, currentIndex));
		}
	}
	
	static {
		knownKeys = new HashMap<>();

		addKnownText(1, 36, " you do the ");
		addKnownText(0, 95, " we don't know ");
		addKnownText(1, 84, " to start all ");
		addKnownText(2, 48, " ");
		
		//Maybe error in the cypher? Nvm, this will only work if "-" is included as a possible character
		addKnownText(2, 76, " I told");
		addKnownText(1, 75, " you have "); 
		addKnownText(0, 73, " nine");  
		addKnownText(1, 0, "I hate ");  
		addKnownText(0, 0, "My grandmother");  
		addKnownText(1, 6, " housework! ");  
		addKnownText(2, 13, " urged ");  
		addKnownText(0, 14, " started ");  
		addKnownText(0, 30, " five miles ");  
		addKnownText(1, 21, " make the ");  
		addKnownText(0, 47, " when she was ");  
		addKnownText(2, 59, " value every year.");  
		
		//Extend the loop
		addKnownText(0, 109, " where ");  
		addKnownText(2, 114, " buy ");    
		addKnownText(0, 119, " hell she is.");	
		
		for(int i = 0; i < knownKeys.size(); i++) {
			System.out.printf("%d = %02X ", knownKeys.keySet().toArray()[i], knownKeys.values().toArray()[i]);
		}
		System.out.println("\n");
	}
	
	
	private int charIndex;
	private byte key;

	public KeyProbability(int index, byte key) {
		this.charIndex = index;
		this.key = key;
	}

	@Override
	public int compareTo(KeyProbability o) {
		return -1 * Float.compare(getProbabilty(), o.getProbabilty());
	}

	private float getProbabilty() {
		if (knownKeys.containsKey(charIndex)) {
			if (knownKeys.get(charIndex) == key) return 1f;
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
		for (int cypherIndex = 0; cypherIndex < Data.cypherCount(); cypherIndex++) {
			byte encrypted = Data.get(cypherIndex, charIndex);
			decrypted[cypherIndex] = (char) (encrypted ^ key);
		}
		return decrypted;
	}

	public byte getKey() {
		return key;
	}
	
	@Override
	public String toString() {
		return String.format("<%s, %2X, %.4f>", Arrays.toString(decrypted()), key, getProbabilty());
	}

}
