/*
 * MIT License
 *
 * Copyright (c) 2025 CJ Remillard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 */
// AUTHOR: CJ Remillard //
//
package CRJLib.assets;

/**
 * Scimat - Scientific Mathematics Utility Class
 * Provides comprehensive mathematical operations with improved accuracy and safety checks.
 *
 * <p>Features:
 * <ul>
 * <li>Basic arithmetic: power, square root, absolute value</li>
 * <li>Exponential and logarithmic functions</li>
 * <li>Trigonometric functions: sin, cos, tan and their inverses</li>
 * <li>Rounding operations: floor, ceil, round</li>
 * <li>Number theory: factorial, GCD, LCM, isPrime</li>
 * <li>Comparison: min, max, clamp</li>
 * <li>Utility: sign, modulo</li>
 * </ul>
 *
 * @author CJ Remillard
 * @version 2.0
 * @since 2025-04-02
 */
public class Scimat
{
    private static final double EPSILON        = 1e-15;  // Machine epsilon for double precision
    private static final int    MAX_ITERATIONS = 100;    // Prevent infinite loops

    // ==================== BASIC ARITHMETIC ====================

    /**
     * Calculates the square root of a number using Newton's method with improved accuracy.
     *
     * @param value The number to calculate the square root of
     * @return The square root of value, or NaN for negative numbers
     */
    public double squareRoot(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Input must be a finite number");

        if (value < 0.0)
            return Double.NaN;  // Return NaN for negative numbers (proper behavior)
        if (value == 0.0 || value == 1.0)
            return value;

        // Initial guess using bit manipulation for better convergence
        double guess = Double.longBitsToDouble((Double.doubleToLongBits(value) >> 1) + 0x1ff0000000000000L);
        double prevGuess;
        int iterations = 0;

        do {
            prevGuess = guess;
            guess = (guess + value / guess) / 2.0;
            iterations++;

            if (iterations >= MAX_ITERATIONS || Math.abs(guess - prevGuess) <= EPSILON * Math.abs(guess)) {
                break;
            }
        } while (true);

        return guess;
    }

    /**
     * Alias for squareRoot() for backwards compatibility.
     * @deprecated Use {@link #squareRoot(double)} instead
     */
    @Deprecated
    public double sqrt(double value) {
        return squareRoot(value);
    }

    /**
     * Calculates base raised to the power of exponent.
     *
     * @param base The base number
     * @param exponent The exponent
     * @return base^exponent
     */
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Calculates the absolute value of a number.
     *
     * @param value The number
     * @return The absolute value
     */
    public double absoluteValue(double value) {
        return Math.abs(value);
    }

    /**
     * Alias for absoluteValue() for backwards compatibility.
     * @deprecated Use {@link #absoluteValue(double)} instead
     */
    @Deprecated
    public double abs(double value) {
        return absoluteValue(value);
    }

    // ==================== EXPONENTIAL & LOGARITHMIC ====================

    /**
     * Calculates e^value (e raised to the power of value).
     *
     * @param value The exponent
     * @return e^value
     */
    public double exponential(double value) {
        return Math.exp(value);
    }

    /**
     * Calculates the natural logarithm (base e) of a value.
     *
     * @param value The value (must be positive)
     * @return ln(value), or NaN if value <= 0
     */
    public double naturalLog(double value) {
        if (value <= 0) return Double.NaN;
        return Math.log(value);
    }

    /**
     * Calculates the base-10 logarithm of a value.
     *
     * @param value The value (must be positive)
     * @return log10(value), or NaN if value <= 0
     */
    public double log10(double value) {
        if (value <= 0) return Double.NaN;
        return Math.log10(value);
    }

    /**
     * Calculates logarithm with custom base.
     *
     * @param value The value (must be positive)
     * @param base The base (must be positive and not 1)
     * @return log_base(value)
     */
    public double logarithm(double value, double base) {
        if (value <= 0 || base <= 0 || base == 1) return Double.NaN;
        return Math.log(value) / Math.log(base);
    }

    // ==================== TRIGONOMETRIC ====================

    /**
     * Calculates the sine of an angle in radians.
     *
     * @param radians The angle in radians
     * @return sin(radians)
     */
    public double sine(double radians) {
        return Math.sin(radians);
    }

    /**
     * Calculates the cosine of an angle in radians.
     *
     * @param radians The angle in radians
     * @return cos(radians)
     */
    public double cosine(double radians) {
        return Math.cos(radians);
    }

    /**
     * Calculates the tangent of an angle in radians.
     *
     * @param radians The angle in radians
     * @return tan(radians)
     */
    public double tangent(double radians) {
        return Math.tan(radians);
    }

    /**
     * Calculates the arc sine (inverse sine) of a value.
     *
     * @param value The value (must be between -1 and 1)
     * @return arcsin(value) in radians, or NaN if out of range
     */
    public double arcSine(double value) {
        if (value < -1.0 || value > 1.0) return Double.NaN;
        return Math.asin(value);
    }

    /**
     * Calculates the arc cosine (inverse cosine) of a value.
     *
     * @param value The value (must be between -1 and 1)
     * @return arccos(value) in radians, or NaN if out of range
     */
    public double arcCosine(double value) {
        if (value < -1.0 || value > 1.0) return Double.NaN;
        return Math.acos(value);
    }

    /**
     * Calculates the arc tangent (inverse tangent) of a value.
     *
     * @param value The value
     * @return arctan(value) in radians
     */
    public double arcTangent(double value) {
        return Math.atan(value);
    }

    /**
     * Converts degrees to radians.
     *
     * @param degrees The angle in degrees
     * @return The angle in radians
     */
    public double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Converts radians to degrees.
     *
     * @param radians The angle in radians
     * @return The angle in degrees
     */
    public double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    // ==================== ROUNDING ====================

    /**
     * Rounds down to the nearest integer (floor function).
     *
     * @param value The value to round down
     * @return The largest integer less than or equal to value
     */
    public double floor(double value) {
        return Math.floor(value);
    }

    /**
     * Rounds up to the nearest integer (ceiling function).
     *
     * @param value The value to round up
     * @return The smallest integer greater than or equal to value
     */
    public double ceiling(double value) {
        return Math.ceil(value);
    }

    /**
     * Rounds to the nearest integer.
     *
     * @param value The value to round
     * @return The nearest integer to value
     */
    public long round(double value) {
        return Math.round(value);
    }

    // ==================== NUMBER THEORY ====================

    /**
     * Calculates the factorial of a non-negative integer.
     *
     * @param n The number (must be >= 0 and <= 20 to avoid overflow)
     * @return n! (n factorial), or -1 if n is invalid
     */
    public long factorial(int n) {
        if (n < 0 || n > 20) return -1;  // Overflow prevention
        if (n == 0 || n == 1) return 1;

        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Calculates the Greatest Common Divisor (GCD) of two integers using Euclid's algorithm.
     *
     * @param a First integer
     * @param b Second integer
     * @return GCD of a and b
     */
    public long greatestCommonDivisor(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Alias for greatestCommonDivisor().
     */
    public long gcd(long a, long b) {
        return greatestCommonDivisor(a, b);
    }

    /**
     * Calculates the Least Common Multiple (LCM) of two integers.
     *
     * @param a First integer
     * @param b Second integer
     * @return LCM of a and b
     */
    public long leastCommonMultiple(long a, long b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a * b) / greatestCommonDivisor(a, b);
    }

    /**
     * Alias for leastCommonMultiple().
     */
    public long lcm(long a, long b) {
        return leastCommonMultiple(a, b);
    }

    /**
     * Checks if a number is prime.
     *
     * @param n The number to check
     * @return true if n is prime, false otherwise
     */
    public boolean isPrime(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    // ==================== COMPARISON ====================

    /**
     * Returns the minimum of two values.
     *
     * @param a First value
     * @param b Second value
     * @return The smaller of a and b
     */
    public double minimum(double a, double b) {
        return Math.min(a, b);
    }

    /**
     * Returns the maximum of two values.
     *
     * @param a First value
     * @param b Second value
     * @return The larger of a and b
     */
    public double maximum(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Clamps a value between a minimum and maximum.
     *
     * @param value The value to clamp
     * @param min The minimum bound
     * @param max The maximum bound
     * @return value clamped to [min, max]
     */
    public double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    // ==================== UTILITY ====================

    /**
     * Returns the sign of a number.
     *
     * @param value The value
     * @return 1 if positive, -1 if negative, 0 if zero
     */
    public int sign(double value) {
        if (value > 0) return 1;
        if (value < 0) return -1;
        return 0;
    }

    /**
     * Calculates the modulo (remainder) of division.
     * This properly handles negative numbers unlike Java's % operator.
     *
     * @param dividend The dividend
     * @param divisor The divisor
     * @return The modulo result (always non-negative if divisor > 0)
     */
    public double modulo(double dividend, double divisor) {
        return ((dividend % divisor) + divisor) % divisor;
    }

    /**
     * Calculates the distance between two 2D points.
     *
     * @param x1 X-coordinate of first point
     * @param y1 Y-coordinate of first point
     * @param x2 X-coordinate of second point
     * @param y2 Y-coordinate of second point
     * @return The Euclidean distance between the points
     */
    public double distance2D(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the distance between two 3D points.
     *
     * @param x1 X-coordinate of first point
     * @param y1 Y-coordinate of first point
     * @param z1 Z-coordinate of first point
     * @param x2 X-coordinate of second point
     * @param y2 Y-coordinate of second point
     * @param z2 Z-coordinate of second point
     * @return The Euclidean distance between the points
     */
    public double distance3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
