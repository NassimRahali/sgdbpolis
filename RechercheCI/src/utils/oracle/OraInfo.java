package utils.oracle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OraInfo
{
    private final static Properties p = Chargement();

    public static Properties Chargement()
    {
        Properties nP = new Properties();
        try
        {
            FileInputStream input = new FileInputStream("config.properties");
            nP.load(input);
        }
        catch (FileNotFoundException ex) { System.err.println("FileNotFoundException"); }
        catch (IOException ex) { System.err.println("IOException"); }
        return nP;
    }
    
    public static void Enregistrer()
    {
        FileOutputStream output;
        try
        {
            output = new FileOutputStream("connection.properties");
            p.store(output, null);
            output.close();
        }
        catch (FileNotFoundException ex) { System.err.println("FileNotFoundException"); }
        catch (IOException ex) { System.err.println("IOException"); }
    }
    
    public static String getOracleHote()
    {
        return p.getProperty("oracleHote");
    }
    
    public static String getOracleDbName()
    {
        return p.getProperty("oracleDbName");
    }
    
    public static String getOraclePort()
    {
        return p.getProperty("oraclePort");
    }
    
    public static String getOracleUsername()
    {
        return p.getProperty("oracleUsername");
    }
    
    public static String getOraclePassword()
    {
        return p.getProperty("oraclePassword");
    }
    
    public static void setOracleHote(String n)
    {
        p.setProperty("oracleHote", n);
        Enregistrer();
    }
    
    public static void setOracleDbName(String n)
    {
        p.setProperty("oracleDbName", n);
        Enregistrer();
    }
    
    public static void setOraclePort(String n)
    {
        p.setProperty("oraclePort", n);
        Enregistrer();
    }
    
    public static void setOracleUsername(String n)
    {
        p.setProperty("oracleUsername", n);
        Enregistrer();
    }
    
    public static void setOraclePassword(String n)
    {
        p.setProperty("oraclePassword", n);
        Enregistrer();
    }
}