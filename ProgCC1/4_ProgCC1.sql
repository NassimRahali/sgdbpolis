create or replace 
PROCEDURE           "PROGCC1" (fname IN SYS.scheduler_filewatcher_result) AS 
  prog          XMLTYPE; -- Fichier dans XMLDIR pour programmer les séances
  schema_url    VARCHAR2(50); -- XSD
  count_n       NUMBER(3) := 1; -- Compteur
  date_seance     VARCHAR2(11); -- Date courante du fichier de programmation
  valid         NUMBER(2);
  prog_alrdy    NUMBER(2);
  seances       XMLTYPE;
  prog_to_ins   XMLTYPE;
  current_seance XMLTYPE;
  salle varchar2(50);
  trancheHoraire varchar2(50);
  film varchar2(50);
  copie varchar2(50);
  seance_exist number(1);
  copie_is_used number(1);
  day_prog XMLTYPE;
  msg varchar(100);
  doc_feedback dbms_xmldom.DOMDocument;
  doc_insert dbms_xmldom.DOMDocument;
  doc_root_no dbms_xmldom.DOMNode;
  d_seances_el dbms_xmldom.DOMElement;
  d_seances_no dbms_xmldom.DOMNode;
  d_seance_el dbms_xmldom.DOMElement;
  d_seance_no dbms_xmldom.DOMNode;
  d_salle_el dbms_xmldom.DOMElement;
  d_salle_no dbms_xmldom.DOMNode;
  d_salle_txt dbms_xmldom.DOMText;
  d_salle_txtno dbms_xmldom.DOMNode;
  d_heure_el dbms_xmldom.DOMElement;
  d_heure_no dbms_xmldom.DOMNode;
  d_heure_txt dbms_xmldom.DOMText;
  d_heure_txtno dbms_xmldom.DOMNode;
  d_film_el dbms_xmldom.DOMElement;
  d_film_no dbms_xmldom.DOMNode;
  d_film_txt dbms_xmldom.DOMText;
  d_film_txtno dbms_xmldom.DOMNode;
  d_copie_el dbms_xmldom.DOMElement;
  d_copie_no dbms_xmldom.DOMNode;
  d_copie_txt dbms_xmldom.DOMText;
  d_copie_txtno dbms_xmldom.DOMNode;
  feedback_file varchar(100);
  feedback_xml XMLTYPE;
  feedback_str  varchar(2000);
  copy_id number(3);
  siege number(3);
BEGIN
  schema_url := 'cours/proj.xsd';
  prog := XMLTYPE(BFILENAME ('XMLDIR', fname.actual_file_name), nls_charset_id('AL32UTF8'));
  valid := prog.isSchemaValid(schema_url);
  feedback_file := replace(fname.actual_file_name, '.xml') || '_feedback.xml';
  
  feedback_str := '<?xml version="1.0"?><seances>';
  WHILE prog.existsNode('//seance[' || count_n || ']') = 1 and valid = 1 LOOP
    -- On récupère la séance
    current_seance := prog.extract('//seance[' || count_n || ']');

    -- On récupère la date de la séance séance.
    date_seance := prog.extract('//seance[' || count_n || ']/jour/text()').getStringVal(); 
    
    -- On récupère les infos de la séance
    salle := prog.extract('//seance[' || count_n || ']/salle/text()').getStringVal();
    trancheHoraire := prog.extract('//seance[' || count_n || ']/trancheHoraire/text()').getStringVal();
    film := prog.extract('//seance[' || count_n || ']/film/text()').getStringVal();
    copie := prog.extract('//seance[' || count_n || ']/copie/text()').getStringVal();

    -- On récupère aussi le nombre de siège correspondant à la salle    
    select seats into siege from rooms where id = salle;
    
    -- Vérification de l'existence du film dans CC1
    select count(1) into copy_id from copies where movie = film and num_copy = copie;
    
    if copy_id = 0 then
      msg := 'Copy not disponible in CC1';
    end if;
    
    trace(msg);
    
    -- Pour le fichier feedback
    feedback_str := feedback_str 
                || '<seance>'
                  || '<jour>' || date_seance || '</jour>'
                  || '<salle>' || salle || '</salle>' 
                  || '<trancheHoraire>' || trancheHoraire || '</trancheHoraire>'
                  || '<film>' || film || '</film>'
                  || '<copie>' || copie || '</copie>';
    
    -- On vérifie si on a déjà une programmation pour ce jour
    SELECT COUNT(JOUR) INTO prog_alrdy FROM PROG WHERE JOUR = TO_DATE(date_seance, 'YYYY/MM/DD');
    
    IF prog_alrdy = 1 THEN
      -- Si on a déjà une progra à ce jour, on la récupère
      select progra into day_prog from prog where JOUR = TO_DATE(date_Seance, 'YYYY/MM/DD');
      
      -- Pour savoir si la tranche horaire et la salle sont déjà prises.
      seance_exist := day_prog.existsNode('//seance[./salle = ' 
                                                    || salle || 
                                                    ' and ./trancheHoraire = ' 
                                                    || trancheHoraire || ']');
      -- Pour savoir si la copie de ce film est utilisée en même temps.
      copie_is_used := day_prog.existsNode('//seance[./trancheHoraire = ' 
                                                      || trancheHoraire ||
                                                      ' and ./film = ' 
                                                      || film ||
                                                      ' and ./copie = '
                                                      || copie || ']');
      
      -- On update avec la séance si les conditions sont remplies
      -- On supprime également la date qui n'est pas utile.
      if seance_exist = 0 and copie_is_used = 0 and copy_id = 1 then
        update prog 
        set progra = insertChildXML(progra, '//seances', 'seance', current_seance)
        where JOUR = TO_DATE(date_seance, 'YYYY/MM/DD');
        update prog 
        set progra = deleteXML(progra, '//jour') 
        where JOUR = TO_DATE(date_seance, 'YYYY/MM/DD');
        msg := 'Insertion ok.';
      else -- Construction du message en conséquence.
        if seance_exist = 1 and copie_is_used = 0 then
          msg := msg || ' Room and time slot are not disponible.';
        elsif seance_exist = 0 and copie_is_used = 1 then
          msg := msg || 'Copy is already used at the same time.';
        else
          msg := msg || 'Room, time slot and copy are already reserved.';
        end if;
      end if;
      
    ELSE
      if copy_id = 1 then
        
        -- document
        doc_insert  := dbms_xmldom.newDomDocument();
        doc_root_no := dbms_xmldom.makeNode(doc_insert);
        -- seances
        d_seances_el := dbms_xmldom.createElement(doc_insert, 'seances');
        d_seances_no := dbms_xmldom.appendChild(doc_root_no, dbms_xmldom.makeNode(d_seances_el));
        -- seance
        d_seance_el := dbms_xmldom.createElement(doc_insert, 'seance');
        dbms_xmldom.setAttribute(d_seance_el, 'places', siege);
        d_seance_no := dbms_xmldom.appendChild(d_seances_no, dbms_xmldom.makeNode(d_seance_el));
        -- childs
        d_salle_el    := dbms_xmldom.createElement(doc_insert, 'salle');
        d_salle_no    := dbms_xmldom.appendChild(d_seance_no, dbms_xmldom.makeNode(d_salle_el));
        d_salle_txt   := dbms_xmldom.createTextNode(doc_insert, prog.extract('//seance[' || count_n || ']/salle/text()').getStringVal());
        d_salle_txtno := dbms_xmldom.appendChild(d_salle_no, dbms_xmldom.makeNode(d_salle_txt));
        d_heure_el    := dbms_xmldom.createElement(doc_insert, 'trancheHoraire');
        d_heure_no    := dbms_xmldom.appendChild(d_seance_no, dbms_xmldom.makeNode(d_heure_el));
        d_heure_txt   := dbms_xmldom.createTextNode(doc_insert, prog.extract('//seance[' || count_n || ']/trancheHoraire/text()').getStringVal());
        d_heure_txtno := dbms_xmldom.appendChild(d_heure_no, dbms_xmldom.makeNode(d_heure_txt));
        d_film_el     := dbms_xmldom.createElement(doc_insert, 'film');
        d_film_no     := dbms_xmldom.appendChild(d_seance_no, dbms_xmldom.makeNode(d_film_el));
        d_film_txt    := dbms_xmldom.createTextNode(doc_insert, prog.extract('//seance[' || count_n || ']/film/text()').getStringVal());
        d_film_txtno  := dbms_xmldom.appendChild(d_film_no, dbms_xmldom.makeNode(d_film_txt));
        d_copie_el    := dbms_xmldom.createElement(doc_insert, 'copie');
        d_copie_no    := dbms_xmldom.appendChild(d_seance_no, dbms_xmldom.makeNode(d_copie_el));
        d_copie_txt   := dbms_xmldom.createTextNode(doc_insert, prog.extract('//seance[' || count_n || ']/copie/text()').getStringVal());
        d_copie_txtno := dbms_xmldom.appendChild(d_copie_no, dbms_xmldom.makeNode(d_copie_txt));
        prog_to_ins   := dbms_xmldom.getXmlType(doc_insert);
        INSERT INTO PROG VALUES (TO_DATE(date_seance, 'YYYY/MM/DD'), prog_to_ins);
        dbms_xmldom.freeDocument(doc_insert);
        msg := 'Insertion ok.';
      end if;
    END IF;
    count_n := count_n + 1;
    feedback_str := feedback_str ||
                    '<message>' || msg || '</message></seance>';
    msg := '';
  END LOOP;
  
  if valid = 0 then
    feedback_str := feedback_str || '<message>File does not respect XSD.</message>';
  end if;
  
  feedback_str := feedback_str || '</seances>';
  doc_feedback := dbms_xmldom.newDOMDocument(XMLTYPE(feedback_str));
  DBMS_XMLDOM.WRITETOFILE(doc_feedback, 'XMLDIR' || '/' || feedback_file);
EXCEPTION
  WHEN OTHERS THEN
    trace('GAME OVER ... ' || SQLERRM);
END progcc1;