/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.oracle;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nassim
 */
public class RechCC1
{    
    private static CallableStatement stmt = null;
    private static Connection c;
    
    public static Connection init()
    {
        if(c != null)
        {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(RechCC1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try
        {
            Class driver = Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//" + OraInfo.getOracleHote()
              + ":" + OraInfo.getOraclePort()
              + "/" + OraInfo.getOracleDbName();
            c = null;
            c = DriverManager.getConnection(url,
              OraInfo.getOracleUsername(),
              OraInfo.getOraclePassword());

        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(RechCC1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return c;
    }
    
    public static void Recherche() throws IOException
    {
        if(c == null)
        {
            c = init();
        }
    }

}
