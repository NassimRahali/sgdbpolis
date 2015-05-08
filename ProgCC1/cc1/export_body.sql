--------------------------------------------------------
--  Fichier créé - mercredi-mai-06-2015   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package Body ALIMCC1_PKG
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "NASSIM"."ALIMCC1_PKG" AS

  -------------
  -- ALIMCC1 --
  -------------
  PROCEDURE INSERT_CC1(mid IN INTEGER, nb_copies IN INTEGER) AS
    film_exist INTEGER;
  BEGIN
    error_pkg.insert_log(nb_copies,'nombre de copies','ALIMCC1_PKG.ALIMCC1');
    IF nb_copies > 0 THEN
      INSERT_COUNTRY(mid);
      film_exist := IS_MOVIE(mid);
      -- Si le film n'existe pas déjà dans CC1, on copie toutes les informations
      IF film_exist = 0 THEN
        error_pkg.insert_log(nb_copies,'nombre de copies','ALIMCC1_PKG.ALIMCC1');
        INSERT_IMAGE(mid);
        INSERT_LANGUAGES(mid);
        INSERT_COMPANIES(mid);
        INSERT_GENRES(mid);
        INSERT_ACTORS(mid);
        INSERT_DIRECTORS(mid);
      END IF;
      -- Màj des copies
      INSUPD_COPIES(mid, nb_copies);

    END IF;
  END INSERT_CC1;

  --------------
  -- IS_MOVIE --
  --------------
  FUNCTION IS_MOVIE(mid IN INTEGER) RETURN INTEGER AS

    movie_data MOVIES%ROWTYPE;
    film_exist INTEGER;
  
  BEGIN

    -- Récupération de toutes les données du film
    SELECT * INTO movie_data
    FROM MOVIES
    WHERE MOVIES.id = mid;

    -- Copie de la certification si elle n'existe pas déjà
    MERGE
      INTO CERTIFICATIONS_CC1 certificationsCC1
      USING
      (
        SELECT *
        FROM CERTIFICATIONS
        WHERE CERTIFICATIONS.id = movie_data.certification
      ) certificationCB
      ON (certificationCB.id = certificationsCC1.id)
      WHEN NOT MATCHED
      THEN
        INSERT(id, name, description)
        VALUES(certificationCB.id, certificationCB.name, certificationCB.description);
    -- Insertion du film
    BEGIN
      INSERT INTO MOVIES_CC1 VALUES movie_data;
      film_exist := 0;
      error_pkg.insert_log(0,'film_exist_val','ALIMCC1_PKG.INSERT_CERT');
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX
          THEN film_exist := 1;
    END;

    RETURN film_exist;

  END IS_MOVIE;

  ----------------------
  -- INSERT_COUNTRIES --
  ----------------------
  PROCEDURE INSERT_COUNTRY(mid IN INTEGER) AS

  movie_data MOVIES%ROWTYPE;
  cntry      COUNTRIES%ROWTYPE;
  BEGIN

  SELECT * INTO movie_data
  FROM MOVIES
  WHERE MOVIES.id = mid;

  SELECT * INTO cntry
  FROM COUNTRIES
  WHERE movie_data.production_country = code;

  error_pkg.insert_log(5,cntry.name,'ALIMCC1_PKG.INSERT_COUNTRY');

  -- Insertion des pays du film
  MERGE
    INTO COUNTRIES_CC1 countriesCC1
    USING
    (
      SELECT *
      FROM COUNTRIES
      WHERE COUNTRIES.code = movie_data.production_country
    ) countriesCB
    ON (countriesCB.code = countriesCC1.code)
    WHEN NOT MATCHED
    THEN
      INSERT(code, name)
      VALUES(countriesCB.code, countriesCB.name);

  END INSERT_COUNTRY;

  ------------------
  -- INSERT_IMAGE --
  ------------------
  PROCEDURE INSERT_IMAGE(mid IN INTEGER) AS

  BEGIN

    MERGE
      INTO IMAGES_CC1 imagesCC1
      USING
      (
        SELECT *
        FROM IMAGES
        WHERE IMAGES.movie = mid
      ) imagesCB
      ON (imagesCB.id = imagesCC1.id)
      WHEN NOT MATCHED
      THEN
        INSERT(id, image, movie)
        VALUES(imagesCB.id, imagesCB.image, imagesCB.movie);

  END INSERT_IMAGE;

  ----------------------
  -- INSERT_LANGUAGES --
  ----------------------
  PROCEDURE INSERT_LANGUAGES(mid IN INTEGER) AS
  
  BEGIN

  -- Insertion des langues du film
  MERGE
    INTO LANGUAGES_CC1 languagesCC1
    USING
    (
      SELECT *
      FROM LANGUAGES
      WHERE LANGUAGES.code IN ( SELECT language
                                FROM SPOKEN_LANG
                                WHERE SPOKEN_LANG.movie = mid)
    ) languagesCB
    ON (languagesCB.code = languagesCC1.code)
    WHEN NOT MATCHED
    THEN
      INSERT(code, name)
      VALUES(languagesCB.code, languagesCB.name);

  -- Insertion des relations "langue parlée dans le film"
  MERGE
    INTO SPOKEN_LANG_CC1 spokenLangCC1
    USING
    (
      SELECT *
      FROM SPOKEN_LANG
      WHERE SPOKEN_LANG.movie = mid
    ) spokenLangCB
    ON (spokenLangCB.movie = spokenLangCC1.movie AND
        spokenLangCB.language = spokenLangCC1.language)
    WHEN NOT MATCHED
    THEN
      INSERT(language, movie)
      VALUES(spokenLangCB.language, spokenLangCB.movie);

  END INSERT_LANGUAGES;

  -------------------
  -- INSERT_ACTORS --
  -------------------
  PROCEDURE INSERT_ACTORS(mid IN INTEGER) AS

    TYPE ARTISTS_RT IS TABLE OF ARTISTS%ROWTYPE;
    TYPE MOVIE_PLAY_RT IS TABLE OF MOVIE_PLAY%ROWTYPE;
    
    actors ARTISTS_RT;
    actors_movie_play MOVIE_PLAY_RT;

  BEGIN

    -- Récupération de tous les acteurs
    SELECT * BULK COLLECT INTO actors
    FROM ARTISTS
    WHERE ARTISTS.ID IN ( SELECT ARTIST
                          FROM MOVIE_PLAY
                          WHERE MOVIE_PLAY.MOVIE = mid);

    -- Insertion des acteurs (l'acteur peut deja exister de par un autre film)
    FOR i IN actors.first .. actors.last LOOP
      BEGIN
        INSERT INTO ARTISTS_CC1 VALUES actors(i);
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX
          THEN NULL;
      END;
    END LOOP;

    -- Insertion des relations "jouer dans le film"
    SELECT * BULK COLLECT INTO actors_movie_play
    FROM MOVIE_PLAY
    WHERE MOVIE_PLAY.movie = mid;

    FOR i IN actors_movie_play.first .. actors_movie_play.last LOOP
      BEGIN
        INSERT INTO MOVIE_PLAY_CC1 VALUES actors_movie_play(i);
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX
          THEN NULL;
      END;
    END LOOP;

  END INSERT_ACTORS;

  ----------------------
  -- INSERT_DIRECTORS --
  ----------------------
  PROCEDURE INSERT_DIRECTORS(mid IN INTEGER) AS
  
  BEGIN

  -- Insertion des directeurs
  MERGE
    INTO ARTISTS_CC1 directorsCC1
    USING
    (
      SELECT *
      FROM ARTISTS
      WHERE ARTISTS.id IN ( SELECT director
                            FROM MOVIE_DIRECT
                            WHERE MOVIE_DIRECT.movie = mid)
    ) directors
    ON (directors.id = directorsCC1.id)
    WHEN NOT MATCHED
    THEN
      INSERT(id, name)
      VALUES(directors.id, directors.name);

  -- Insertion des relations "diriger le film"
  MERGE
    INTO MOVIE_DIRECT_CC1 movieDirectCC1
    USING
    (
      SELECT *
      FROM MOVIE_DIRECT
      WHERE MOVIE_DIRECT.movie = mid
    ) movieDirectCB
    ON (movieDirectCB.movie = movieDirectCC1.movie AND
        movieDirectCB.director = movieDirectCC1.director)
    WHEN NOT MATCHED
    THEN
      INSERT(movie, director)
      VALUES(movieDirectCB.movie, movieDirectCB.director);

  END INSERT_DIRECTORS;

  ----------------------
  -- INSERT_COMPANIES --
  ----------------------
  PROCEDURE INSERT_COMPANIES(mid IN INTEGER) AS
  
  BEGIN

  -- Insertion des companies de production du film
  MERGE
    INTO COMPANIES_CC1 companiesCC1
    USING
    (
      SELECT *
      FROM COMPANIES
      WHERE COMPANIES.id IN ( SELECT company
                              FROM PRODUCTION_COMPANIES
                              WHERE PRODUCTION_COMPANIES.movie = mid)
    ) companiesCB
    ON (companiesCB.id = companiesCC1.id)
    WHEN NOT MATCHED
    THEN
      INSERT(id, name)
      VALUES(companiesCB.id, companiesCB.name);

  -- Insertion des relations "film produit par la companie"
  MERGE
    INTO PRODUCTION_COMPANIES_CC1 productionCompaniesCC1
    USING
    (
      SELECT *
      FROM PRODUCTION_COMPANIES
      WHERE PRODUCTION_COMPANIES.movie = mid
    ) productionCompaniesCB
    ON (productionCompaniesCB.movie = productionCompaniesCC1.movie AND
        productionCompaniesCB.company = productionCompaniesCC1.company)
    WHEN NOT MATCHED
    THEN
      INSERT(company, movie)
      VALUES(productionCompaniesCB.company, productionCompaniesCB.movie);

  END INSERT_COMPANIES;

  -------------------
  -- INSERT_GENRES --
  -------------------
  PROCEDURE INSERT_GENRES(mid IN INTEGER) AS
  
  BEGIN

  -- Insertion des genres du film
  MERGE
    INTO GENRES_CC1 genresCC1
    USING
    (
      SELECT *
      FROM GENRES
      WHERE GENRES.id IN (SELECT genre
                          FROM MOVIE_GENRES
                          WHERE MOVIE_GENRES.movie = mid)
    ) genresCB
    ON (genresCB.id = genresCC1.id)
    WHEN NOT MATCHED
    THEN
      INSERT(id, name)
      VALUES(genresCB.id, genresCB.name);

  -- Insertion des relation "film du genre"
  MERGE
    INTO MOVIE_GENRES_CC1 movieGenresCC1
    USING
    (
      SELECT *
      FROM MOVIE_GENRES
      WHERE MOVIE_GENRES.movie = mid
    ) movieGenresCB
    ON (movieGenresCB.movie = movieGenresCC1.movie AND
        movieGenresCB.genre = movieGenresCC1.genre)
    WHEN NOT MATCHED
    THEN
      INSERT(genre, movie)
      VALUES(movieGenresCB.genre, movieGenresCB.movie);


  END INSERT_GENRES;

  -------------------
  -- INSUPD_COPIES --
  -------------------
  PROCEDURE INSUPD_COPIES(mid IN INTEGER, nb_copies IN INTEGER) AS

    -- TRT signifie Table of Row Type
    TYPE COPIES_TRT IS TABLE OF COPIES%ROWTYPE;

    copies_cb COPIES_TRT;
    i NUMBER := 1;
  
  BEGIN

    -- Récupérations de toutes les copies du film
    SELECT * BULK COLLECT INTO copies_cb
    FROM COPIES
    WHERE COPIES.movie = mid;

    WHILE i <= nb_copies LOOP
      -- transfert de la copie
      INSERT INTO COPIES_CC1 VALUES copies_cb(i);
      -- suppression de la copie de CB
      DELETE FROM COPIES
      WHERE (num_copy = copies_cb(i).num_copy
        AND movie = copies_cb(i).movie);
      i := i + 1;
    END LOOP;

  END INSUPD_COPIES;

  ------------------------------------------------------------------------------
  -- JOBCC1
  ------------------------------------------------------------------------------
  PROCEDURE JOBCC1 AS

    nb_copies_cc1 INTEGER;

  BEGIN

    FOR movie IN (SELECT id, nb_copies
                  FROM MOVIES)
    LOOP
      nb_copies_cc1 := DBMS_RANDOM.value(low => 0, high => movie.nb_copies / 2);
      INSERT_CC1(movie.id, nb_copies_cc1);
    END LOOP;

  END JOBCC1;

END ALIMCC1_PKG;

/
