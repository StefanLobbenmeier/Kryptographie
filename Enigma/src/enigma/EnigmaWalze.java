package enigma;

import java.util.Arrays;
import java.util.Optional;

import static util.CharUtils.charToNum;
import static util.CharUtils.numToChar;

public class EnigmaWalze {
	
	private int[] vectors;
	private int nextRotationBefore;
	private Optional<EnigmaWalze> nextWalze = Optional.empty();

	public EnigmaWalze(char[] characterValues, int nextRotationBefore) {
		this.nextRotationBefore = nextRotationBefore;
		this.vectors = transformCharactervaluesToVectors(characterValues);
	}

	public EnigmaWalze(char[] characterValues) {
		this(characterValues, 25);
	}
	
	private int[] transformCharactervaluesToVectors(char[] characterValues) {
		int[] vectors = new int[characterValues.length];
		for(int i = 0; i < characterValues.length; i++) {
			vectors[i] = vector(characterValues, i);
		}
		return vectors;
	}

	public char transformChar(char c) {
		int pos = charToNum(c);
		return applyVector(pos);
	}
	
	public void rotate() {
		nextRotationBefore--;
		if (shouldRotateNextOne()) {
			nextRotationBefore += 26;
			nextWalze.ifPresent((walze) -> walze.rotate());
		}
		
		int length = vectors.length;
		int[] newVectors = new int[length];
		for(int pos = 0; pos < length; pos++) {
			newVectors[pos] = vectors[(pos + 1) % length];
		}
		vectors = newVectors;
	}

	public boolean shouldRotateNextOne() {
		return nextRotationBefore == 1;
	}
	
	protected int vector(char[] characterValues, int pos) {
		return (charToNum(characterValues[pos])-pos+26)%26;
	}
	
	protected char applyVector(int pos) {
		return numToChar(pos + vectors[pos]);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this.getClass().equals(obj.getClass())) {
			EnigmaWalze other = (EnigmaWalze) obj;
			return nextRotationBefore == other.nextRotationBefore &&
					Arrays.equals(vectors, other.vectors);
		}
		return false;
	}
	
	@Override
	public String toString() {
		int length = vectors.length;
		char[] values = new char[length];

		for(int i = 0; i < length; i++) {
			values[i] = applyVector(i);
		}
		
		return String.copyValueOf(values);
	}

	public void setNextWalze(EnigmaWalze walze) {
		nextWalze = Optional.of(walze);
	}
}
