package hj�lpprogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import paresePdf.PDFToText;
import paresePdf.ParseToObjekt;
import paresePdf.T�vling;
import paresePdf.WrongFormatPDFParseException;
import uiProgram.LopadPDFController;
import uiProgram.MainUi;

public class H�llaKollP�Mapp implements Runnable {
	private File mapp = null;
	private boolean k�ra = true;
	private MainUi mainUi;
	private boolean ers�ttLopp = true;
	private LopadPDFController loadPDFController;

	public H�llaKollP�Mapp(File mapp, MainUi mainUi, boolean ers�ttLopp, LopadPDFController loadPDFController) {
		this.mapp = mapp;
		this.mainUi = mainUi;
		this.ers�ttLopp = ers�ttLopp;
		this.loadPDFController = loadPDFController;
	}

	ArrayList<String> inl�staFiler = new ArrayList<>();

	public void run() {
		while (k�ra) {
//			System.out.println("_____________�r den pausad = " + pausa);
			if (pausa == false) {
				for (File fil : mapp.listFiles()) {
//					System.out.println("filen just nu = " + fil.getAbsolutePath());
					if (Redaninl�st(fil) == false && fil.getName().split("\\.")[fil.getName().split("\\.").length - 1]
							.equalsIgnoreCase("PDF")) {// !fil.getAbsolutePath().equalsIgnoreCase("C:\\BOOTNXT")){
						T�vling t;
						try {

							t = new ParseToObjekt().ParseToT�vling(PDFToText.getTextFromPDF(fil.getAbsolutePath()));
							if (ers�ttLopp == true)
								mainUi.addLoppErs�tt(t);
							else
								mainUi.addLoppHoppa�ver(t);

							loadPDFController.addinl�stFil(fil.getName());
						} catch (WrongFormatPDFParseException | FileNotFoundException e) {
							// System.out.println("Det var fel i PDF:en som inte
							// s�g ut som standarden b�r, s� filen gick inte att
							// l�sa tyv�r :/");
							// e.printStackTrace();
							loadPDFController.addFelaktikFil(fil.getName());
						}

						// ta hand om "t�vlingen" som m�jligen har l�sts in och
						// annars
						inl�staFiler.add(fil.getAbsolutePath());
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

	private boolean Redaninl�st(File fil) {
		for (String s : inl�staFiler) {
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
