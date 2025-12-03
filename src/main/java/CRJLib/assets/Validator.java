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

import java.util.regex.Pattern;

/**
 * Validator - Input Validation Utility Class
 * Provides comprehensive validation methods for common data types and formats.
 *
 * <p>Features:
 * <ul>
 * <li>String validation: empty, blank, length, pattern matching</li>
 * <li>Numeric validation: range, positive, negative</li>
 * <li>Email validation</li>
 * <li>URL validation</li>
 * <li>IP address validation</li>
 * <li>Phone number validation</li>
 * <li>Credit card number validation (Luhn algorithm)</li>
 * <li>Date format validation</li>
 * </ul>
 *
 * @author CJ Remillard
 * @version 1.0
 */
public class Validator
{
    // Common regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"
    );

    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$"
    );

    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9]+$"
    );

    // ==================== STRING VALIDATION ====================

    /**
     * Checks if a string is null or empty.
     *
     * @param value The string to check
     * @return true if null or empty
     */
    public boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Checks if a string is null, empty, or contains only whitespace.
     *
     * @param value The string to check
     * @return true if null, empty, or blank
     */
    public boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks if a string has a minimum length.
     *
     * @param value The string to check
     * @param minLength Minimum required length
     * @return true if string meets minimum length
     */
    public boolean hasMinLength(String value, int minLength) {
        return value != null && value.length() >= minLength;
    }

    /**
     * Checks if a string has a maximum length.
     *
     * @param value The string to check
     * @param maxLength Maximum allowed length
     * @return true if string is within maximum length
     */
    public boolean hasMaxLength(String value, int maxLength) {
        return value == null || value.length() <= maxLength;
    }

    /**
     * Checks if a string length is within a range.
     *
     * @param value The string to check
     * @param minLength Minimum length (inclusive)
     * @param maxLength Maximum length (inclusive)
     * @return true if string length is within range
     */
    public boolean hasLengthInRange(String value, int minLength, int maxLength) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }

    /**
     * Checks if a string matches a regular expression pattern.
     *
     * @param value The string to check
     * @param pattern The regex pattern
     * @return true if string matches pattern
     */
    public boolean matchesPattern(String value, String pattern) {
        return value != null && value.matches(pattern);
    }

    /**
     * Checks if a string contains only alphanumeric characters.
     *
     * @param value The string to check
     * @return true if string is alphanumeric
     */
    public boolean isAlphanumeric(String value) {
        return value != null && ALPHANUMERIC_PATTERN.matcher(value).matches();
    }

    // ==================== NUMERIC VALIDATION ====================

    /**
     * Checks if an integer is within a range.
     *
     * @param value The value to check
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return true if value is within range
     */
    public boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Checks if a double is within a range.
     *
     * @param value The value to check
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return true if value is within range
     */
    public boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Checks if a number is positive (greater than zero).
     *
     * @param value The value to check
     * @return true if value is positive
     */
    public boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Checks if a number is negative (less than zero).
     *
     * @param value The value to check
     * @return true if value is negative
     */
    public boolean isNegative(double value) {
        return value < 0;
    }

    /**
     * Checks if a number is non-negative (zero or positive).
     *
     * @param value The value to check
     * @return true if value is non-negative
     */
    public boolean isNonNegative(double value) {
        return value >= 0;
    }

    // ==================== EMAIL VALIDATION ====================

    /**
     * Validates an email address format.
     * This is a basic validation and may not catch all invalid formats.
     *
     * @param email The email address to validate
     * @return true if email format appears valid
     */
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // ==================== URL VALIDATION ====================

    /**
     * Validates a URL format.
     *
     * @param url The URL to validate
     * @return true if URL format appears valid
     */
    public boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    // ==================== IP ADDRESS VALIDATION ====================

    /**
     * Validates an IPv4 address format.
     *
     * @param ip The IP address to validate
     * @return true if IP address format is valid
     */
    public boolean isValidIPv4(String ip) {
        return ip != null && IPV4_PATTERN.matcher(ip).matches();
    }

    // ==================== PHONE NUMBER VALIDATION ====================

    /**
     * Validates a phone number format (basic validation).
     * Accepts various formats: (123) 456-7890, 123-456-7890, +1234567890, etc.
     *
     * @param phone The phone number to validate
     * @return true if phone number format appears valid
     */
    public boolean isValidPhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches() &&
               phone.replaceAll("[^0-9]", "").length() >= 7;
    }

    // ==================== CREDIT CARD VALIDATION ====================

    /**
     * Validates a credit card number using the Luhn algorithm.
     *
     * @param cardNumber The credit card number (can include spaces or dashes)
     * @return true if card number passes Luhn check
     */
    public boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null) return false;

        // Remove spaces and dashes
        String cleaned = cardNumber.replaceAll("[\\s-]", "");

        // Must be all digits and 13-19 characters
        if (!cleaned.matches("\\d{13,19}")) {
            return false;
        }

        // Luhn algorithm
        int sum = 0;
        boolean alternate = false;

        for (int i = cleaned.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cleaned.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }

    // ==================== DATE VALIDATION ====================

    /**
     * Validates a date string in format YYYY-MM-DD.
     *
     * @param date The date string to validate
     * @return true if date format is valid
     */
    public boolean isValidDateYYYYMMDD(String date) {
        if (date == null) return false;

        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        if (!pattern.matcher(date).matches()) {
            return false;
        }

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;

        // Check days in month
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Check for leap year
        if (month == 2 && isLeapYear(year)) {
            return day <= 29;
        }

        return day <= daysInMonth[month - 1];
    }

    /**
     * Checks if a year is a leap year.
     *
     * @param year The year to check
     * @return true if year is a leap year
     */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // ==================== PASSWORD STRENGTH ====================

    /**
     * Checks if a password meets basic strength requirements.
     * Requirements: At least 8 characters, contains uppercase, lowercase, digit, and special character.
     *
     * @param password The password to check
     * @return true if password meets strength requirements
     */
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    /**
     * Checks if a password meets custom strength requirements.
     *
     * @param password The password to check
     * @param minLength Minimum length required
     * @param requireUppercase Require at least one uppercase letter
     * @param requireLowercase Require at least one lowercase letter
     * @param requireDigit Require at least one digit
     * @param requireSpecial Require at least one special character
     * @return true if password meets all requirements
     */
    public boolean isStrongPassword(String password, int minLength,
                                   boolean requireUppercase, boolean requireLowercase,
                                   boolean requireDigit, boolean requireSpecial) {
        if (password == null || password.length() < minLength) {
            return false;
        }

        boolean hasUpper = !requireUppercase;
        boolean hasLower = !requireLowercase;
        boolean hasDigit = !requireDigit;
        boolean hasSpecial = !requireSpecial;

        for (char c : password.toCharArray()) {
            if (requireUppercase && Character.isUpperCase(c)) hasUpper = true;
            if (requireLowercase && Character.isLowerCase(c)) hasLower = true;
            if (requireDigit && Character.isDigit(c)) hasDigit = true;
            if (requireSpecial && !Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
