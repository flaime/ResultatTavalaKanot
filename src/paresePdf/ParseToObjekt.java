package paresePdf;

import java.util.ArrayList;

public class ParseToObjekt {
	
	private T�vling t�vlingen;
	private String typ = "non";
	public T�vling ParseToT�vling (String data) throws WrongFormatPDFParseException{
		String[] datumSplit = data.split("(\\n|\\r)");//[1];
		String datum ="";

		if(datumSplit.length >= 1)
			datum = datumSplit[1];
		else
			throw new WrongFormatPDFParseException("Den ineh�ller inte ens 2 rader d�ligt och s� ska den andra vara datumet...");
		
		//skapar t�vlingsobjektet som vi sedan bygger vidare p� med de lopp vi hittar
		t�vlingen = new T�vling(datum);
				
		data = rensaData(data);
				
		
		//tittar vad de �r f�r typ f�r att parsa dem korekt
		if(datumSplit[0].trim().toLowerCase().contains("Resultatlista".toLowerCase())){
			typ = "Resultatlista";
			t�vlingen = parsaResultatlista(t�vlingen, data);
		}else if(datumSplit[0].trim().toLowerCase().contains("Startlista".toLowerCase())){
			typ = "Startlista";
			throw new WrongFormatPDFParseException("Programet st�djer �n inte startlistor :( Men s�g g�rna till om du vill hj�lpa till :)");
		}else
			throw new WrongFormatPDFParseException("den b�rjar inte med \"Resultatlista\" eller \"Startlista\" som f�rsta ord p� en egen rad");
		
		
		return t�vlingen;
	}
	
	private T�vling parsaResultatlista(T�vling t�vling, String data){
		
		t�vling.addLopp(taUtLopp(delaUppILopp(data)));
//		System.out.println(taUtLopp(delaUppILopp(data)));
		
		return t�vling;
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
		
		
		//mata in n�got f�r att tab bort de f�rsta raderan och sen dela det p� 3 och stoppa in i arraylist f�r att sedan skilla till ParsaToillPlacering 
	
		lopp.addBana(taUtBandorUrLoppData(data));
		
		return lopp;
	}
	
	private ArrayList<Bana> taUtBandorUrLoppData(String loppData){
		ArrayList<Bana> banorna = new ArrayList<>();
		String[] loppDataSplittad = loppData.split("(?m)Tid:\\n");
		String[] banData = loppDataSplittad[1].split("(\\n|\\r)");
		int hurM�ngaBanor = banData.length/4;
		for(int i =0;i< hurM�ngaBanor;i++){
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
	
	
	//det vi tar bort �r rubriker och annat som st�r h�gst upp p� vardera sida som f�r oss inte tillf�r n�got:) 
	private String rensaData(String data){
		data = data.replaceAll("(?m)den (\\d\\d|\\d).*\\nSida.*av.*\\n(Resultatlista|Startlista).*\\n", "");
		data = data.replaceAll("(?m)den (\\d\\d|\\d).*\\nSida.*av.*\\n", "");
		data = data.replaceAll("^(Resultatlista|Startlista).*\\n", "");
		//r�kade bli n�gra upprepningar som jag kom p� att slutet och b�rjan beh�vde de tv� understa (av de tre �van) f�r att funka...
		//ja du kan g�ras b�ttre alltid men f�r r�cka s� nu
		
		data = data.replaceAll("(?m)\\d\\d\\d\\d-\\d\\d-\\d\\d(\\n|).*(\\n|).*Datum.*(\\n|).*Distans.*(\\n|).*Starttid.*(\\n|).*Klass.*(\\n|).*Heat.*\\n", "");
		//vilket �r i "ren" regex(med multiline):
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
				loppen.add(v�ndP�Radernas�rdning(lopp)); //d� loppen �r bakofram och de hamnar "sista" f�rst..
				lopp = "";
			}else
				lopp += rader[i]+"\n";
			
		}
		return loppen;
	}
	
	private String v�ndP�Radernas�rdning(String lopp){
		String ret = "";
		String[] rader = lopp.split("(\\n|\\r)");
		for (int i = rader.length-1; i >= 0 ; i--) {
			ret += rader[i] +"\n";
		}
		return ret;
	}

}
