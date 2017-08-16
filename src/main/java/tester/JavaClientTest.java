package tester;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class JavaClientTest {

//	public static void main(String[] args) {
//		System.out.println("\n============Output:============ \n" + callURL("https://httpbin.org/post"));//callURL("http://localhost:8080/CrunchifyRESTJerseyExample/crunchify/ctofservice/"));
//	}
// 
//	public static String callURL(String myURL) {
//		System.out.println("Requested URL: " + myURL);
//		StringBuilder sb = new StringBuilder();
//		URLConnection urlConn = null;
//		InputStreamReader in = null;
//		try {
//			URL url = new URL(myURL);
//			urlConn = url.openConnection();
//			if (urlConn != null)
//				urlConn.setReadTimeout(60 * 1000);
//			if (urlConn != null && urlConn.getInputStream() != null) {
//				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
//				BufferedReader bufferedReader = new BufferedReader(in);
//				if (bufferedReader != null) {
//					int cp;
//					while ((cp = bufferedReader.read()) != -1) {
//						sb.append((char) cp);
//					}
//					bufferedReader.close();
//				}
//			}
//			in.close();
//		} catch (Exception e) {
//			throw new RuntimeException("Exception while calling URL:" + myURL, e);
//		}
// 
//		return sb.toString();
//	}
	
	

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

//		HttpURLConnectionExample http = new HttpURLConnectionExample();
		JavaClientTest http = new JavaClientTest();

//		System.out.println("Testing 1 - Send Http GET request");
//		http.sendGet();

//		System.out.println("\nTesting 2 - Send Http POST request");
//		http.sendPost();
		
		System.out.println("\nTesting sendPostTävling - Send Http POST request");
		//http.sendPostTävling();
		System.out.println("\nTesting sendPostTävling - skikat eller?");
		
//		System.out.println("Testing 1 - Send Http GET request");
//		http.sendGet();

	}
	
	
	// HTTP POST request
		private void sendPostTävling() throws Exception {

			String strURL = "http://localhost:6423/api/values/";
			String postData = "x=val1&y=val2";
			URL url = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json"); 
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("Set-Cookie", sessionCookie);
			conn.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));

			
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			// How to add postData as http body?
			String str =  "{\"datum\":\"kalaskuldatum\",\"loppena\":[{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:37\",\"klass\":\"H14\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"1\",\"banorna\":[]},{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:40\",\"klass\":\"H14\",\"typ\":\"försök\",\"typNummer\":\"2\",\"loppNummer\":\"2\",\"banorna\":[]},{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:41\",\"klass\":\"H16\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"3\",\"banorna\":[{\"namn\":\"Klas G�ran\",\"klubb\":\"närkets pk\",\"bana\":\"1\",\"tid\":\"12 min\"},{\"namn\":\"jonas lefson\",\"klubb\":\"närkets pk\",\"bana\":\"2\",\"tid\":\"9 min\"},{\"namn\":\"M�rten Frisk\",\"klubb\":\"Kalix paddelf�rening\",\"bana\":\"3\",\"tid\":\"12 min\"}]},{\"Datum\":\"12/4/2003\",\"distans\":\"1000m\",\"tid\":\"12:43\",\"klass\":\"D14\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"4\",\"banorna\":[]}]}";
			byte[] outputInBytes = str.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			
			os.write( outputInBytes );    
			os.close();
			
			
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());


			
			
//			String url = "http://localhost:6423/api/values/";
//			
//			URL obj = new URL(url);
//			HttpURLConnection con = (HttpURLConnection) /*(HttpsURLConnection)*/ obj.openConnection();
//
//			//add reuqest header
//			con.setRequestMethod("PUT");//"POST");
//			con.setRequestProperty("User-Agent", USER_AGENT);
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//			con.setRequestProperty("Content-Type","application/json");
//			
//			
//			
//			
//			
//			
//			
//			
//
//			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//
//			// Send post request
//			con.setDoOutput(true);
////			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
////			wr.writeBytes(urlParameters);
////			wr.flush();
////			wr.close();
//			
//			String str =  "{\"x\": \"val1\",\"y\":\"val2\"}";
//			byte[] outputInBytes = str.getBytes("UTF-8");
//			OutputStream os = con.getOutputStream();
//			os.write( outputInBytes );    
//			os.close();
//			
//			
//
//			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + urlParameters);
//			System.out.println("Response Code : " + responseCode);
//
//			BufferedReader in = new BufferedReader(
//			        new InputStreamReader(con.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			in.close();
//
//			//print result
//			System.out.println(response.toString());

		}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url =  "http://localhost:13866/Api/BannerFlow/";//http://www.google.com/search?q=mkyong";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "http://localhost:13866/Api/BannerFlow/" + "1" + "?text="+ "LitataxtTillSaker"; //"https://selfsolve.apple.com/wcResults.do";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) /*(HttpsURLConnection)*/ obj.openConnection();

		//add reuqest header
		con.setRequestMethod("PUT");//"POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
}
