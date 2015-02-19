DROP TYPE MOVIE_T FORCE;
DROP TYPE ARTIST_T FORCE;
DROP TYPE ACTORS_T FORCE;
DROP TYPE DIRECTORS_T FORCE;
DROP TYPE LANGUAGE_T FORCE;
DROP TYPE LANGUAGES_T FORCE;
DROP TYPE GENRE_T FORCE;
DROP TYPE GENRES_T FORCE;
DROP TYPE COUNTRY_T FORCE;
DROP TYPE COMPANY_T FORCE;
DROP TYPE COMPANIES_T FORCE;
DROP TYPE CERTIF_T FORCE;

/* ------- */
/* MOVIE_T */
/* ------- */
CREATE OR REPLACE TYPE MOVIE_T AS OBJECT 
  ( 
    movie_id            INTEGER,
    title               VARCHAR2(100 CHAR),
    overview            VARCHAR2(1000 CHAR),
    release_date        DATE,
    vote_average        NUMBER(3,1),
    vote_count          NUMBER(6),
    runtime             NUMBER(3,0),
    production_country  CHAR (2 CHAR),
    languages           LANGUAGES_T,
    companies           COMPANIES_T,
    country             COUNTRY_T,
    genres              GENRES_T,
    actors              ACTORS_T,
    directors           DIRECTORS_T,
    cert                CERTIF_T,
    copies              INTEGER
  );
/

/* -------------------------------- */
/* ARTIST_T, ACTORS_T & DIRECTORS_T */
/* -------------------------------- */
CREATE OR REPLACE TYPE ARTIST_T AS OBJECT 
  (
    artist_id   NUMBER(7),
    name        VARCHAR2(50 CHAR)
  );
/
CREATE OR REPLACE TYPE ACTORS_T IS TABLE OF ARTIST_T;
/
CREATE OR REPLACE TYPE DIRECTORS_T IS TABLE OF ARTIST_T;
/

/* ------------------------ */
/* LANGUAGE_T & LANGUAGES_T */
/* ------------------------ */
CREATE OR REPLACE TYPE LANGUAGE_T AS OBJECT 
  (
    code CHAR(2 CHAR),
    name VARCHAR2(20 CHAR)
  );
/
CREATE OR REPLACE TYPE LANGUAGES_T IS TABLE OF LANGUAGE_T;
/

/* ------------------ */
/* GENRE_T & GENRES_T */
/* ------------------ */
CREATE OR REPLACE TYPE GENRE_T AS OBJECT 
  (
    genre_id    NUMBER(5,0),
    genre_name  VARCHAR2(16 CHAR)
  );
/
CREATE OR REPLACE TYPE GENRES_T AS TABLE OF GENRE_T;
/

/* ----------*/
/* COUNTRY_T */
/* ----------*/
CREATE OR REPLACE TYPE COUNTRY_T AS OBJECT 
  (
    code CHAR(2 CHAR),
    name VARCHAR2(40 CHAR)
  );
/

/* ----------------------- */
/* COMPANY_T & COMPANIES_T */
/* ----------------------- */
CREATE OR REPLACE TYPE COMPANY_T AS OBJECT 
  (
    id    NUMBER(5,0),
    name  VARCHAR2(70 CHAR)
  );
/
CREATE OR REPLACE TYPE COMPANIES_T IS TABLE OF COMPANY_T;
/

/* ---------*/
/* CERTIF_T */
/* ---------*/
CREATE OR REPLACE TYPE CERTIF_T AS OBJECT 
  (
    name        VARCHAR2 (5 CHAR),
    description VARCHAR2 (250 CHAR)
  );
/