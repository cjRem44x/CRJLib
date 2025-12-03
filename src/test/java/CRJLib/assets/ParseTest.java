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
 * JUnit 5 test suite for Parse class.
 * Tests string parsing operations for various primitive types.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@DisplayName("Parse Operations Tests")
class ParseTest {

    private Parse parse;

    @BeforeEach
    void setUp() {
        parse = new Parse();
    }

    @Test
    @DisplayName("Test string to int conversion")
    void testStringToInt() {
        assertEquals(123, parse.str_int("123"));
        assertEquals(-456, parse.str_int("-456"));
        assertEquals(0, parse.str_int("0"));
    }

    @Test
    @DisplayName("Test string to int with invalid input")
    void testStringToIntInvalid() {
        // Parse class returns 0 for invalid input
        assertEquals(0, parse.str_int("invalid"));
        assertEquals(0, parse.str_int("12.34"));
    }

    @Test
    @DisplayName("Test string to long conversion")
    void testStringToLong() {
        assertEquals(123456789L, parse.str_long("123456789"));
        assertEquals(-987654321L, parse.str_long("-987654321"));
        assertEquals(0L, parse.str_long("0"));
    }

    @Test
    @DisplayName("Test string to long with invalid input")
    void testStringToLongInvalid() {
        assertEquals(0L, parse.str_long("invalid"));
    }

    @Test
    @DisplayName("Test string to float conversion")
    void testStringToFloat() {
        assertEquals(123.45f, parse.str_float("123.45"), 0.001);
        assertEquals(-67.89f, parse.str_float("-67.89"), 0.001);
        assertEquals(0.0f, parse.str_float("0.0"), 0.001);
    }

    @Test
    @DisplayName("Test string to float with invalid input")
    void testStringToFloatInvalid() {
        assertEquals(0.0f, parse.str_float("invalid"), 0.001);
    }

    @Test
    @DisplayName("Test string to double conversion")
    void testStringToDouble() {
        assertEquals(123.456789, parse.str_double("123.456789"), 0.000001);
        assertEquals(-987.654321, parse.str_double("-987.654321"), 0.000001);
        assertEquals(0.0, parse.str_double("0.0"), 0.000001);
    }

    @Test
    @DisplayName("Test string to double with invalid input")
    void testStringToDoubleInvalid() {
        assertEquals(0.0, parse.str_double("invalid"), 0.000001);
    }

    @Test
    @DisplayName("Test parsing edge cases")
    void testEdgeCases() {
        // Test maximum values
        assertEquals(Integer.MAX_VALUE, parse.str_int(String.valueOf(Integer.MAX_VALUE)));
        assertEquals(Long.MAX_VALUE, parse.str_long(String.valueOf(Long.MAX_VALUE)));

        // Test minimum values
        assertEquals(Integer.MIN_VALUE, parse.str_int(String.valueOf(Integer.MIN_VALUE)));
        assertEquals(Long.MIN_VALUE, parse.str_long(String.valueOf(Long.MIN_VALUE)));
    }
}
