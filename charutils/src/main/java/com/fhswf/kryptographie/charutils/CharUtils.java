package com.fhswf.kryptographie.charutils;

import java.util.ArrayList;
import java.util.List;

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
}
