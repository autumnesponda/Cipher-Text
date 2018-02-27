// VigenereCipher.java
// Created by Dillon Esponda
// implements encryption and decryption of Vigenere Cipher

package ciphertextfxml;

/**
 *
 * @author dhusky
 */
public class VigenereCipher
{
    public static String encrypt(String plainText, String key)
    {
	StringBuilder sb = new StringBuilder();
	
	// using j to keep track of which letter we should use in the key,
	// so any non-alpha characters don't mess up the count of which letter
	// in the key we're in.
	for(int i = 0, j = 0; i < plainText.length(); i++)
	{
	    if(Character.isAlphabetic(plainText.charAt(i)))
		sb.append((char)(((plainText.charAt(i) - 'a' + key.charAt(j++ % key.length()) - 'a') % 26) + 'a'));
	    else
		sb.append(plainText.charAt(i));
	}
	return sb.toString();
    }
    
    public static String decrypt(String cipherText, String key)
    {
	StringBuilder sb = new StringBuilder();
	
	for(int i = 0, j = 0; i < cipherText.length(); i++)
	{
	    if(Character.isAlphabetic(cipherText.charAt(i)))
		sb.append((char)(((cipherText.charAt(i) - key.charAt(j++ % key.length()) + 26) % 26) + 'a'));
	    else
		sb.append(cipherText.charAt(i));
	}
	return sb.toString();    
    }
    
}
