--------------------------------------------------------
--  Fichier créé - jeudi-février-19-2015   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package ALIMCB_PKG
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "NASSIM"."ALIMCB_PKG" AS

UniqueConstraintViolated      Exception;
IntegrityConstraintViolated   Exception;
CannotInsertNull              Exception;
CheckConstraintViolated       Exception;

/** http://www.ora-code.com **/
PRAGMA EXCEPTION_INIT(UniqueConstraintViolated, -00001);
PRAGMA EXCEPTION_INIT(IntegrityConstraintViolated, -2291);
PRAGMA EXCEPTION_INIT(CannotInsertNull, -1400);
PRAGMA EXCEPTION_INIT(CheckConstraintViolated, -2290);

PROCEDURE INSERT_CB(movie MOVIE_T, image BLOB);
PROCEDURE INSERT_MOVIE(movie MOVIE_T, image BLOB);
PROCEDURE INSERT_LANGUAGES(lang LANGUAGES_T, movie_id INTEGER);
PROCEDURE INSERT_COMPANIES(companies COMPANIES_T, movie_id INTEGER);
PROCEDURE INSERT_COUNTRY(country COUNTRY_T);
PROCEDURE INSERT_GENRES(genres GENRES_T, movie_id INTEGER);
PROCEDURE INSERT_ACTORS(actors ACTORS_T, movie_id INTEGER);
PROCEDURE INSERT_DIRECTORS(directors DIRECTORS_T, movie_id INTEGER);
FUNCTION INSERT_CERTIF(certif CERTIF_T) RETURN INTEGER;
	
END ALIMCB_PKG;

/
