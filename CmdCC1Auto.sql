create or replace
PACKAGE CMDCC1AUTO_PKG AS
	
	PROCEDURE CMD_AUTO(d in DATE);

END CMDCC1AUTO_PKG;

create or replace 
PACKAGE BODY CMDCC1AUTO_PKG AS
	PROCEDURE CMD_AUTO(d in DATE) AS
		xmlBase XMLTYPE;
		j DATE;
		exist NUMBER;
		cpt NUMBER;
		nPlacesOld NUMBER;
		coeff NUMBER;
		commande NUMBER;
		nPlacesNew NUMBER;
		nSeance NUMBER;
		vote_avg NUMBER;
		idFilm NUMBER;
	BEGIN
		-- récupération de la date
		j := d;

		-- pour une semaine
		FOR i in 0 .. 6 LOOP
			-- 1 jour en plus			
			j := j + interval '1' day;

			-- programmation pour ce jour existe ?
			SELECT COUNT(*) INTO exist FROM PROG
				WHERE EXTRACT(DAY FROM JOUR) = EXTRACT(DAY FROM j)
				AND EXTRACT(MONTH FROM JOUR) = EXTRACT(MONTH FROM j)
				AND EXTRACT(YEAR FROM JOUR) = EXTRACT(YEAR FROM j);

			IF exist = 1 THEN
				-- XMLTYPE correspondant au jour
				SELECT PROGRA INTO xmlBase FROM PROG 
					WHERE EXTRACT(DAY FROM JOUR) = EXTRACT(DAY FROM j)
					AND EXTRACT(MONTH FROM JOUR) = EXTRACT(MONTH FROM j)
					AND EXTRACT(YEAR FROM JOUR) = EXTRACT(YEAR FROM j);

				-- pour chaque programmation
        cpt := 1;
				--WHILE xmlBase.existsNode('//seance[' || cpt || ']') = 1 LOOP
					nPlacesOld := xmlBase.EXTRACT('//seance[' || cpt || ']/@places').getNumberVal();
					nSeance := xmlBase.EXTRACT('//seance[' || cpt || ']/trancheHoraire/text()').getNumberVal();
					idFilm := xmlBase.EXTRACT('//seance[' || cpt || ']/film/text()').getNumberVal();

					-- il y a un vote average ?
					SELECT COUNT(VOTE_AVERAGE) INTO exist FROM MOVIES
						WHERE ID = idFilm;

					IF exist = 1 THEN
						SELECT VOTE_AVERAGE INTO vote_avg FROM MOVIES
							WHERE ID = idFilm;
					ELSE
						vote_avg := 4;
					END IF;

					coeff := 20 - (2*vote_avg);
					IF nSeance = 1 OR nSeance = 2 THEN
						coeff := coeff / 2;
					ELSE
						coeff := coeff / 3;
					END IF;

					commande := nPlacesOld / coeff;
					nPlacesNew := nPlacesOld - commande;
          nPlacesNew := TRUNC(nPlacesNew);
          
          UPDATE PROG SET PROGRA = 
            updateXML(PROGRA, '//seance[' || cpt || ']/@places', nPlacesNew)
            WHERE EXTRACT(DAY FROM JOUR) = EXTRACT(DAY FROM j)
            AND EXTRACT(MONTH FROM JOUR) = EXTRACT(MONTH FROM j)
            AND EXTRACT(YEAR FROM JOUR) = EXTRACT(YEAR FROM j);
          
          cpt := cpt + 1;
				--END LOOP;

			END IF;
		END LOOP;

	END CMD_AUTO;
END CMDCC1AUTO_PKG;