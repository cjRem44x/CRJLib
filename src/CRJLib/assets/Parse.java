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
 * Parse utility class for converting strings to various numeric types.
 * This class provides safe parsing methods that handle potential errors
 * and return default values when parsing fails.
 * 
 * Features:
 * - String to Integer conversion
 * - String to Long conversion
 * - String to Float conversion
 * - String to Double conversion
 * - Error handling with default values
 * 
 * @author CJ Remillard
 * @version 1.0
 */
public class Parse 
{
    /**
     * Converts a string to an integer.
     * Returns 0 if the conversion fails.
     * 
     * @param s String to convert 
     * @return Integer value from string, or 0 if conversion fails
     * 
     * Example:
     * str_int("123") - Returns 123
     * str_int("abc") - Returns 0
     */
    public 
    int str_int(String s)
    {
        try
        {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }

        return 0;
    } 

    /**
     * Converts a string to a long value.
     * Returns 0 if the conversion fails.
     * 
     * @param s String to convert
     * @return Long value from string, or 0 if conversion fails
     * 
     * Example:
     * str_long("123456789") - Returns 123456789
     * str_long("abc") - Returns 0
     */
    public 
    long str_long(String s)
    {
        try
        {
            return Long.parseLong(s);
        } catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }

        return 0;
    } 

    /**
     * Converts a string to a float value.
     * Returns 0.0 if the conversion fails.
     * 
     * @param s String to convert
     * @return Float value from string, or 0.0 if conversion fails
     * 
     * Example:
     * str_float("123.45") - Returns 123.45f
     * str_float("abc") - Returns 0.0f
     */
    public 
    float str_float(String s)
    {
        try
        {
            return Float.parseFloat(s);
        } catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }

        return (float)0.0;
    } 

    /**
     * Converts a string to a double value.
     * Returns 0.0 if the conversion fails.
     * 
     * @param s String to convert
     * @return Double value from string, or 0.0 if conversion fails
     * 
     * Example:
     * str_double("123.456789") - Returns 123.456789
     * str_double("abc") - Returns 0.0
     */
    public 
    double str_double(String s)
    {
        try
        {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }

        return 0.0;
    } 
}
