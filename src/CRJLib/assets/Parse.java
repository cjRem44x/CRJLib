package CRJLib.assets;

public class Parse 
{

    // STRING TO NUMBER //
    //
    /******************************
     * 
     * @param s String to convert 
     * @return Integer from string 
     */
    //
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
    //
    /*********************************
     * 
     * @param s String to convert
     * @return Long form string
     */
    //
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
    //
    /******************************
     * 
     * @param s String to convert
     * @return Float to return
     */
    //
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
    //
    /*****************************
     * 
     * @param s String to convert
     * @return Double from string
     */
    //
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
