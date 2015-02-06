/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package utils.oracle;

/**
 *
 * @author Nassim
 */
public interface OraProcedures
{
    //<editor-fold defaultstate="collapsed" desc="ARTISTS">
    /*
        1:  id                  Int
        2:  name                String
    */
    final String INSERT_ARTISTS = 
      "{call ALIMCB.insert_artists(?, ?)}";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CERTIFICATONS">
    /*
        1:  id                  Int
        2:  name                String
        3:  description         String
    */
    final String INSERT_CERTIFICATIONS = 
      "{call ALIMCB.insert_certifications(?, ?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="COMPANIES">
    /*
        1:  id                  Int
        2:  name                String
    */
    final String INSERT_COMPANIES = 
      "{call ALIMCB.insert_COMPANIES(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="COUNTRIES">
    /*
        1:  code                String
        2:  name                String
    */
    final String INSERT_COUNTRIES = 
      "{call ALIMCB.insert_countries(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GENRES">
    /*
        1:  id                  Int
        2:  name                String
    */
    final String INSERT_GENRES = 
      "{call ALIMCB.insert_genres(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="LANGUAGES">
    /*
        1:  code                String
        2:  name                String
    */
    final String INSERT_LANGUAGES = 
      "{call ALIMCB.insert_languages(?, ?)}";
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="MOVIES">
    /*
        1:  id                   Int
        2:  title                String
        3:  overview             String
        4:  released_date        Date
        5:  vote_average         Float
        6:  vote_count           Int
        7:  certification        String
        8:  production_country   String
        9:  runtime              Int
        10: nb_copies            Int
    */
    final String INSERT_MOVIES = 
      "{call ALIMCB.insert_movie(?,?,?,?,?,?,?,?,?,?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="IMAGES">
    /*
        1:  id                  Int
        2:  image               BLOB
        3:  movie               Int
    */
    final String INSERT_IMAGES = 
      "{call ALIMCB.insert_images(?, ?, ?)}";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="COPIES">
    /*
        1:  num_copy            Int
        2:  movie               Int
    */
    final String INSERT_COPIES = 
      "{call ALIMCB.insert_copies(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PRODUCTION_COMPANIES">
    /*
        1:  company             Int
        2:  movie               Int
    */
    final String INSERT_PRODUCTION_COMPANIES = 
      "{call ALIMCB.insert_production_companies(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SPOKEN_LANG">
    /*
        1:  language            String
        2:  movie               Int
    */
    final String INSERT_SPOKEN_LANG = 
      "{call ALIMCB.insert_spoken_lang(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="MOVIE_DIRECT">
    /*
        1:  movie               Int
        2:  director            Int
    */
    final String INSERT_MOVIE_DIRECT = 
      "{call ALIMCB.insert_movie_direct(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="MOVIE_GENRES">
    /*
        1:  genre               Int
        2:  movie               Int
    */
    final String INSERT_MOVIE_GENRES = 
      "{call ALIMCB.insert_movie_genres(?, ?)}";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="MOVIE_PLAY">
    /*
        1:   movie              Int
        2:   artist             Int
    */
    final String INSERT_MOVIE_PLAY =
      "{call ALIMCB.insert_movie_play(?,?)}";
    //</editor-fold>
}
