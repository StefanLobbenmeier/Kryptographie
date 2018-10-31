package imageutil.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aes.AES;
import imageutil.BmpImage;

public class TestBmpImage {

	private static final File ORG_BMP_FILE = new File("data/enc_image.bmp");
	private static final File OUTPUT_ENC_FILE = new File("data/test_enc_image.bmp");
	private static final File OUTPUT_DEC_FILE = new File("data/test_dec_image.bmp");
	private static final File ACTUAL_DEC_FILE = new File("cheat/dec_image.bmp");

	private static final File TMP_FILE = new File("data/tmp.bmp");
	private AES aes;
	private BmpImage bmpImage;

	@BeforeEach
	void init() throws FileNotFoundException, IOException {
		aes = new AES();
		bmpImage = new BmpImage();
		bmpImage.readFile(ORG_BMP_FILE);
	}

	@Test
	void testSaveFile() throws Exception {
		bmpImage.saveFile(OUTPUT_ENC_FILE);
		assertFilesEquals(ORG_BMP_FILE, OUTPUT_ENC_FILE);
	}

	private void assertFilesEquals(File expected, File actual) throws IOException {
		assertArrayEquals(Files.readAllBytes(expected.toPath()), Files.readAllBytes(actual.toPath()));
	}
	
	@Test
	void testDecryption() throws Exception {
		bmpImage.decrypt(aes);
		bmpImage.saveFile(OUTPUT_DEC_FILE);
		assertFilesEquals(ACTUAL_DEC_FILE, OUTPUT_DEC_FILE);
	}
	
	@Test
	void testEnAndDecryption() throws Exception {
		bmpImage.encrypt(aes);
		bmpImage.decrypt(aes);
		bmpImage.saveFile(TMP_FILE);
		assertFilesEquals(ORG_BMP_FILE, TMP_FILE);
	}

}
