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

package CRJLib;

import CRJLib.Std;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for CRJLib.Std class.
 * Tests basic Std functionality including random number generation,
 * URL handling, and file operations.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@DisplayName("CRJLib Std Tests")
class StdTest {

    private Std std;
    private File testFile;

    @BeforeEach
    void setUp() {
        std = new Std();
        testFile = new File("test_junit.txt");
    }

    @AfterEach
    void tearDown() {
        // Clean up test files
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    @DisplayName("Test pseudo-random number generation")
    void testPseudoRandom() {
        int random = std.prand(1, 100);
        assertTrue(random >= 1 && random <= 100,
                "Random number should be between 1 and 100");
    }

    @Test
    @DisplayName("Test secure random number generation")
    void testSecureRandom() {
        int random = std.srand(1, 100);
        assertTrue(random >= 1 && random <= 100,
                "Secure random number should be between 1 and 100");
    }

    @Test
    @DisplayName("Test random number range boundaries")
    void testRandomBoundaries() {
        // Test that random numbers are within range over multiple iterations
        for (int i = 0; i < 100; i++) {
            int prand = std.prand(10, 20);
            int srand = std.srand(10, 20);

            assertTrue(prand >= 10 && prand <= 20,
                    "Pseudo-random should be in range [10, 20]");
            assertTrue(srand >= 10 && srand <= 20,
                    "Secure random should be in range [10, 20]");
        }
    }

    @Test
    @DisplayName("Test file opening")
    void testOpenFile() throws IOException {
        // Create a test file
        assertTrue(testFile.createNewFile(), "Test file should be created");

        // Note: open_file may fail in headless environments
        // We just test that it doesn't throw an exception
        assertDoesNotThrow(() -> std.open_file(testFile),
                "Opening a file should not throw an exception");
    }

    @Test
    @DisplayName("Test URL opening")
    void testOpenUrl() {
        // Note: This may fail in headless environments or without a browser
        // We just test that it doesn't throw an exception
        assertDoesNotThrow(() -> std.open_url("https://www.example.com"),
                "Opening a URL should not throw an exception");
    }

    @Test
    @DisplayName("Test coutln output")
    void testCoutln() {
        // Test that coutln doesn't throw exceptions
        assertDoesNotThrow(() -> std.coutln("Test message"),
                "coutln should not throw an exception");
    }

    @Test
    @DisplayName("Test cout output")
    void testCout() {
        // Test that cout doesn't throw exceptions
        assertDoesNotThrow(() -> std.cout("Test message"),
                "cout should not throw an exception");
    }

    @Test
    @DisplayName("Test log output")
    void testLog() {
        // Test that log doesn't throw exceptions
        assertDoesNotThrow(() -> std.log("Test log message"),
                "log should not throw an exception");
    }
}
