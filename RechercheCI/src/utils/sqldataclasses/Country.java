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
public class Country implements SQLData
{
    public String code;
    public String country;

    public final static String SQLTYPENAME = "COUNTRY_T";

    public Country()
    {
        code = "??";
        country = "NULL";
    }
    
    public Country(DBObject object)
    {
        code = object.get("iso_3166_1").toString();
        country = object.get("name").toString();
    }

    public String getCode()
    {
        return code;
    }

    public String getCountry()
    {
        return country;
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return Country.SQLTYPENAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        code = stream.readNString();
        country = stream.readNString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeNString(code);
        stream.writeNString(country);
    }
}
