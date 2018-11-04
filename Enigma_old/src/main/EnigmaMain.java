package main;

import enigma.Enigma;

public class EnigmaMain {

	public static void main(String[] args) {
		new EnigmaMain().bruteForce();
	}

	private void bruteForce() {
		for(int i = 0; i < 26*26*26; i++) {
			char encrypted = enigma.encrypt(getCurrentProgressChar());
			logProgress(encrypted, i);
		}
	}
	
	
	

	private void logProgress(char encrypted, int i) {
		if(isProgress(encrypted)) {
			progress++;
			decrypted += encrypted;
			
			if(worthPrinting()) {
				System.out.printf("%s, decrypted %.2f%%, total tries %.2f%% (%d)%n", 
						decrypted, 100.0 * progress / cypher.length(), 100.0 * i / (26 * 26 * 26), i);
			}
		} else {
			if (encrypted == firstPartOfDecrypted.charAt(0)) {
				decrypted = "" +  encrypted;
				progress = 1;
			} else {
				decrypted = "";
				progress = 0;
			}
		}
	}

	private boolean worthPrinting() {
		// TODO Auto-generated method stub
		return progress > 1;
	}

	private boolean isProgress(char encrypted) {
		if (progress < firstPartOfDecrypted.length()) {
			return encrypted == firstPartOfDecrypted.charAt(progress);
		} else return true;
	}

	private char getCurrentProgressChar() {
		return cypher.charAt(progress);
	}


	private Enigma enigma;
	private int progress;
	
	private final String cypher = "BGLRAOGHKRWRGSKCNSJAXXUUEXNSRXQUDXOSZHNIWLUVKXIPJSNJTM";
	private String decrypted;
	private String firstPartOfDecrypted = "WETTERBERICHT";
	
	public EnigmaMain() {
		enigma = new Enigma();
		progress = 0;
	}	
	

}
