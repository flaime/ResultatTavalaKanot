package loadDatabasParts;

//import static org.mockito.Mockito.inOrder;

import java.io.File;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import paresePdf.Bana;
import paresePdf.Lopp;
import paresePdf.Person;
import paresePdf.Tävling;

//import linus.testMSaccessDatabasConection.test.MSaccessDatabas.Conection.Tävling.Bana;
//import linus.testMSaccessDatabasConection.test.MSaccessDatabas.Conection.Tävling.Lopp;
//import linus.testMSaccessDatabasConection.test.MSaccessDatabas.Conection.Tävling.Person;
//import linus.testMSaccessDatabasConection.test.MSaccessDatabas.Conection.Tävling.Tävling;

public class ParseDatabasToTävling {
	
//	public static void main(String[] args){
//		String databasURL = "C:\\Users\\ahlin\\OneDrive\\SPK\\testMedAcess\\Lidköping 2015.mdb";
//		String databasURL2 = "C:\\Users\\ahlin\\OneDrive\\SPK\\testMedAcess\\SUC Stockholm 20161.mdb";
//		String databasURL3 = "/Users/linus/Documents/saker/SM 2017_A_20170718_19_10.mdb";
//		System.out.println(new File(databasURL3));
//		new ParseDatabasToTävling(new File(databasURL3).getAbsolutePath());
//	}
	
	private Tävling Tävling = null;
	private DatabasClass db;
	public ParseDatabasToTävling(String databasURL) throws IllegalArgumentException{
		
		Tävling Tävling = null;
		try {
			db = new DatabasClass(databasURL);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Databas conncetion could not set or not found or wrong type");
		}
	}
		
	public Tävling parserDatbas(){
		//create and set day string of all the days
		Tävling = createCompetitionWhitDaysSet();
		
		
		//load alla the lop:s and (start and finiced lopps)
		try {
			ResultSet StartOrResultat = db.query("SELECT * FROM [Start/Resultat];");
			
			Map<Integer, Lopp> lopp = new HashMap<>();
			
			while(StartOrResultat.next()){
//				Bana bb = new Bana(StartOrResultat.getString("Licens1"), "clubb"/* StartOrResultat.getString("klubb")*/, StartOrResultat.getString("bana") , StartOrResultat.getString("tid"));
				Bana b = null;
				if(getPersons(StartOrResultat).size()>0)
					b = new Bana(getPersons(StartOrResultat), StartOrResultat.getString("bana") , StartOrResultat.getString("tid"));
				
				if(StartOrResultat.getInt("lopp") == 0){
					//emty not yet placed do noting
				}else if(lopp.containsKey(new Integer(StartOrResultat.getInt("lopp")) )&& b != null){
//					List<Person> person = getPersons(StartOrResultat);
//					Bana b = new Bana(namn, klubb, bana, tid) //TODO titta till så att detta med personer sköts bättre
					lopp.get(StartOrResultat.getInt("lopp")).addBana(new ArrayList<Bana>( Arrays.asList(b)));// add(lop);
					System.out.println(b);
				}else if( b != null){
					Lopp lop = new Lopp(StartOrResultat.getString("Datum"), StartOrResultat.getString("Distans"), StartOrResultat.getString("Starttid"), StartOrResultat.getString("Klass"), StartOrResultat.getString("Omgång"), StartOrResultat.getInt("Omgångnr"), StartOrResultat.getInt("Lopp"));
					lop.addBana(new ArrayList<Bana>( Arrays.asList(b)));
					lopp.put(new Integer(StartOrResultat.getInt("lopp")), lop);
				}
			}
			

			Tävling.addLopp(new ArrayList<Lopp>(lopp.values()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Wrong whit the lopp");
		}
		
		System.out.println("Tävling = " + Tävling.getJsonString());
		System.out.println("Tävling = " + Tävling);
		
		return Tävling;
	}

	/**
	 * @param Tävling
	 * @return
	 */
	private Tävling createCompetitionWhitDaysSet() {
		Tävling Tävling = null;
		try {
			ResultSet days = db.query("SELECT Datum FROM Dagar;");
			String daysString = "";
			while(days.next()){
				daysString += " " + days.getString("Datum");
			}
			Tävling = new Tävling(daysString);
			System.out.println("days: " + daysString);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Databas did not contain annay days set");
		}
		return Tävling;
	}
	
	private List<Person> getPersons(ResultSet startOrResultat) {
		List<Person> persons = new ArrayList<>();
		try {
			Person p = getPerson(startOrResultat.getString("Licens1"));
			if(p != null)
				persons.add(p);
		} catch (SQLException e) {
			throw new IllegalArgumentException("Wrong Licens1 for one person");
		}
		try {
			Person p = getPerson(startOrResultat.getString("Licens2"));
			if(p != null)
				persons.add(p);
		} catch (SQLException e) {
			throw new IllegalArgumentException("Wrong Licens2 for one person");
		}
		try {
			Person p = getPerson(startOrResultat.getString("Licens3"));
			if(p != null)
				persons.add(p);
		} catch (SQLException e) {
			throw new IllegalArgumentException("Wrong Licens3 for one person");
		}
		try {
			Person p = getPerson(startOrResultat.getString("Licens4"));
			if(p != null)
				persons.add(p);
		} catch (SQLException e) {
			throw new IllegalArgumentException("Wrong Licens4 for one person");
		}
		return persons;
	}

	private Person getPerson(String id){
		try {
//			PreparedStatement ps = conn.prepareStatement(
//			        "SELECT ID FROM localities WHERE locName=?");
//			ps.setString(1, "LEÓN");
			ResultSet person = db.query("SELECT * FROM [Deltagare] WHERE Licensnr = '" + id + "'");
			if(person != null && person.next()){
				System.out.println(person.getNString("Förnamn"));
				System.out.println(person.getNString("Efternamn"));
				System.out.println(person.getNString("Kön"));
				System.out.println(person.getNString("Födelseår"));
				System.out.println(person.getNString("Klubb"));
				return new Person(person.getNString("Förnamn"), person.getNString("Efternamn"), person.getNString("Kön"), person.getNString("Födelseår"), id, person.getNString("Klubb"));
			}else 
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
