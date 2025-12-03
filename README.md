# CRJLib

[![Maven CI/CD](https://github.com/cjremillard/CRJLib/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/cjremillard/CRJLib/actions/workflows/maven-ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://openjdk.org/)

A comprehensive Java utility library providing a wide range of functionality for system operations, cryptography, string manipulation, and memory management.

## Overview

CRJLib is a versatile Java library designed to simplify common programming tasks and provide advanced functionality in a clean, easy-to-use interface. The library is built with Maven, includes comprehensive JUnit 5 tests, and features automated CI/CD via GitHub Actions.

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

### Prerequisites
- Java 11 or later
- Maven 3.6+ (for building from source)

### Option 1: Using Maven (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/cjremillard/CRJLib.git
cd CRJLib
```

2. Build and install to local Maven repository:
```bash
mvn clean install
```

3. Add the dependency to your project's `pom.xml`:
```xml
<dependency>
    <groupId>com.cjremillard</groupId>
    <artifactId>crjlib</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Option 2: Using Pre-built JAR

1. Download the latest JAR from [Releases](https://github.com/cjremillard/CRJLib/releases)
2. Add the JAR to your project's classpath

### Option 3: Build from Source

```bash
# Clone the repository
git clone https://github.com/cjremillard/CRJLib.git
cd CRJLib

# Build the project
mvn clean package

# The JAR will be in target/crjlib-1.0.0.jar
```

### Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report

# Generate Javadoc
mvn javadoc:javadoc
```

### Java 9+ Compatibility Note

If using the Malloc class (not recommended) on Java 9 or later, you need to add JVM arguments:

```bash
java --add-opens java.base/sun.misc=ALL-UNNAMED -jar your-app.jar
```

Or in Maven Surefire plugin configuration:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>--add-opens java.base/sun.misc=ALL-UNNAMED</argLine>
    </configuration>
</plugin>
```

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

**Basic Encryption (Fixed Salt - For Testing Only):**
```java
// Generate encryption key (uses fixed salt - NOT secure for production!)
SecretKey key = std.jcrypt.gen_key("yourPassword");

// Encrypt a file
std.jcrypt.enc_file("input.txt", key, ".enc");

// Decrypt a file
std.jcrypt.dec_file("input.txt.enc", key, ".enc");
```

**Secure Encryption (Custom Salt - Recommended for Production):**
```java
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;

// Generate a unique random salt
byte[] salt = std.jcrypt.generateRandomSalt();

// Save the salt (e.g., prepend to encrypted file or store separately)
Files.write(Paths.get("input.txt.salt"), salt);

// Generate encryption key with custom salt
SecretKey key = std.jcrypt.gen_key("yourPassword", salt);

// Encrypt a file
std.jcrypt.enc_file("input.txt", key, ".enc");

// Later, to decrypt:
// Load the salt
byte[] savedSalt = Files.readAllBytes(Paths.get("input.txt.salt"));

// Generate the same key using saved salt
SecretKey decryptKey = std.jcrypt.gen_key("yourPassword", savedSalt);

// Decrypt the file
std.jcrypt.dec_file("input.txt.enc", decryptKey, ".enc");
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

### Cryptography (Jcrypt)

**IMPORTANT:** The default `gen_key(String)` method uses a **fixed salt** for backwards compatibility. This is **NOT secure for production use** as it allows rainbow table attacks.

**For production applications:**
- Use `gen_key(String, byte[])` with unique random salts
- Call `generateRandomSalt()` to create cryptographically secure salts
- Store salts alongside encrypted files (prepend to file or use separate metadata file)
- Never reuse salts across different files

**Secure practices:**
- The library uses AES-256 encryption
- Key derivation uses PBKDF2WithHmacSHA256 (65,536 iterations)
- Always use strong passwords (12+ characters, mixed case, numbers, symbols)

### Password Hashing (PHash)

**SECURE by default:**
- Automatically generates unique random salt per password
- Uses PBKDF2WithHmacSHA1 with 10,000 iterations
- Salts are stored with the hash (format: `salt:hash`)
- No configuration needed - just use `hash()` and `auth()`

### Memory Management (Malloc)

**EXTREMELY DANGEROUS - NOT RECOMMENDED:**
- Uses deprecated `sun.misc.Unsafe` API
- Can crash the JVM with segmentation faults
- No bounds checking, type safety, or garbage collection
- May not work on Java 17+
- **Only use for educational purposes**

**Safer alternatives:**
- Use `java.nio.ByteBuffer.allocateDirect()` for off-heap memory
- Use the Foreign Function & Memory API (Java 19+)
- Use regular Java objects and let the JVM manage memory

### General Security

- Always handle exceptions appropriately in production code
- Validate all user input before processing
- Use secure random number generation (`srand()` instead of `prand()` for security-critical operations)
- Keep your Java runtime updated
- Review the [CHANGELOG.md](CHANGELOG.md) for security updates

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Project Structure

```
CRJLib/
├── src/
│   ├── main/java/
│   │   └── CRJLib/
│   │       ├── Std.java           # Main library interface
│   │       └── assets/
│   │           ├── Terminal.java   # System command execution
│   │           ├── Str.java        # String operations
│   │           ├── Parse.java      # String parsing
│   │           ├── Jcrypt.java     # File encryption
│   │           ├── PHash.java      # Password hashing
│   │           ├── Malloc.java     # Memory management (deprecated)
│   │           ├── Scimat.java     # Math operations
│   │           ├── FIO.java        # File I/O
│   │           └── CJRWindow.java  # Basic Swing wrapper
│   └── test/java/
│       └── CRJLib/
│           ├── StdTest.java        # Std tests
│           └── assets/
│               ├── ParseTest.java
│               ├── JcryptTest.java
│               ├── PHashTest.java
│               ├── MallocTest.java
│               ├── ScimatTest.java
│               └── FIOTest.java
├── .github/
│   └── workflows/
│       └── maven-ci.yml            # GitHub Actions CI/CD
├── pom.xml                         # Maven configuration
├── LICENSE                         # MIT License
├── README.md                       # This file
└── CHANGELOG.md                    # Version history
```

## Testing

CRJLib includes a comprehensive test suite built with JUnit 5:

- **7 test classes** covering all major components
- **60+ test cases** ensuring reliability
- **Automated CI/CD** via GitHub Actions
- **Multi-platform testing** (Ubuntu, Windows, macOS)
- **Multi-version testing** (Java 11, 17, 21)

Run tests locally:
```bash
mvn test
```

View test results:
```bash
mvn surefire-report:report
# Open target/site/surefire-report.html
```

## Documentation

- **API Documentation**: Generate with `mvn javadoc:javadoc`
- **Changelog**: See [CHANGELOG.md](CHANGELOG.md) for version history
- **Examples**: See test files in `src/test/java/` for usage examples

## Author

**CJ Remillard**
- GitHub: [@cjremillard](https://github.com/cjremillard)

## Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Make your changes** and add tests
4. **Run the test suite**: `mvn test`
5. **Commit your changes**: `git commit -m 'Add amazing feature'`
6. **Push to your branch**: `git push origin feature/amazing-feature`
7. **Open a Pull Request**

### Contribution Guidelines

- Follow existing code style and conventions
- Add JUnit tests for new features
- Update documentation (Javadoc and README)
- Ensure all tests pass before submitting PR
- Update CHANGELOG.md with your changes

### Reporting Issues

Found a bug or have a suggestion? Please [open an issue](https://github.com/cjremillard/CRJLib/issues) with:
- Clear description of the problem
- Steps to reproduce (for bugs)
- Expected vs actual behavior
- Java version and OS information

## Disclaimer

The memory management features using `sun.misc.Unsafe` are not recommended for production use as they can lead to crashes and undefined behavior if used incorrectly. Use these features at your own risk.