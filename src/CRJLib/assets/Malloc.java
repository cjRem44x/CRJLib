package CRJLib.assets;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class Malloc 
{ 
    // FIELDS //
    //
    public final  int    BYTE   = 1, 
                         SHORT  = 2, 
                         INT    = 4, 
                         LONG   = 8,
                         FLOAT  = 4,
                         DOUBLE = 8;
    private final Unsafe UNSAFE = get_unsafe();


    // MEMORY //
    //
    public 
    long alloc(int size)
    {
        if (size > 0 )
        {
            long p = UNSAFE.allocateMemory(size);
            return p;
        }
        return 0;
    }
    //
    public 
    void free(long p)
    {
        UNSAFE.freeMemory(p);
    }


    // READ/WRITE //
    //
    public 
    byte rbyte(long p)
    {
        return UNSAFE.getByte(p);
    }
    //
    public 
    void wbyte(long p, byte b)
    {
        UNSAFE.putByte(p, (byte)b);
    }
    //
    public 
    short rshort(long p)
    {
        return UNSAFE.getShort(p);
    }
    //
    public 
    void wshort(long p, short s)
    {
        UNSAFE.putShort(p, (short)s);
    }
    //
    public 
    int rint(long p)
    {
        return UNSAFE.getInt(p);
    }
    //
    public 
    void wint(long p, int i)
    {
        UNSAFE.putInt(p, (int)i);
    }
    //
    public 
    long rlong(long p)
    {
        return UNSAFE.getLong(p);
    }
    //
    public 
    void wlong(long p, long l)
    {
        UNSAFE.putLong(p, (long)l);
    }
    //
    public
    float rfloat(long p) 
    {
        return UNSAFE.getFloat(p);
    }
    //
    public 
    void wfloat(long p, float f) 
    {
        UNSAFE.putFloat(p, (float)f);
    }
    //
    public
    double rdouble(long p) 
    {
        return UNSAFE.getDouble(p);
    }
    //
    public 
    void wdouble(long p, double d) 
    {
        UNSAFE.putDouble(p, (double)d);
    }


    // ACCESS UNSAFE //
    //
    private static 
    Unsafe get_unsafe() 
    {
        try 
        {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) 
        {
            e.printStackTrace();
        } catch (SecurityException e) 
        {            
            e.printStackTrace();
        } catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
        } catch (IllegalAccessException e) 
        {
            e.printStackTrace();
        }

        return null;
    }  

}