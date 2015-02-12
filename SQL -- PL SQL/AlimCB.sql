/* PACKAGE SPEC */
CREATE OR REPLACE PACKAGE ALIMCB_PKG AS

	PROCEDURE insert_artist
		(
			id					IN ARTISTS.id%type,
			name				IN ARTISTS.name%type
		);

	PROCEDURE insert_certification
		(
			id					IN MOVIES.certification%type,
			name				IN CERTIFICATIONS.name%type,
			description			IN CERTIFICATIONS.description%type
			-- SHOULD BE OK
		);
		
	PROCEDURE insert_company
		(
			id					IN COMPANIES.id%type,
			name				IN COMPANIES.name%type
		);
		
	PROCEDURE insert_country
		(
			code				IN MOVIES.production_country%type,
			name				IN COUNTRIES.name%type
		);
		
	PROCEDURE insert_genre
		(
			id					IN GENRES.id%type,
			name				IN GENRES.name%type
		);
		
	PROCEDURE insert_language
		(
			code				IN LANGUAGES.code%type,
			name				IN LANGUAGES.name%type
		);
		
	PROCEDURE insert_movie
		(
			id					IN MOVIES.id%type,
			title				IN MOVIES.title%type,
			overview			IN MOVIES.overview%type,
			released_date		IN MOVIES.released_date%type,
			vote_average		IN MOVIES.vote_average%type,
			vote_count			IN MOVIES.vote_count%type,
			certification		IN MOVIES.certification%type,
			production_country	IN MOVIES.production_country%type,
			runtime				IN MOVIES.runtime%type,
			nb_copies			IN MOVIES.nb_copies%type
		);
		
	PROCEDURE insert_image
		(
			id					IN IMAGES.id%type,
			image				IN IMAGES.image%type,
			movie				IN MOVIES.id%type
		);
		
	PROCEDURE insert_copies
		(
			num_copy			IN COPIES.num_copy%type,
			movie				IN MOVIES.id%type
		);
		
	PROCEDURE insert_production_company
		(
			company				IN COMPANIES.id%type,
			movie				IN MOVIES.id%type
		);
		
	PROCEDURE insert_spoken_lang
		(
			lang				IN LANGUAGES.code%type,
			movie				IN MOVIES.id%type	
		);
		
	PROCEDURE insert_movie_direct
		(
			movie				IN MOVIES.id%type,
			director			IN ARTISTS.id%type
		);

	PROCEDURE insert_movie_genre
		(
			genre				IN GENRES.id%type,
			movie				IN MOVIES.id%type
		);

	PROCEDURE insert_movie_play
		(
			movie				IN MOVIES.id%type,
			artist				IN ARTISTS.id%type
		);
	
	FUNCTION is_movie_exists() RETURN INTEGER;
END ALIMCB;
/

CREATE OR REPLACE PACKAGE BODY PKG_ALIMCB 
AS -- PACKAGE BODY

	/* DEBUT INSERT_ARTIST */
	PROCEDURE insert_artist
		(
			id_artist			IN ARTISTS.id%type,
			name_artist			IN ARTISTS.name%type
		)
	AS
	BEGIN
		MERGE INTO ARTISTS arti
		USING dual
		ON (id = id_artist)
		WHEN NOT MATCHED THEN
			INSERT (id, name) VALUES (id_artist, name_artist);
		
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_artist;
	/* FIN INSERT_ARTIST */

	/* DEBUT INSERT_CERTIFICATION */
	PROCEDURE insert_certification
		(
			id_cert				IN MOVIES.certification%type,
			name_cert			IN CERTIFICATIONS.name%type,
			description_cert	IN CERTIFICATIONS.description%type
		)
	AS
	BEGIN
		MERGE INTO CERTIFICATIONS cert
		USING dual
		ON (id = id_cert)
		WHEN NOT MATCHED THEN
			INSERT (id, name, description) VALUES (id_cert, name_cert, description_cert);
			
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_certification;
	/* FIN INSERT_CERTIFICATION */

	/* DEBUT INSERT_COMPANY */
	PROCEDURE insert_company
		(
			id_comp				IN COMPANIES.id%type,
			name_comp			IN COMPANIES.name%type
		)
	AS
	BEGIN
		MERGE INTO COMPANIES comp
		USING dual
		ON (id = id_comp)
		WHEN NOT MATCHED THEN
			INSERT (id, name) VALUES (id_comp, name_comp);
			
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_company;
	/* FIN INSERT_COMPANY */

	/* DEBUT INSERT_COUNTRY */
	PROCEDURE insert_country
		(
			code_coun			IN MOVIES.production_country%type,
			name_coun			IN COUNTRIES.name%type
		)
	AS
	BEGIN
		MERGE INTO COUNTRIES coun
		USING dual
		ON (code = code_coun)
		WHEN NOT MATCHED THEN
			INSERT (code, name) VALUES (code_coun, name_coun);
			
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_country;
	/* FIND INSERT_COUNTRY */
	
	/* DEBUT INSERT_GENRE */
	PROCEDURE insert_genre
		(
			id_genre			IN GENRES.id%type,
			name_genre			IN GENRES.name%type
		)
	AS
	BEGIN
		MERGE INTO GENRES genr
		USING dual
		ON (id = id_genre)
		WHEN NOT MATCHED THEN
			INSERT (id, name) VALUES (id_genre, name_genre);
			
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_genres;
	/* FIN INSERT_GENRE */
	
	/* DEBUT INSERT_GENRE */
	PROCEDURE insert_language
		(
			code_lang			IN LANGUAGES.code%type,
			name_lang			IN LANGUAGES.name%type
		)
	AS
	BEGIN
		MERGE INTO LANGUAGES lang
		USING dual
		ON (lang.code = code)
		WHEN NOT MATCHED THEN
			INSERT (lang.code, lang.name) VALUES(code, name);
		
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_language;
	/* FIN INSERT_GENRE */
	
	/* DEBUT INSERT_MOVIE */
	PROCEDURE insert_movie
		(
			id_mov					IN MOVIES.id%type,
			title_mov				IN MOVIES.title%type,
			overview_mov			IN MOVIES.overview%type,
			released_date_mov		IN MOVIES.released_date%type,
			vote_average_mov		IN MOVIES.vote_average%type,
			vote_count_mov			IN MOVIES.vote_count%type,
			certification_mov		IN MOVIES.certification%type,
			production_country_mov	IN MOVIES.production_country%type,
			runtime_mov				IN MOVIES.runtime%type,
			nb_copies_mov			IN MOVIES.nb_copies%type
		)
	AS
	BEGIN
		MERGE INTO MOVIES mov
		USING dual
		ON (id = id_mov)
		WHEN NOT MATCHED THEN
			INSERT (id, title, overview, released_date, vote_average, vote_count, certification, production_country, runtime, nb_copies) 
			VALUES (id_mov, title_mov, overview_mov, released_date_mov, vote_average_mov, vote_count_mov, certification_mov, production_country_mov, runtime_mov, nb_copies_mov);
		
		-- EXCEPTION TO DO !!!
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);	
	END insert_movie;
	/* DEBUT INSERT_MOVIE */
	
	PROCEDURE insert_image
		(
			id_img					IN IMAGES.id%type,
			image_img				IN IMAGES.image%type,
			movie_img				IN MOVIES.id%type
		)
	AS
	BEGIN
		MERGE INTO IMAGES img
		USING dual
		ON (id = id_img)
		WHEN NOT MATCHED THEN
			INSERT (IMAGES.id, IMAGES.image, MOVIES.id)
			VALUES (id_img, image_img, movie_img);
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_image;
	
	PROCEDURE insert_copies
		(
			num_copy_cop			IN COPIES.num_copy%type,
			movie_cop				IN MOVIES.id%type
		)
	AS
		tmp INTEGER;
	BEGIN
		MERGE INTO COPIES
		USING dual
		ON (id = movie_cop)
		WHEN NOT MATCHED THEN
			INSERT (num_copy, movie_cop) VALUES (num_copy_cop, movie_cop);
		WHEN MATCHED THEN
			BEGIN
				SELECT num_copy FROM COPIES INTO tmp WHERE id = movie_cop;
				UPDATE COPIES SET num_copy = tmp + num_copy_cop;
			END
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_copies;	

	PROCEDURE insert_production_company
		(
			company_pc				IN COMPANIES.id%type,
			movie_pc				IN MOVIES.id%type
		)
	AS
	BEGIN
		MERGE INTO COPIES
		USING dual
		ON (MOVIES.id = company_pc AND COMPANIES.id = movie_pc)
		WHEN NOT MATCHED THEN
			INSERT (COMPANIES.id, MOVIES.id) VALUES (company_pc, movie_pc);	
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_production_company;
	
	PROCEDURE insert_movie_direct
		(
			movie_dir				IN MOVIES.id%type,
			director_dir			IN ARTISTS.id%type
		)
	AS
	BEGIN
		MERGE INTO MOVIE_DIRECT
		USING dual
		ON (MOVIES.id = movie_dir AND ARTISTS.id = director_dir)
		WHEN NOT MATCHED THEN
			INSERT (MOVIES.id, ARTISTS.id) VALUES (movie_dir, director_dir)
	END insert_movie_direct;
	
	PROCEDURE insert_movie_genre
		(
			genre				IN GENRES.id%type,
			movie				IN MOVIES.id%type
		)
	AS
	BEGIN
		MERGE INTO MOVIE_GENRE
		USING dual
		ON (
	END insert_movie_genre;
	
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