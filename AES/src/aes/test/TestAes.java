package aes.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aes.AES;

public class TestAes {
	
	private AES aes;

	@BeforeEach
	void init() {
		aes = new AES();
	}

	@Test
	void testEnAndDecryption() throws Exception {
		String plainString = "Ich teste Kryptographie";
		byte[] plain = plainString.getBytes();

		byte[] cipher = aes.encrypt(plain);
		
		assertNotEquals(plain, cipher); // :)

		byte[] plainAgain = aes.decrypt(cipher);
		
		assertNotEquals(cipher, plainAgain);
		
		assertArrayEquals(plain, plainAgain);
		assertEquals(plainString, new String(plainAgain));
	}

}
