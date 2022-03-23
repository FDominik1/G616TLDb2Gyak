SET SERVEROUTPUT ON
DECLARE
    a number(5);
    b number(6);
    c number(7);
    
BEGIN
    a:=10;
    b:=20;
    c:=a+b;
    
    dbms_output.put_line('az osszeg: '||c);
    
END;
    


