/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.sqldataclasses;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 *
 * @author Nassim
 */
public class Certification implements SQLData
{
    public String name;
    public String description;
    public final static String SQLTYPENAME = "CERTIF_T";
    
    public Certification(String c)
    {
        name = c;
        description = c;
        switch(name)
        {
            case "G":
                description = "Ok for all";
                break;
            case "PG":
                description = "Maybe not for children";
                break;
            case "PG-13":
                description = "Not for children < 13 yo";
                break;
            case "R":
                description = "For adult";
                break;
            case "NC-17":
                description = "Not for children < 17 yo";
                break;
            default:
                description = "NA";
                break;
        }
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return Certification.SQLTYPENAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        name = stream.readNString();
        description = stream.readNString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeNString(name);
        stream.writeNString(description);
    }

    String getName()
    {
        return name;
    }

    String getDescription()
    {
        return description;
    }
    
}
