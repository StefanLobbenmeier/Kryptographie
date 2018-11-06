package util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;

public class CharUtils {
	public static int charToNum(char c) {
		if(Character.isUpperCase(c))
			return c - 'A';
		else throw new Error("Only Accepts letters A-Z");
	}
    
    public static char numToChar(int num) {
    	return (char) ('A' + ((num + 26) % 26)) ; //Addiere 26 damit negative nums auch konvertiert werden koennen
    }

	public static List<Character> toCharacterList(String str_abccba) {
		List<Character> abccba = new ArrayList<>();
		for(char c:str_abccba.toCharArray()) {
			abccba.add(c);
		}
		return abccba;
	}
	
	public Collector<Object, StringJoiner, String> characterToStringJoiner() {
		return Collector.of(
				() -> new StringJoiner(""), 
				(joiner, c) -> joiner.add(String.valueOf(c)), 
				(j1, j2) -> j1.merge(j2),
				StringJoiner::toString);
	}
}
