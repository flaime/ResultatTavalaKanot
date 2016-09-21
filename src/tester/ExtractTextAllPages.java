package tester;
//PDFxStreame

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
 
public class ExtractTextAllPages {
  public static void main (String[] args) throws java.io.IOException {
//    String pdfFilePath = args[0];
 
    String pdfFilePath = "C:\\besattningsregattan-2016.pdf";
    Document pdf = PDF.open(pdfFilePath);

  }
}