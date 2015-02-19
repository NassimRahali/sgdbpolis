/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.sqldataclasses;

import com.mongodb.DBObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import utils.others.CBHelper;
/**
 *
 * @author Nassim
 */
public class Movie implements SQLData
{
    private final static String SQLTYPENAME = "MOVIE_T";
    public int id;
    public String title;
    public String overview;
    public Date release_date;
    public double vote_average;
    public int vote_count;
    public int runtime;
    public String production_country;
    public List<Language> languages;
    public List<Company> companies;
    public Country country;
    public List<Genre> genres;
    public List<Artist> actors;
    public List<Artist> directors;
    public Certification certif;
    public int copies;
    
    public STRUCT country_str;
    public STRUCT certif_str;
    public ARRAY languages_arr;
    public ARRAY companies_arr;
    public ARRAY genres_arr;
    public ARRAY actors_arr;
    public ARRAY directors_arr;
    
    private String poster_path;
    
    public Movie(DBObject dbo)
    {
        String tmp = null;
        languages = new ArrayList<>();
        companies = new ArrayList<>();
        genres = new ArrayList<>();
        actors = new ArrayList<>();
        directors = new ArrayList<>();
        
        id = Integer.parseInt(dbo.get("_id").toString());
        tmp = dbo.get("title").toString();
        title = CBHelper.CorrectInput(tmp, 100, null);
        
        if(dbo.get("overview") != null)
        {
            tmp = dbo.get("overview").toString();
            overview = CBHelper.CorrectInput(tmp, 1000, "NA");
        }
        
        if(!dbo.get("release_date").toString().isEmpty())
        {
            release_date = CBHelper.CorrectDate(dbo.get("release_date").toString());
        }
        vote_average = Double.parseDouble(dbo.get("vote_average").toString());
        vote_count = Integer.parseInt(dbo.get("vote_count").toString());
        
        if(dbo.get("runtime") != null)
        {
            runtime = Integer.parseInt(dbo.get("runtime").toString());
        }
        

        List<DBObject> pc = (List)dbo.get("production_countries");
        if (pc.size() != 0)
        {
            country = new Country(pc.remove(0));
        }
        else
        {
            country = new Country();
        }
        
        production_country = country.getCode();

        if((List)dbo.get("spoken_languages") != null)
        {    
            List<DBObject> tmp2 = (List)dbo.get("spoken_languages");
            tmp2.forEach(lang ->
            {
                languages.add(new Language(lang));
            });
        }
        
        if((List)dbo.get("production_companies") != null)
        {    
            List<DBObject> tmp2 = (List)dbo.get("production_companies");
            tmp2.forEach(prodc ->
            {
                companies.add(new Company(prodc));
            });
        }
        
        if((List)dbo.get("genres") != null)
        {    
            List<DBObject> tmp2 = (List)dbo.get("genres");
            tmp2.forEach(g ->
            {
                genres.add(new Genre(g));
            });
        }
        
        if((List)dbo.get("actors") != null)
        {    
            List<DBObject> tmp2 = (List)dbo.get("actors");
            tmp2.forEach(act ->
            {
                actors.add(new Artist(act));
            });
        }
        
        if((List)dbo.get("directors") != null)
        {    
            List<DBObject> tmp2 = (List)dbo.get("directors");
            tmp2.forEach(dir ->
            {
                directors.add(new Artist(dir));
            });
        }
        
        if(dbo.get("certification") != null)
        {
            certif = new Certification(dbo.get("certification").toString());
        }
        else
        {
            certif = new Certification("?");
        }
        
        if(dbo.get("poster_path") != null)
        {
            poster_path = dbo.get("poster_path").toString();
        }
    }
    
    public InputStream getPoster()
    {
        String defaulturl = "http://www.channelweb.co.uk/IMG/131/125131/"
          + "cloud-question-mark-original-185x185.jpg?1314176681";
        URL url = null;
        try
        {
            if(poster_path != null)
            {
                url = new URL("http://image.tmdb.org/t/p/w185" + poster_path);
                return url.openStream();
            }
            else
            {
                url = new URL(defaulturl); 
                return url.openStream();
            }
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void initArrayAndStruct(Connection con) throws SQLException
    {
        // COUNTRY_T
        Object[] country_attr = new Object[]
        {
            country.getCode(),
            country.getCountry()
        };
        StructDescriptor struct_desc1 = StructDescriptor.createDescriptor("COUNTRY_T", con);
        country_str = new STRUCT(struct_desc1, con, country_attr);
        
        // CERTIF_T
        StructDescriptor struct_desc2 = StructDescriptor.createDescriptor("CERTIF_T", con);
        Object[] cert_attr = new Object[]
        {
            certif.getName(),
            certif.getDescription()
        };
        certif_str = new STRUCT(struct_desc2, con, cert_attr);
        
        // LANGUAGES_T
        ArrayDescriptor array_desc = ArrayDescriptor.createDescriptor("LANGUAGES_T", con);
        languages_arr = new ARRAY(array_desc, con, languages.toArray());
        
        //COMPANIES_T
        array_desc = ArrayDescriptor.createDescriptor("COMPANIES_T", con);
        companies_arr = new ARRAY(array_desc, con, companies.toArray());
        
        // GENRES_T
        array_desc = ArrayDescriptor.createDescriptor("GENRES_T", con);
        genres_arr = new ARRAY(array_desc, con, genres.toArray());
        
        // ACTORS_T
        array_desc = ArrayDescriptor.createDescriptor("ACTORS_T", con);
        actors_arr = new ARRAY(array_desc, con, actors.toArray());
        
        // DIRECTORS_T
        array_desc = ArrayDescriptor.createDescriptor("DIRECTORS_T", con);
        directors_arr = new ARRAY(array_desc, con, directors.toArray());        
    }
    
    @Override
    public String getSQLTypeName() throws SQLException
    {
        return Movie.SQLTYPENAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException
    {
        id = stream.readInt();
        title = stream.readString();
        overview = stream.readString();
        release_date = stream.readDate();
        vote_average = stream.readDouble();
        vote_count = stream.readInt();
        runtime = stream.readInt();
        production_country = stream.readString();
        languages = (List<Language>) stream.readArray().getArray();
        companies = (List<Company>) stream.readArray().getArray();
        genres = (List<Genre>) stream.readArray().getArray();
        actors = (List<Artist>) stream.readArray().getArray();
        directors = (List<Artist>) stream.readArray().getArray();
        country = (Country) stream.readObject(Country.class);
        certif = (Certification) stream.readObject(Certification.class);
        copies = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeInt(id);
        stream.writeString(title);
        stream.writeString(overview);
        stream.writeDate(release_date);
        stream.writeDouble(vote_average);
        stream.writeInt(vote_count);
        stream.writeInt(runtime);
        stream.writeString(production_country);
        stream.writeArray(languages_arr);
        stream.writeArray(companies_arr);
        stream.writeStruct(country_str);
        stream.writeArray(genres_arr);
        stream.writeArray(actors_arr);
        stream.writeArray(directors_arr);
        stream.writeStruct(certif_str);
        stream.writeInt(copies);
    }
}