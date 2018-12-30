package com.fhswf.kryptographie;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.SecureRandom;

/**
 * Hello world!
 */
public class App {

    public static final BigInteger DEFAULT_MODULUS = BigInteger.valueOf(65537);

    public static void main(String[] args) throws InvalidKeyException {
        task1a();
        task1b();

        task3();
    }

    private static void task1a() {
        System.out.println("1 a)");

        BigInteger c = BigInteger.valueOf(5);

        ShortRSAKey arthursKey = new ShortRSAKey(BigInteger.valueOf(221), BigInteger.valueOf(55));
        ShortRSAKey fordsKey = new ShortRSAKey(BigInteger.valueOf(391), BigInteger.valueOf(3));

        decryptCommonPrimeRsa(c, arthursKey, fordsKey);
        System.out.println();
    }

    private static void task1b() {
        System.out.println("1 b)");

        BigInteger rsa_cypher = readBigIntegerFromFile("rsa/data/rsa_cipher.txt");
        BigInteger e_arthur = readBigIntegerFromFile("rsa/data/rsa_public_key_arthur.txt");
        BigInteger e_ford = readBigIntegerFromFile("rsa/data/rsa_public_key_ford.txt");

        BigInteger m = decryptCommonPrimeRsa(rsa_cypher, new ShortRSAKey(e_arthur, DEFAULT_MODULUS), new ShortRSAKey(e_ford, DEFAULT_MODULUS));


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

    private static BigInteger decryptCommonPrimeRsa(BigInteger c, ShortRSAKey arthursKey, ShortRSAKey fordsKey) {
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

    private static BigInteger getCommonPrime(ShortRSAKey arthursKey, ShortRSAKey fordsKey) {
        return EuklideanAlgorithm.ggT(arthursKey.getModulus(), fordsKey.getModulus());
    }

    static BigInteger getD(BigInteger p, BigInteger q, BigInteger e) {
        System.out.println("p = [" + p + "], q = [" + q + "], e = [" + e + "]");
        BigInteger a = e;
        BigInteger b = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        System.out.printf("a = %s%n", a);
        System.out.printf("b = %s%n", b);
        ExtendedEuklidianResult result = EuklideanAlgorithm.extendedGgT(a, b);

        System.out.println(result);

        if (result.getRest().equals(BigInteger.ONE))
            return b.add(result.getS());

        throw new IllegalArgumentException(String.format("e = %s and (p-1)*(q-1)=%s share a common Factor bigger than 1", e, b));

    }

    private static void task3() {
        System.out.println("3");

        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(1024, random);
        BigInteger q = BigInteger.probablePrime(1024, random);
        BigInteger n = p.multiply(q);

        BigInteger e = BigInteger.probablePrime(1024, random);
        BigInteger d = getD(p, q, e);

        ShortRSAKey publicKey = new ShortRSAKey(n, e);
        ShortRSAKey privteKey = new ShortRSAKey(n, d);

        BigInteger message = new BigInteger("31415926");
        BigInteger encrypted = publicKey.crypt(message);

        System.out.println("encrypted = " + encrypted);

        BigInteger decrypted = privteKey.crypt(encrypted);

        System.out.println("decrypted = " + decrypted);
    }
}
