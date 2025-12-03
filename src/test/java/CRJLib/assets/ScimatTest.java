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
 * JUnit 5 test suite for Scimat class.
 * Tests mathematical operations including square root calculations.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@DisplayName("Scimat Math Operations Tests")
class ScimatTest {

    private Scimat scimat;
    private static final double EPSILON = 0.000001;

    @BeforeEach
    void setUp() {
        scimat = new Scimat();
    }

    @Test
    @DisplayName("Test square root of positive numbers")
    void testSqrtPositive() {
        assertEquals(5.0, scimat.sqrt(25), EPSILON,
                "Square root of 25 should be 5");
        assertEquals(10.0, scimat.sqrt(100), EPSILON,
                "Square root of 100 should be 10");
        assertEquals(1.414213562, scimat.sqrt(2), EPSILON,
                "Square root of 2 should be ~1.414");
    }

    @Test
    @DisplayName("Test square root of zero")
    void testSqrtZero() {
        assertEquals(0.0, scimat.sqrt(0), EPSILON,
                "Square root of 0 should be 0");
    }

    @Test
    @DisplayName("Test square root of one")
    void testSqrtOne() {
        assertEquals(1.0, scimat.sqrt(1), EPSILON,
                "Square root of 1 should be 1");
    }

    @Test
    @DisplayName("Test square root of negative numbers returns NaN")
    void testSqrtNegative() {
        double result = scimat.sqrt(-25);
        assertTrue(Double.isNaN(result),
                "Square root of negative number should be NaN");
    }

    @Test
    @DisplayName("Test square root matches Math.sqrt")
    void testSqrtMatchesStandard() {
        double[] testValues = {0, 1, 2, 4, 9, 16, 25, 100, 0.25, 0.5, Math.PI};

        for (double value : testValues) {
            assertEquals(Math.sqrt(value), scimat.sqrt(value), EPSILON,
                    "sqrt(" + value + ") should match Math.sqrt");
        }
    }

    @Test
    @DisplayName("Test square root of Pi")
    void testSqrtPi() {
        double expected = Math.sqrt(Math.PI);
        double actual = scimat.sqrt(Math.PI);
        assertEquals(expected, actual, EPSILON,
                "Square root of Pi should match Math.sqrt(PI)");
    }

    @Test
    @DisplayName("Test square root of very small numbers")
    void testSqrtVerySmall() {
        assertEquals(Math.sqrt(0.0001), scimat.sqrt(0.0001), EPSILON);
        assertEquals(Math.sqrt(1e-10), scimat.sqrt(1e-10), EPSILON);
    }

    @Test
    @DisplayName("Test square root of very large numbers")
    void testSqrtVeryLarge() {
        assertEquals(Math.sqrt(1e10), scimat.sqrt(1e10), EPSILON);
        assertEquals(Math.sqrt(1e20), scimat.sqrt(1e20), EPSILON);
    }
}
