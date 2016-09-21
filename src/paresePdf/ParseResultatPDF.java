package paresePdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseResultatPDF {

	public static void main(String [] args){
		PDFManager pdfManager = new PDFManager();
		pdfManager.setFilePath("C:\\besattningsregattan-2016.pdf");
		try {
			String textPdf = pdfManager.ToText();
			System.out.println(textPdf);
			ParseResultatPDF p = new ParseResultatPDF();
			p.parasa(textPdf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	
	public void parasa(String text){
		letaReapåAllaLopp(text);
	}
	
	
	private ArrayList<String> letaReapåAllaLopp(String text){
		ArrayList<String > ret = new ArrayList<>();
		text = tabortöverflödigt(text);
		String datum = text.substring(0, 11);
		System.out.println("datum = " + datum);
		String[] loppen = text.split("\\d\\d\\d\\d-\\d\\d-\\d\\d");
		
//		System.out.println("nummer0 = \"" + loppen[0] + "\"");
//		System.out.println("nummer1 = \"" + loppen[1] + "\"");
		
		ArrayList<Lopp> loppenParsad = new ArrayList<>();
		for (String s : loppen) {
			if(!s.trim().replaceAll("(\n|\r)", "").equalsIgnoreCase("")) //titta så att det inte är tomt...
			 loppenParsad.add(parasalopp(s));
			System.out.println("------------- har den inehåll "+(!s.trim().replaceAll("(\n|\r)", "").equalsIgnoreCase(""))+ " " +s);
		}

		
		
		Pattern p = Pattern.compile("^[a-zA-Z]+([0-9]+).*");
		Matcher m = p.matcher(text);

		if (m.find()) {
//		    System.out.println(m.group(1));
		}
		
		
		
		
		
		for(int i=0;i < loppen.length;i++){
//			System.out.println("----------------------- nytt lopp---------------");
//			System.out.println(loppen[i]);
		}
		
		
		
		return ret;
	}
	private Lopp parasalopp(String text) {//vi vet att det inehåller text i alla fall
		String distnas = text.split("\\d\\d:\\d\\d")[0];
		String tid = text.substring(distnas.length()+1, distnas.length()+6);
		
		return null;
	}



	/*
	 * tar bort "rubrikerna" på varje ny sida... (om de finns)
	 */
	private String tabortöverflödigt(String text){
		
		String mactning1 ="Resultatlista";//= "(?m)Resultatlista\n\\d\\d\\d\\d-\\d\\d-\\d\\d.*"; //Resultatlista\n\d\d\d\d-\d\d-\d\d.*
		String mactning2 = "(?m)\\n^.*Sida\\s\\d*\\sav.*";
		String mactning3 = "(?m)\\n^.*\\d\\d\\d\\d-\\d\\d-\\d\\d.*Datum DistansStarttid KlassHeat";
		
//		text = Pattern.compile(mactning1, Pattern.MULTILINE).matcher(text).replaceAll("");
		text = text.replaceAll(mactning1, "");
		text = text.replaceAll(mactning2, "");
		text = text.replaceAll(mactning3, "");
		
		text = tabortBlankrader(text);
		return text;
	}
	
	private String tabortBlankrader(String text){
		return text.replaceAll("(?m)^[ \t]*\r?(\n|\r)", "");		
	}
}
