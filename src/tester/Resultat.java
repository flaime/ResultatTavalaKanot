package tester;

public class Resultat {

	public static void main(String[] args){
		String text = "Resultatlista Kanot SM 2015";
		String text2 = "Startlista Kanot SM 2016";
		text = text2;
		if(text.toLowerCase().contains("Resultatlista".toLowerCase())){
			System.out.println("�r Resultatlista");
		}else if(text.toLowerCase().contains("Startlista".toLowerCase())){
			System.out.println("�r Startlista");
		}else
			System.out.println("ineh�ller inget");
	}
}
