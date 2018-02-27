// CaesarCipher.java
// Created by Dillon Esponda
// implements encryption and decryption of Caesar Cipher

package ciphertextfxml;

public class CaesarCipher
{
    public static String encrypt(String plainText, int offset)
    {
	// reels in the offset if it's bigger than 26
	int moddedOffset = offset % 26;
	StringBuilder sb = new StringBuilder();
	for(char ch: plainText.toCharArray())
	{
	    if (Character.isAlphabetic(ch))
		sb.append((char)((((ch + moddedOffset) - 'a') % 26) + 'a'));
	    else
		sb.append(ch);
	}
	return sb.toString();
    }
    
    public static String decrypt(String cipherText, int offset)
    {
	// reels in the offset if it's bigger than 26
	int moddedOffset = offset % 26;
	StringBuilder sb = new StringBuilder();
	for(char ch: cipherText.toCharArray())
	{
	    if (Character.isAlphabetic(ch))
	    {
		// if subtracting the offset brings it below zero,
		// add 26 (the range characters) to bring it back
		// to a positive value
		if(ch-moddedOffset-'a' < 0)
		    sb.append((char)(((ch - moddedOffset - 'a' + 26) % 26) + 'a'));

		else
		    sb.append((char)(((ch - moddedOffset - 'a') % 26) + 'a'));
	    }
	    else
		sb.append(ch);
	}
	return sb.toString();
    }
}
