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
			System.out.println(p.ParseToTävling(PDFToText.getTextFromPDF("C:\\ResultatLista_KanotSM_M�ndag_FM_F�rs�k_500m.pdf")));//StartLista_SprintSM_2015_M�ndag.pdf"));//startlista_20160719_tis_v2.pdf"));//ResultatLista_KanotSM_M�ndag_FM_F�rs�k_500m.pdf"));//("C:\\besattningsregattan-2016.pdf"));
		} catch (WrongFormatPDFParseException | FileNotFoundException e) {
			System.out.println("Det var fel i PDF:en som inte s�g ut som standarden b�r, s� filen gick inte att l�sa tyv�r :/");
			e.printStackTrace();
		}
	}
}
