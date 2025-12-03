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

import java.util.Optional;

/**
 * Parse utility class for converting strings to various data types.
 * This class provides safe parsing methods that handle potential errors gracefully.
 *
 * <p>Features:
 * <ul>
 * <li>Primitive type parsing: int, long, float, double, byte, short</li>
 * <li>Boolean parsing with flexible input</li>
 * <li>Hex and binary string parsing</li>
 * <li>Optional-based parsing for proper error handling</li>
 * <li>Array parsing (comma-separated values)</li>
 * <li>Default value support</li>
 * </ul>
 *
 * @author CJ Remillard
 * @version 2.0
 */
public class Parse
{
    // ==================== INTEGER PARSING ====================

    /**
     * Parses a string to an integer.
     * Returns 0 if the conversion fails.
     *
     * @param input String to convert
     * @return Integer value from string, or 0 if conversion fails
     * @deprecated Use {@link #toInt(String, int)} or {@link #tryParseInt(String)} for better error handling
     */
    @Deprecated
    public int str_int(String input) {
        return toInt(input, 0);
    }

    /**
     * Parses a string to an integer with a default value.
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed integer or defaultValue if parsing fails
     */
    public int toInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Attempts to parse a string to an integer.
     *
     * @param input String to convert
     * @return Optional containing the parsed value, or empty if parsing fails
     */
    public Optional<Integer> tryParseInt(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }

    // ==================== LONG PARSING ====================

    /**
     * Parses a string to a long value.
     * Returns 0 if the conversion fails.
     *
     * @param input String to convert
     * @return Long value from string, or 0 if conversion fails
     * @deprecated Use {@link #toLong(String, long)} or {@link #tryParseLong(String)} for better error handling
     */
    @Deprecated
    public long str_long(String input) {
        return toLong(input, 0L);
    }

    /**
     * Parses a string to a long with a default value.
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed long or defaultValue if parsing fails
     */
    public long toLong(String input, long defaultValue) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Attempts to parse a string to a long.
     *
     * @param input String to convert
     * @return Optional containing the parsed value, or empty if parsing fails
     */
    public Optional<Long> tryParseLong(String input) {
        try {
            return Optional.of(Long.parseLong(input));
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }

    // ==================== FLOAT PARSING ====================

    /**
     * Parses a string to a float value.
     * Returns 0.0 if the conversion fails.
     *
     * @param input String to convert
     * @return Float value from string, or 0.0 if conversion fails
     * @deprecated Use {@link #toFloat(String, float)} or {@link #tryParseFloat(String)} for better error handling
     */
    @Deprecated
    public float str_float(String input) {
        return toFloat(input, 0.0f);
    }

    /**
     * Parses a string to a float with a default value.
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed float or defaultValue if parsing fails
     */
    public float toFloat(String input, float defaultValue) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Attempts to parse a string to a float.
     *
     * @param input String to convert
     * @return Optional containing the parsed value, or empty if parsing fails
     */
    public Optional<Float> tryParseFloat(String input) {
        try {
            return Optional.of(Float.parseFloat(input));
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }

    // ==================== DOUBLE PARSING ====================

    /**
     * Parses a string to a double value.
     * Returns 0.0 if the conversion fails.
     *
     * @param input String to convert
     * @return Double value from string, or 0.0 if conversion fails
     * @deprecated Use {@link #toDouble(String, double)} or {@link #tryParseDouble(String)} for better error handling
     */
    @Deprecated
    public double str_double(String input) {
        return toDouble(input, 0.0);
    }

    /**
     * Parses a string to a double with a default value.
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed double or defaultValue if parsing fails
     */
    public double toDouble(String input, double defaultValue) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Attempts to parse a string to a double.
     *
     * @param input String to convert
     * @return Optional containing the parsed value, or empty if parsing fails
     */
    public Optional<Double> tryParseDouble(String input) {
        try {
            return Optional.of(Double.parseDouble(input));
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }

    // ==================== BYTE & SHORT PARSING ====================

    /**
     * Parses a string to a byte with a default value.
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed byte or defaultValue if parsing fails
     */
    public byte toByte(String input, byte defaultValue) {
        try {
            return Byte.parseByte(input);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Parses a string to a short with a default value.
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed short or defaultValue if parsing fails
     */
    public short toShort(String input, short defaultValue) {
        try {
            return Short.parseShort(input);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    // ==================== BOOLEAN PARSING ====================

    /**
     * Parses a string to a boolean.
     * Accepts: "true", "false", "yes", "no", "1", "0", "on", "off" (case-insensitive).
     *
     * @param input String to convert
     * @param defaultValue Value to return if parsing fails
     * @return Parsed boolean or defaultValue if parsing fails
     */
    public boolean toBoolean(String input, boolean defaultValue) {
        if (input == null) return defaultValue;

        String normalized = input.trim().toLowerCase();
        switch (normalized) {
            case "true":
            case "yes":
            case "1":
            case "on":
                return true;
            case "false":
            case "no":
            case "0":
            case "off":
                return false;
            default:
                return defaultValue;
        }
    }

    /**
     * Attempts to parse a string to a boolean.
     *
     * @param input String to convert
     * @return Optional containing the parsed value, or empty if parsing fails
     */
    public Optional<Boolean> tryParseBoolean(String input) {
        if (input == null) return Optional.empty();

        String normalized = input.trim().toLowerCase();
        switch (normalized) {
            case "true":
            case "yes":
            case "1":
            case "on":
                return Optional.of(true);
            case "false":
            case "no":
            case "0":
            case "off":
                return Optional.of(false);
            default:
                return Optional.empty();
        }
    }

    // ==================== HEX & BINARY PARSING ====================

    /**
     * Parses a hexadecimal string to an integer.
     * Supports both "0x" prefix and raw hex strings.
     *
     * @param hexString Hexadecimal string (e.g., "FF" or "0xFF")
     * @param defaultValue Value to return if parsing fails
     * @return Parsed integer or defaultValue if parsing fails
     */
    public int hexToInt(String hexString, int defaultValue) {
        try {
            String cleaned = hexString.trim();
            if (cleaned.startsWith("0x") || cleaned.startsWith("0X")) {
                cleaned = cleaned.substring(2);
            }
            return Integer.parseInt(cleaned, 16);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Parses a binary string to an integer.
     * Supports both "0b" prefix and raw binary strings.
     *
     * @param binaryString Binary string (e.g., "1010" or "0b1010")
     * @param defaultValue Value to return if parsing fails
     * @return Parsed integer or defaultValue if parsing fails
     */
    public int binaryToInt(String binaryString, int defaultValue) {
        try {
            String cleaned = binaryString.trim();
            if (cleaned.startsWith("0b") || cleaned.startsWith("0B")) {
                cleaned = cleaned.substring(2);
            }
            return Integer.parseInt(cleaned, 2);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    // ==================== ARRAY PARSING ====================

    /**
     * Parses a comma-separated string of integers.
     *
     * @param input Comma-separated string (e.g., "1,2,3,4")
     * @return Array of integers, or empty array if parsing fails
     */
    public int[] toIntArray(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] result = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = toInt(parts[i].trim(), 0);
        }

        return result;
    }

    /**
     * Parses a comma-separated string of doubles.
     *
     * @param input Comma-separated string (e.g., "1.5,2.7,3.14")
     * @return Array of doubles, or empty array if parsing fails
     */
    public double[] toDoubleArray(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new double[0];
        }

        String[] parts = input.split(",");
        double[] result = new double[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = toDouble(parts[i].trim(), 0.0);
        }

        return result;
    }

    // ==================== RADIX PARSING ====================

    /**
     * Parses a string as an integer with a specified radix (base).
     *
     * @param input String to parse
     * @param radix The radix (base) to use (2-36)
     * @param defaultValue Value to return if parsing fails
     * @return Parsed integer or defaultValue if parsing fails
     */
    public int parseWithRadix(String input, int radix, int defaultValue) {
        try {
            return Integer.parseInt(input.trim(), radix);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }
}
