package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import aes.AES;
import imageutil.BmpImage;

public class AESMain {

	private static final File ORG_BMP_FILE = new File("data/enc_image.bmp");
	private static final File TARGET_FILE = new File("data/dec_image.bmp");
	
	public static void main(String[] args) throws Exception {
		BmpImage image = new BmpImage();
		AES aes = new AES();
		image.readFile(ORG_BMP_FILE);
		image.decrypt(aes);
		image.saveFile(TARGET_FILE);
	}
}
