/*
 * Copyright (c) 2024 CJ Remillard
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

/**
 * String utility class providing basic string operations.
 * This class offers methods for string manipulation and analysis.
 * 
 * Features:
 * - String length calculation
 * - Byte-based length determination
 * 
 * @author CJ Remillard
 * @version 1.0
 */
public class Str 
{   
    // FIELDS //
    //
    private static final Malloc MEM = new Malloc();
    
    
    /**
     * Calculates the length of a string in bytes.
     * This method is useful when you need to know the actual byte size
     * of a string, which can differ from the character count for Unicode strings.
     * 
     * @param s The string to measure
     * @return The length of the string in bytes
     * 
     * Example:
     * len("Hello") - Returns 5
     * len("你好") - Returns 6 (3 bytes per character)
     */
    public
    int len(String s)
    {
        byte[] b = s.getBytes();
        return b.length;
    }


    /**
     * Factory method to create a new ConstMemStr instance.
     * This provides a convenient way to create memory-managed strings
     * without directly using the ConstMemStr constructor.
     * 
     * @param s The string to store in memory
     * @return A new ConstMemStr instance containing the specified string
     * @throws NullPointerException if the input string is null
     */
    public 
    ConstMemStr ConstMemStr(String s)
    {
        return new ConstMemStr(s);
    }

    /**
     * A memory-managed string implementation that stores each character in separate memory locations.
     * This class provides a way to work with strings while maintaining explicit memory control.
     * 
     * Features:
     * - Individual character memory allocation
     * - Memory safety checks
     * - Null pointer protection
     * - Automatic memory cleanup
     * 
     * @author CJ Remillard
     * @version 1.0
     */
    public 
    class ConstMemStr
    {
        // FIELDS //
        //
        private long[] POINTERS;  // Array of memory pointers for each character


        /**
         * Constructs a new ConstMemStr with the specified string.
         * Allocates memory for each character in the string.
         * 
         * @param s The string to store in memory
         * @throws NullPointerException if the input string is null
         */
        public ConstMemStr(String s)
        {
            alloc(s);
        }


        /**
         * Returns the length of the stored string.
         * 
         * @return The number of characters in the string
         */
        public 
        int len()
        {
            return POINTERS.length;
        }

        /**
         * Replaces the current string with a new one.
         * Frees the old memory before allocating new memory.
         * 
         * @param s The new string to store
         * @throws NullPointerException if the input string is null
         */
        public
        void set(String s)
        {
            free();
            alloc(s);
        }

        /**
         * Returns the character at the specified index.
         * 
         * @param index The position of the character to retrieve
         * @return The character at the specified position
         * @throws IndexOutOfBoundsException if the index is out of range
         * @throws NullPointerException if the character pointer is null
         */
        public
        char charAt(int index)
        {
            if (index < 0 || index >= POINTERS.length)
            {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            if (POINTERS[index] == 0)
            {
                throw new NullPointerException("Null pointer");
            }
            return (char) MEM.rbyte(POINTERS[index]);
        }

        /**
         * Retrieves the complete string from memory.
         * 
         * @return The stored string
         * @throws NullPointerException if any character pointer is null
         */
        public
        String get()
        {
            if (POINTERS.length == 0)
            {
                throw new NullPointerException("Null pointer");
            }  
            for (int i = 0; i < POINTERS.length; i++)
            {
                if (POINTERS[i] == 0)
                {
                    throw new NullPointerException("Null pointer");
                }
            }
            byte[] b = new byte[POINTERS.length];
            for (int i = 0; i < POINTERS.length; i++)
            {
                b[i] = MEM.rbyte(POINTERS[i]);
            }
            return new String(b);
        }

        /**
         * Frees all allocated memory for the string.
         * This method should be called when the string is no longer needed
         * to prevent memory leaks.
         */
        public
        void free()
        {
            for (int i = 0; i < POINTERS.length; i++)
            {
                MEM.free(POINTERS[i]);
                POINTERS[i] = 0;
            }
        }

        /**
         * Displays the memory locations and corresponding characters of the string.
         * This method is useful for debugging and understanding the memory layout
         * of the ConstMemStr instance.
         * 
         * Output format:
         * @ mem location: [address] - char: [character]
         */
        public 
        void disp()
        {
            for (int i = 0; i < POINTERS.length; i++)
            {
                System.out.println(
                    "@ mem location: " + POINTERS[i] +
                    " - char: " + (char) MEM.rbyte(POINTERS[i])
                );
            }
        }

        /**
         * Allocates memory for each character in the string.
         * 
         * @param s The string to allocate memory for
         * @throws NullPointerException if the input string is null
         */
        private 
        void alloc(String s)
        {
            byte[] b = s.getBytes();
            POINTERS = new long[b.length];
            for (int i = 0; i < b.length; i++)
            {
                POINTERS[i] = MEM.alloc(MEM.BYTE);
                MEM.wbyte(POINTERS[i], b[i]);
            }
        }

        
    } // END OF INNER CLASS //

    /**
     * Factory method to create a new MutMemStr instance.
     * This provides a convenient way to create mutable memory-managed strings
     * without directly using the MutMemStr constructor.
     * 
     * @param s The string to store in memory
     * @return A new MutMemStr instance containing the specified string
     * @throws NullPointerException if the input string is null
     */
    public 
    MutMemStr MutMemStr(String s)
    {
        return new MutMemStr(s);
    }

    /**
     * A mutable memory-managed string implementation that stores each character in separate memory locations.
     * This class extends the functionality of ConstMemStr by adding string mutation capabilities.
     * 
     * Features:
     * - Individual character memory allocation
     * - Memory safety checks
     * - Null pointer protection
     * - Automatic memory cleanup
     * - String concatenation
     * - Character removal
     * - Character insertion
     * - Substring search
     * 
     * @author CJ Remillard
     * @version 1.0
     */
    public 
    class MutMemStr
    {
        // FIELDS //
        //
        private long[] POINTERS;  // Array of memory pointers for each character


        /**
         * Constructs a new MutMemStr with the specified string.
         * Allocates memory for each character in the string.
         * 
         * @param s The string to store in memory
         * @throws NullPointerException if the input string is null
         */
        public MutMemStr(String s)
        {
            alloc(s);
        }


        /**
         * Returns the length of the stored string.
         * 
         * @return The number of characters in the string
         */
        public 
        int len()
        {
            return POINTERS.length;
        }

        /**
         * Replaces the current string with a new one.
         * Frees the old memory before allocating new memory.
         * 
         * @param s The new string to store
         * @throws NullPointerException if the input string is null
         */
        public
        void set(String s)
        {
            free();
            alloc(s);
        }

        /**
         * Returns the character at the specified index.
         * 
         * @param index The position of the character to retrieve
         * @return The character at the specified position
         * @throws IndexOutOfBoundsException if the index is out of range
         * @throws NullPointerException if the character pointer is null
         */
        public
        char charAt(int index)
        {
            if (index < 0 || index >= POINTERS.length)
            {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            if (POINTERS[index] == 0)
            {
                throw new NullPointerException("Null pointer");
            }
            return (char) MEM.rbyte(POINTERS[index]);
        }

        /**
         * Retrieves the complete string from memory.
         * 
         * @return The stored string
         * @throws NullPointerException if any character pointer is null
         */
        public
        String get()
        {
            if (POINTERS.length == 0)
            {
                throw new NullPointerException("Null pointer");
            }  
            for (int i = 0; i < POINTERS.length; i++)
            {
                if (POINTERS[i] == 0)
                {
                    throw new NullPointerException("Null pointer");
                }
            }
            byte[] b = new byte[POINTERS.length];
            for (int i = 0; i < POINTERS.length; i++)
            {
                b[i] = MEM.rbyte(POINTERS[i]);
            }
            return new String(b);
        }

        /**
         * Frees all allocated memory for the string.
         * This method should be called when the string is no longer needed
         * to prevent memory leaks.
         */
        public
        void free()
        {
            for (int i = 0; i < POINTERS.length; i++)
            {
                MEM.free(POINTERS[i]);
                POINTERS[i] = 0;
            }
        }

        /**
         * Displays the memory locations and corresponding characters of the string.
         * This method is useful for debugging and understanding the memory layout
         * of the MutMemStr instance.
         * 
         * Output format:
         * @ mem location: [address] - char: [character]
         */
        public 
        void disp()
        {
            for (int i = 0; i < POINTERS.length; i++)
            {
                System.out.println(
                    "@ mem location: " + POINTERS[i] +
                    " - char: " + (char) MEM.rbyte(POINTERS[i])
                );
            }
        }

        /**
         * Concatenates a new string to the end of the existing string.
         * 
         * @param s The string to append
         * @throws NullPointerException if the input string is null
         */
        public
        void cat(String s)
        {
            if (s == null) {
                throw new NullPointerException("Input string cannot be null");
            }
            
            byte[] newBytes = s.getBytes();
            long[] newPointers = new long[POINTERS.length + newBytes.length];
            
            // Copy existing pointers
            System.arraycopy(POINTERS, 0, newPointers, 0, POINTERS.length);
            
            // Allocate and copy new characters
            for (int i = 0; i < newBytes.length; i++) {
                newPointers[POINTERS.length + i] = MEM.alloc(MEM.BYTE);
                MEM.wbyte(newPointers[POINTERS.length + i], newBytes[i]);
            }
            
            POINTERS = newPointers;
        }

        /**
         * Removes a character at the specified index.
         * 
         * @param i The index of the character to remove
         * @throws IndexOutOfBoundsException if the index is out of range
         */
        public
        void rem(int i)
        {
            if (i < 0 || i >= POINTERS.length) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            
            // Free the memory at the specified index
            MEM.free(POINTERS[i]);
            
            // Shift remaining pointers
            for (int j = i; j < POINTERS.length - 1; j++) {
                POINTERS[j] = POINTERS[j + 1];
            }
            
            // Create new array with one less element
            long[] newPointers = new long[POINTERS.length - 1];
            System.arraycopy(POINTERS, 0, newPointers, 0, POINTERS.length - 1);
            POINTERS = newPointers;
        }

        /**
         * Inserts a character at the specified index.
         * 
         * @param c The character to insert
         * @param i The index where to insert the character
         * @throws IndexOutOfBoundsException if the index is out of range
         */
        public
        void index(char c, int i)
        {
            if (i < 0 || i > POINTERS.length) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            
            // Create new array with one more element
            long[] newPointers = new long[POINTERS.length + 1];
            
            // Copy existing pointers up to insertion point
            System.arraycopy(POINTERS, 0, newPointers, 0, i);
            
            // Allocate and set new character
            newPointers[i] = MEM.alloc(MEM.BYTE);
            MEM.wbyte(newPointers[i], (byte)c);
            
            // Copy remaining pointers
            System.arraycopy(POINTERS, i, newPointers, i + 1, POINTERS.length - i);
            
            POINTERS = newPointers;
        }

        /**
         * Checks if a substring exists within the current string.
         * 
         * @param s The substring to search for
         * @return true if the substring is found, false otherwise
         * @throws NullPointerException if the input string is null
         */
        public
        boolean in(String s)
        {
            if (s == null) {
                throw new NullPointerException("Input string cannot be null");
            }
            
            if (s.isEmpty()) {
                return true;  // Empty string is always a substring
            }
            
            String current = get();
            return current.contains(s);
        }

        /**
         * Allocates memory for each character in the string.
         * 
         * @param s The string to allocate memory for
         * @throws NullPointerException if the input string is null
         */
        private 
        void alloc(String s)
        {
            byte[] b = s.getBytes();
            POINTERS = new long[b.length];
            for (int i = 0; i < b.length; i++)
            {
                POINTERS[i] = MEM.alloc(MEM.BYTE);
                MEM.wbyte(POINTERS[i], b[i]);
            }
        }
    } // END OF MutMemStr CLASS //


} // END OF OUTER CLASS //
