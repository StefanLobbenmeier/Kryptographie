package enigma;

import java.util.Arrays;
import java.util.Optional;

import static util.CharUtils.charToNum;
import static util.CharUtils.numToChar;

public class RotatingEnigmaWalze implements IEnigmaWalze {

	private int[] vectors;
	private int[] invertedVectors;
	private int nextRotationBefore;
	private Optional<RotatingEnigmaWalze> nextWalze = Optional.empty();

	public RotatingEnigmaWalze(char[] characterValues, int nextRotationBefore) {
		this.nextRotationBefore = nextRotationBefore;
		transformCharactervaluesToVectors(characterValues);
	}

	public RotatingEnigmaWalze(char[] characterValues) {
		this(characterValues, 25);
	}
	
	private void transformCharactervaluesToVectors(char[] characterValues) {
		vectors = new int[characterValues.length];
		invertedVectors = new int[characterValues.length];
		for(int i = 0; i < characterValues.length; i++) {
			int vector = vector(characterValues, i);
			vectors[i] = vector;
			invertedVectors[(i + vector + 26)%26] = -vector;
		}
	}

	@Override
	public char transformChar(char c) {
		int pos = charToNum(c);
		return applyVector(pos);
	}

	public char untransformChar(char c) {
		int pos = charToNum(c);
		return applyInvertedVector(pos);
	}
	
	public void rotate() {
		nextRotationBefore--;
		if (shouldRotateNextOne()) {
			nextRotationBefore += 26;
			nextWalze.ifPresent((walze) -> walze.rotate());
		}
		
		int length = vectors.length;
		int[] newVectors = new int[length];
		int[] newInvertedVectors = new int[length];
		for(int pos = 0; pos < length; pos++) {
			newVectors[pos] = vectors[(pos + 1) % length];
			newInvertedVectors[pos] = invertedVectors[(pos + 1) % length];
		}
		vectors = newVectors;
		invertedVectors = newInvertedVectors;
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
	
	protected char applyInvertedVector(int pos) {
		return numToChar(pos + invertedVectors[pos]);
	}
	
	public InvertedEnigmaWalze inverted() {
		return new InvertedEnigmaWalze(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this.getClass().equals(obj.getClass())) {
			RotatingEnigmaWalze other = (RotatingEnigmaWalze) obj;
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

	public void setNextWalze(RotatingEnigmaWalze walze) {
		nextWalze = Optional.of(walze);
	}
}
