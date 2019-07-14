package hjälpprogram;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import loadDatabasParts.LoadDatabasInformation;
import loadDatabasParts.ParseDatabasToTävling;
import paresePdf.Tävling;
import uiProgram.MainUi;

public class ReloadDatabasAutomatic extends Thread{// implements Runnable {

	private ParseDatabasToTävling databasLoader;
	private MainUi mainUi;
	private boolean replace = false;
	private boolean run = false;
	private ReentrantLock lock = new ReentrantLock();

	
	
	public ReloadDatabasAutomatic(ParseDatabasToTävling databas, MainUi mainUi, boolean replace) {
		databasLoader = databas;
		this.mainUi = mainUi;
		this.replace = replace;
	}

	public boolean isRuning() {
		return run;
	}

	public void setRuning(boolean run) {
		System.out.println("hej");
		synchronized (this) {
			this.run = run;
			System.out.println("hej2");
            this.notifyAll();
            System.out.println("e");
        }
		System.out.println("run = " + run);
//		this.run = run;
	}
	
	public void setDatabasLoader(ParseDatabasToTävling databasLoader){
		lock.lock();
	    try {
	    	this.databasLoader = databasLoader;
	    } finally {
	        lock.unlock();
	    }
	}

	private int milisecondsBetwenDataReading = 4000;
	@Override
	public void run() {System.out.println("1");
			while (true) {
				boolean mabyRun = false;
				synchronized (this) {
					mabyRun = run;
				}
				while (mabyRun) {
					dataExtraction();
					synchronized (this) {
						mabyRun = run;
					}
				}
				
				synchronized (this) {
				try {
                    this.wait();
                } catch (InterruptedException e) {}
				}
			}
	}

	/**
	 * 
	 */
	private void dataExtraction() {
		System.out.println("run = true");

		Tävling t = null;
		lock.lock();
		try {
			t = databasLoader.parserDatbas();
		} finally {
		    lock.unlock();
		}
		
		
		lock.lock();
		try {
			if (replace == true){
				mainUi.addKlubbarErsätt(t);
				mainUi.addLoppErsätt(t);
			}
			else{
				mainUi.addKlubbarHoppaÖver(t);
				mainUi.addLoppHoppaÖver(t);
			}
		} finally {
		    lock.unlock();
		}
		

		try {
			Thread.sleep(milisecondsBetwenDataReading);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void replaseLopp(boolean replace){
		lock.lock();
		try {
			this.replace = replace;
		} finally {
		    lock.unlock();
		}
	}
   
	public int getMilisecondsBetwenDataReading() {
		return milisecondsBetwenDataReading;
	}

	public void setMilisecondsBetwenDataReading(int milisecondsBetwenDataReading) {
		this.milisecondsBetwenDataReading = milisecondsBetwenDataReading;
	}

}