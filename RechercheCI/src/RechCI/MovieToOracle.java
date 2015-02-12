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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author Thibault
 */
public class MovieToOracle
{
    private int Id;
    private String Title;
    private String Overview;
    private Timestamp Released_date;
    private double Vote_average;
    private int Vote_count;
    private String Certification;
    private String Production_country;
    private int Runtime;
    
    private Map<Integer, String> Artists;
    private Map<String, String> Languages;
    private Map<Integer, String> Genres;
    private Map<Integer, String> Companies;
    //Certifications ???
    
    private Blob Image;
    
    public MovieToOracle()
    {
        Artists = new HashMap<>();
        Languages = new HashMap<>();
        Genres = new HashMap<>();
        Companies = new HashMap<>();
    }
    

    public int getId()
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
        this.Title = Title;
    }

    public String getOverview()
    {
        return Overview;
    }

    public void setOverview(String Overview)
    {
        System.out.println("overview : " + Overview);
        this.Overview = Overview;
    }

    public Timestamp getReleased_date()
    {
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
            java.util.logging.Logger.getLogger(MovieToOracle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public double getVote_average()
    {
        return Vote_average;
    }

    public void setVote_average(String Vote_average)
    {
        double vote = Double.parseDouble(Vote_average);
        System.out.println("vote average : " + vote);
        this.Vote_average = vote;
    }

    public int getVote_count()
    {
        return Vote_count;
    }

    public void setVote_count(String Vote_count)
    {
        int vote = Integer.parseInt(Vote_count);
        System.out.println("vote count : " + vote);
        this.Vote_count = vote;
    }

    public String getCertification()
    {
        return Certification;
    }

    public void setCertification(String Certification)
    {
        System.out.println("certification : " + Certification);
        this.Certification = Certification;
    }

    public String getProduction_country()
    {
        return Production_country;
    }

    public void setProduction_country(String Production_country)
    {
        String[] token = Production_country.split("\"name\" : \"");
        String[] countries = token[1].split("\"");
        String country = countries[0];
        System.out.println(country);
        this.Production_country = country;
    }

    public int getRuntime()
    {
        return Runtime;
    }

    public void setRuntime(String Runtime)
    {
        int run = Integer.parseInt(Runtime);
        System.out.println("runtime : " + run);
        this.Runtime = run;
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
            String[] strID = tokenID[i+1].split("}");
            String idS = strID[0];
            int id = Integer.parseInt(idS);
            Artists.put(id, comp);
            System.out.println("id : " + id + " Company : " + comp);
        }
    }

    public Blob getImage()
    {
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
            Logger.getLogger(MovieToOracle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
