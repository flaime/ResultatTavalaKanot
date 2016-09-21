package tester;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paresePdf.Bana;
import paresePdf.Lopp;
import paresePdf.T�vling;

public class TillJsonEgenbygd {
	
	public static void main(String [] args){
		T�vling t = new T�vling("kalaskuldatum");
		ObservableList<Lopp> lop = FXCollections.observableArrayList();
		lop.add(new Lopp("12/4/2003", "500m", "12:37", "H14", "f�rs�k", 1, 1));
		lop.add(new Lopp("12/4/2003", "500m", "12:40", "H14", "f�rs�k", 2, 2));
		Lopp l1 = new Lopp("12/4/2003", "500m", "12:41", "H16", "f�rs�k", 1, 3);
		ArrayList<Bana> banorna = new ArrayList<>();
		banorna.add(new Bana("Klas G�ran", "n�rkets pk", "1", "12 min"));
		banorna.add(new Bana("jonas lefson", "n�rkets pk", "2", "9 min"));
		banorna.add(new Bana("M�rten Frisk", "Kalix paddelf�rening", "3", "12 min"));
		l1.addBana(banorna);
		lop.add(l1);
		lop.add(new Lopp("12/4/2003", "1000m", "12:43", "D14", "f�rs�k", 1, 4));
		t.addLoppErs�tt(lop);
		
		System.out.println( t.getJsonString());
	}

}
