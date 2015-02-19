/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.sqldataclasses;

import com.mongodb.DBObject;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 *
 * @author Nassim
 */
public class Company implements SQLData
{

    public int id;
    public String name;
    public final static String SQLTYPENAME = "COMPANY_T";
    
    public Company(DBObject dbo)
    {
        id = Integer.parseInt(dbo.get("id").toString());
        name = dbo.get("name").toString();
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return Company.SQLTYPENAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        id = stream.readInt();
        name = stream.readNString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(id);
        stream.writeNString(name);
    }
    
}
