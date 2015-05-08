job

DECLARE
  jobno binary_integer;
BEGIN
  SYS.DBMS_JOB.SUBMIT(jobno, 'ALIMCC1_PKG.JOBCC1;’, sysdate, 'sysdate+1/1440');
  COMMIT;
END;