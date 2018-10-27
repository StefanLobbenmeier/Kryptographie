package enigma;

import java.util.StringJoiner;

public class Enigma {


	private static final char[] werte_walze_1 = new char[] {'B','D','F','H','J','L','C','P','R','T','X','V','Z','N','Y','E','I','W','G','A','K','M','U','S','Q','O'};
	private static final char[] werte_walze_2 = new char[] {'A','J','D','K','S','I','R','U','X','B','L','H','W','T','M','C','Q','G','Z','N','P','Y','F','V','O','E'};
	private static final char[] werte_walze_3 = new char[] {'E','K','M','F','L','G','D','Q','V','Z','N','T','O','W','Y','H','X','U','S','P','A','I','B','R','C','J'};
	private static final char[] werte_umkehrwalze = new char[] {'Y','R','U','H','Q','S','L','D','P','X','N','G','O','K','M','I','E','B','F','Z','C','W','V','J','A','T'};

	public static final EnigmaWalze WALZE_1 = new EnigmaWalze(werte_walze_1, 22);
	public static final EnigmaWalze WALZE_2 = new EnigmaWalze(werte_walze_2);
	public static final EnigmaWalze WALZE_3 = new EnigmaWalze(werte_walze_3);
	public static final EnigmaWalze Umkehrwalze = new EnigmaWalze(werte_umkehrwalze);
	
	private EnigmaWalze[] walzen;
	
	
	
	public Enigma() {
		WALZE_1.setNextWalze(WALZE_2);
		WALZE_2.setNextWalze(WALZE_3);
		walzen = new EnigmaWalze[] {WALZE_1, WALZE_2, WALZE_3, Umkehrwalze, Umkehrwalze, WALZE_3, WALZE_2, WALZE_1};
	}
	
	public char encrypt(char c) {
		char encryptedC = getValueWithoutRotation(c);
		rotate();
		return encryptedC;
	}
	
	public char getValueWithoutRotation(char c) {
		char encryptedC = c;
//		StringJoiner joiner = new StringJoiner(" -> ");
//		joiner.add(String.valueOf(encryptedC));
		for(EnigmaWalze walze: walzen) {
			encryptedC = walze.transformChar(encryptedC);
//			joiner.add(String.valueOf(encryptedC));
		}
//		System.out.println(joiner.toString());
		return encryptedC;
	}

	public void rotate() {
		WALZE_1.rotate();
	}
}
