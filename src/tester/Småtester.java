package tester;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sm�tester {

	public static void main (String[] args){
		
		
		System.out.println(testOmDe�rTal("  2"));
	}
	
	public static boolean testOmDe�rTal(String s){
		
		s= s.trim();
		
		Pattern pattern = Pattern.compile("\\d*");
		Matcher  matcher = pattern.matcher(s);
		if(s.equals(""))
			return false;
		else if( matcher.matches() ){
			return true;
		}
		else
			return false;
	}
}
