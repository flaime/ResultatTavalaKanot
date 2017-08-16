package hjälpprogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import paresePdf.PDFToText;
import paresePdf.ParseToObjekt;
import paresePdf.Tävling;
import paresePdf.WrongFormatPDFParseException;
import uiProgram.LopadPDFController;
import uiProgram.MainUi;

public class HållaKollPåMapp implements Runnable {
	private File mapp = null;
	private boolean köra = true;
	private MainUi mainUi;
	private boolean ersättLopp = true;
	private LopadPDFController loadPDFController;

	public HållaKollPåMapp(File mapp, MainUi mainUi, boolean ersättLopp, LopadPDFController loadPDFController) {
		this.mapp = mapp;
		this.mainUi = mainUi;
		this.ersättLopp = ersättLopp;
		this.loadPDFController = loadPDFController;
	}

	ArrayList<String> inlästaFiler = new ArrayList<>();

	public void run() {
		while (köra) {
//			System.out.println("_____________är den pausad = " + pausa);
			if (pausa == false) {
				for (File fil : mapp.listFiles()) {
//					System.out.println("filen just nu = " + fil.getAbsolutePath());
					if (Redaninläst(fil) == false && fil.getName().split("\\.")[fil.getName().split("\\.").length - 1]
							.equalsIgnoreCase("PDF")) {// !fil.getAbsolutePath().equalsIgnoreCase("C:\\BOOTNXT")){
						Tävling t;
						try {

							t = new ParseToObjekt().ParseToTävling(PDFToText.getTextFromPDF(fil.getAbsolutePath()));
							if (ersättLopp == true)
								mainUi.addLoppErsätt(t);
							else
								mainUi.addLoppHoppaÖver(t);

							loadPDFController.addinlästFil(fil.getName());
						} catch (WrongFormatPDFParseException | FileNotFoundException e) {
							// System.out.println("Det var fel i PDF:en som inte
							// såg ut som standarden bör, så filen gick inte att
							// läsa tyvär :/");
							// e.printStackTrace();
							loadPDFController.addFelaktikFil(fil.getName());
						}

						// ta hand om "Tävlingen" som möjligen har lästs in och
						// annars
						inlästaFiler.add(fil.getAbsolutePath());
					}
				}

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private boolean Redaninläst(File fil) {
		for (String s : inlästaFiler) {
			if (fil.getAbsolutePath().equalsIgnoreCase(s))
				return true;
		}
		return false;
	}

	private boolean pausa = false;

	public void pausa(boolean pausa) {
		this.pausa = pausa;
	}
	

}
