package paresePdf;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bana {

	private final StringProperty namn = new SimpleStringProperty("non");
	private final StringProperty klubb = new SimpleStringProperty("non");
	private final StringProperty bana = new SimpleStringProperty("non");
	private final StringProperty tid = new SimpleStringProperty("non");
	
	public Bana(String namn, String klubb, String bana, String tid) {
		this.namn.set(namn);
		this.klubb.set(klubb);
		this.bana.set(bana);
		this.tid.set(tid);
	}
	
	
	@Override
	public String toString() {
		return namn.get() + "\t" +  klubb.get() + "\t" + bana.get() + "\t" + tid.get();
	}
	public void setBanNummer(String nummer){
		bana.set(nummer);
	}

	public String getJson() {
		String json = "{";
		json += transformTillV�rde("namn", namn.getValue());
		json += ",";
		json += transformTillV�rde("klubb", klubb.getValue());
		json += ",";
		json += transformTillV�rde("bana", bana.getValue());
		json += ",";
		json += transformTillV�rde("tid", tid.getValue());
		json += "}";
		return json;
	}
	private String transformTillV�rde(String namn , String v�rde){
		return "\""+namn+"\":\""+v�rde+"\"";
	}
	
	public StringProperty getNamn() {
		return namn;
	}
	
	public StringProperty getKlubb() {
		return klubb;
	}
	
	public StringProperty getTid() {
		return tid;
	}
	
	public StringProperty getBanaNummer() {
		return bana;
	}


	public void setPersonNamn(String newValue) {
		namn.setValue(newValue);
		
	}


	public void setKlubb(String klubben) {
		klubb.setValue(klubben);
		
	}


	public void setTiden(String tid) {
		this.tid.setValue(tid);
		
	}
}
