/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package RechCI;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author Thibault
 */
public class MovieToOracle
{
    private Integer Id;
    private String Title;
    private String Overview;
    private Timestamp Released_date;
    private Double Vote_average;
    private Integer Vote_count;
    private String Certification;
    private Integer Runtime;
    
    private Map<Integer, String> Artists;
    private Map<String, String> Languages;
    private Map<Integer, String> Genres;
    private Map<Integer, String> Companies;
    private Map<String, String> ProductionCountry;
    //Certifications ???
    
    private Blob Image;
    
    public MovieToOracle()
    {
        Artists = new HashMap<>();
        Languages = new HashMap<>();
        Genres = new HashMap<>();
        Companies = new HashMap<>();
        ProductionCountry = new HashMap<>();
    }
    
    
    public Integer getId()
    {
        return Id;
    }
    
    public void setId(String Id)
    {
        int id = Integer.parseInt(Id);
        System.out.println("_id : " + id);
        this.Id = id;
    }
    
    public String getTitle()
    {
        return Title;
    }
    
    public void setTitle(String Title)
    {
        System.out.println("title : " + Title);
        Title = Title.trim();
        if(Title.length() > 100)
        {
            Title = Title.substring(0, 99);
        }
        this.Title = Title;
    }
    
    public String getOverview()
    {
        return Overview;
    }
    
    public void setOverview(String Overview)
    {
        System.out.println("overview : " + Overview);
        Overview = Overview.trim();
        if(Overview.length() > 1000)
        {
            Overview = Overview.substring(0, 999);
        }
        this.Overview = Overview;
    }
    
    public Object getReleased_date()
    {
        if(Released_date == null)
        {
            return java.sql.Types.NULL;
        }
        return Released_date;
    }
    
    public void setReleased_date(String RDate)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(RDate);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            System.out.println("date : " + timestamp.toString());
            this.Released_date = timestamp;
        }
        catch(Exception ex)
        {
            this.Released_date = null;
        }
    }
    
    public Object getVote_average()
    {
        if(Vote_average <= 0)
        {
            return java.sql.Types.NULL;
        }
        if(Vote_average == null)
        {
            return java.sql.Types.NULL;
        }
        return Vote_average;
    }
    
    public void setVote_average(String Vote_average)
    {
        try
        {
            double vote = Double.parseDouble(Vote_average);
            System.out.println("vote average : " + vote);
            this.Vote_average = vote;
        } catch(Exception ex)
        {
            this.Vote_average = null;
        }
    }
    
    public Object getVote_count()
    {
        if(Vote_count == null)
        {
            return java.sql.Types.NULL;
        }
        if(Vote_count <= 0)
        {
            return java.sql.Types.NULL;
        }
        return Vote_count;
    }
    
    public void setVote_count(String Vote_count)
    {
        try
        {
            int vote = Integer.parseInt(Vote_count);
            System.out.println("vote count : " + vote);
            this.Vote_count = vote;
        } catch(Exception ex)
        {
            this.Vote_count = null;
        }
    }
    
    public Object getCertification()
    {
        if(Certification == null)
        {
            return java.sql.Types.NULL;
        }
        return Certification;
    }
    
    public void setCertification(String Certification)
    {
        if(Certification == null)
        {
            Certification = "NULL";
        }
        
        Certification = Certification.trim();
        if(Certification.length() > 5)
        {
            Certification = "NULL";
        }
        System.out.println("certification : " + Certification);
        this.Certification = Certification;
    }
    
    public Object getProductionCountry()
    {
        return ProductionCountry;
    }
    
    public void setProduction_country(String Production_country)
    {
        String[] token = Production_country.split("\"name\" : \"");
        String[] tokenID = Production_country.split("\"iso_3166_1\" : \"");
        String[] countries = token[1].split("\"");
        String[] id = tokenID[1].split("\"");
        String country = countries[0];
        String idC = id[0];
        System.out.println("iso : " + idC + " country : " + country);
        this.ProductionCountry.put(idC, country);
    }
    
    public Object getRuntime()
    {
        if(Runtime <= 0)
        {
            return java.sql.Types.NULL;
        }
        if(Runtime == null)
        {
            return java.sql.Types.NULL;
        }
        return Runtime;
    }
    
    public void setRuntime(String Runtime)
    {
        try
        {
            int run = Integer.parseInt(Runtime);
            System.out.println("runtime : " + run);
            this.Runtime = run;
        } catch (Exception ex)
        {
            this.Runtime = null;
        }
    }
    
    public Map<Integer, String> getArtists()
    {
        return Artists;
    }
    
    public void setArtists(String actors)
    {
        String[] tokenID = actors.split("\"id\" : ");
        String[] tokenActors = actors.split("\"name\" : \"");
        for(int i = 0 ; i < tokenActors.length-1 ; i++)
        {
            String[] strAct = tokenActors[i+1].split("\"");
            String actor = strAct[0];
            actor = actor.trim();
            if(actor.length() > 50)
            {
                actor = actor.substring(0, 49);
            }
            String[] strID = tokenID[i+1].split(" , ");
            String idS = strID[0];
            int id = Integer.parseInt(idS);
            Artists.put(id, actor);
            System.out.println("id : " + id + " name : " + actor);
        }
    }
    
    public Map<String, String> getLanguages()
    {
        return Languages;
    }
    
    public void setLanguages(String languages)
    {
        String[] tokenISO = languages.split("\"iso_639_1\" : \"");
        String[] tokenLang = languages.split("\"name\" : \"");
        for(int i = 0 ; i < tokenLang.length-1 ; i++)
        {
            String[] strLang = tokenLang[i+1].split("\"");
            String lang = strLang[0];
            lang = lang.trim();
            if(lang.length() > 20)
            {
                lang = lang.substring(0, 19);
            }
            String[] strISO = tokenISO[i+1].split("\"");
            String ISO = strISO[0];
            Languages.put(ISO, lang);
            System.out.println("iso : " + ISO + " language : " + lang);
        }
    }
    
    public Map<Integer, String> getGenres()
    {
        return Genres;
    }
    
    public void setGenres(String genres)
    {
        String[] tokenID = genres.split("\"id\" : ");
        String[] tokenGenres = genres.split("\"name\" : \"");
        for(int i = 0 ; i < tokenGenres.length-1 ; i++)
        {
            String[] strGenre = tokenGenres[i+1].split("\"");
            String genre = strGenre[0];
            genre = genre.trim();
            if(genre.length() > 16)
            {
                genre = genre.substring(0, 15);
            }
            String[] strID = tokenID[i+1].split(" , ");
            String idS = strID[0];
            int id = Integer.parseInt(idS);
            Artists.put(id, genre);
            System.out.println("id : " + id + " genre : " + genre);
        }
    }
    
    public Map<Integer, String> getCompanies()
    {
        return Companies;
    }
    
    public void setCompanies(String companies)
    {
        String[] tokenID = companies.split("\"id\" : ");
        String[] tokenComp = companies.split("\"name\" : \"");
        for(int i = 0 ; i < tokenComp.length-1 ; i++)
        {
            String[] strComp = tokenComp[i+1].split("\"");
            String comp = strComp[0];
            comp = comp.trim();
            if(comp.length() > 70)
            {
                comp = comp.substring(0, 69);
            }
            String[] strID = tokenID[i+1].split("}");
            String idS = strID[0];
            int id = Integer.parseInt(idS);
            Artists.put(id, comp);
            System.out.println("id : " + id + " Company : " + comp);
        }
    }
    
    public Object getImage()
    {
        if(Image == null)
        {
            return java.sql.Types.NULL;
        }
        return Image;
    }
    
    public void setImage(String img)
    {
        try
        {
            URL url = new URL("http://cf2.imgobject.com/t/p/w185" + img);
            InputStream is = url.openStream();
            byte[] buff = new byte[is.available()];
            
            int n = 0;
            while((n = is.read(buff)) > 0);
            System.out.println("image into blob");
            this.Image = new SerialBlob(buff);
            
        } catch (IOException | SQLException ex)
        {
            this.Image = null;
        }
    }
}
