/*
* RAHALI Nassim
* M18
* 2014-2015
*/
package utils.oracle;

import GUI.PlacesCC1;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import utils.oracle.Data.ProjectionType;
import utils.oracle.Data.ProjectionsJour;

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
    
    public static void Recherche(PlacesCC1 gui, Calendar date) throws IOException
    {
        if(c == null)
        {
            c = init();
        }
        
        // Reset JTable
        resetJTab(gui);
        
        for(int i = 0 ; i < 7 ; i++)
        {
            try
            {
                Map map = c.getTypeMap();
                map.put("PROJECTIONS_JOUR", ProjectionsJour.class);
                if(stmt == null)
                {
                    stmt = c.prepareCall("{? = call RECHCC1_PKG.RECH_PROG(?)}");
                }
                stmt.clearParameters();
                Date d = new Date(date.getTime().getTime());
                stmt.registerOutParameter(1, OracleTypes.ARRAY, "PROJECTIONS_JOUR");
                stmt.setDate(2, d);
                stmt.execute();                
                ProjectionsJour projs = new ProjectionsJour((ARRAY)stmt.getArray(1));
                System.out.println("Query effectuée : " + projs.getProjs().size());
                
                // Ajout JTable
                ajoutJTab(projs, gui.getTabs().get(i));
                
            }
            catch (IndexOutOfBoundsException ex)
            {
                System.out.println("Pas de projections le jour j+" + i);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(RechCC1.class.getName()).log(Level.SEVERE, null, ex);
            }
            date.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    
    private static void ajoutJTab(ProjectionsJour projs, JTable tab)
    {
        DefaultTableModel dtm = (DefaultTableModel)tab.getModel();
        dtm.setRowCount(0);
        for (ProjectionType proj : projs.getProjs())
        {
            dtm.addRow(new Object[]{
                proj.getFilm().getTitre(),
                proj.getFilm().getActeurs(),
                proj.getFilm().getCert(),
                proj.getFilm().getDuree(),
                proj.getSeance(),
                proj.getSalle(),
                proj.getPlacesDispo()
            });
        }
        tab.setModel(dtm);
    }
    
    private static void resetJTab(PlacesCC1 gui)
    {
        for (JTable tab : gui.getTabs())
        {
            DefaultTableModel dtm = (DefaultTableModel)tab.getModel();
            dtm.setRowCount(0);
            tab.setModel(dtm);
        }
    }
}


