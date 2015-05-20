/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package utils.oracle.Data;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.STRUCT;

/**
 *
 * @author Thibault
 */
public class ProjectionType implements SQLData
{
    private int salle;
    private int seance;
    private int placesDispo;
    private FilmType film;
    
    public ProjectionType(STRUCT s)
    {
        try
        {
            Object[] object = s.getAttributes();
            salle = ((BigDecimal)object[0]).intValueExact();
            seance = ((BigDecimal)object[1]).intValueExact();
            placesDispo = ((BigDecimal)object[2]).intValueExact();
            film = new FilmType((STRUCT)object[3]);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(ProjectionType.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return "PROJECTION_TYPE";
    }
    
    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        System.out.println(stream.readObject());
    }
    
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        
    }

    public int getSalle()
    {
        return salle;
    }

    public int getSeance()
    {
        return seance;
    }

    public int getPlacesDispo()
    {
        return placesDispo;
    }

    public FilmType getFilm()
    {
        return film;
    }
    
}
