package aes;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES implements Encryter {

	private static final String CIPHER_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM_MODE = "CBC";
	private static final String CIPHER_ALGORITHM_PADDING = "PKCS5Padding";
    private static final String CIPHER = CIPHER_ALGORITHM + "/" + CIPHER_ALGORITHM_MODE + "/" + CIPHER_ALGORITHM_PADDING;
    
	private SecretKeySpec secretKey;
	private IvParameterSpec ivParameterSpec;

    public AES()
    {
    	byte[] key = new byte[32];
    	//256-Bit-Schlüssel
    	key[0]=(byte) 0x84;
    	key[1]=0x15;
    	key[2]=(byte) 0xb6;
    	key[3]=(byte) 0x9f;
    	key[4]=0x0d;
    	key[5]=0x44;
    	key[6]=(byte) 0x92;
    	key[7]=(byte) 0x8b;
    	key[8]=(byte) 0xe7;
    	key[9]=(byte) 0xf2;
    	key[10]=(byte) 0xd3;
    	key[11]=0x30;
    	key[12]=0x5f;
    	key[13]=0x7d;
    	key[14]=0x21;
    	key[15]=(byte) 0x97;
    	key[16]=(byte) 0xab;
    	key[17]=(byte) 0xae;
    	key[18]=(byte) 0x9a;
    	key[19]=0x10;
    	key[20]=0x74;
    	key[21]=0x66;
    	key[22]=0x2d;
    	key[23]=(byte) 0x83;
    	key[24]=0x1f;
    	key[25]=0x62;
    	key[26]=(byte) 0xfe;
    	key[27]=0x55;
    	key[28]=0x0b;
    	key[29]=0x32;
    	key[30]=0x42;
    	key[31]=0x79;

    	byte[] iv = new byte[16];
		//128-Bit-Initialisierungsvektor
    	iv [0]=(byte) 0x99;
    	iv[1]=0x61;
    	iv[2]=(byte) 0x8f;
    	iv[3]=(byte) 0xd2;
    	iv[4]=(byte) 0xa5;
    	iv[5]=0x05;
    	iv[6]=0x13;
    	iv[7]=(byte) 0x82;
    	iv[8]=(byte) 0x9d;
    	iv[9]=0x2f;
    	iv[10]=(byte) 0xf4;
    	iv[11]=0x73;
    	iv[12]=(byte) 0xf6;
    	iv[13]=(byte) 0x85;
    	iv[14]=0x06;
    	iv[15]=(byte) 0xe3;

    	secretKey = new SecretKeySpec(key, CIPHER_ALGORITHM);
    	ivParameterSpec = new IvParameterSpec(iv);
    }

    /**
     * Encrypts the given plain text
     *
     * @param plainText The plain text to encrypt
     * @throws Exception 
     */
    public byte[] encrypt(byte[] plainText) throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        return cipher.doFinal(plainText);
    }

    /**
     * Decrypts the given byte array
     *
     * @param cipherText The data to decrypt
     * @throws Exception
     */
    public byte[] decrypt(byte[] cipherText) throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        return cipher.doFinal(cipherText);
    }

}
