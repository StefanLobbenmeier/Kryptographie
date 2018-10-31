package imageutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import aes.Encryter;

public class BmpImage {
	
	private byte[] filemetadata = new byte[10];
	private byte[] actualImageOffsetDescriptions = new byte[4];
	private byte[] imageMetadata;
	private byte[] imageData;
	private List<byte[]> getAllArrays() {
		return Arrays.asList(filemetadata, actualImageOffsetDescriptions, imageMetadata, imageData);
	}

	public void readFile(File sourceFile) throws FileNotFoundException, IOException {
		RandomAccessFile file = new RandomAccessFile(sourceFile, "r");
		long fileLength = file.length();
		System.out.printf("Reading from file %s with length %d.%n", sourceFile.getAbsolutePath(), fileLength);

		file.read(filemetadata);
		file.read(actualImageOffsetDescriptions);
		
		int actualImageOffset = ByteBuffer.wrap(actualImageOffsetDescriptions).order(ByteOrder.LITTLE_ENDIAN).getInt();
		System.out.printf("The actual Image Offset is %d.%n", actualImageOffset);
		
		imageMetadata = new byte[actualImageOffset - 14];
		file.read(imageMetadata);
		
		int imageDataLength = (int) (fileLength - actualImageOffset);
		System.out.printf("The actual Image Data Length is %d.%n", imageDataLength);
		imageData = new byte[imageDataLength];
		file.read(imageData);
		
		boolean finished = file.read(new byte[1]) == -1;
		if (! finished)
			throw new Error("Bad Programming: The file is not finished yet");
		
		file.close();
	}
	
	public void encrypt(Encryter encryter) throws Exception {
		imageData = encryter.encrypt(imageData);
	}
	
	public void decrypt(Encryter encryter) throws Exception {
		imageData = encryter.decrypt(imageData);
	}
	
	public void saveFile(File destinationFile) throws IOException {
		RandomAccessFile file = new RandomAccessFile(destinationFile, "rw");
		for (byte[] data: getAllArrays()) {
			file.write(data);
		}
		file.close();
	}
}
