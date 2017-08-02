package hjälpprogram;
 
 import paresePdf.Tävling;
 import uiProgram.MainUi;
 
 public class ReloadDatabasAutomatic implements Runnable {
 
 private String datbasURL;
 private MainUi mainUi;
 private boolean replace = false;
 private boolean run = true;
 public boolean isRuning() {
 	return run;
 }
 
 public void setRuning(boolean run) {
 	this.run = run;
 }
 
 private int milisecondsBetwenDataReading = 4000;
 
 public ReloadDatabasAutomatic(String databas, MainUi mainUi, boolean replace) {
 	datbasURL = databas;
 	this.mainUi = mainUi;
 	this.replace = replace;
 }
 
 @Override
 public void run() {
 	
 	
 	while(run){
 		
 		Tävling t ;
 		if (replace == true)
 			mainUi.addLoppErsätt(t);
 		else
 			mainUi.addLoppHoppaÖver(t);
 		
 		try {
 			Thread.sleep(milisecondsBetwenDataReading);
 		} catch (InterruptedException e) {
 			e.printStackTrace();
 		}
 	}
 }
 
 public int getMilisecondsBetwenDataReading() {
 	return milisecondsBetwenDataReading;
 }
 
 public void setMilisecondsBetwenDataReading(int milisecondsBetwenDataReading) {
 	this.milisecondsBetwenDataReading = milisecondsBetwenDataReading;
 }
 
 }