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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * PHash class provides secure password hashing functionality using PBKDF2.
 * This class implements a secure password hashing scheme that is resistant
 * to brute-force attacks and rainbow table attacks.
 * 
 * Features:
 * - PBKDF2 with HMAC-SHA1
 * - Configurable cost factor
 * - Secure random salt generation
 * - Password verification
 * - Base64 encoding for storage
 * 
 * @author CJ Remillard
 * @version 1.0
 */
public class PHash 
{ 
    // FIELDS //
    //
    /** Identifier for the hash format */
    public static final  String       ID = "$31$";
    /** Default cost factor (2^16 iterations) */
    public static final  int          DEFAULT_COST = 16;
    /** The key derivation algorithm used */
    private static final String       ALGORITHM = "PBKDF2WithHmacSHA1";
    /** Size of the derived key in bits */
    private static final int          SIZE = 128;
    /** Pattern for validating hash format */
    private static final Pattern      layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
    /** Secure random number generator for salt */
    private final        SecureRandom random;
    /** Current cost factor */
    private final        int          cost;

    /**
     * Default constructor using DEFAULT_COST.
     */
    public PHash()
    {
        this(DEFAULT_COST);
    }

    /**
     * Constructor with custom cost factor.
     * 
     * @param cost Cost factor (2^cost iterations)
     * @throws IllegalArgumentException if cost is out of range
     */
    public PHash(int cost)
    {
        iterations(cost);
        this.cost = cost;
        this.random = new SecureRandom();
    }

    /**
     * Calculate iterations from cost factor.
     * 
     * @param cost Cost factor
     * @return Number of iterations (2^cost)
     * @throws IllegalArgumentException if cost is out of range
     */
    private static 
    int iterations(int cost)
    {
        if ((cost < 0) || (cost > 30))
            throw new IllegalArgumentException("cost: "+cost);
        return 1 << cost;
    }

    /**
     * Hash a password string.
     * 
     * @param password Password to hash
     * @return Hashed password string
     */
    public 
    String hash(String password)
    {
        return hash(password.toCharArray());
    }

    /**
     * Hash a password character array.
     * This is the preferred method as it allows for secure password handling.
     * 
     * @param password Password as character array
     * @return Hashed password string in format: $31$cost$hash
     */
    public 
    String hash(char[] password) 
    {
        // Generate random salt
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        
        // Generate hash using PBKDF2
        byte[] dk = pbkdf2(password, salt, 1 << cost);
        
        // Combine salt and hash
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        
        // Encode as Base64
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + cost + '$' + enc.encodeToString(hash);
    }

    /**
     * Verify a password against a hash.
     * 
     * @param password Password to verify
     * @param token Hash to verify against
     * @return true if password matches, false otherwise
     * @throws IllegalArgumentException if token format is invalid
     */
    public 
    boolean auth(char[] password, String token) 
    {
        // Parse hash format
        Matcher m = layout.matcher(token);
        if (!m.matches())
            throw new IllegalArgumentException("Invalid token format");
            
        // Extract cost and hash
        int iterations = iterations(Integer.parseInt(m.group(1)));
        byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
        
        // Extract salt and verify
        byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
        byte[] check = pbkdf2(password, salt, iterations);
        
        // Compare hashes
        int zero = 0;
        for (int idx = 0; idx < check.length; ++idx)
            zero |= hash[salt.length + idx] ^ check[idx];
        return zero == 0;
    }

    /**
     * Verify a password string against a hash.
     * 
     * @param password Password to verify
     * @param token Hash to verify against
     * @return true if password matches, false otherwise
     */
    public 
    boolean auth(String password, String token)
    {
        return auth(password.toCharArray(), token);
    }

    /**
     * Generate PBKDF2 hash.
     * 
     * @param password Password as character array
     * @param salt Salt bytes
     * @param iterations Number of iterations
     * @return Derived key bytes
     * @throws IllegalStateException if algorithm is missing or invalid
     */
    private static 
    byte[] pbkdf2(char[] password, byte[] salt, int iterations) 
    {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try 
        {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) 
        {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) 
        {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }
}
