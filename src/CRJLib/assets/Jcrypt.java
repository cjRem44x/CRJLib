/*
 * Copyright (c) 2024 CJ Remillard
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package CRJLib.assets;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.KeySpec;

/**
 * Jcrypt class provides file encryption and decryption functionality using AES encryption.
 * This class implements secure file encryption with password-based key derivation
 * and supports both encryption and decryption of files.
 * 
 * Features:
 * - AES encryption/decryption
 * - Password-based key derivation (PBKDF2)
 * - File encryption/decryption
 * - Secure key generation
 * - Automatic file cleanup
 * 
 * @author CJ Remillard
 * @version 1.0
 */
public class Jcrypt 
{
    // FIELDS //
    //
    /** The encryption algorithm used (AES) */
    public final String ALGO = "AES";
    
    /**
     * Generate a SecretKey from a password string using PBKDF2.
     * This method creates a cryptographically secure key suitable for AES encryption.
     * 
     * @param password Password to use for key generation
     * @return AES-compatible SecretKey
     * @throws Exception If key generation fails
     * 
     * Note: Uses a fixed salt for demonstration. In production, use a unique salt per file.
     */
    public  
    SecretKey gen_key(String password) 
    throws Exception 
    {
        // Use a consistent salt (for production, consider storing salt with encrypted files)
        byte[] salt = "RandomSalt123".getBytes();
        
        // Generate PBE key using PBKDF2
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        
        // Convert to AES key
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Encrypt a file using AES encryption.
     * The original file is deleted after successful encryption.
     * 
     * @param input_path Path to file to encrypt
     * @param sk Secret key for encryption
     * @param exten Extension for encrypted file
     * @throws Exception If encryption fails
     * 
     * Example:
     * enc_file("secret.txt", key, ".enc") creates "secret.txt.enc"
     */
    public 
    void enc_file(String input_path, SecretKey sk, Str exten) 
    throws Exception 
    {
        File in = new File(input_path);
        if (!in.exists()) 
        {
            throw new FileNotFoundException("Input file not found: " + in.getAbsolutePath());
        }
        
        File enc_file = new File(in.getParent(), in.getName() + exten);
        proc_file(Cipher.ENCRYPT_MODE, in, enc_file, sk);
        in.delete();
    }

    /**
     * Decrypt a file using AES decryption.
     * The encrypted file is deleted after successful decryption.
     * 
     * @param enc_path Path to encrypted file to decrypt
     * @param sk Secret key for decryption
     * @param exten Extension of encrypted file
     * @throws Exception If decryption fails
     * 
     * Example:
     * dec_file("secret.txt.enc", key, ".enc") restores "secret.txt"
     */
    public  
    void dec_file(String enc_path, SecretKey sk, String exten) 
    throws Exception 
    {
        File enc_file = new File(enc_path);
        if (!enc_file.exists()) 
        {
            throw new FileNotFoundException("Encrypted file not found: " + enc_file.getAbsolutePath());
        }
        
        if (!enc_file.getName().endsWith(exten)) 
        {
            throw new IllegalArgumentException("File does not have "+exten+" extension: " + enc_file.getName());
        }
        
        String org = enc_file.getName().replace(exten, "");
        File dec_file = new File(enc_file.getParent(), org);
        proc_file(Cipher.DECRYPT_MODE, enc_file, dec_file, sk);
        enc_file.delete();
    }

    /**
     * Process a file for encryption or decryption.
     * This internal method handles the actual encryption/decryption process.
     * 
     * @param cipher_mode Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @param in_file Input file
     * @param out_file Output file
     * @param sk Secret key
     * @throws Exception If process fails
     */
    private  
    void proc_file(int cipher_mode, File in_file, File out_file, SecretKey sk) 
    throws Exception 
    {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(cipher_mode, sk);
        
        try (FileInputStream fis = new FileInputStream(in_file);
             FileOutputStream fos = new FileOutputStream(out_file);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) 
        {
            // Process file in chunks for better memory management
            byte[] buf = new byte[4096];
            int b_read;
            while ((b_read = fis.read(buf)) != -1) 
            {
                cos.write(buf, 0, b_read);
            }
        }
    }
}