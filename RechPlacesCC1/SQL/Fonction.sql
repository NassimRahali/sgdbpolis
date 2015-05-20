create or replace 
PACKAGE RECHCC1_PKG AS

	FUNCTION RECH_PROG(d in DATE) RETURN PROJECTIONS_JOUR;

END RECHCC1_PKG;

create or replace 
PACKAGE BODY RECHCC1_PKG AS
	FUNCTION RECH_PROG(d in DATE) RETURN PROJECTIONS_JOUR AS
		xml XMLTYPE;
		jProg DATE;
		film FILM_TYPE;
		prog PROJECTION_TYPE;
		progJour PROJECTIONS_JOUR;
		exist NUMBER;
		nProg NUMBER;
		salle NUMBER;
		horaire NUMBER;
		idFilm NUMBER;
		places NUMBER;
		acteurs VARCHAR2(1000);
		titre VARCHAR2(200);
		cert VARCHAR2(10);
		duree NUMBER;
	BEGIN
		jProg := d;

		SELECT COUNT(*) INTO exist FROM PROG
			WHERE EXTRACT(DAY FROM JOUR) = EXTRACT(DAY FROM jProg)
			AND EXTRACT(MONTH FROM JOUR) = EXTRACT(MONTH FROM jProg)
			AND EXTRACT(YEAR FROM JOUR) = EXTRACT(YEAR FROM jProg);
		
		IF exist = 1 THEN
			-- XMLTYPE correspondant au jour
			SELECT PROGRA INTO xml FROM PROG 
				WHERE EXTRACT(DAY FROM JOUR) = EXTRACT(DAY FROM jProg)
				AND EXTRACT(MONTH FROM JOUR) = EXTRACT(MONTH FROM jProg)
				AND EXTRACT(YEAR FROM JOUR) = EXTRACT(YEAR FROM jProg);

			-- Nombre de programmations ce jour là
			nProg := xml.EXTRACT('count(//seance)').getNumberVal();
		
			-- Pour chaque programmation
			FOR i in 1 .. nProg LOOP
				-- Récupérer infos séance (XPATH)
				salle := xml.EXTRACT('//seance[' || i || ']/salle/text()').getNumberVal();
				horaire := xml.EXTRACT('//seance[' || i || ']/trancheHoraire/text()').getNumberVal();
				idFilm := xml.EXTRACT('//seance[' || i || ']/film/text()').getNumberVal();
				places := xml.EXTRACT('//seance[' || i || ']/@places').getNumberVal();

				-- Récupérer infos film
				SELECT wm_concat(NAME) INTO acteurs FROM MOVIES, MOVIE_DIRECT, ARTISTS
				WHERE MOVIES.ID = MOVIE_DIRECT.MOVIE
				AND MOVIE_DIRECT.DIRECTOR = ARTISTS.ID;

				SELECT TITLE INTO titre FROM MOVIES
				WHERE ID = idFilm;

				SELECT NAME INTO cert FROM MOVIES, CERTIFICATIONS
				WHERE MOVIES.CERTIFICATION = CERTIFICATIONS.ID;

				SELECT RUNTIME INTO duree FROM MOVIES
				WHERE ID = idFilm;

				film := FILM_TYPE(titre, acteurs, cert, duree);
				prog := PROJECTION_TYPE(salle, horaire, places, film);
				progJour.extend(1);
				progJour(progJour.count) := prog;

			END LOOP;
		END IF;

		RETURN progJour;

	END RECH_PROG;
END RECHCC1_PKG;