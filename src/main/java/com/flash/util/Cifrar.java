package com.flash.util;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

public class Cifrar {

	@SuppressWarnings("unused")
	private static Cipher rsa;

	public static String cifrar(String texto) throws Exception {

		// Se salva y recupera de fichero la clave publica
		PublicKey publicKey = loadPublicKey("registro/publickey.dat");
		// Obtener la clase para encriptar/desencriptar
		rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		// Escribimos el encriptado para verlo, con caracteres visibles
		String secure = Encrypt(texto, publicKey);
		return secure;
	}

	public static String decifrar(String texto) throws Exception {
		// Se salva y recupera de fichero la clave privada
		PrivateKey privateKey = loadPrivateKey("registro/privatekey.dat");
		// Se escribe el texto desencriptado
		String unsecure = Decrypt(texto, privateKey);
		return unsecure;
	}

	private static PublicKey loadPublicKey(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		int numBtyes = fis.available();
		byte[] bytes = new byte[numBtyes];
		fis.read(bytes);
		fis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec keySpec = new X509EncodedKeySpec(bytes);
		PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
		return keyFromBytes;
	}

	private static PrivateKey loadPrivateKey(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		int numBtyes = fis.available();
		byte[] bytes = new byte[numBtyes];
		fis.read(bytes);
		fis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
		return keyFromBytes;
	}

	public static String Encrypt(String plain, PublicKey publicKey) throws Exception {

		byte[] encryptedBytes;

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		encryptedBytes = cipher.doFinal(plain.getBytes());

		return bytesToString(encryptedBytes);

	}

	public static String bytesToString(byte[] b) {
		byte[] b2 = new byte[b.length + 1];
		b2[0] = 1;
		System.arraycopy(b, 0, b2, 1, b.length);
		return new BigInteger(b2).toString(36);
	}

	public static String Decrypt(String result, PrivateKey privateKey) throws Exception {

		byte[] decryptedBytes;

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		decryptedBytes = cipher.doFinal(stringToBytes(result));
		return new String(decryptedBytes);
	}

	public static byte[] stringToBytes(String s) {
		byte[] b2 = new BigInteger(s, 36).toByteArray();
		return Arrays.copyOfRange(b2, 1, b2.length);
	}

}
