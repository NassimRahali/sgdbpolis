-- Va supprimer le tableau contenant les rowid des films à transférer
CREATE OR REPLACE TRIGGER clear_movies_array
BEFORE INSERT OR UPDATE OF nb_copies ON MOVIES
BEGIN
  MOVIES_CC1_PKG.movies_rowid.delete;
END;
/

-- Sauvegarde les rowid des films à transférer
CREATE OR REPLACE TRIGGER save_movies_to_transfer
AFTER UPDATE OF nb_copies ON MOVIES
FOR EACH ROW
WHEN(NEW.nb_copies > 0)
BEGIN
  MOVIES_CC1_PKG.movies_rowid(MOVIES_CC1_PKG.movies_rowid.count + 1) := :new.rowid;
END;
/

-- Appelle la procédure de transfère de film vers CC1 pour chaque film dont le nb de copie a été modifié
CREATE OR REPLACE TRIGGER alim_cc1_trigger
AFTER UPDATE OF nb_copies ON MOVIES
DECLARE
  
  nb_copies_cc1 INTEGER;
  nb_copies_cb  INTEGER;
  movie_id NUMBER;

BEGIN

  FOR i in 1 .. MOVIES_CC1_PKG.movies_rowid.count LOOP

    SELECT id, nb_copies INTO movie_id, nb_copies_cb
    FROM MOVIES
    WHERE rowid = MOVIES_CC1_PKG.movies_rowid(i);

    nb_copies_cc1 := DBMS_RANDOM.value(low => 0, high => nb_copies_cb / 2);
    ALIMCC1_PKG.INSERT_CC1(movie_id, nb_copies_cc1);

  END LOOP;

END;
/