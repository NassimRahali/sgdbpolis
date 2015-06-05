/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.oracle.Data;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

/**
 *
 * @author Thibault
 */
public class ProjectionsJour implements SQLData
{
    private ArrayList<ProjectionType> projs;
    
    public ProjectionsJour(ARRAY a)
    {
        projs = new ArrayList<>();
        try
        {
            Object[] object = (Object[])a.getArray();
            for (Object o : object)
            {
                projs.add(new ProjectionType((STRUCT)o));
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(ProjectionsJour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return "PROJECTIONS_JOUR";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {

    }

    public ArrayList<ProjectionType> getProjs()
    {
        return projs;
    }
    
    
    
}
