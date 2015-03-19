/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.others;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Nassim
 */
public class CBHelper
{   
    public static String CorrectInput(String str, int len, String bydefault)
    {
        if (str == null || str.isEmpty())
        {
            return bydefault;
        }
        
        return (str.length() > len ? str.substring(0, len - 1) : str);
    }
    
    public static Date CorrectDate(String date)
    {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try
        {
            if (date != null)
                d = new Date(sf.parse(date).getTime());
        }
        catch (ParseException ex)
        {
            System.err.println(ex.getMessage());
        }
        return d;
    }
}
