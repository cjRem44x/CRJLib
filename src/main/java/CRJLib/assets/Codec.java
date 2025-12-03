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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Codec - Encoding and Decoding Utility Class
 * Provides various encoding and decoding operations for data transformation.
 *
 * <p>Features:
 * <ul>
 * <li>Base64 encoding/decoding</li>
 * <li>Hexadecimal encoding/decoding</li>
 * <li>Binary string encoding/decoding</li>
 * <li>String to byte array conversions</li>
 * <li>Integer to various representations</li>
 * </ul>
 *
 * @author CJ Remillard
 * @version 1.0
 */
public class Codec
{
    // ==================== BASE64 ENCODING ====================

    /**
     * Encodes a string to Base64.
     *
     * @param input The string to encode
     * @return Base64-encoded string
     */
    public String toBase64(String input) {
        if (input == null) return null;
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Encodes a byte array to Base64.
     *
     * @param bytes The byte array to encode
     * @return Base64-encoded string
     */
    public String toBase64(byte[] bytes) {
        if (bytes == null) return null;
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Decodes a Base64 string.
     *
     * @param encoded The Base64-encoded string
     * @return Decoded string
     */
    public String fromBase64(String encoded) {
        if (encoded == null) return null;
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    /**
     * Decodes a Base64 string to a byte array.
     *
     * @param encoded The Base64-encoded string
     * @return Decoded byte array
     */
    public byte[] fromBase64ToBytes(String encoded) {
        if (encoded == null) return null;
        return Base64.getDecoder().decode(encoded);
    }

    /**
     * Encodes a string to URL-safe Base64 (no padding).
     *
     * @param input The string to encode
     * @return URL-safe Base64-encoded string
     */
    public String toBase64UrlSafe(String input) {
        if (input == null) return null;
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Decodes a URL-safe Base64 string.
     *
     * @param encoded The URL-safe Base64-encoded string
     * @return Decoded string
     */
    public String fromBase64UrlSafe(String encoded) {
        if (encoded == null) return null;
        byte[] decoded = Base64.getUrlDecoder().decode(encoded);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    // ==================== HEXADECIMAL ENCODING ====================

    /**
     * Encodes a string to hexadecimal.
     *
     * @param input The string to encode
     * @return Hexadecimal string
     */
    public String toHex(String input) {
        if (input == null) return null;
        return toHex(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Encodes a byte array to hexadecimal.
     *
     * @param bytes The byte array to encode
     * @return Hexadecimal string
     */
    public String toHex(byte[] bytes) {
        if (bytes == null) return null;

        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    /**
     * Decodes a hexadecimal string.
     *
     * @param hex The hexadecimal string
     * @return Decoded string
     */
    public String fromHex(String hex) {
        if (hex == null) return null;
        byte[] bytes = fromHexToBytes(hex);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Decodes a hexadecimal string to a byte array.
     *
     * @param hex The hexadecimal string
     * @return Decoded byte array
     */
    public byte[] fromHexToBytes(String hex) {
        if (hex == null) return null;

        int len = hex.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // ==================== BINARY STRING ENCODING ====================

    /**
     * Converts an integer to a binary string.
     *
     * @param value The integer value
     * @return Binary string representation
     */
    public String toBinaryString(int value) {
        return Integer.toBinaryString(value);
    }

    /**
     * Converts an integer to a binary string with fixed width (padded with zeros).
     *
     * @param value The integer value
     * @param width The desired width
     * @return Padded binary string representation
     */
    public String toBinaryString(int value, int width) {
        String binary = Integer.toBinaryString(value);
        return String.format("%" + width + "s", binary).replace(' ', '0');
    }

    /**
     * Converts a byte array to a binary string.
     *
     * @param bytes The byte array
     * @return Binary string representation
     */
    public String toBinaryString(byte[] bytes) {
        if (bytes == null) return null;

        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return binary.toString();
    }

    // ==================== INTEGER CONVERSIONS ====================

    /**
     * Converts an integer to a hexadecimal string.
     *
     * @param value The integer value
     * @return Hexadecimal string representation
     */
    public String intToHex(int value) {
        return Integer.toHexString(value);
    }

    /**
     * Converts an integer to a hexadecimal string with fixed width (padded).
     *
     * @param value The integer value
     * @param width The desired width
     * @return Padded hexadecimal string representation
     */
    public String intToHex(int value, int width) {
        return String.format("%0" + width + "x", value);
    }

    /**
     * Converts an integer to an octal string.
     *
     * @param value The integer value
     * @return Octal string representation
     */
    public String intToOctal(int value) {
        return Integer.toOctalString(value);
    }

    // ==================== BYTE ARRAY UTILITIES ====================

    /**
     * Converts a string to a byte array using UTF-8 encoding.
     *
     * @param input The string to convert
     * @return Byte array
     */
    public byte[] stringToBytes(String input) {
        if (input == null) return null;
        return input.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array to a string using UTF-8 encoding.
     *
     * @param bytes The byte array to convert
     * @return String
     */
    public String bytesToString(byte[] bytes) {
        if (bytes == null) return null;
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts an integer to a byte array (4 bytes, big-endian).
     *
     * @param value The integer value
     * @return Byte array representation
     */
    public byte[] intToBytes(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }

    /**
     * Converts a byte array (4 bytes, big-endian) to an integer.
     *
     * @param bytes The byte array (must be at least 4 bytes)
     * @return Integer value
     */
    public int bytesToInt(byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            throw new IllegalArgumentException("Byte array must be at least 4 bytes");
        }

        return ((bytes[0] & 0xFF) << 24) |
               ((bytes[1] & 0xFF) << 16) |
               ((bytes[2] & 0xFF) << 8) |
               (bytes[3] & 0xFF);
    }

    /**
     * Converts a long to a byte array (8 bytes, big-endian).
     *
     * @param value The long value
     * @return Byte array representation
     */
    public byte[] longToBytes(long value) {
        return new byte[] {
            (byte) (value >> 56),
            (byte) (value >> 48),
            (byte) (value >> 40),
            (byte) (value >> 32),
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }

    /**
     * Converts a byte array (8 bytes, big-endian) to a long.
     *
     * @param bytes The byte array (must be at least 8 bytes)
     * @return Long value
     */
    public long bytesToLong(byte[] bytes) {
        if (bytes == null || bytes.length < 8) {
            throw new IllegalArgumentException("Byte array must be at least 8 bytes");
        }

        return ((long) (bytes[0] & 0xFF) << 56) |
               ((long) (bytes[1] & 0xFF) << 48) |
               ((long) (bytes[2] & 0xFF) << 40) |
               ((long) (bytes[3] & 0xFF) << 32) |
               ((long) (bytes[4] & 0xFF) << 24) |
               ((long) (bytes[5] & 0xFF) << 16) |
               ((long) (bytes[6] & 0xFF) << 8) |
               ((long) (bytes[7] & 0xFF));
    }
}
