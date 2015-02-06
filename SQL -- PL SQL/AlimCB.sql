CREATE OR REPLACE PACKAGE ALIMCB AS
	PROCEDURE insert_artists();
	PROCEDURE insert_certifications();
	PROCEDURE insert_companies();
	PROCEDURE insert_countries();
	PROCEDURE insert_genres();
	PROCEDURE insert_languages();
	PROCEDURE insert_movies();
	PROCEDURE insert_images();
	PROCEDURE insert_copies();
	PROCEDURE insert_production_companies();
	PROCEDURE insert_spoken_lang();
	PROCEDURE insert_movie_direct();
	PROCEDURE insert_movie_genres();
	PROCEDURE insert_movie_play();
	
	FUNCTION is_movie_exists() RETURN INTEGER;
END ALIMCB;
/

CREATE OR REPLACE PACKAGE BODY ALIMCB AS

	PROCEDURE insert_artists()
	AS
	BEGIN
	END insert_artists;

	PROCEDURE insert_certifications()
	AS
	BEGIN
	END insert_certifications;

	PROCEDURE insert_companies()
	AS
	BEGIN
	END insert_companies;

	PROCEDURE insert_countries()
	AS
	BEGIN
	END insert_countries;

	PROCEDURE insert_genres()
	AS
	BEGIN
	END insert_genres;

	PROCEDURE insert_languages()
	AS
	BEGIN
	END insert_languages;

	PROCEDURE insert_movies()
	AS
	BEGIN
	END insert_movies;

	PROCEDURE insert_images()
	AS
	BEGIN
	END insert_images;
	
	PROCEDURE insert_copies()
	AS
	BEGIN
	END insert_copies;	

	PROCEDURE insert_production_companies()
	AS
	BEGIN
	END insert_production_companies;
	
	PROCEDURE insert_movie_direct()
	AS
	BEGIN
	END insert_movie_direct;
	
	PROCEDURE insert_movie_genres()
	AS
	BEGIN
	END insert_movie_genres;
	
	PROCEDURE insert_movie_play()
	AS
	BEGIN
	END insert_movie_play;
	
	FUNCTION is_movie_exists() RETURN INTEGER
	AS
		tmp INTEGER
	BEGIN
	END is_movie_exists;

END ALIMCB;
/