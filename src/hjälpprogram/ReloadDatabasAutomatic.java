package hj‰lpprogram;

import paresePdf.T‰vling;
import uiProgram.MainUi;

public class ReloadDatabasAutomatic implements Runnable {

	private String datbasURL;
	private MainUi mainUi;
	private boolean replace = false;
	private boolean run = true;

	public ReloadDatabasAutomatic(String databas, MainUi mainUi, boolean replace) {
		datbasURL = databas;
		this.mainUi = mainUi;
		this.replace = replace;
	}

	public boolean isRuning() {
		return run;
	}

	public void setRuning(boolean run) {
		this.run = run;
	}

	private int milisecondsBetwenDataReading = 4000;

	@Override
	public void run() {

		while (true) {
			if (run) {

				T‰vling t = null;
				if (replace == true)
					mainUi.addLoppErs‰tt(t);
				else
					mainUi.addLoppHoppa÷ver(t);

				try {
					Thread.sleep(milisecondsBetwenDataReading);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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