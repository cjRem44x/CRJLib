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
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for FIO class.
 * Tests file I/O operations including read, write, and delete.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@DisplayName("FIO File Operations Tests")
class FIOTest {

    private FIO fio;
    private static final String TEST_FILE = "test_fio.txt";
    private static final String TEST_CONTENT = "Hello, World!";

    @BeforeEach
    void setUp() {
        fio = new FIO();
    }

    @AfterEach
    void tearDown() {
        // Clean up test file
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    @DisplayName("Test file writing")
    void testWriteFile() {
        fio.write_file(TEST_FILE, TEST_CONTENT);

        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "File should exist after writing");
    }

    @Test
    @DisplayName("Test file reading")
    void testReadFile() {
        // Write then read
        fio.write_file(TEST_FILE, TEST_CONTENT);
        String content = fio.read_file(TEST_FILE);

        assertEquals(TEST_CONTENT, content,
                "Read content should match written content");
    }

    @Test
    @DisplayName("Test file deletion")
    void testDeleteFile() {
        // Write file first
        fio.write_file(TEST_FILE, TEST_CONTENT);
        assertTrue(new File(TEST_FILE).exists(), "File should exist before deletion");

        // Delete file
        fio.del_file(TEST_FILE);
        assertFalse(new File(TEST_FILE).exists(), "File should not exist after deletion");
    }

    @Test
    @DisplayName("Test write, read, delete cycle")
    void testFullCycle() {
        // Write
        fio.write_file(TEST_FILE, TEST_CONTENT);
        assertTrue(new File(TEST_FILE).exists());

        // Read
        String content = fio.read_file(TEST_FILE);
        assertEquals(TEST_CONTENT, content);

        // Delete
        fio.del_file(TEST_FILE);
        assertFalse(new File(TEST_FILE).exists());
    }

    @Test
    @DisplayName("Test overwriting existing file")
    void testOverwriteFile() {
        String firstContent = "First content";
        String secondContent = "Second content";

        // Write first content
        fio.write_file(TEST_FILE, firstContent);
        assertEquals(firstContent, fio.read_file(TEST_FILE));

        // Overwrite with second content
        fio.write_file(TEST_FILE, secondContent);
        assertEquals(secondContent, fio.read_file(TEST_FILE),
                "File should contain overwritten content");
    }

    @Test
    @DisplayName("Test writing empty content")
    void testWriteEmptyContent() {
        fio.write_file(TEST_FILE, "");
        String content = fio.read_file(TEST_FILE);
        assertEquals("", content, "Empty file should read as empty string");
    }

    @Test
    @DisplayName("Test writing multiline content")
    void testWriteMultilineContent() {
        String multiline = "Line 1\nLine 2\nLine 3";
        fio.write_file(TEST_FILE, multiline);
        String content = fio.read_file(TEST_FILE);
        assertEquals(multiline, content, "Multiline content should be preserved");
    }

    @Test
    @DisplayName("Test reading non-existent file")
    void testReadNonExistentFile() {
        String content = fio.read_file("nonexistent_file.txt");
        assertNull(content, "Reading non-existent file should return null");
    }

    @Test
    @DisplayName("Test deleting non-existent file")
    void testDeleteNonExistentFile() {
        // Should not throw exception
        assertDoesNotThrow(() -> fio.del_file("nonexistent_file.txt"),
                "Deleting non-existent file should not throw exception");
    }

    @Test
    @DisplayName("Test writing special characters")
    void testWriteSpecialCharacters() {
        String specialChars = "Special: @#$%^&*()_+-=[]{}|;:',.<>?/`~";
        fio.write_file(TEST_FILE, specialChars);
        String content = fio.read_file(TEST_FILE);
        assertEquals(specialChars, content,
                "Special characters should be preserved");
    }
}
