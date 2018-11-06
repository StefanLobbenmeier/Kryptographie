package multipletimepad;

import java.util.Collection;
import java.util.HashSet;

public class PossibleChars {

	private static Collection<Character> possibleValues;

	public static int count() {
		return getPossibleValues().size();
	}

	public static Collection<Character> getPossibleValues() {
		if (possibleValues == null) {		
			possibleValues = new HashSet<>();
			
			for (char c = 'a'; c <= 'z'; c++) {
				possibleValues.add(c);
				possibleValues.add(Character.toUpperCase(c));
			}

			possibleValues.add('\'');
			possibleValues.add(' ');
			
			possibleValues.add('!');
			possibleValues.add('.');
			possibleValues.add(',');
			possibleValues.add('?');
			
			possibleValues.add('-');
			
		} return possibleValues;
	}

	public static boolean contains(char c) {
		return getPossibleValues().contains(c);
	}
}
