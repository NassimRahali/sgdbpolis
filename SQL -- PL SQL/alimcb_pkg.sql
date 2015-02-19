/****************** PACKAGE SPEC ******************/
CREATE OR REPLACE PACKAGE ALIMCB_PKG AS

UniqueConstraintViolated      Exception;
IntegrityConstraintViolated   Exception;
CannotInsertNull              Exception;
CheckConstraintViolated       Exception;

/** http://www.ora-code.com **/
-- Should relatively well cover the schema
PRAGMA EXCEPTION_INIT(UniqueConstraintViolated, -00001);
PRAGMA EXCEPTION_INIT(IntegrityConstraintViolated, -2291);
PRAGMA EXCEPTION_INIT(CannotInsertNull, -1400);
PRAGMA EXCEPTION_INIT(CheckConstraintViolated, -2290);

PROCEDURE INSERT_CB(movie MOVIE_T, image BLOB);
PROCEDURE INSERT_MOVIE(movie MOVIE_T, image BLOB);
PROCEDURE INSERT_LANGUAGES(lang LANGUAGES_T, movie_id INTEGER);
PROCEDURE INSERT_COMPANIES(companies COMPANIES_T, movie_id INTEGER);
PROCEDURE INSERT_COUNTRY(country COUNTRY_T, movie_id INTEGER);
PROCEDURE INSERT_GENRES(genres GENRES_T, movie_id INTEGER);
PROCEDURE INSERT_ACTORS(actors ACTORS_T, movie_id INTEGER);
PROCEDURE INSERT_DIRECTORS(directors DIRECTORS_T, movie_id INTEGER);
FUNCTION INSERT_CERTIF(certif CERTIF_T) RETURN INTEGER;
	
END ALIMCB_PKG;
/

/****************** PACKAGE BODY ******************/
create or replace PACKAGE BODY ALIMCB_PKG AS

  /* --------- */
  /* INSERT_CB */
  /* --------- */
  PROCEDURE INSERT_CB(movie MOVIE_T, image BLOB) AS
    movie_found   NUMBER(1);
    old_nb_copies INTEGER;
  BEGIN
    SELECT COUNT(*) INTO movie_found FROM MOVIES WHERE id = movie.movie_id;
    IF movie_found = 0 THEN
      old_nb_copies := 0;
      INSERT_MOVIE(movie, image);
    ELSE
      SELECT nb_copies INTO old_nb_copies FROM MOVIES WHERE id = movie.movie_id;
    END IF;

    UPDATE MOVIES SET nb_copies = (old_nb_copies + movie.copies) WHERE id = movie.movie_id;
    FOR cpt IN (old_nb_copies + 1)..(old_nb_copies + movie.copies) LOOP
      INSERT INTO COPIES VALUES(cpt, movie.movie_id);
    END LOOP;
    COMMIT;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK not unique', 'ALIMCB_PKG.INSERT_CB');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_CB');
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_CB');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error : ' || SQLCODE, 'ALIMCB_PKG.INSERT_CB');
  END INSERT_CB;

  /* ------------ */
  /* INSERT_MOVIE */
  /* ------------ */
  PROCEDURE INSERT_MOVIE(movie MOVIE_T, image BLOB) AS
    runtime     INTEGER;
    vote_count  INTEGER;
    vote_avg    INTEGER;
    cert_id     INTEGER;
  BEGIN
    IF movie.runtime <= 0 THEN
      runtime := NULL;
    ELSE
      runtime := movie.runtime;
    END IF;

    IF movie.vote_count <= 0 THEN
      vote_count := NULL;
      vote_avg := NULL;
    ELSE
      vote_count := movie.vote_count;
      vote_avg := movie.vote_average;
    END IF;

    IF movie.cert IS NOT NULL THEN
      cert_id := INSERT_CERTIF(movie.cert);
    ELSE
      cert_id := NULL;
    END IF;

    INSERT INTO MOVIES VALUES (movie.movie_id, movie.title, movie.overview, movie.release_date,
      vote_avg, vote_count, cert_id, movie.production_country, runtime, movie.copies);

    INSERT INTO IMAGES VALUES (SEQUENCE_IMAGE.NEXTVAL, image, movie.movie_id);
    INSERT_LANGUAGES(movie.languages, movie.movie_id);
    INSERT_COMPANIES(movie.companies, movie.movie_id);
    INSERT_COUNTRY(movie.country);
    INSERT_GENRES(movie.genres, movie.movie_id);
    INSERT_ACTORS(movie.actors, movie.movie_id);
    INSERT_DIRECTORS(movie.directors, movie.movie_id);
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK not unique', 'ALIMCB_PKG.INSERT_MOVIE');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_MOVIE');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%RUNTIME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'runtime must be positive : ' || movie.runtime, 'ALIMCB_PKG.INSERT_MOVIE');
      ELSIF SQLERRM LIKE '%VOTE_COUNT%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'vote_count must be >= 0', 'ALIMCB_PKG.INSERT_MOVIE');
      ELSIF SQLERRM LIKE '%VOTE_AVERAGE%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'vote_average must be between 0 and 10','ALIMCB_PKG.INSERT_MOVIE');
      ELSIF SQLERRM LIKE '%NB_COPIES%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'nb_copies must not be NULL and must be >= 0', 'ALIMCB_PKG.INSERT_MOVIE');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_MOVIE');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_MOVIE');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLCODE, 'ALIMCB_PKG.INSERT_MOVIE');
  END INSERT_MOVIE;

  /* ---------------- */
  /* INSERT_LANGUAGES */
  /* ---------------- */
  PROCEDURE INSERT_LANGUAGES(lang LANGUAGES_T, movie_id INTEGER) AS
    lang_found  INTEGER;
  BEGIN
    FOR i IN 1 .. lang.COUNT LOOP
      SELECT COUNT(*) INTO lang_found FROM LANGUAGES WHERE code LIKE lang(i).code;

      IF lang_found = 0 THEN
        INSERT INTO LANGUAGES VALUES(lang(i).code, lang(i).name);
        INSERT INTO SPOKEN_LANG VALUES(lang(i).code, movie_id);
      END IF;
    END LOOP;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_LANGUAGES');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_LANGUAGES');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Lang name must not be NULL', 'ALIMCB_PKG.INSERT_LANGUAGES');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_LANGUAGES');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_LANGUAGES');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_LANGUAGES');
  END INSERT_LANGUAGES;

  /* ---------------- */
  /* INSERT_COMPANIES */
  /* ---------------- */
  PROCEDURE INSERT_COMPANIES(companies COMPANIES_T, movie_id INTEGER) AS
  company_found INTEGER;
  BEGIN
    FOR i IN 1 .. companies.COUNT LOOP
      SELECT COUNT(*) INTO company_found FROM COMPANIES WHERE imagecv od = companies(i).id;

      IF company_found = 0 THEN
        INSERT INTO COMPANIES VALUES(companies(i).id, companies(i).name);
        INSERT INTO PRODUCTION_COMPANIES VALUES(companies(i).id, movie_id);
      END IF;
    END LOOP;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_COMPANIES');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_COMPANIES');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Company name must not be NULL', 'ALIMCB_PKG.INSERT_COMPANIES');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_COMPANIES');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_COMPANIES');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_COMPANIES');
  END INSERT_COMPANIES;

  /* -------------- */
  /* INSERT_COUNTRY */
  /* -------------- */
  PROCEDURE INSERT_COUNTRY(country COUNTRY_T) AS
    country_found INTEGER;
  BEGIN
    SELECT COUNT(*) INTO country_found FROM COUNTRIES WHERE code LIKE country.code;

    IF country_found = 0 THEN
      INSERT INTO COUNTRIES VALUES(country.code, country.name);
    END IF;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_COUNTRY');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_COUNTRY');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Country name must not be NULL', 'ALIMCB_PKG.INSERT_COUNTRY');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_COUNTRY');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_COUNTRY');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_COUNTRY');
  END INSERT_COUNTRY;

  /* ------------- */
  /* INSERT_GENRES */
  /* ------------- */
  PROCEDURE INSERT_GENRES(genres GENRES_T, movie_id INTEGER) AS
    genre_found INTEGER;
  BEGIN
    FOR i IN 1 .. genres.COUNT LOOP
      SELECT COUNT(*) INTO genre_found FROM GENRES WHERE id = genres(i).genre_id;

      IF genre_found = 0 THEN
        INSERT INTO GENRES VALUES(genres(i).genre_id, genres(i).genre_name);
        INSERT INTO MOVIE_GENRES VALUES(genres(i).genre_id, movie_id);
      END IF;
    END LOOP;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_GENRES');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_GENRES');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Genre name is invalid', 'ALIMCB_PKG.INSERT_GENRES');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_GENRES');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_GENRES');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_GENRES');
  END INSERT_GENRES;  

  /* ------------- */
  /* INSERT_ACTORS */
  /* ------------- */
  PROCEDURE INSERT_ACTORS(actors ACTORS_T, movie_id INTEGER) AS
    actor_found INTEGER;
  BEGIN
    FOR i IN 1 .. actors.COUNT LOOP
      SELECT COUNT(*) INTO actor_found FROM ARTISTS WHERE id = actors(i).artist_id;

      IF actor_found = 0 THEN
        INSERT INTO ARTISTS VALUES(actors(i).artist_id, actors(i).name);
        INSERT INTO MOVIE_PLAY VALUES(movie_id, actors(i).artist_id);
      END IF;
    END LOOP;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_ACTORS');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_ACTORS');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Actor name is invalid', 'ALIMCB_PKG.INSERT_ACTORS');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_ACTORS');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_ACTORS');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_ACTORS');
  END INSERT_ACTORS;   

  /* ---------------- */
  /* INSERT_DIRECTORS */
  /* ---------------- */
  PROCEDURE INSERT_DIRECTORS(directors DIRECTORS_T, movie_id INTEGER) AS
    director_found INTEGER;
  BEGIN
    FOR i IN 1 .. directors.COUNT LOOP
      SELECT COUNT(*) INTO director_found FROM ARTISTS WHERE id = directors(i).artist_id;

      IF director_found = 0 THEN
        INSERT INTO ARTISTS VALUES(directors(i).artist_id, directors(i).name);
        INSERT INTO MOVIE_DIRECT VALUES(movie_id, directors(i).artist_id);
      END IF;
    END LOOP;
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_DIRECTORS');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_DIRECTORS');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Director name is invalid', 'ALIMCB_PKG.INSERT_DIRECTORS');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_DIRECTORS');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_DIRECTORS');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_DIRECTORS');
  END INSERT_DIRECTORS;   
  
  /* ------------- */
  /* INSERT_CERTIF */
  /* ------------- */
  FUNCTION INSERT_CERTIF(certif CERTIF_T) RETURN INTEGER IS
    certif_found  INTEGER;
    certif_id     INTEGER;
  BEGIN
    IF NOT certif.name IN ('G', 'PG', 'PG-13', 'R', 'NC-17') THEN
      certif_id := NULL;
      RETURN certif_id;
    END IF;

    SELECT COUNT(*) INTO certif_found FROM CERTIFICATIONS WHERE name LIKE certif.name;

    IF certif_found = 0 THEN
      INSERT INTO CERTIFICATIONS(id, name) VALUES(SEQUENCE_CERTIF.NEXTVAL, certif.name);
      RETURN SEQUENCE_CERTIF.CURRVAL;
    END IF; 

    SELECT id INTO certif_id FROM CERTIFICATIONS WHERE name LIKE certif.name;
    RETURN certif_id;
    
  EXCEPTION
    WHEN UniqueConstraintViolated THEN
      ERROR_PKG.INSERT_LOG(sqlcode, 'PK must be unique', 'ALIMCB_PKG.INSERT_CERTIF');
    WHEN IntegrityConstraintViolated THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Missing ref.', 'ALIMCB_PKG.INSERT_CERTIF');
    WHEN CheckConstraintViolated THEN
      IF SQLERRM LIKE '%NAME%' THEN
        ERROR_PKG.INSERT_LOG(sqlcode, 'Certification name is invalid', 'ALIMCB_PKG.INSERT_CERTIF');
      ELSE
        ERROR_PKG.INSERT_LOG(sqlcode, 'Other error ' || SQLERRM, 'ALIMCB_PKG.INSERT_CERTIF');
      END IF;
    WHEN CannotInsertNull THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'NULL field', 'ALIMCB_PKG.INSERT_CERTIF');
    WHEN OTHERS THEN 
      ERROR_PKG.INSERT_LOG(sqlcode, 'Other error' || SQLERRM, 'ALIMCB_PKG.INSERT_CERTIF');
  END INSERT_CERTIF;

END ALIMCB_PKG;