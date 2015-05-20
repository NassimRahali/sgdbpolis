Types
-----

create or replace TYPE FILM_TYPE AS OBJECT
(
	titre VARCHAR2(200 CHAR),
	acteurs VARCHAR2(1000 CHAR),
	certification VARCHAR2(10 CHAR),
	duree NUMBER
);

create or replace TYPE PROJECTION_TYPE AS OBJECT
(
	salle NUMBER,
	seance NUMBER,
	placesDispo NUMBER,
	film FILM_TYPE
);

create or replace TYPE PROJECTIONS_JOUR AS TABLE OF PROJECTION_TYPE;