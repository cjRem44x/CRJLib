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

import java.io.*;
import java.util.ArrayList;

/**
 * The FIO class provides utility methods for performing basic file operations,
 * such as writing, reading, and deleting files.
 * 
 * @author CJ Remillard
 * @version 1.0
 * @since 2025-04-02
 */
public class FIO 
{    
    // WRITE FILE //
    //
    /*******************************************************
     * Writes a given string to a specified file.
     * If the file does not exist, it will be created.
     *
     * @param path The path of the file to write to.
     * @param s    The string content to write to the file.
     *******************************************************
     */
    public 
    void write_file(String path, String s)
    {
        try 
        {
            File f = new File(path);
            
            // Using BufferedWriter for efficient writing
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
                writer.write(s);
            } catch (IOException e) {
                e.printStackTrace();    
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //
    //
    /*****************************************************************
     * Writes an array of strings to a specified file.
     * Each element of the array is written to the file sequentially.
     * If the file does not exist, it will be created.
     *
     * @param path The path of the file to write to.
     * @param arr  The array of strings to write.
     *****************************************************************
     */
    public 
    void write_arr_file(String path, String[] arr)
    {
        try 
        {
            File f = new File(path);
            
            // Ensure the file exists before writing
            if (!f.exists()) {
                f.createNewFile();
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) 
            {
                for (String s : arr) 
                {
                    writer.write(s);
                    writer.newLine(); // Ensures each string is written on a new line
                }
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }  


    // DELETE FILE //
    //
    /**********************************************************************
     * Deletes a specified file from the system.
     *
     * @param path The path of the file to delete.
     **********************************************************************
     */
    public 
    void del_file(String path)
    {
        try 
        {
            if (path == null)
                throw new IllegalArgumentException("Path cannot be null");

            File f = new File(path);
            if (!f.exists()) {
                System.out.println("File does not exist: " + path);
                return;
            }

            if (!f.delete()) {
                System.out.println("Failed to delete file: " + path);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    // READ FILE //
    //
    /**********************************************************************
     * Reads a file and returns its contents as an array of strings.
     * Each line in the file is stored as a separate element in the array.
     *
     * @param path The path of the file to read.
     * @return An array of strings containing the file contents.
     **********************************************************************
     */
    public 
    String[] read_file(String path) 
    {
        var arr = new ArrayList<>();

        try 
        {
            File f = new File(path);
            
            // Using BufferedReader for efficient reading
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    arr.add(line);
                }
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        String[] arr_str = new String[arr.size()];
        arr.toArray(arr_str);
        arr.clear();
        arr = null;
        System.gc();
        return arr_str;
    }
    //
    //
    /*******************************************************************************
     * Reads a file and returns its contents as an array of characters.
     * Each character corresponds to the first character of each line in the file.
     *
     * @param path The path of the file to read.
     * @return An array of characters containing the first character of each line.
     *******************************************************************************
     */
    public 
    char[] read_file_char(String path)
    {
        String[] arr = read_file(path);
        char[] arr_char = new char[arr.length];
        
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].isEmpty()) {
                arr_char[i] = arr[i].charAt(0); // Extract first character of each line
            } else {
                arr_char[i] = '\0'; // Assign null character if line is empty
            }
        }
        
        return arr_char;
    }


}// END OF CLASS //
