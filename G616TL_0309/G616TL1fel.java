package g616tldb2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class G616TL1fel {

	Connection conn = null;
	Statement s = null;
	PreparedStatement ps = null;
	Scanner sc = new Scanner(System.in);
	ResultSet rs = null;
	CallableStatement cs = null;


	public void StatikusTablaLetrehozas() {

		String sqlp_auto = "create table auto ( rsz char(6) primary key,"
				+ "tipus char(10) not null, szin char(10) default 'feher',"
				+ "evjarat number(4), ar number(8) check(ar>0) )";
		String sqlp_tulaj = "create table tulaj( id number(3) primary key,"
				+ "nev char(20) not null, cim char(20), szuldatum date)";

		if (conn != null) {

			try {
				s = conn.createStatement();
				s.executeUpdate(sqlp_auto);
				System.out.println("Autó tábla létrejött!");
				s.executeUpdate(sqlp_tulaj);
				System.out.println("Tulajdonos tábla létrejött!");
				s.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}

		}

	}

	public void DrReg() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Sikeres driver regisztrálás!");
		} catch (Exception ex) {
			System.err.println("Hiba történt: " + ex.getMessage());
		}
	}

	public void Connect() {

		String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
		String user = "H22_DJ7PNE";
		String pwd = "DJ7PNE";
		try {

			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("Sikeres kapcsolódás\n");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void LeKapcs() {

		if (conn != null) {

			try {

				conn.close();
				System.out.println("Sikeres lekapcsolódás!");

			} catch (Exception ex) {

				System.err.println(ex.getMessage());

			}

		}

	}



	public void StatikusTablaModositas() {

		if (conn != null) {

			try {

				String sqlp = "alter table auto add(tulaj_id references tulaj)";
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Autó tábla módosítva!");
				s.close();

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}

		}

	}

	

	public void StatikusAdatfelvitel() {

		if (conn != null) {

			String sqlp_tulaj = "insert into tulaj values (1, 'Tóth Máté', "
					+ " 'Miskolc', to_date('1980.05.12', 'yyyy.mm.dd'))";
			String[] sqlp = { "insert into auto values('aaa111', 'opel', 'piros', 2014, 1650000, 1)",
					"insert into auto values('bbb222', 'mazda', null, 2016, 2800000, 1)",
					"insert into auto(rsz, tipus, evjarat, ar) values('ccc333', 'ford', 2009, 15000000)" };

			try {

				s = conn.createStatement();
				s.executeUpdate(sqlp_tulaj);
				System.out.println("Tulaj felvéve!");
				s.close();

			} catch (Exception ex) {

				System.err.println(ex.getMessage());

			}

			for (int i = 0; i < sqlp.length; i++) {
				try {

					s = conn.createStatement();
					s.executeUpdate(sqlp[i]);
					System.out.println("Autó felvéve");
					s.close();

				} catch (Exception ex) {

					System.err.println(ex.getMessage());

				}
			}

		}

	}

	

	public void DinamikusAdatfelvitel() {

		if (conn != null) {

			String sqlp = "insert into auto (rsz, tipus, szin, evjarat, ar, tulaj_id)" + "values (?, ?, ?, ?, ?, ?)";

			System.out.println("Kérem a rendszámot: ");
			String rsz = sc.next().trim();
			System.out.println("Kérem a típust: ");
			String tipus = sc.next().trim();
			System.out.println("Kérem a színt: ");
			String szin = sc.next().trim();
			System.out.println("Kérem az évjáratot: ");
			int ev = sc.nextInt();
			System.out.println("Kérem az árat: ");
			float ar = sc.nextFloat();
			System.out.println("Kérem a tulajdonos azonosítóját: ");
			int tulaj_id = sc.nextInt();
			try {

				ps = conn.prepareStatement(sqlp);
				ps.setString(1, rsz);
				ps.setString(2, tipus);
				ps.setString(3, szin);
				ps.setInt(4, ev);
				ps.setFloat(5, ar);
				ps.setInt(6, tulaj_id);
				ps.executeUpdate();
				ps.close();
				System.out.println("Autó felvéve!");

			} catch (Exception ex) {

				System.err.println(ex.getMessage());

			}

		}

	}

	

	public void DinamikusAdattorles() {

		System.out.println("Törlendõ autó: ");
		String rsz = sc.next();
		String sqlp = "delete from" + "user" + ".AUTO" + " where rsz=?";

		if (conn != null) {

			try {

				ps = conn.prepareStatement(sqlp);
				ps.setString(1, rsz);
				ps.close();
				System.out.println(rsz + " renszámú autó törölve\n");

			} catch (Exception ex) {

				System.err.println(ex.getMessage());

			}

		}

	}

	public void StatikusAdattorles() {

		System.out.println("Törlendõ autó: ");
		String rsz = sc.next();
		String sqlp = "delete from auto where rsz like '" + rsz + "'";

		if (conn != null) {
			try {
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				s.close();
				System.out.println(rsz + " rendszámú autó törölve!\n");

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}




	public void StatikusLekerdezes() {

		if (conn != null) {
			String sqlp = "select * from auto";
			System.out.println("Rendszám Típus Szín Évjárat Ár Tulaj");
			System.out.println("--------------------------------------");

			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				rs = s.getResultSet();
				while (rs.next()) {

					String rsz = rs.getString("rsz");
					String tipus = rs.getString("tipus");
					String szin = rs.getString("szin");
					int evjarat = rs.getInt("evjarat");
					int ar = rs.getInt("ar");
					int tulaj_id = rs.getInt("tulaj_id");

					System.out
							.println(rsz + "\t\t" + tipus + "\t" + szin + "\t" + evjarat + "\t" + ar + "\t" + tulaj_id);
				}
				rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}




	public void ModosithatoKurzor() {

		System.out.println("Szín: ");
		String szin = sc.next().trim();
		String sqlp = "select ar from auto where szin = '" + szin + "'";

		if (conn != null) {

			try {

				s = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				rs = s.executeQuery(sqlp);
				while (rs.next()) {
					int regiar = rs.getInt("ar");
					rs.updateInt("ar", (regiar * 2));
					rs.updateRow();
				}
			} catch (Exception ex) {
				System.err.println(ex.getMessage());

			}

		}

	}




	public void InEljarasHivas() {

		if (conn != null) {

			try {

				String sqlp = "create or replace procedure arcsokkent " + "(kor IN number) is " + "begin "
						+ "update auto set ar=ar*0.9 where " + "to_char(sysdate, 'yyyy')-evjarat > kor ; " + "end; ";
				System.out.println("Kor: ");
				int kor = sc.nextInt();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Függvény létrejött\n");
				cs = conn.prepareCall("{call arcsokkent(?)}");
				cs.setInt(1, kor);
				cs.execute();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}

		}

	}




	public void OutEljarasHivas() {

		if (conn != null) {

			try {

				String sqlp = "create or replace procedure atlagar " + "(sz IN char, atl OUT number) is " + "begin "
						+ "select (avg(ar) into atl from auto where szin=sz; " + "end;";
				System.out.println("Szín: ");
				String szin = sc.next();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Eljárás létrejött\n");
				cs = conn.prepareCall("{call atlagar(?, ?)}");
				cs.setString(1, szin);
				cs.registerOutParameter(2, java.sql.Types.FLOAT);
				cs.execute();
				float atlag = cs.getFloat(2);
				System.out.println(szin + " autók átlagára: " + atlag + "\n");

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}

		}

	}

	public void FuggvenyHivas() {

		if (conn != null) {

			try {

				String sqlp = "create or replace function atlagarfv " + "(sz IN char) return number is "
						+ "atl number(10,2); " + "begin " + "select avg(ar) into atl from auto where szin=sz; "
						+ "return atl; " + "end;";
				System.out.println("Szín: ");
				String szin = sc.next();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Fgv létrejött\n");
				cs = conn.prepareCall("{? = call atlagarfv(?)}");
				cs.setString(2, szin);
				cs.registerOutParameter(1, java.sql.Types.FLOAT);
				cs.execute();
				float atlag = cs.getFloat(1);
				System.out.println(szin + " autók átlagára: " + atlag + "\n");

			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}

	}




	public void DinamikusTablaTorles() {

		String sqlp = "create or replace procedure tablatorles(nev IN char) is " + "begin "
				+ "execute immidiate 'drop table' || nev; " + "end;";
		System.out.println("Törlendõ tábla: ");
		String name = sc.next().trim();
		if (conn != null) {

			try {

				s = conn.createStatement();
				s.executeUpdate(sqlp);
				cs = conn.prepareCall("{call tablatorles(?)}");
				cs.setString(1, name);
				cs.execute();
				System.out.println(name + " tábla törölve\n");

			} catch (Exception ex) {

				System.err.println(ex.getMessage());

			}

		}

	}




	public void DinamikusModositas() {
		
		if(conn!=null) {
			
			String sqlp = "update auto1 set ar=ar-?";
			System.out.println("Mennyivel csökkentsük az árát?");
			int arcsokk = sc.nextInt();
			try {
				
				conn.setAutoCommit(false);
				
				try {
					
					ps = conn.prepareStatement(sqlp);
					ps.setInt(1, arcsokk);
					ps.executeUpdate();
					conn.commit();
					System.out.println("Módosítás megtörtént\n");
					
				}catch(Exception ex) {
					
					System.err.println(ex.getMessage());
					
				}
				
				conn.setAutoCommit(true);
				
			}catch(Exception ex) {
				
				System.err.println(ex.getMessage());
				
			}
			
		}
		
	}

}