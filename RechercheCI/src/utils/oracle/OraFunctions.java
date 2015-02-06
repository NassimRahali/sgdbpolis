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
public interface OraFunctions
{
    //<editor-fold defaultstate="collapsed" desc="IS_MOVIE_EXISTS">
    /*
        1:  isExists            Boolean
        2:  id                  Int
    */
    final String IS_MOVIE_EXISTS = 
      "{? = call ALIMCB.is_movie_exists(?)}";
    //</editor-fold>
}
