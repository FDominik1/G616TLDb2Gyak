package G616TL2fel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.net.aso.s;

public class G616TL2fel {
	public static void main(String[] args) {
		
		DriverReg();
		Connect();
		LeKapcs(args);
		
		
	}
	public static void DriverReg() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Sikeres driver regisztrálás\n");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	public static void Connect() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
		String user = "username";
		String pwd = "password";
		try {
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("Sikeres kapcsolódás\n");
		} catch (Exception e)  {
			System.err.println(e.getMessage());
		}
				
	}
	public static void LeKapcs(Object conn) {

		if (conn != null) {
			try {
				((Connection) conn).close();
				System.out.println("sikeres lekapcsolodás\n");
			} catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	public void InsertTul(String tulajazon, String nev, String szemig, String szulhely, String szulido) {
		String sqlp = "insert into Tulajdonos values("+ tulajazon + ","+nev +","+ szemig + ","+szulhely+","+szulido+")";
		try {
			Connection conn = null;
			Statement s = conn.createStatement();
			s.execute(sqlp);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void StatikusAdatfelvitel(Object conn) {
		
		if (conn != null) {
			String sqlp_tul = "insert into tulaj values (1, 'Tóth Máté' , "+" 'Miskolc' , to date ('1980.05.12', 'yyyy.mm.dd))";
			String[] sqlp = {
					"insert into auto values('aaa111','opel','piros',2014,1650000,1)",
					"insert into auto values('bbb222','mazda',null,2016,2800000,1)",
					"insert into auto (rsz, tipus, evjarat, ar) values ('ccc333', 'ford',2009, 15000000)"
			};
			Object s;
			try {
				s = ((Connection) conn).createStatement();
				((Statement) s).executeUpdate(sqlp_tul);
				System.out.println("tulaj felveve \n");
				((Connection) s).close();
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
			
			for (int i=0; i<sqlp.length; i++) {
				try {
					s = ((Connection) conn).createStatement();
					((Statement) s).executeUpdate(sqlp[i]);
					System.out.println("Autó felvéve\n");
					((Connection) s).close();
				}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
			
		}
	}
	
 }
	public void StatikusTablaLetrehozas(String tulaj, Object conn, Object s) {
		String sqlp_auto = "create table auto ( rsz char(6) primary key, " + "tipus char(10) not null, szin char (10) default 'feher', " + "evjharat number(4), ar number (8) check (ar>100) ) ";
		String sqlp_tulaj = "create table tulaj(id number(3) primary key, " + "nev char(20) not null, cim char(20), szuldatum date)";
		if ( conn!= null) {
			try {
				s = ((Connection) conn).createStatement();
				((Statement) s).executeUpdate(sqlp_auto);
				System.out.println("autó tábla létrejött \n");
				((Statement) s).executeUpdate(sqlp_tulaj);
				System.out.println("Tulajdonos tábla létrejött \n");
				((Connection) s).close();
			}catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	public void StatikusLekerdezes(Object conn, Statement s, ResultSet rs) {
		if (conn != null) {
			String sqlp = "select * from auto";
			System.out.println("rendszám tipus szin évjárat ár tulaj");
			System.out.println("------------------------------------");
			try {
				s = ((Connection) conn).createStatement();
				s.executeQuery(sqlp);
				rs = s.getResultSet();
				while (rs.next()) {
					String rsz = rs.getString("rsz");
					String tipus = rs.getString("tipus");
					String szin = rs.getString("szin");
					int evjarat = rs.getInt("evjarat");
					int ar = rs.getInt("ar");
					int tulaj_id = rs.getInt("tulaj_id");
					System.out.println(rsz+"\t\t"+tipus+"\t"+szin+"\t"+evjarat+"\t"+ar+"\t"+tulaj_id);
				}
				rs.close();
			} catch(Exception ex) {
					System.err.println(ex.getMessage());
			}
		}
	}
	public void StatikusAdattorles(Object conn, Statement s) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("törlendõ autó rendszáma: ");
		String rsz = sc.next();
		String sqlp = "delete from auto where rsz like '"+rsz + "'";
		if (conn !=null) {
			try {
				s = ((Connection) conn).createStatement();
				s.executeUpdate(sqlp);
				s.close();
				System.out.println(rsz + "rendszámú autó törölve \n");		
			}catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
}

