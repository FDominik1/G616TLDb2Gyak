SET SERVEROUTPUT ON
BEGIN
    dbms_output.put_line(TO_CHAR(SYSDATE,'yyyy-mm-dd hh24:mi:ss'));
END;