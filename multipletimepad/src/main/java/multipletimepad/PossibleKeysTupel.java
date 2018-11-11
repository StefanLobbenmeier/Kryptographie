package multipletimepad;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class PossibleKeysTupel {

	private int index;
	private Set<Byte> possibleKeys;
	
//	private double[] charProbability = new char[26]

	public PossibleKeysTupel(int index, Set<Byte> possibleKeys) {
		this.index = index;
		this.possibleKeys = possibleKeys;
	}

	public KeyProbability[] charsOrderedByLikelihood() {
		KeyProbability[] keyProbabilities = possibleKeys
				.stream()
				.map((key) -> new KeyProbability(index, key))
				.sorted()
				.toArray((size) -> new KeyProbability[size]);
		return keyProbabilities;
	}

}
