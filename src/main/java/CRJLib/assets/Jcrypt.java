/*
 * Copyright (c) 2025 CJ Remillard
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
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * Jcrypt class provides file encryption and decryption functionality using AES encryption.
 * This class implements secure file encryption with password-based key derivation
 * and supports both encryption and decryption of files.
 *
 * <p><strong>SECURITY WARNING:</strong> This class uses a FIXED salt by default for
 * backwards compatibility with existing encrypted files. For production use with new
 * files, you should:
 * <ul>
 * <li>Generate a unique random salt for each file</li>
 * <li>Store the salt alongside the encrypted file (prepended or in metadata)</li>
 * <li>Use {@link #gen_key(String, byte[])} with your own salt management</li>
 * </ul>
 * Using a fixed salt allows attackers to precompute rainbow tables and weakens security.
 *
 * <p>Features:
 * <ul>
 * <li>AES encryption/decryption</li>
 * <li>Password-based key derivation (PBKDF2)</li>
 * <li>File encryption/decryption</li>
 * <li>Secure key generation with custom salt support</li>
 * <li>Automatic file cleanup</li>
 * </ul>
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
     * Generate a SecretKey from a password string using PBKDF2 with a fixed salt.
     * This method creates a cryptographically secure key suitable for AES encryption.
     *
     * <p><strong>SECURITY WARNING:</strong> This method uses a FIXED salt ("RandomSalt123")
     * for backwards compatibility with existing encrypted files. This is NOT secure for
     * production use as it allows rainbow table attacks. For new files, use
     * {@link #gen_key(String, byte[])} with a unique random salt per file.
     *
     * @param password Password to use for key generation
     * @return AES-compatible SecretKey
     * @throws Exception If key generation fails
     *
     * @see #gen_key(String, byte[])
     * @see #generateRandomSalt()
     */
    public
    SecretKey gen_key(String password)
    throws Exception
    {
        // SECURITY WARNING: Using fixed salt for backwards compatibility only
        byte[] salt = "RandomSalt123".getBytes();
        return gen_key(password, salt);
    }

    /**
     * Generate a SecretKey from a password string using PBKDF2 with a custom salt.
     * This method creates a cryptographically secure key suitable for AES encryption.
     *
     * <p><strong>Recommended Usage:</strong> Generate a unique random salt for each file
     * using {@link #generateRandomSalt()}, store it alongside the encrypted file, and
     * use the same salt when decrypting.
     *
     * @param password Password to use for key generation
     * @param salt Unique salt bytes (recommended: 16 bytes from {@link #generateRandomSalt()})
     * @return AES-compatible SecretKey
     * @throws Exception If key generation fails
     *
     * @see #generateRandomSalt()
     */
    public
    SecretKey gen_key(String password, byte[] salt)
    throws Exception
    {
        // Generate PBE key using PBKDF2
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        // Convert to AES key
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Generate a cryptographically secure random salt for key derivation.
     * Use this method to create unique salts for each file encryption operation.
     *
     * <p>The generated salt should be stored alongside the encrypted file
     * (e.g., prepended to the file or stored in metadata) so it can be used
     * during decryption.
     *
     * @return 16 bytes of cryptographically secure random data
     */
    public
    byte[] generateRandomSalt()
    {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
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
    void enc_file(String input_path, SecretKey sk, String exten) 
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
