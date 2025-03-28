# CRJLib

A comprehensive Java utility library providing a wide range of functionality for system operations, cryptography, string manipulation, and memory management.

## Overview

CRJLib is a versatile Java library designed to simplify common programming tasks and provide advanced functionality in a clean, easy-to-use interface. The library is built with a focus on performance, security, and ease of use.

## Features

### 1. Terminal Operations (`Terminal.java`)
- Execute system commands
- Capture command output
- Support for Windows command prompt
- Error handling and process management

### 2. String Operations (`Str.java`)
- String length calculation
- Byte-based length determination
- Unicode support

### 3. Cryptography (`Jcrypt.java`)
- AES encryption/decryption
- Password-based key derivation (PBKDF2)
- File encryption/decryption
- Secure key generation
- Automatic file cleanup

### 4. Password Hashing (`PHash.java`)
- PBKDF2 with HMAC-SHA1
- Configurable cost factor
- Secure random salt generation
- Password verification
- Base64 encoding for storage

### 5. String Parsing (`Parse.java`)
- String to Integer conversion
- String to Long conversion
- String to Float conversion
- String to Double conversion
- Error handling with default values

### 6. Memory Management (`Malloc.java`)
- Direct memory allocation and deallocation
- Read/write operations for primitive types
- Memory size constants
- Unsafe memory access (use with caution)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/CRJLib.git
```

2. Add the library to your Java project:
   - Copy the `src/CRJLib` directory to your project's source folder
   - Add the library to your project's build path

## Usage

### Basic Usage

```java
import CRJLib.Std;
import CRJLib.assets.*;

public class Example {
    public static void main(String[] args) {
        // Initialize the main library interface
        Std std = new Std();
        
        // Execute a terminal command
        std.term.write("dir");
        
        // Get string length
        int length = std.str.len("Hello World");
        
        // Parse a string to integer
        int number = std.pars.str_int("123");
        
        // Generate a random number
        int random = std.prand(1, 100);
    }
}
```

### Advanced Usage

#### File Encryption
```java
// Generate encryption key
SecretKey key = std.jcrypt.gen_key("yourPassword");

// Encrypt a file
std.jcrypt.enc_file("input.txt", key, std.str);

// Decrypt a file
std.jcrypt.dec_file("input.txt.enc", key, ".enc");
```

#### Password Hashing
```java
// Hash a password
String hashedPassword = std.phash.hash("userPassword");

// Verify a password
boolean isValid = std.phash.auth("userPassword", hashedPassword);
```

#### Memory Management
```java
// Allocate memory
long ptr = std.mem.alloc(std.mem.INT);

// Write to memory
std.mem.wint(ptr, 42);

// Read from memory
int value = std.mem.rint(ptr);

// Free memory
std.mem.free(ptr);
```

## Security Considerations

- The library uses secure cryptographic algorithms (AES, PBKDF2)
- Password hashing is implemented with salt and configurable cost factor
- Memory management operations should be used with caution
- Always handle exceptions appropriately in production code

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

CJ Remillard

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Disclaimer

The memory management features using `sun.misc.Unsafe` are not recommended for production use as they can lead to crashes and undefined behavior if used incorrectly. Use these features at your own risk.