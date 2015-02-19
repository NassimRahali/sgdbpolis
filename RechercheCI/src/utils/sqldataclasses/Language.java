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
public class Language implements SQLData
{
    public String code;
    public String language;
    public final static String SQLTYPENAME = "LANGUAGE_T";
    
    public Language(DBObject dbo)
    {
        code = dbo.get("iso_639_1").toString();
        language = dbo.get("name").toString();
        if(language.contains((CharSequence)"?") || language.isEmpty())
        {
            language = "NA";
        }
    }  
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return Language.SQLTYPENAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        code = stream.readString();
        language = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeString(code);
        stream.writeString(language);
    }
    
}
