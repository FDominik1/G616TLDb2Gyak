SET SERVEROUTPUT ON
DECLARE
    a number(5);
    b number(6);
    c number(7);
    
BEGIN
    a:=12;
    b:=3.14;
    c:=2*a*b;
    
    dbms_output.put_line('a kor kerulete: '||c);
    
END;
    