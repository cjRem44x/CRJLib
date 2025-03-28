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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Terminal class provides functionality to execute system commands and capture their output.
 * This class is designed to work with Windows command prompt (cmd.exe) and provides
 * methods for both synchronous command execution and output capture.
 * 
 * Features:
 * - Execute single commands
 * - Execute multiple commands
 * - Capture command output
 * - Error handling and process management
 * 
 * @author CJ Remillard
 * @version 1.0
 */
public class Terminal 
{ 
    /**
     * Executes a single command string in the terminal.
     * This method wraps the command in 'cmd /c' to ensure proper Windows command prompt execution.
     * 
     * @param cmds The command string to execute
     * @return TRUE if the command executes successfully, FALSE otherwise
     * 
     * Example:
     * write("dir") - Lists directory contents
     * write("echo Hello") - Prints "Hello" to console
     */
    public 
    boolean write(String cmds)
    {
        // Split the command while preserving quoted strings
        List<String> commandList = new ArrayList<>();
        commandList.add("cmd");
        commandList.add("/c");
        commandList.add(cmds);
        return write(commandList.toArray(new String[0]));
    }

    /**
     * Executes multiple commands in the terminal.
     * This method allows for more complex command execution with multiple arguments.
     * 
     * @param cmds Array of command strings to execute
     * @return TRUE if all commands execute successfully, FALSE otherwise
     * 
     * Example:
     * write(new String[]{"cmd", "/c", "dir", "&&", "echo", "Done"})
     */
    public 
    boolean write(String[] cmds) 
    {
        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.inheritIO(); // makes sure the child process uses the same I/O as the parent.
        pb.redirectErrorStream(true); // Redirect error stream to standard output
    
        try 
        {
            Process p = pb.start();
            int exitCode = p.waitFor(); // Ensures process completion
            
            if (exitCode != 0) 
            {
                System.err.println("Process exited with error code: " + exitCode);
                return false;
            }

            return true;
        } catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restore the interrupt status
        }

        return false;
    }

    /**
     * Executes a command and returns its output as a String.
     * This method is useful when you need to capture and process command output.
     * 
     * @param command The command to execute
     * @return The output of the command as a String, or null if execution fails
     * 
     * Example:
     * String output = executeAndGetOutput("dir");
     */
    public String executeAndGetOutput(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", command);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            
            // Read the output using Scanner with a custom delimiter to capture all output
            java.util.Scanner s = new java.util.Scanner(p.getInputStream()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            
            p.waitFor();
            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
