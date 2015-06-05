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
public class FilmType implements SQLData
{
    private String titre;
    private String acteurs;
    private String cert;
    private int duree;
    
    public FilmType(STRUCT s)
    {
        try
        {
            Object[] object = s.getAttributes();
            titre = object[0].toString();
            if(object[1] != null)
                acteurs = object[1].toString();
            cert = object[2].toString();
            duree = ((BigDecimal)object[3]).intValueExact();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(FilmType.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return "FILM_TYPE";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {

    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        
    }

    public String getTitre()
    {
        return titre;
    }

    public String getActeurs()
    {
        return acteurs;
    }

    public String getCert()
    {
        return cert;
    }

    public int getDuree()
    {
        return duree;
    }
    
    
    
}
