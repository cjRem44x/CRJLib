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
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for PHash class.
 * Tests password hashing and authentication operations.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@DisplayName("PHash Password Tests")
class PHashTest {

    private PHash phash;
    private static final String TEST_PASSWORD = "testPassword123";
    private static final String WRONG_PASSWORD = "wrongPassword456";

    @BeforeEach
    void setUp() {
        phash = new PHash();
    }

    @Test
    @DisplayName("Test password hashing")
    void testPasswordHashing() {
        String hash = phash.hash(TEST_PASSWORD);
        assertNotNull(hash, "Hash should not be null");
        assertFalse(hash.isEmpty(), "Hash should not be empty");
        assertNotEquals(TEST_PASSWORD, hash, "Hash should differ from password");
    }

    @Test
    @DisplayName("Test hashing produces unique salts")
    void testUniqueSalts() {
        String hash1 = phash.hash(TEST_PASSWORD);
        String hash2 = phash.hash(TEST_PASSWORD);

        assertNotNull(hash1, "First hash should not be null");
        assertNotNull(hash2, "Second hash should not be null");
        assertNotEquals(hash1, hash2,
                "Same password should produce different hashes due to unique salts");
    }

    @Test
    @DisplayName("Test password authentication with correct password")
    void testAuthenticationSuccess() {
        String hash = phash.hash(TEST_PASSWORD);
        assertTrue(phash.auth(TEST_PASSWORD, hash),
                "Authentication should succeed with correct password");
    }

    @Test
    @DisplayName("Test password authentication with wrong password")
    void testAuthenticationFailure() {
        String hash = phash.hash(TEST_PASSWORD);
        assertFalse(phash.auth(WRONG_PASSWORD, hash),
                "Authentication should fail with wrong password");
    }

    @Test
    @DisplayName("Test hash format contains salt and hash")
    void testHashFormat() {
        String hash = phash.hash(TEST_PASSWORD);
        assertTrue(hash.contains(":"), "Hash should contain separator");

        String[] parts = hash.split(":");
        assertEquals(2, parts.length, "Hash should have two parts (salt:hash)");
        assertFalse(parts[0].isEmpty(), "Salt part should not be empty");
        assertFalse(parts[1].isEmpty(), "Hash part should not be empty");
    }

    @Test
    @DisplayName("Test empty password")
    void testEmptyPassword() {
        String hash = phash.hash("");
        assertNotNull(hash, "Hash of empty string should not be null");

        assertTrue(phash.auth("", hash),
                "Empty password should authenticate correctly");
        assertFalse(phash.auth("notEmpty", hash),
                "Non-empty password should not match empty password hash");
    }

    @Test
    @DisplayName("Test password with special characters")
    void testSpecialCharacters() {
        String specialPassword = "p@ssw0rd!#$%^&*()_+-=[]{}|;:',.<>?/`~";
        String hash = phash.hash(specialPassword);

        assertTrue(phash.auth(specialPassword, hash),
                "Password with special characters should authenticate");
    }

    @Test
    @DisplayName("Test very long password")
    void testLongPassword() {
        String longPassword = "a".repeat(1000);
        String hash = phash.hash(longPassword);

        assertTrue(phash.auth(longPassword, hash),
                "Very long password should authenticate correctly");
    }

    @Test
    @DisplayName("Test authentication with invalid hash format")
    void testInvalidHashFormat() {
        assertFalse(phash.auth(TEST_PASSWORD, "invalidhash"),
                "Authentication should fail with invalid hash format");
        assertFalse(phash.auth(TEST_PASSWORD, ""),
                "Authentication should fail with empty hash");
    }

    @Test
    @DisplayName("Test hash consistency")
    void testHashConsistency() {
        String hash = phash.hash(TEST_PASSWORD);

        // Authentication should work multiple times
        assertTrue(phash.auth(TEST_PASSWORD, hash));
        assertTrue(phash.auth(TEST_PASSWORD, hash));
        assertTrue(phash.auth(TEST_PASSWORD, hash));
    }
}
