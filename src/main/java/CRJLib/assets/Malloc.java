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

import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * Malloc class provides direct memory management functionality using sun.misc.Unsafe.
 * This class allows for low-level memory operations including allocation, deallocation,
 * and direct memory access for various primitive types.
 *
 * <p><strong>&#x26A0; DANGER - USE AT YOUR OWN RISK &#x26A0;</strong>
 *
 * <p><strong>THIS CLASS IS EXTREMELY DANGEROUS AND DEPRECATED</strong>
 *
 * <p>This class uses {@code sun.misc.Unsafe}, which is:
 * <ul>
 * <li><strong>NOT part of the public Java API</strong> - May be removed in future Java versions</li>
 * <li><strong>CAN CRASH THE JVM</strong> - Invalid memory access causes immediate crashes</li>
 * <li><strong>BYPASSES ALL JAVA SAFETY</strong> - No bounds checking, no type safety, no garbage collection</li>
 * <li><strong>NON-PORTABLE</strong> - Behavior varies across JVM implementations and platforms</li>
 * <li><strong>REMOVED IN NEWER JAVA</strong> - Java 9+ restricts access, Java 17+ requires --add-opens</li>
 * <li><strong>CAUSES MEMORY LEAKS</strong> - Allocated memory is never freed by GC</li>
 * <li><strong>UNDEFINED BEHAVIOR</strong> - Can corrupt JVM internal state</li>
 * </ul>
 *
 * <p><strong>Potential Problems:</strong>
 * <ul>
 * <li>Segmentation faults and JVM crashes</li>
 * <li>Memory corruption and data races</li>
 * <li>Security vulnerabilities</li>
 * <li>Memory leaks if {@link #free(long)} is not called</li>
 * <li>Use-after-free bugs</li>
 * <li>Buffer overflows</li>
 * </ul>
 *
 * <p><strong>Why This Class Exists:</strong><br>
 * This is an educational/experimental class to demonstrate low-level memory operations.
 * It should <strong>NEVER</strong> be used in production code.
 *
 * <p><strong>Better Alternatives:</strong>
 * <ul>
 * <li>{@link java.nio.ByteBuffer#allocateDirect(int)} - For off-heap memory</li>
 * <li>{@link java.lang.foreign} API (Java 19+) - Modern replacement for Unsafe</li>
 * <li>JNI with native C/C++ - If you truly need low-level control</li>
 * <li>Regular Java objects - Let the JVM manage memory safely</li>
 * </ul>
 *
 * <p><strong>If You Still Choose To Use This:</strong>
 * <ul>
 * <li>ALWAYS call {@link #free(long)} for every {@link #alloc(int)}</li>
 * <li>NEVER use freed memory addresses</li>
 * <li>NEVER access memory beyond allocated bounds</li>
 * <li>Test extensively in a sandbox environment</li>
 * <li>Be prepared for production crashes</li>
 * </ul>
 *
 * <p><strong>Java Version Compatibility:</strong><br>
 * Java 8: Works<br>
 * Java 9-16: Requires {@code --add-opens java.base/sun.misc=ALL-UNNAMED}<br>
 * Java 17+: May not work at all, strongly discouraged
 *
 * @author CJ Remillard
 * @version 1.0
 * @deprecated This class uses the dangerous and deprecated {@code sun.misc.Unsafe} API.
 *             Use {@link java.nio.ByteBuffer#allocateDirect(int)} or the Foreign Function
 *             &amp; Memory API (Java 19+) instead. This class may stop working in future Java versions.
 */
@Deprecated(since = "1.0", forRemoval = false)
@SuppressWarnings({"removal", "sunapi", "restricted"})
public class Malloc 
{ 
    // FIELDS //
    //
    /** Size constants for primitive types in bytes */
    public static final  int   BYTE   = 1,  // 1 byte
                               SHORT  = 2,  // 2 bytes
                               INT    = 4,  // 4 bytes
                               LONG   = 8,  // 8 bytes
                               FLOAT  = 4,  // 4 bytes
                               DOUBLE = 8;  // 8 bytes
    /** Unsafe instance for direct memory operations */
    private final Unsafe UNSAFE = get_unsafe();


    /**
     * Allocate memory of specified size.
     * 
     * @param size Size in bytes to allocate
     * @return Memory address of allocated block, or 0 if allocation fails
     */
    public 
    long alloc(int size)
    {
        if (size > 0 )
        {
            long p = UNSAFE.allocateMemory(size);
            return p;
        }
        return 0;
    }


    /**
     * Free allocated memory.
     * 
     * @param p Memory address to free
     */
    public 
    void free(long p)
    {
        UNSAFE.freeMemory(p);
    }


    /**
     * Read a byte from memory.
     * 
     * @param p Memory address to read from
     * @return Byte value at the specified address
     */
    public 
    byte rbyte(long p)
    {
        return UNSAFE.getByte(p);
    }


    /**
     * Write a byte to memory.
     * 
     * @param p Memory address to write to
     * @param b Byte value to write
     */
    public 
    void wbyte(long p, byte b)
    {
        UNSAFE.putByte(p, (byte)b);
    }


    /**
     * Read a short from memory.
     * 
     * @param p Memory address to read from
     * @return Short value at the specified address
     */
    public 
    short rshort(long p)
    {
        return UNSAFE.getShort(p);
    }


    /**
     * Write a short to memory.
     * 
     * @param p Memory address to write to
     * @param s Short value to write
     */
    public 
    void wshort(long p, short s)
    {
        UNSAFE.putShort(p, (short)s);
    }


    /**
     * Read an integer from memory.
     * 
     * @param p Memory address to read from
     * @return Integer value at the specified address
     */
    public 
    int rint(long p)
    {
        return UNSAFE.getInt(p);
    }


    /**
     * Write an integer to memory.
     * 
     * @param p Memory address to write to
     * @param i Integer value to write
     */
    public 
    void wint(long p, int i)
    {
        UNSAFE.putInt(p, (int)i);
    }


    /**
     * Read a long from memory.
     * 
     * @param p Memory address to read from
     * @return Long value at the specified address
     */
    public 
    long rlong(long p)
    {
        return UNSAFE.getLong(p);
    }


    /**
     * Write a long to memory.
     * 
     * @param p Memory address to write to
     * @param l Long value to write
     */
    public 
    void wlong(long p, long l)
    {
        UNSAFE.putLong(p, (long)l);
    }


    /**
     * Read a float from memory.
     * 
     * @param p Memory address to read from
     * @return Float value at the specified address
     */
    public
    float rfloat(long p) 
    {
        return UNSAFE.getFloat(p);
    }


    /**
     * Write a float to memory.
     * 
     * @param p Memory address to write to
     * @param f Float value to write
     */
    public 
    void wfloat(long p, float f) 
    {
        UNSAFE.putFloat(p, (float)f);
    }


    /**
     * Read a double from memory.
     * 
     * @param p Memory address to read from
     * @return Double value at the specified address
     */
    public
    double rdouble(long p) 
    {
        return UNSAFE.getDouble(p);
    }


    /**
     * Write a double to memory.
     * 
     * @param p Memory address to write to
     * @param d Double value to write
     */
    public 
    void wdouble(long p, double d) 
    {
        UNSAFE.putDouble(p, (double)d);
    }


    /**
     * Get the Unsafe instance for direct memory operations.
     * This method uses reflection to access the Unsafe class.
     * 
     * @return Unsafe instance, or null if access fails
     */
    private static 
    Unsafe get_unsafe() 
    {
        try 
        {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) 
        {
            e.printStackTrace();
        } catch (SecurityException e) 
        {            
            e.printStackTrace();
        } catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
        } catch (IllegalAccessException e) 
        {
            e.printStackTrace();
        }

        return null;
    }  


}