/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.oracle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.sqldataclasses.Movie;

/**
 *
 * @author Nassim
 */
public class AlimCB
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
                Logger.getLogger(AlimCB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AlimCB.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return c;
    }
    
    public static void InsertMovies(List<Movie> movie_list) throws IOException
    {
        if(c == null)
        {
            c = init();
        }
        
        try
        {
            Map map = c.getTypeMap();
            map.put("MOVIE_T", Movie.class);            
            for(Movie m : movie_list)
            {
                // ARRAYS AND STRUCTS
                m.initArrayAndStruct(c);
                
                // BLOB
                File tmp = blobTempSaved(m.getPoster());
                InputStream is = new FileInputStream(tmp);
                
                if(stmt == null)
                {
                    stmt = c.prepareCall("{CALL ALIMCB_PKG.INSERT_CB(?, ?)}");
                }
                stmt.clearParameters();
                stmt.setObject(1, m);
                stmt.setBinaryStream(2, is, tmp.length());
                stmt.execute();
                is.close();
                tmp.delete();
                c.commit();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(AlimCB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static File blobTempSaved(InputStream poster)
    {
        OutputStream outputStream = null;
        try
        {
            int nb_read = 0;
            File f = new File("blob_tmp");
            f.setWritable(true);
            outputStream = new FileOutputStream(f);
            byte[] stock = new byte[2048];
            while ((nb_read = poster.read(stock)) != -1)
            {
                outputStream.write(stock, 0, nb_read);
            }
            return f;
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(AlimCB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(AlimCB.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                outputStream.close();
            } catch (IOException ex)
            {
                Logger.getLogger(AlimCB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
