package paresePdf;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bana {

	private final StringProperty namn = new SimpleStringProperty("non");
	private final StringProperty klubb = new SimpleStringProperty("non");
	private final StringProperty bana = new SimpleStringProperty("non");
	private final StringProperty tid = new SimpleStringProperty("non");
	
	private final List<Person> persons = new ArrayList<>();
	
	public Bana(String namn, String klubb, String bana, String tid) {
		this.namn.set(namn);
		this.klubb.set(klubb);
		this.bana.set(bana);
		this.tid.set(tid);
	}
	
	
	public Bana(List<Person> persons, String bana, String tid) {
		this.persons.addAll(persons);
		this.namn.set(nameConcat());
		this.klubb.set(ClubbConcat());
		this.bana.set(bana);
		this.tid.set(tid);
	}
	private String nameConcat(){
		String concatNames ="";
		for(int i =0; i< persons.size(); i++){
			if(0 == i)
				concatNames += persons.get(i).getForName() + " " + persons.get(i).getSureName();
			else
				concatNames += "/" + persons.get(i).getForName() + " " + persons.get(i).getSureName();
		}
			return concatNames;
	}
	
	private String ClubbConcat(){
		String concatNames ="";
		for(int i =0; i< persons.size(); i++){
			if(0 == i)
				concatNames += persons.get(i).getKlub();
			else
				concatNames += "/" + persons.get(i).getKlub();
		}
			return concatNames;
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
		json += transformTillVärde("namn", namn.getValue());
		json += ",";
		json += transformTillVärde("klubb", klubb.getValue());
		json += ",";
		json += transformTillVärde("bana", bana.getValue());
		json += ",";
		json += transformTillVärde("tid", tid.getValue());
		json += "}";
		return json;
	}
	private String transformTillVärde(String namn , String värde){
		return "\""+namn+"\":\""+värde+"\"";
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
