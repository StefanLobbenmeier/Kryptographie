package com.fhswf.kryptographie;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InvalidKeyException
    {
        task1a();
        task1b();
    }

    private static void task1a() {
        System.out.println("1 a)");

        BigInteger c = BigInteger.valueOf(5);

        RSAPublicKey arthursKey = new ShortRSAKey(BigInteger.valueOf(221), BigInteger.valueOf(55));
        RSAPublicKey fordsKey = new ShortRSAKey(BigInteger.valueOf(391), BigInteger.valueOf(3));

        decryptCommonPrimeRsa(c, arthursKey, fordsKey);
        System.out.println();
    }

    private static void task1b() {
        System.out.println("1 b)");

        BigInteger rsa_cypher = readBigIntegerFromFile("rsa/data/rsa_cipher.txt");
        BigInteger e_arthur = readBigIntegerFromFile("rsa/data/rsa_public_key_arthur.txt");
        BigInteger e_ford = readBigIntegerFromFile("rsa/data/rsa_public_key_ford.txt");

        BigInteger modulus = BigInteger.valueOf(65537);

        BigInteger m = decryptCommonPrimeRsa(rsa_cypher, new ShortRSAKey(e_arthur, modulus), new ShortRSAKey(e_ford, modulus));



        String message = new String(m.toByteArray());
        System.out.println("message = " + message);
    }

    private static BigInteger readBigIntegerFromFile(String path) {
        Path filepath = new File(path).toPath();
        System.out.println("filepath = " + filepath.toAbsolutePath());
        try {
            return Files.lines(filepath).findFirst().map(number -> new BigInteger(number)).get();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static BigInteger decryptCommonPrimeRsa(BigInteger c, RSAPublicKey arthursKey, RSAPublicKey fordsKey) {
        BigInteger commonP = getCommonPrime(arthursKey, fordsKey);
        BigInteger arthursQ = arthursKey.getModulus().divide(commonP);
        BigInteger fordsQ = fordsKey.getModulus().divide(commonP);

        System.out.println("commonP = " + commonP);
        System.out.println("arthursQ = " + arthursQ);
        System.out.println("fordsQ = " + fordsQ);

        BigInteger arthursD = getD(commonP, arthursQ, arthursKey.getPublicExponent());
        BigInteger fordsD = getD(commonP, fordsQ, fordsKey.getPublicExponent());

        System.out.println("arthursD = " + arthursD);
        System.out.println("fordsD = " + fordsD);

        BigInteger m = c.modPow(arthursD, arthursKey.getModulus());

        System.out.println("m = " + m);

        return m;
    }

    private static BigInteger getCommonPrime(RSAPublicKey arthursKey, RSAPublicKey fordsKey) {
        return EuklideanAlgorithm.ggT(arthursKey.getModulus(), fordsKey.getModulus());
    }

    static BigInteger getD(BigInteger p, BigInteger q, BigInteger e) {
        BigInteger a = e;
        BigInteger b = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        System.out.printf("a = %s%n", a);
        System.out.printf("b = %s%n", b);
        ExtendedEuklidianResult result = EuklideanAlgorithm.extendedGgT(a, b);

        System.out.println(result);

        return b.add(result.getS());

    }
}
