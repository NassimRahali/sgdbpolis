/* PACKAGE SPEC */
CREATE OR REPLACE PACKAGE ALIMCB_PKG AS

	PROCEDURE insert_cb
    (
		movie_movi				IN MOVIES_T,
		image_movi				IN IMAGES_T,
		arti_movi				IN ARR_ARTISTS_T,
		direct_movi 			IN ARR_MOVIE_DIRECT_T,
		genre_movi				IN ARR_GENRES_T,
		lang_movi				IN ARR_SPOKEN_LANG_T,
		comp_movi				IN ARR_PRODUCTION_COMPAGNIES_T,
		country					IN COUNTRIES_T,
		numCop_movi				IN INTEGER
    );

	PROCEDURE insert_artists
    (
		artists_art				IN ARR_ARTISTS_T,
		movie_art				IN MOVIES.id%type,
		isActor					IN INTEGER
    );
	
	PROCEDURE insert_genres
    (
		genre_gen				IN ARR_GENRES_T,
		movie_gen				IN MOVIES.id%type
    );
  
	PROCEDURE insert_langs
    (
		lang_lan				IN ARR_SPOKEN_LANG_T,
		movie_lan				IN MOVIES.id%type
    );
  
	PROCEDURE insert_companies
    (
		comp_com				IN ARR_PRODUCTION_COMPAGNIES_T,
		movie_com				IN MOVIES.id%type
    );

	PROCEDURE insert_artist
		(
			id_artist			IN ARTISTS.id%type,
			name_artist			IN ARTISTS.name%type
		);

	PROCEDURE insert_certification
		(
			id_cert 			IN MOVIES.certification%type,
			name_cert			IN CERTIFICATIONS.name%type,
			description_cert	IN CERTIFICATIONS.description%type
			-- SHOULD BE OK
		);
		
	PROCEDURE insert_company
		(
			id_commp			IN COMPANIES.id%type,
			name_comp			IN COMPANIES.name%type
		);
		
	PROCEDURE insert_country
		(
			code_coun			IN MOVIES.production_country%type,
			name_coun			IN COUNTRIES.name%type
		);
		
	PROCEDURE insert_genre
		(
			id_genre			IN GENRES.id%type,
			name_genre			IN GENRES.name%type
		);
		
	PROCEDURE insert_language
		(
			code_lang			IN LANGUAGES.code%type,
			name_lang			IN LANGUAGES.name%type
		);
		
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
		);
		
	PROCEDURE insert_image
		(
			id_img					IN IMAGES.id%type,
			image_img				IN IMAGES.image%type,
			movie_img				IN MOVIES.id%type
		);
		
	PROCEDURE insert_copies
		(
			num_copy_cop			IN COPIES.num_copy%type,
			movie_cop				IN MOVIES.id%type
		);
		
	PROCEDURE insert_production_company
		(
			company_pc				IN COMPANIES.id%type,
			movie_pc				IN MOVIES.id%type
		);
		
	PROCEDURE insert_spoken_lang
		(
			lang_slang				IN LANGUAGES.code%type,
			movie_slang			IN MOVIES.id%type	
		);
		
	PROCEDURE insert_movie_direct
		(
			movie_dir				IN MOVIES.id%type,
			director_dir			IN ARTISTS.id%type
		);

	PROCEDURE insert_movie_genre
		(
			genre_genr				IN GENRES.id%type,
			movi_genr				IN MOVIES.id%type
		);

	PROCEDURE insert_movie_play
		(
			movie_play				IN MOVIES.id%type,
			artist_play				IN ARTISTS.id%type
		);
	
	FUNCTION is_movie_exists() RETURN INTEGER;
END ALIMCB;
/

CREATE OR REPLACE PACKAGE BODY PKG_ALIMCB 
AS -- PACKAGE BODY

	/* DEBUT INSERT_CB */
	PROCEDURE insert_cb
	(
		movie_movi				IN MOVIES_T,
		image_movi				IN IMAGE_T,
		arti_movi				IN ARR_ARTISTS_T,
		direct_movi 			IN ARR_MOVIE_DIRECT_T,
		genre_movi				IN ARR_GENRES_T,
		lang_movi				IN ARR_SPOKEN_LANG_T,
		comp_movi				IN ARR_PRODUCTION_COMPAGNIES_T,
		country_movi			IN COUNTRIES_T,
		nbCop_movi				IN INTEGER
	)
	AS
	BEGIN
		MERGE INTO MOVIES m
		USING dual
		ON (movie_movi.id = m.id)
		WHEN NOT MATCHED THEN
			INSERT (id, title, overview, release_date, vote_average, vote_count,
					certification, production_country, runtime, nb_copies)
			VALUES (movie_movi.id, movie_movi.title, movie_movi.overview, 
					movie_movi.release_date, movie_miv.vote_average, 
					movie_movi.vote_count, movie_movi.certification,
					movie_movi.production_country, movie_movi.runtime,
					movie_movi.nd_copies);	
    
		insert_artists(arti_movi, movie_movi.id, 1);
		insert_artists(direct_movi, movie_movi.id, 0);
		insert_genres(genre_movi, movie_movi.id);
		insert_langs(lang_movi, movie_movi.id);
		insert_companies(comp_movi, movie_movi.id);
	
	IF rCoun.Id IS NOT NULL
    THEN
    BEGIN
      insert_country(country_movi.code, country_movi.name);
      
      EXCEPTION
        WHEN OTHERS THEN NULL;
    END;
    END IF;
    
    add_copies(movie_movi.ide, nbCop_movi);
    
    EXCEPTION
        WHEN OTHERS THEN /* TO DO */
	END insert_cb;
	/* FIN INSERT_CB */

	/* DEBUT INSERT_ARTISTS */
	PROCEDURE insert_artists
    (
		artists_art				IN ARR_ARTISTS_T,
		movie_art				IN MOVIES.id%type,
		isActor					IN INTEGER
    )
	AS
		BEGIN
		IF artists_art IS NOT NULL
		THEN
		  FOR cpt IN 1..artists_art.count LOOP
			  BEGIN
				insert_artist(artists_art(cpt).id, artists_art(cpt).name);
				IF isActor == 1
					THEN
						insert_movie_play(artists_art(cpt).id, movie_art(cpt).id);
					ELSE
						insert_movie_direct(artists_art(cpt).id, movie_art(cpt).id);
				END IF;
				EXCEPTION
				  WHEN OTHERS THEN CONTINUE;
			  END;
		 END LOOP;
		END IF;
		
		EXCEPTION
		  WHEN OTHERS THEN /*TO DO */
	END insert_artists;
	/* FIN INSERT_ARTISTS */

	/* DEBUT INSERT_GENRES */
	PROCEDURE insert_genres
    (
		genre_gen				IN ARR_GENRES_T,
		movie_gen				IN MOVIES.id%type
    )
    )
	/* FIN INSERT_GENRES */
  
	/* DEBUT INSERT_LANGS */
	PROCEDURE insert_langs
    (
		lang_lan				IN ARR_SPOKEN_LANG_T,
		movie_lan				IN MOVIES.id%type
    )
	/* FIN INSERT_LANGS */
  
	/* DEBUT INSERT_COMPAGNIES */
	PROCEDURE insert_companies
    (
		comp_com				IN ARR_PRODUCTION_COMPAGNIES_T,
		movie_com				IN MOVIES.id%type
    )
	/* FIN INSERT_COMPAGNIES */
	
	/* DEBUT ADD_COPIES */
	PROCEDURE add_copies
    (
      Id  IN MOVIES.id%type,
      Nb  IN INTEGER
    )
	/* FIN ADD_COPIES */
	
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
	
	/* DEBUT INSERT_LANGUAGE */
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
	/* FIN INSERT_LANGUAGE */
	
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
	/* FIN INSERT_MOVIE */
	
	/* DEBUT INSERT_IMAGE */
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
	/* FIN INSERT_IMAGE */
	
	/* DEBUT INSERT_COPIES */
	PROCEDURE insert_copies
		(
			num_copy_cop			IN COPIES.num_copy%type,
			movie_cop				IN MOVIES.id%type
		)
	AS
		tmp INTEGER;
	BEGIN
		MERGE INTO COPIES cop
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
	/* FIN INSERT_COPIES */

	/* DEBUT INSERT_PRODUCTION_COMPAGNY */
	PROCEDURE insert_production_company
		(
			company_pc				IN COMPANIES.id%type,
			movie_pc				IN MOVIES.id%type
		)
	AS
	BEGIN
		MERGE INTO PRODUCTION_COMPANIES prod
		USING dual
		ON (MOVIES.id = company_pc AND COMPANIES.id = movie_pc)
		WHEN NOT MATCHED THEN
			INSERT (COMPANIES.id, MOVIES.id) VALUES (company_pc, movie_pc);	
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_production_company;
	/* FIN INSERT_PRODUCTION_COMPAGNY */
	
	/* DEBUT INSERT_SPOKEN_LANG */
	PROCEDURE insert_spoken_lang
		(
			lang_slang			IN LANGUAGES.code%type,
			movie_slang			IN MOVIES.id%type
		)
	AS
	BEGIN
		MERGE INTO SPOKEN_LANG sl
		USING dual
		ON (MOVIES.id = movie_slang AND LANGUAGES.id = lang_slang)
		WHEN NOT MATCHED THEN
			INSERT (LANGUAGES.id, MOVIES.id) VALUES (lang_slang, movie_slang);	
	EXCEPTION
		WHEN OTHERS THEN dbms_output.put_line(SQLERRM);
	END insert_spoken_lang;
	/* FIN INSERT_SPOKEN_LANG */
	
	/* DEBUT INSERT_MOVIE_DIRECT*/
	PROCEDURE insert_movie_direct
		(
			movie_dir				IN MOVIES.id%type,
			director_dir			IN ARTISTS.id%type
		)
	AS
	BEGIN
		MERGE INTO MOVIE_DIRECT md
		USING dual
		ON (MOVIES.id = movie_dir AND ARTISTS.id = director_dir)
		WHEN NOT MATCHED THEN
			INSERT (MOVIES.id, ARTISTS.id) VALUES (movie_dir, director_dir)
	END insert_movie_direct;
	/* FIN INSERT_MOVIE_DIRECT */
	
	/* DEBUT INSERT_MOVIE_GENRE */
	PROCEDURE insert_movie_genre
		(
			genre_genr				IN GENRES.id%type,
			movie_genr				IN MOVIES.id%type
		)
	AS
	BEGIN
		MERGE INTO MOVIE_GENRE
		USING dual
		ON (MOVIE.id = movie_genr AND GENRES.id = genre_genr)
		WHEN NOT MATCHED THEN
			INSERT (MOVIES.id, GENRES.id) VALUES (movie_genr, genre_genr)
	END insert_movie_genre;
	/* FIN INSERT_MOVIE_GENRE */
	
	/* DEBUT INSERT_MOVIE_PLAY */
	PROCEDURE insert_movie_play
		(
			movie_play				IN MOVIES.id%type,
			artist_play				IN ARTISTS.id%type
		)
	AS
	BEGIN
		MERGE INTO MOVIE_PLAY
		USING dual
		ON (MOVIE.id = movie_play AND ARTISTS.id = artist_play)
		WHEN NOT MATCHED THEN
			INSERT (MOVIES.id, ARTISTS.id) VALUES (movie_play, artist_play) 
	END insert_movie_play;
	/* FIN INSERT_MOVIE_PLAY */
	
	FUNCTION is_movie_exists() RETURN INTEGER
	AS
		tmp INTEGER
	BEGIN
	END is_movie_exists;

END ALIMCB;
/