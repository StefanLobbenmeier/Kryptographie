import java.util.Arrays;

public class Vigenere {

	public static final double kRho = 0.0762;
	public static final double kRandom = 1.0/26.0;
	
	

    public static void main(String[] args) {
        String cypher = "kjegimoeugcmoemgcbkxmojlovpgkbkjqksmtactxmmvqycmoectupgkbk" +
        "jqkcqkseovmoemslbzvzoyzkbqtumxcqkspgkmoemykimvacftrkmyzmoe" +
        "lkeegclaelllbzvznftkelgiqkwaovircmyzmhvvnvzhvqaelygzgtprzm" +
        "hvsoelkiqiyeoctnzvglaoelkeegclyvqjrclvcxvznlbbfzjvucftlnmt" +
        "emxymxvqtbwsdbyfnxzaykmxvciyircmszbnrczlvjyigimxmmxjbkctzj" +
        "qiywlkihvzgeakzvkizglpkeazzusvctuitjmoemtjknnixqmtwckjakee" +
        "kilkkqniqnemxbmtemtuqkxmojtkzvyrozvvrzmhvuakbkieoieuctkect" +
        "jaiywtzvgtpzemndmtzpxbwkevzfptvauiokwwxkokymtuisvkqvzzvlov" +
        "irkmaelsrknkmyzknxmziwykiawlkeekx";
        
        Vigenere vigenere = new Vigenere();
        
        double koinzidenzindex = vigenere.koinzidenzindex(cypher);
        System.out.printf("Der Koinzidenzindex beträgt %.4f.%n", koinzidenzindex);
        
        double keySize = vigenere.guessKeySize(koinzidenzindex);
        System.out.printf("Die Laenge des Schluessels betraegt etwa ~%f.%n", keySize);
        
        String clearText = vigenere.crackVigenere(cypher, 3, 'e');
        System.out.println(clearText);
        
        String key = vigenere.crackVigenereKey(cypher, 3, 'e');
        System.out.printf("Der Key war: %s%n", key);
    
    }
    
    public String encrypt(String message, String key) {
    	StringBuilder ecryptedMessageBuilder = new StringBuilder();
    	
    	int currentKeyPos = 0;
    	for (char c: message.toCharArray()) {
    		ecryptedMessageBuilder.append(numToChar(charToNum(c) + charToNum(key.charAt(currentKeyPos))));
    		currentKeyPos = (currentKeyPos + 1) % key.length();
    	}
    	
    	return ecryptedMessageBuilder.toString();
    }
    
    public int charToNum(char c) {
    	return (c - 'a') % 26;
    }
    
    public char numToChar(int num) {
    	return (char) ('a' + ((num + 26) % 26)) ; //Addiere 26 damit negative nums auch konvertiert werden koennen
    }
    
    public String decrypt(String cypher, String key) {
    	String decryptionKey = convertKeyToDecrpytionKey(key);
    	return encrypt(cypher, decryptionKey);
    }
    
    public String convertKeyToDecrpytionKey(String key) {
    	StringBuilder decryptionKeyBuilder = new StringBuilder();
    	
    	for (char c: key.toCharArray()) {
    		decryptionKeyBuilder.append(numToChar(-charToNum(c)));
    	}
    	
    	return decryptionKeyBuilder.toString();
    }
    
    public String crackVigenere(String cypher, int keyLen, char charWithHighestProbability) {
    	return decrypt(cypher, crackVigenereKey(cypher, keyLen, charWithHighestProbability));
    }
    
    public String crackVigenereKey(String cypher, int keyLen, char charWithHighestProbability) {
    	StringBuilder encryptionKeyBuilder = new StringBuilder();
    	
    	for(int i = 0; i < keyLen; i++) {
    		encryptionKeyBuilder.append(guessKeyChar(slicedCypher(cypher, keyLen, i), charWithHighestProbability));
    	}
    	
    	return encryptionKeyBuilder.toString();
    }
    
    public String slicedCypher(String cypher, int keyLen, int index) {
    	StringBuilder slicedCypherBuilder = new StringBuilder();
    	
    	for(int i = index; i < cypher.length(); i+=keyLen) {
    		slicedCypherBuilder.append(cypher.charAt(i));
    	}
    	
    	return slicedCypherBuilder.toString();
    }
    
    public char guessKeyChar(String slicedCypher, char charWithHighestProbability) {
    	return numToChar(charToNum(highestCountChar(slicedCypher))-charToNum(charWithHighestProbability));
    }
    
    public char highestCountChar(String message) {
		int maxCount = 0;
		char maxCountChar = 'a';
		int[] counts = countChars(message);
		
		for (int i = 0; i < counts.length; i++) {
			int count = counts[i];
			if (count > maxCount) {
				maxCount = count;
				maxCountChar = numToChar(i);
			}
		}
		return maxCountChar;
    }
    
    public double guessKeySize(double koinzidenzindex) {
    	if (koinzidenzindex <= kRandom) {
    		return Double.MAX_VALUE;
    	}
		return (kRho - kRandom) / (koinzidenzindex - kRandom);
    	
    }

	public double koinzidenzindex(String string) {
		return 1.0 * countPairs(string) / maxPossiblePairs(string);
	}

	private int maxPossiblePairs(String string) {
		return string.length() * (string.length() - 1);
	}
	
	public int countPairs(String string) {
		int totalPairs = 0;
		
		for (int count: countChars(string)) {
			totalPairs += count * (count - 1);
		}
		
		return totalPairs;
	}

	public int[] countChars(String string) {
		int[] counts = new int[26];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}
		if (string == null) return counts;
		for (char c: string.toCharArray()) {
			counts[charToNum(c)]++;
		}
		return counts;
	}
}



