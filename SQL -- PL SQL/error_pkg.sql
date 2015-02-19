CREATE OR REPLACE PACKAGE ERROR_PKG AS 

PROCEDURE INSERT_LOG(code in NUMBER := null, msg in VARCHAR2 := null, proc VARCHAR2 := null);

END ERROR_PKG;
/

CREATE OR REPLACE PACKAGE BODY ERROR_PKG AS

  PROCEDURE INSERT_LOG(code IN NUMBER := NULL, msg IN VARCHAR2 := NULL, proc VARCHAR2 := NULL) AS
    /*
      Ce pragma est une directive de compilation permettant de spécifier
      qu’un bloc de programmation s’exécute de manière autonome sur
      le plan transactionnel.
    */
    PRAGMA autonomous_transaction;

    BEGIN
         INSERT INTO LOGS VALUES (SEQUENCE_ERROR.NEXTVAL, SYSDATE, code, msg, proc);
         COMMIT; -- Indépendant
    EXCEPTION
      WHEN OTHERS THEN
         DBMS_OUTPUT.PUT_LINE('ERROR_PKG.INSERT_LOG : ' || SQLERRM);
   END;

END ERROR_PKG;
/