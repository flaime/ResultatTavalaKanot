package paresePdf;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.pdfclown.documents.Document;
import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.ContentScanner;
import org.pdfclown.documents.contents.fonts.Font;
import org.pdfclown.documents.contents.objects.ContainerObject;
import org.pdfclown.documents.contents.objects.ContentObject;
import org.pdfclown.documents.contents.objects.ShowText;
import org.pdfclown.documents.contents.objects.Text;
import org.pdfclown.files.File;

public class PDFToText {

	public static String getTextFromPDF(String filePath) throws FileNotFoundException {
		File file = null;
		String text ="_";
		hej ="";
//		System.out.println("----2");
		try {
			// 1. Opening the PDF file...
			{
				// String filePath =
				// "C:\\besattningsregattan-2016.pdf";//promptFileChoice("Please
				// select a PDF file");
				
				//tog bort try catch och ersätter med throws så vi kan fånga det senare när vi försöker tesa olika filer...
				file = new File(filePath);

				
//				try {
//					file = new File(filePath);
//				} catch (Exception e) {
//					throw new RuntimeException(filePath + " file access error.", e);
//				}
			}
			Document document = file.getDocument();

			// 2. Text extraction from the document pages.
			for (Page page : document.getPages()) {
				// if(!promptNextPage(page, false))
				// {
				// quit();
				// break;
				// }
//				System.out.println("----3");
				text = text+ extract(new ContentScanner(page)); // Wraps the page contents into // a scanner.									
//				System.out.print(extract(new ContentScanner(page)));
				
			}
		} finally {
			// 3. Closing the PDF file...
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					/* NOOP */}
			}
		}
//		System.out.println("----5");
////		System.out.println(text);
//		System.out.print(hej);
		return hej;
	}

	/**
	 * Scans a content level looking for text.
	 */
	/*
	 * NOTE: Page contents are represented by a sequence of content objects,
	 * possibly nested into multiple levels.
	 */
	private static String hej="";
	private static String extract(ContentScanner level) {
		String ret ="";
		if (level == null)
			return"";
			

		while (level.moveNext()) {
			ContentObject content = level.getCurrent();
			if (content instanceof ShowText) {
				Font font = level.getState().getFont();
				// Extract the current text chunk, decoding it!
				String tillfäligText = (font.decode(((ShowText) content).getText()));
				if(!tillfäligText.trim().equals("")){
					ret += tillfäligText+"\n";
				}
//				System.out.println(font.decode(((ShowText) content).getText()));
			} else if (content instanceof Text || content instanceof ContainerObject) {
				// Scan the inner level!
				extract(level.getChildLevel());
			}
		}
//		System.out.print(ret);
//		System.out.println("klalas"+ret.length());
//		System.out.println("----4" );//+ ret);
		hej = hej+ret;
		return ret;
	}
	private static String tabortBlankrader(String text){
		return text.replaceAll("(?m)^[ \t]*\r?(\n|\r)", "");	//kan verka som den inte funkar men det gör den så länge man inte andvänder println utan print funkar det fint
	}
}
