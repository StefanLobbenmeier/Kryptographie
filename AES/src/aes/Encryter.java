package aes;

public interface Encryter {
	public byte[] encrypt(byte[] plain) throws Exception;
	public byte[] decrypt(byte[] cipher) throws Exception;
}
