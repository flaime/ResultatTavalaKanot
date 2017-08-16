package tester;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Småtester {

	public static void main (String[] args){
		
		
		System.out.println(testOmDeÄrTal("  2"));
	}
	
	public static boolean testOmDeÄrTal(String s){
		
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
