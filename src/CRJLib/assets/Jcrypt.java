package CRJLib.assets;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.KeySpec;

public class Jcrypt 
{
    // FIELDS //
    //
    public final String ALGO = "AES";
    

    // ENCRYPTION //
    //
    /**
     * Generate a SecretKey from a password string
     * 
     * @param password Password to use for key generation
     * @return AES-compatible SecretKey
     * @throws Exception If key generation fails
     */
    //
    public  
    SecretKey gen_key(String password) 
    throws Exception 
    {
        // Use a consistent salt (for production, consider storing salt with encrypted files)
        byte[] salt = "RandomSalt123".getBytes();
        
        // Generate PBE key
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        
        // Convert to AES key
        return new SecretKeySpec(keyBytes, "AES");
    }
    //
    /**
     * Encrypt a file
     * 
     * @param input_path Path to file to encrypt
     * @param sk Secret key for encryption
     * @param exten Extension for encrypted file
     * @throws Exception If encryption fails
     */
    //
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
    //
    /**
     * Decrypt a file
     * 
     * @param enc_path Path to encrypted file to decrypt
     * @param sk Secret key for decryption
     * @param exten Extension of encrypted file
     * @throws Exception If decryption fails
     */
    //
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
    //
    /**
     * Process a file for encryption or decryption
     * 
     * @param cipher_mode Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @param in_file Input file
     * @param out_file Output file
     * @param sk Secret key
     * @throws Exception If process fails
     */
    //
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
            
            byte[] buf = new byte[4096];
            int b_read;
            while ((b_read = fis.read(buf)) != -1) 
            {
                cos.write(buf, 0, b_read);
            }
        }
    }

    
}