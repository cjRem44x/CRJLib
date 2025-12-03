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
 * JUnit 5 test suite for Malloc class.
 * Tests direct memory management operations using Unsafe.
 *
 * WARNING: These tests use sun.misc.Unsafe which is dangerous and deprecated.
 * Tests may fail on Java 9+ without --add-opens flag.
 *
 * @author CJ Remillard
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@DisplayName("Malloc Memory Management Tests")
class MallocTest {

    private Malloc malloc;

    @BeforeEach
    void setUp() {
        malloc = new Malloc();
    }

    @Test
    @DisplayName("Test byte allocation and read/write")
    void testByteOperations() {
        long ptr = malloc.alloc(Malloc.BYTE);
        assertNotEquals(0, ptr, "Allocation should return non-zero address");

        byte testValue = 42;
        malloc.wbyte(ptr, testValue);
        assertEquals(testValue, malloc.rbyte(ptr),
                "Read byte should match written byte");

        malloc.free(ptr);
    }

    @Test
    @DisplayName("Test short allocation and read/write")
    void testShortOperations() {
        long ptr = malloc.alloc(Malloc.SHORT);
        assertNotEquals(0, ptr, "Allocation should return non-zero address");

        short testValue = 12345;
        malloc.wshort(ptr, testValue);
        assertEquals(testValue, malloc.rshort(ptr),
                "Read short should match written short");

        malloc.free(ptr);
    }

    @Test
    @DisplayName("Test int allocation and read/write")
    void testIntOperations() {
        long ptr = malloc.alloc(Malloc.INT);
        assertNotEquals(0, ptr, "Allocation should return non-zero address");

        int testValue = 42;
        malloc.wint(ptr, testValue);
        assertEquals(testValue, malloc.rint(ptr),
                "Read int should match written int");

        malloc.free(ptr);
    }

    @Test
    @DisplayName("Test long allocation and read/write")
    void testLongOperations() {
        long ptr = malloc.alloc(Malloc.LONG);
        assertNotEquals(0, ptr, "Allocation should return non-zero address");

        long testValue = 9876543210L;
        malloc.wlong(ptr, testValue);
        assertEquals(testValue, malloc.rlong(ptr),
                "Read long should match written long");

        malloc.free(ptr);
    }

    @Test
    @DisplayName("Test float allocation and read/write")
    void testFloatOperations() {
        long ptr = malloc.alloc(Malloc.FLOAT);
        assertNotEquals(0, ptr, "Allocation should return non-zero address");

        float testValue = 3.14159f;
        malloc.wfloat(ptr, testValue);
        assertEquals(testValue, malloc.rfloat(ptr), 0.00001,
                "Read float should match written float");

        malloc.free(ptr);
    }

    @Test
    @DisplayName("Test double allocation and read/write")
    void testDoubleOperations() {
        long ptr = malloc.alloc(Malloc.DOUBLE);
        assertNotEquals(0, ptr, "Allocation should return non-zero address");

        double testValue = 3.141592653;
        malloc.wdouble(ptr, testValue);
        assertEquals(testValue, malloc.rdouble(ptr), 0.000000001,
                "Read double should match written double");

        malloc.free(ptr);
    }

    @Test
    @DisplayName("Test multiple allocations")
    void testMultipleAllocations() {
        long ptr1 = malloc.alloc(Malloc.INT);
        long ptr2 = malloc.alloc(Malloc.INT);
        long ptr3 = malloc.alloc(Malloc.INT);

        assertNotEquals(0, ptr1);
        assertNotEquals(0, ptr2);
        assertNotEquals(0, ptr3);

        // Write different values
        malloc.wint(ptr1, 10);
        malloc.wint(ptr2, 20);
        malloc.wint(ptr3, 30);

        // Verify values are independent
        assertEquals(10, malloc.rint(ptr1));
        assertEquals(20, malloc.rint(ptr2));
        assertEquals(30, malloc.rint(ptr3));

        // Free all
        malloc.free(ptr1);
        malloc.free(ptr2);
        malloc.free(ptr3);
    }

    @Test
    @DisplayName("Test allocation with zero size")
    void testZeroAllocation() {
        long ptr = malloc.alloc(0);
        assertEquals(0, ptr, "Allocating zero bytes should return 0");
    }

    @Test
    @DisplayName("Test allocation with negative size")
    void testNegativeAllocation() {
        long ptr = malloc.alloc(-1);
        assertEquals(0, ptr, "Allocating negative bytes should return 0");
    }

    @Test
    @DisplayName("Test size constants")
    void testSizeConstants() {
        assertEquals(1, Malloc.BYTE, "BYTE should be 1");
        assertEquals(2, Malloc.SHORT, "SHORT should be 2");
        assertEquals(4, Malloc.INT, "INT should be 4");
        assertEquals(8, Malloc.LONG, "LONG should be 8");
        assertEquals(4, Malloc.FLOAT, "FLOAT should be 4");
        assertEquals(8, Malloc.DOUBLE, "DOUBLE should be 8");
    }
}
