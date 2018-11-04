package com.fhswf.kryptographie.enigma.enigma;

public class InvertedEnigmaWalze implements IEnigmaWalze {
	
	private RotatingEnigmaWalze base;

	public InvertedEnigmaWalze(RotatingEnigmaWalze base) {
		this.base = base;
	}

	@Override
	public char transformChar(char c) {
		return base.untransformChar(c);
	}

}
