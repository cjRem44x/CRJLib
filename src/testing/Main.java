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

package testing;

import CRJLib.Std;
import CRJLib.assets.*;
import java.io.File;
import javax.crypto.SecretKey;

/**
 * Main test class for CRJLib library.
 * This class provides comprehensive testing of all major components
 * of the CRJLib library including:
 * - Terminal operations
 * - String manipulation
 * - Random number generation
 * - Parsing operations
 * - Encryption/Decryption
 * - Password hashing
 * - Memory management
 * - URL handling
 * - File operations
 * 
 * Each test section is clearly labeled and provides feedback on
 * the success or failure of the operations being tested.
 * 
 * @author CJ Remillard
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        // Initialize the main library interface
        Std std = new Std();
        
        // Test Terminal functionality
        std.coutln("\n=== Testing Terminal ===");
        std.coutln("Testing dir command:");
        std.term.write("dir");
        
        std.coutln("\nTesting echo command:");
        std.term.write("echo Hello from CRJLib!");
        
        std.coutln("\nTesting command output capture:");
        String dirOutput = std.term.executeAndGetOutput("dir");
        std.coutln("Directory listing length: " + dirOutput.length());
        
        // Test String functionality
        std.coutln("\n=== Testing String Operations ===");
        String testStr = "Hello World";
        std.coutln("String length: " + std.str.len(testStr));
        
        // Test Random number generation
        std.coutln("\n=== Testing Random Numbers ===");
        std.coutln("Pseudo-random number (1-100): " + std.prand(1, 100));
        std.coutln("Secure-random number (1-100): " + std.srand(1, 100));
        
        // Test Parse functionality
        std.coutln("\n=== Testing Parse Operations ===");
        std.coutln("String to int: " + std.pars.str_int("123"));
        std.coutln("String to long: " + std.pars.str_long("123456789"));
        std.coutln("String to float: " + std.pars.str_float("123.45"));
        std.coutln("String to double: " + std.pars.str_double("123.456789"));
        
        // Test Jcrypt functionality
        std.coutln("\n=== Testing Jcrypt Operations ===");
        try 
        {
            // Create a test file for encryption
            File testFile = new File("test.txt");
            java.io.FileWriter writer = new java.io.FileWriter(testFile);
            writer.write("This is a test file for encryption");
            writer.close();
            
            // Generate encryption key and encrypt the file
            SecretKey key = std.jcrypt.gen_key("testPassword123");
            std.jcrypt.enc_file("test.txt", key, std.str);
            
            // Decrypt the file
            std.jcrypt.dec_file("test.txt.enc", key, ".enc");
            
            // Clean up test files
            new File("test.txt.enc").delete();
            std.coutln("Encryption/Decryption test completed successfully");
        } catch (Exception e) 
        {
            std.coutln("Error in encryption test: " + e.getMessage());
        }
        
        // Test PHash functionality
        std.coutln("\n=== Testing PHash Operations ===");
        String password = "testPassword123";
        String hashedPassword = std.phash.hash(password);
        std.coutln("Original password: " + password);
        std.coutln("Hashed password: " + hashedPassword);
        std.coutln("Password verification: " + std.phash.auth(password, hashedPassword));
        
        // Test Malloc functionality
        std.coutln("\n=== Testing Malloc Operations ===");
        // Allocate memory for different data types
        long intPtr = std.mem.alloc(std.mem.INT);
        long doublePtr = std.mem.alloc(std.mem.DOUBLE);
        
        // Write test values to allocated memory
        std.mem.wint(intPtr, 42);
        std.mem.wdouble(doublePtr, 3.14159);
        
        // Read and verify values from memory
        std.coutln("Allocated int value: " + std.mem.rint(intPtr));
        std.coutln("Allocated double value: " + std.mem.rdouble(doublePtr));
        
        // Free allocated memory
        std.mem.free(intPtr);
        std.mem.free(doublePtr);
        
        // Test URL handling
        std.coutln("\n=== Testing URL Handling ===");
        boolean urlSuccess = std.open_url_link("https://www.google.com");
        std.coutln("URL opening " + (urlSuccess ? "succeeded" : "failed"));
        
        // Test File operations
        std.coutln("\n=== Testing File Operations ===");
        File testFile = new File("test.txt");
        try {
            testFile.createNewFile();
            boolean fileSuccess = std.open_file_on_desktop(testFile);
            std.coutln("File opening " + (fileSuccess ? "succeeded" : "failed"));
            testFile.delete();
        } catch (Exception e) {
            std.coutln("Error testing file operations: " + e.getMessage());
        }
        
        std.coutln("\n=== All Tests Completed ===");


        // MEM ALLOC TESTS //
        long p_pi = std.mem.alloc(std.mem.DOUBLE);
        std.mem.wdouble(p_pi, 3.141592653);


        // SCIMAT TESTS //
        std.coutln("\n=== Testing Math Operations ===");
        std.coutln("Square root of 25: " + std.sim.sqrt(25));
        std.coutln("Square root of -25: " + std.sim.sqrt(-25));
        std.coutln("Square root of 0: " + std.sim.sqrt(0));
        std.coutln("Square root of 1: " + std.sim.sqrt(1));
        
        std.log("Square root of Pi ("+std.mem.rdouble(p_pi)+"): " + std.sim.sqrt(std.mem.rdouble(p_pi)));
        std.mem.free(p_pi);


        // STR TESTS //
        std.coutln("\n=== Testing String Operations ===");
        final var s = std.str.ConstMemStr(hashedPassword);
        std.coutln("String length: " + s.len());
        s.disp();
        s.set("Hello, World!");
        std.coutln("String length: " + s.len());
        s.disp();
        s.free();

        final var m = std.str.MutMemStr("Hello, World!");
        std.coutln("String length: " + m.len());
        std.log("String: " + m.get());
        m.cat("Hello, World!");
        std.log("String: " + m.get());
        m.rem(0);
        std.log("String: " + m.get());
        m.index('H', 0);
        std.log("String: " + m.get());
        m.free();

    }
}
