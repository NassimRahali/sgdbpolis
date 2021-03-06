CREATE OR REPLACE PACKAGE ALIMCC1_PKG AS 

-- Procédure d'insertion + update pour l'avant-dernière
  PROCEDURE INSERT_CC1(mid IN INTEGER, nb_copies IN INTEGER);
  PROCEDURE INSERT_IMAGE(mid IN INTEGER);
  PROCEDURE INSERT_LANGUAGES(mid IN INTEGER);
  PROCEDURE INSERT_COMPANIES(mid IN INTEGER);
  PROCEDURE INSERT_COUNTRIES(mid IN INTEGER);
  PROCEDURE INSERT_GENRES(mid IN INTEGER);
  PROCEDURE INSERT_ACTORS(mid IN INTEGER);
  PROCEDURE INSERT_DIRECTORS(mid IN INTEGER);
  PROCEDURE INSUPD_COPIES(mid IN INTEGER, nb_copies IN INTEGER);
  FUNCTION COPY_MOVIE(mid IN INTEGER) RETURN INTEGER;
  
  -- Pour le job
  PROCEDURE JOBCC1;
  
END ALIMCC1_PKG;