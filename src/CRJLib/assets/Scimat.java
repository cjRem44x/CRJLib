/*
 * MIT License
 *
 * Copyright (c) 2025 CJ Remillard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
*/
// AUTHOR: CJ Remillard //
//
package CRJLib.assets;

/**
 * Scimat - Scientific Mathematics Utility Class
 * Provides mathematical operations with improved accuracy and safety checks.
 */
public class Scimat 
{
    private static final double EPSILON        = 1e-15;  // Machine epsilon for double precision
    private static final int    MAX_ITERATIONS = 100;  // Prevent infinite loops

    /**
     * Calculates the square root of a number using Newton's method with improved accuracy.
     * 
     * @param n The number to calculate the square root of
     * @return The square root of n, or 0.0 for negative numbers
     * @throws IllegalArgumentException if the input is NaN or infinite
     */
    public double sqrt(double n) 
    {
        // Input validation
        if (Double.isNaN(n) || Double.isInfinite(n)) 
            throw new IllegalArgumentException("Input must be a finite number");

        if (n < 0.0) 
            return 0.0;           // Return 0 for negative numbers
        if (n == 0.0 || n == 1.0)
            return n;             // Special cases

        // Initial guess using bit manipulation for better convergence
        double guess = Double.longBitsToDouble((Double.doubleToLongBits(n) >> 1) + 0x1ff0000000000000L);
        double prevGuess;
        int iterations = 0;

        do {
            prevGuess = guess;
            guess = (guess + n / guess) / 2.0;
            iterations++;

            // Check for convergence or maximum iterations
            if (iterations >= MAX_ITERATIONS || 
                Math.abs(guess - prevGuess) <= EPSILON * Math.abs(guess)) {
                break;
            }
        } while (true);

        return guess;
    }

    /**
     * Calculates the absolute value of a number.
     * 
     * @param n The number to calculate the absolute value of
     * @return The absolute value of n
     */
    public double abs(double n)
    {
        return Math.abs(n);  // Using Math.abs for better performance and accuracy
    }
}
