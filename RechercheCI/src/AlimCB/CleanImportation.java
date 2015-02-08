/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlimCB;

import RechCI.MovieDetails;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author kaoutare
 */
public class CleanImportation 
{
    public String removeWhiteSpace(String s)
    {
       String tmp = s.replaceFirst("^\\s++","");
       tmp = tmp.replaceFirst("$\\s++","");
       return tmp;
    }
    
    public MovieDetails importVerify(MovieDetails mD)
    {
        //INTEGER
        if(Integer.parseInt(mD.getId()) == 0)
        {
            mD.setId("NULL");
        }
        
        if(Integer.parseInt(mD.getRuntime()) == 0)
        {
            mD.setRuntime("NULL");
        }
        
        if(Integer.parseInt(mD.getRevenue()) == 0)
        {
            mD.setRevenue("NULL");
        }
        
        if(Integer.parseInt(mD.getBudget()) == 0)
        {
            mD.setBudget("NULL");
        }
        
        if(Integer.parseInt(mD.getVoteAverage()) == 0)
        {
            mD.setVoteAverage("NULL");
        }
        
        if(Integer.parseInt(mD.getVoteCount()) == 0)
        {
            mD.setVoteCount("NULL");
        }
         
        //VARCHAR2
        if(mD.getCertification() == null)
        {
            mD.setCertification("NULL");
        }
        else
        {
            mD.setCertification(this.removeWhiteSpace(mD.getCertification()));
        }
        
        if(mD.getStatus() == null)
        {
            mD.setStatus("NULL");
        }
        else
        {
            mD.setStatus(this.removeWhiteSpace(mD.getStatus()));
        }

        if(mD.getTitle() == null)
        {
            mD.setTitle("NULL");
        }
        else 
        {
            mD.setTitle(this.removeWhiteSpace(mD.getTitle()));
        }

        if(mD.getHomepage() == null)
        {
            mD.setHomepage("NULL");
        }
        else
        {
            mD.setHomepage(this.removeWhiteSpace(mD.getHomepage())); 
        }
        
        if(mD.getOriginalTitle()== null)
        {
            mD.setOriginalTitle("NULL");
        }
        else
        {
            mD.setOriginalTitle(this.removeWhiteSpace(mD.getOriginalTitle()));
        }
        
        if(mD.getOverview() == null)
        {
            mD.setOverview("NULL");
        }
        else
        {
            mD.setOverview(this.removeWhiteSpace(mD.getOverview()));
        }
        
        if(mD.getTagline() == null)
        {
            mD.setTagline("NULL");
        }
        else
        {
            mD.setTagline(this.removeWhiteSpace(mD.getTagline()));
        }
        
        if(mD.getProductionCountries() == null)
        {
            mD.setStatus("NULL");
        }
        else
        {
            mD.setProductionCountries(this.removeWhiteSpace(mD.getProductionCountries())); 
        }
        
        return mD;
    }

    public Set<String> importVerfifyActors(Set <String> sA)
    {
        Set<String> set_A = new HashSet<>();
        
        Iterator<String> it = sA.iterator();
        while (it.hasNext()) 
        {
            String tmp = it.next();
            
            if(tmp == null)
            {
                set_A.add("NULL");
            }
            else
            {
                set_A.add(tmp);
            }
        }
        
        return set_A;
    }
    
    public Set<String> importVerfifyPosterPath(Set <String> sP)
    {
        Set<String> set_P = new HashSet<>();
        
        Iterator<String> it = sP.iterator();
        while (it.hasNext()) 
        {
            String tmp = it.next();
            
            if(tmp == null)
            {
                set_P.add("NULL");
            }
            else
            {
                set_P.add(tmp);
            }
        }
        
        return set_P;
    }
    
    public Set<String> importVerfifyGenres(Set <String> sG)
    {
        Set<String> set_G = new HashSet<>();
        
        Iterator<String> it = sG.iterator();
        while (it.hasNext()) 
        {
            String tmp = it.next();
            
            if(tmp == null)
            {
                set_G.add("NULL");
            }
            else
            {
                set_G.add(tmp);
            }
        }
        
        return set_G;
    }
    
    public Set<String> importVerfifyDirectors(Set <String> sD)
    {
        Set<String> set_D = new HashSet<>();
        
        Iterator<String> it = sD.iterator();
        while (it.hasNext()) 
        {
            String tmp = it.next();
            
            if(tmp == null)
            {
                set_D.add("NULL");
            }
            else
            {
                set_D.add(tmp);
            }
        }
        
        return set_D;
    }
    
    public Set<String> importVerfifyLanguages(Set <String> sL)
    {
        Set<String> set_L = new HashSet<>();
        
        Iterator<String> it = sL.iterator();
        while (it.hasNext()) 
        {
            String tmp = it.next();
            
            if(tmp == null)
            {
                set_L.add("NULL");
            }
            else
            {
                set_L.add(tmp);
            }
        }
        
        return set_L;
    }
    
    public Set<String> importVerfifyCompagnies(Set <String> sC)
    {
        Set<String> set_C = new HashSet<>();
        
        Iterator<String> it = sC.iterator();
        while (it.hasNext()) 
        {
            String tmp = it.next();
            
            if(tmp == null)
            {
                set_C.add("NULL");
            }
            else
            {
                set_C.add(tmp);
            }
        }
        
        return set_C;
    }
}
