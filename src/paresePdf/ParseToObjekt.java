package paresePdf;

import java.util.ArrayList;

public class ParseToObjekt {
	
	private Tävling tävlingen;
	private String typ = "non";
	public Tävling ParseToTävling (String data) throws WrongFormatPDFParseException{
		String[] datumSplit = data.split("(\\n|\\r)");//[1];
		String datum ="";

		if(datumSplit.length >= 1)
			datum = datumSplit[1];
		else
			throw new WrongFormatPDFParseException("Den inehåller inte ens 2 rader dåligt och så ska den andra vara datumet...");
		
		//skapar tävlingsobjektet som vi sedan bygger vidare på med de lopp vi hittar
		tävlingen = new Tävling(datum);
				
		data = rensaData(data);
				
		
		//tittar vad de är för typ för att parsa dem korekt
		if(datumSplit[0].trim().toLowerCase().contains("Resultatlista".toLowerCase())){
			typ = "Resultatlista";
			tävlingen = parsaResultatlista(tävlingen, data);
		}else if(datumSplit[0].trim().toLowerCase().contains("Startlista".toLowerCase())){
			typ = "Startlista";
			throw new WrongFormatPDFParseException("Programet stödjer än inte startlistor :( Men säg gärna till om du vill hjälpa till :)");
		}else
			throw new WrongFormatPDFParseException("den börjar inte med \"Resultatlista\" eller \"Startlista\" som första ord på en egen rad");
		
		
		return tävlingen;
	}
	
	private Tävling parsaResultatlista(Tävling tävling, String data){
		
		tävling.addLopp(taUtLopp(delaUppILopp(data)));
//		System.out.println(taUtLopp(delaUppILopp(data)));
		
		return tävling;
	}
	
	
	private ArrayList<Lopp> taUtLopp(ArrayList<String> LoppLista){
		ArrayList<Lopp> loppen = new ArrayList<>();
		
		
		
		LoppLista.forEach(x -> {
			loppen.add(parasaTillLopp(x));
		});
		
		return loppen;
	}
	
	private Lopp parasaTillLopp(String data){
		String[] dataRadvis = data.split("(\\n|\\r)");
		
		String Datum ="non";
		String distans = "non";
		String tid = "non";
		String klass = "non";
		String typ = "non"; //likt final mellanhet osv
		int typNummer = -1;
		int loppNummer = -1;
		
		Datum = dataRadvis[0];
		distans = dataRadvis[1];
		tid = dataRadvis[2];
		klass = dataRadvis[3];
		typ = dataRadvis[4].split(" ")[0];
		typNummer = Integer.parseInt(dataRadvis[4].split(" ")[1]);
		loppNummer = Integer.parseInt(dataRadvis[5]);
		
		Lopp lopp = new Lopp(Datum, distans, tid, klass, typ, typNummer, loppNummer);
		
		
		//mata in något för att tab bort de första raderan och sen dela det på 3 och stoppa in i arraylist för att sedan skilla till ParsaToillPlacering 
	
		lopp.addBana(taUtBandorUrLoppData(data));
		
		return lopp;
	}
	
	private ArrayList<Bana> taUtBandorUrLoppData(String loppData){
		ArrayList<Bana> banorna = new ArrayList<>();
		String[] loppDataSplittad = loppData.split("(?m)Tid:\\n");
		String[] banData = loppDataSplittad[1].split("(\\n|\\r)");
		int hurMångaBanor = banData.length/4;
		for(int i =0;i< hurMångaBanor;i++){
			if(i+4 < banData.length){
				String bana = "";
				Bana b = new Bana(banData[i*4], banData[i*4+1], banData[i*4+2], banData[i*4+3]);
//				bana += banData[i*4] + "\n";
//				bana += banData[i*4+1] + "\n";
//				bana += banData[i*4+2] + "\n";
//				bana += banData[i*4+3];
//				ret.add(bana);
//				System.out.println(bana);
//				System.out.println("---");
				banorna.add(b);
			}
		}
		return banorna;
	}
	
	private ArrayList<Placering> ParsaToillPlacering(ArrayList<String> placeringar){
		ArrayList<Placering> placeringarna = new ArrayList<>();
		for(String placering: placeringar){
			placeringarna.add(ParsaToillPlacering(placering));
		}
		return placeringarna;
	}
	
	private Placering ParsaToillPlacering(String placeringar){
		
		String[] plac = placeringar.split("(\\n|\\r)");
		return new Placering(plac[0], plac[1],plac[2], plac[3]); //namn, klubb, placering, tid
	}
	
	
	//det vi tar bort är rubriker och annat som står högst upp på vardera sida som för oss inte tillför något:) 
	private String rensaData(String data){
		data = data.replaceAll("(?m)den (\\d\\d|\\d).*\\nSida.*av.*\\n(Resultatlista|Startlista).*\\n", "");
		data = data.replaceAll("(?m)den (\\d\\d|\\d).*\\nSida.*av.*\\n", "");
		data = data.replaceAll("^(Resultatlista|Startlista).*\\n", "");
		//råkade bli några upprepningar som jag kom på att slutet och början behövde de två understa (av de tre åvan) för att funka...
		//ja du kan göras bättre alltid men får räcka så nu
		
		data = data.replaceAll("(?m)\\d\\d\\d\\d-\\d\\d-\\d\\d(\\n|).*(\\n|).*Datum.*(\\n|).*Distans.*(\\n|).*Starttid.*(\\n|).*Klass.*(\\n|).*Heat.*\\n", "");
		//vilket är i "ren" regex(med multiline):
		//   \d\d\d\d-\d\d-\d\d(\n|).*(\n|).*Datum.*(\n|).*Distans.*(\n|).*Starttid.*(\n|).*Klass.*(\n|).*Heat.*\n
		
		return data;
	}
	
	private ArrayList<String> delaUppILopp(String data){
		ArrayList<String> loppen = new ArrayList<>();
		
		String[] rader = data.split("(\\n|\\r)");
		String lopp = "";
		for (int i = rader.length-1; i >= 0 ; i--) {
			if(rader[i].trim().matches("\\d\\d\\d\\d-\\d\\d-\\d\\d")){
				lopp += rader[i]+"\n";
				loppen.add(vändPåRadernasÅrdning(lopp)); //då loppen är bakofram och de hamnar "sista" först..
				lopp = "";
			}else
				lopp += rader[i]+"\n";
			
		}
		return loppen;
	}
	
	private String vändPåRadernasÅrdning(String lopp){
		String ret = "";
		String[] rader = lopp.split("(\\n|\\r)");
		for (int i = rader.length-1; i >= 0 ; i--) {
			ret += rader[i] +"\n";
		}
		return ret;
	}

}
