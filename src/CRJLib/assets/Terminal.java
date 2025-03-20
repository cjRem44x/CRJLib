package CRJLib.assets;

import java.io.IOException;

public class Terminal 
{ 
    // WRITE //
    //
    /************************************************************
     * 
     * @param cmds Commands to write to the Terminal
     * @return TRUE if method-function succeeds, otherwise FALSE
     */
    //
    public 
    boolean write(String cmds)
    {
        String[] s = cmds.split("\\s+");
        return write(s);
    }
    //
    /************************************************************
     * 
     * @param cmds Commands to write to the Terminal
     * @return TRUE if method-function succeeds, otherwise FALSE
     */
    //
    public 
    boolean write(String[] cmds) 
    {
        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.inheritIO(); // makes sure the child process uses the same I/O as the parent.
    
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


}
