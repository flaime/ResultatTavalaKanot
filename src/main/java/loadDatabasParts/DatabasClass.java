package loadDatabasParts;

//import java.sql.Connection;
//import java.sql.DriverManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import net.ucanaccess.jdbc.JackcessOpenerInterface;

//import com.healthmarketscience.jackcess.Database;
//import com.healthmarketscience.jackcess.DatabaseBuilder;

/**
 * Hello world!
 *
 */
public class DatabasClass {
	
	private Connection conn;
	
//	public static void main(String[] args) {
//		String databasURL = "C:\\Users\\ahlin\\OneDrive\\SPK\\testMedAcess\\Lidköping 2015.mdb";
//		System.out.println("Has ben started");
//		try {
//			DatabasClass db = new DatabasClass(databasURL);
//			System.out.println(db.query("SELECT * FROM Klubbar;"));
//			System.out.println("comando run");
//			
//		} catch (ClassNotFoundException e) {
//			System.out.println("class nod found");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println("SQL fel");
//			e.printStackTrace();
//		}
//		System.out.println("fine");
//		
//		System.out.println();
//	}
	
	public DatabasClass(String urlToDtabas) throws SQLException, ClassNotFoundException{
		String driver="net.ucanaccess.jdbc.UcanaccessDriver";
		Class.forName(driver);
		conn=DriverManager.getConnection("jdbc:ucanaccess://"+urlToDtabas);
	}
	
	public ResultSet query(String SQL) throws SQLException{
		Statement stmt=conn.createStatement();
		ResultSet result=stmt.executeQuery(SQL);
		return result;

	}
}
