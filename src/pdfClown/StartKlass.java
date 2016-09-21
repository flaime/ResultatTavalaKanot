package pdfClown;

import java.io.FileNotFoundException;

import paresePdf.PDFToText;
import paresePdf.ParseResultatPDF;
import paresePdf.ParseToObjekt;
import paresePdf.WrongFormatPDFParseException;

public interface StartKlass {
	public static void main(String[] args){
		ParseToObjekt p = new ParseToObjekt();
		try {
			System.out.println(p.ParseToTävling(PDFToText.getTextFromPDF("C:\\ResultatLista_KanotSM_Måndag_FM_Försök_500m.pdf")));//StartLista_SprintSM_2015_Måndag.pdf"));//startlista_20160719_tis_v2.pdf"));//ResultatLista_KanotSM_Måndag_FM_Försök_500m.pdf"));//("C:\\besattningsregattan-2016.pdf"));
		} catch (WrongFormatPDFParseException | FileNotFoundException e) {
			System.out.println("Det var fel i PDF:en som inte såg ut som standarden bör, så filen gick inte att läsa tyvär :/");
			e.printStackTrace();
		}
	}
}
