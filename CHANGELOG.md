# Changelog

All notable changes to CRJLib will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - 2025-XX-XX

### Added - New Assets
- **Http** - HTTP client utility for web requests
  - GET, POST, PUT, DELETE methods
  - Custom headers support
  - Configurable timeouts
  - Response handling with status codes
  - URL encoding/decoding utilities
  - Query string builder
- **Validator** - Comprehensive input validation
  - String validation (empty, blank, length, patterns)
  - Numeric range validation
  - Email format validation
  - URL format validation
  - IPv4 address validation
  - Phone number validation
  - Credit card validation (Luhn algorithm)
  - Date format validation
  - Password strength checking
- **Codec** - Encoding and decoding utilities
  - Base64 encoding/decoding (standard and URL-safe)
  - Hexadecimal encoding/decoding
  - Binary string conversion
  - Byte array utilities
  - Integer/Long to bytes conversion

### Enhanced - Existing Assets
- **Scimat** (v2.0) - Massively expanded mathematical operations
  - Exponential and logarithmic functions (exp, ln, log10, custom base)
  - Trigonometric functions (sin, cos, tan, asin, acos, atan)
  - Angle conversions (degrees ↔ radians)
  - Rounding operations (floor, ceiling, round)
  - Number theory (factorial, GCD, LCM, isPrime)
  - Comparison utilities (min, max, clamp)
  - Utility functions (sign, modulo)
  - Distance calculations (2D and 3D Euclidean distance)
  - Better naming: `squareRoot()`, `absoluteValue()`, `power()`
  - Backward compatibility aliases for old methods

- **Parse** (v2.0) - Enhanced parsing with better error handling
  - New methods with better naming: `toInt()`, `toLong()`, `toFloat()`, `toDouble()`
  - Optional-based parsing: `tryParseInt()`, `tryParseLong()`, etc.
  - Default value support for all parse methods
  - Boolean parsing with flexible input (yes/no, 1/0, on/off, true/false)
  - Hexadecimal parsing with `hexToInt()`
  - Binary parsing with `binaryToInt()`
  - Byte and short parsing
  - Array parsing: `toIntArray()`, `toDoubleArray()`
  - Custom radix parsing with `parseWithRadix()`
  - Old methods (`str_int`, etc.) deprecated but maintained for compatibility

### Changed
- Updated **Std.java** to include new utility instances:
  - `std.http` - HTTP client
  - `std.valid` - Validator
  - `std.codec` - Codec utilities
- Better error handling throughout Parse class (no more stack trace printing)
- Improved documentation for all enhanced classes

### Removed
- **CJRWindow.java** - Removed unused window component
- **WinTest.java** - Removed corresponding test file

### Breaking Changes
- Scimat: `sqrt(-x)` now returns `NaN` instead of `0.0` (proper IEEE behavior)
- Parse methods no longer print stack traces to stderr

### Migration Guide
**For Scimat users:**
- Old: `sim.sqrt(value)` → New: `sim.squareRoot(value)` (or keep using deprecated `sqrt()`)
- Old: `sim.abs(value)` → New: `sim.absoluteValue(value)` (or keep using deprecated `abs()`)

**For Parse users:**
- Old: `pars.str_int("123")` → New: `pars.toInt("123", 0)` or `pars.tryParseInt("123")`
- Use Optional-based methods for better error handling without null checks

### Compatibility
- All old method names maintained with `@Deprecated` annotations
- Existing code will continue to work with deprecation warnings
- Recommended to migrate to new method names for better readability

## [1.0.0] - 2025-01-XX

### Added
- Initial release of CRJLib
- Maven build system with proper POM configuration
- Comprehensive JUnit 5 test suite covering all components
- GitHub Actions CI/CD pipeline for automated testing
- Javadoc generation configured in Maven
- Security enhancements:
  - Added `gen_key(String, byte[])` overload in Jcrypt for custom salt support
  - Added `generateRandomSalt()` method for secure salt generation
- Strong deprecation warnings for Malloc class (Unsafe usage)
- MIT License copyright headers on all source files

### Changed
- Restructured project to follow Maven standard directory layout
  - Moved source code to `src/main/java`
  - Moved test code to `src/test/java`
- Enhanced Jcrypt security documentation with prominent warnings about fixed salt
- Updated Malloc class with comprehensive danger warnings in Javadoc
- Fixed bug in test file (Main.java:96) with incorrect parameter type

### Removed
- Removed all Scala files (Std.scala, Jcrypt.scala, PHash.scala)
- Project is now pure Java for better maintainability

### Security
- **SECURITY WARNING**: Jcrypt's default `gen_key(String)` method uses a fixed salt for backwards compatibility
- Users should migrate to `gen_key(String, byte[])` with unique salts for production use
- Malloc class flagged as deprecated and dangerous due to Unsafe API usage

### Components
This release includes the following components:

#### CRJLib.Std
- Main library interface
- Terminal operations (Windows cmd integration)
- Random number generation (pseudo-random and secure)
- URL and file opening utilities
- Console output methods

#### CRJLib.assets.Terminal
- Command execution interface
- Output capture functionality

#### CRJLib.assets.Str
- String length calculation
- Memory-based string operations (ConstMemStr, MutMemStr)

#### CRJLib.assets.Parse
- String to primitive type conversions (int, long, float, double)

#### CRJLib.assets.Jcrypt
- AES file encryption/decryption
- PBKDF2 key derivation
- Support for custom salts

#### CRJLib.assets.PHash
- Password hashing with PBKDF2
- Automatic unique salt generation per password
- Password verification/authentication

#### CRJLib.assets.Malloc
- Direct memory management using sun.misc.Unsafe
- **DEPRECATED**: Not recommended for production use
- Supports byte, short, int, long, float, double operations

#### CRJLib.assets.Scimat
- Mathematical operations (square root implementation)

#### CRJLib.assets.FIO
- File read/write/delete operations
- Simple file I/O interface

#### CRJLib.assets.CJRWindow
- Basic Swing window wrapper
- FPS control for game loops

### Testing
- 7 comprehensive JUnit 5 test suites with 60+ test cases
- Tests for all major components
- Automated testing via GitHub Actions on multiple platforms (Ubuntu, Windows, macOS)
- Multi-version Java testing (Java 11, 17, 21)

### Documentation
- Complete Javadoc for all public APIs
- Professional README with installation and usage instructions
- Security warnings prominently displayed
- MIT License included

### Known Issues
- Malloc class requires `--add-opens java.base/sun.misc=ALL-UNNAMED` on Java 9+
- Malloc may not work on Java 17+ and is not recommended for use
- Terminal class is Windows-specific (uses cmd.exe)
- Default Jcrypt encryption uses fixed salt (see Security section)

### Requirements
- Java 11 or later
- Maven 3.6+ for building
- For Malloc: Java 8-16 recommended, Java 17+ discouraged

---

## [Unreleased]

### Planned for Future Releases
- Cross-platform Terminal support (Linux/macOS)
- Improved Parse error handling (distinguish between 0 and parse failure)
- Additional cryptographic algorithms in Jcrypt
- Performance benchmarks

---

[1.0.0]: https://github.com/cjremillard/CRJLib/releases/tag/v1.0.0
