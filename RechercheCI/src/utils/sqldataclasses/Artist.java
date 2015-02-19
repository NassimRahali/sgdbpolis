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
public class Artist implements SQLData
{
    public int id;
    public String name;
    public final static String SQLTYPENAME = "ARTIST_T"; 
    
    public Artist(DBObject dbo)
    {
        id = Integer.parseInt(dbo.get("id").toString());
        name = dbo.get("name").toString();
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return Artist.SQLTYPENAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        id = stream.readInt();
        name = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(id);
        stream.writeString(name);
    }
    
}
