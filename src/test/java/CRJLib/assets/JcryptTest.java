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

import org.junit.jupiter.api.*;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for Jcrypt class.
 * Tests file encryption and decryption operations.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@DisplayName("Jcrypt Encryption Tests")
class JcryptTest {

    private Jcrypt jcrypt;
    private File testFile;
    private File encryptedFile;
    private static final String TEST_PASSWORD = "testPassword123";
    private static final String TEST_CONTENT = "This is a test file for encryption";

    @BeforeEach
    void setUp() throws IOException {
        jcrypt = new Jcrypt();
        testFile = new File("test_crypt.txt");
        encryptedFile = new File("test_crypt.txt.enc");

        // Create test file
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(TEST_CONTENT);
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up test files
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
        if (encryptedFile != null && encryptedFile.exists()) {
            encryptedFile.delete();
        }
    }

    @Test
    @DisplayName("Test key generation")
    void testKeyGeneration() throws Exception {
        SecretKey key = jcrypt.gen_key(TEST_PASSWORD);
        assertNotNull(key, "Generated key should not be null");
        assertEquals("AES", key.getAlgorithm(), "Key algorithm should be AES");
    }

    @Test
    @DisplayName("Test key generation with custom salt")
    void testKeyGenerationWithSalt() throws Exception {
        byte[] salt = jcrypt.generateRandomSalt();
        assertNotNull(salt, "Generated salt should not be null");
        assertEquals(16, salt.length, "Salt should be 16 bytes");

        SecretKey key = jcrypt.gen_key(TEST_PASSWORD, salt);
        assertNotNull(key, "Generated key with custom salt should not be null");
        assertEquals("AES", key.getAlgorithm(), "Key algorithm should be AES");
    }

    @Test
    @DisplayName("Test random salt generation")
    void testRandomSaltGeneration() {
        byte[] salt1 = jcrypt.generateRandomSalt();
        byte[] salt2 = jcrypt.generateRandomSalt();

        assertNotNull(salt1, "First salt should not be null");
        assertNotNull(salt2, "Second salt should not be null");
        assertEquals(16, salt1.length, "First salt should be 16 bytes");
        assertEquals(16, salt2.length, "Second salt should be 16 bytes");
        assertFalse(java.util.Arrays.equals(salt1, salt2),
                "Two random salts should be different");
    }

    @Test
    @DisplayName("Test file encryption")
    void testFileEncryption() throws Exception {
        SecretKey key = jcrypt.gen_key(TEST_PASSWORD);

        // Encrypt the file
        jcrypt.enc_file(testFile.getPath(), key, ".enc");

        // Verify encrypted file exists and original is deleted
        assertTrue(encryptedFile.exists(), "Encrypted file should exist");
        assertFalse(testFile.exists(), "Original file should be deleted after encryption");

        // Verify encrypted content is different from original
        byte[] encryptedContent = Files.readAllBytes(encryptedFile.toPath());
        assertFalse(new String(encryptedContent).contains(TEST_CONTENT),
                "Encrypted content should not contain plain text");
    }

    @Test
    @DisplayName("Test file decryption")
    void testFileDecryption() throws Exception {
        SecretKey key = jcrypt.gen_key(TEST_PASSWORD);

        // Encrypt then decrypt
        jcrypt.enc_file(testFile.getPath(), key, ".enc");
        jcrypt.dec_file(encryptedFile.getPath(), key, ".enc");

        // Verify decrypted file exists and encrypted is deleted
        assertTrue(testFile.exists(), "Decrypted file should exist");
        assertFalse(encryptedFile.exists(), "Encrypted file should be deleted after decryption");

        // Verify content matches original
        String decryptedContent = Files.readString(testFile.toPath());
        assertEquals(TEST_CONTENT, decryptedContent,
                "Decrypted content should match original");
    }

    @Test
    @DisplayName("Test encryption/decryption round trip")
    void testRoundTrip() throws Exception {
        SecretKey key = jcrypt.gen_key(TEST_PASSWORD);

        // Encrypt
        jcrypt.enc_file(testFile.getPath(), key, ".enc");
        assertTrue(encryptedFile.exists(), "Encrypted file should exist");

        // Decrypt
        jcrypt.dec_file(encryptedFile.getPath(), key, ".enc");
        assertTrue(testFile.exists(), "Decrypted file should exist");

        // Verify content
        String content = Files.readString(testFile.toPath());
        assertEquals(TEST_CONTENT, content, "Round-trip content should match");
    }

    @Test
    @DisplayName("Test encryption with non-existent file")
    void testEncryptNonExistentFile() throws Exception {
        SecretKey key = jcrypt.gen_key(TEST_PASSWORD);
        File nonExistent = new File("nonexistent.txt");

        assertThrows(FileNotFoundException.class,
                () -> jcrypt.enc_file(nonExistent.getPath(), key, ".enc"),
                "Encrypting non-existent file should throw FileNotFoundException");
    }

    @Test
    @DisplayName("Test decryption with wrong extension")
    void testDecryptWrongExtension() throws Exception {
        SecretKey key = jcrypt.gen_key(TEST_PASSWORD);

        // Try to decrypt file without proper extension
        assertThrows(IllegalArgumentException.class,
                () -> jcrypt.dec_file(testFile.getPath(), key, ".enc"),
                "Decrypting file with wrong extension should throw IllegalArgumentException");
    }
}
