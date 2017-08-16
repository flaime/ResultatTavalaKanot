package uiProgram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * @author ahlin
 */
public class PushaController {


    @FXML
    private TextArea PushInfo;
    
    @FXML
    private TextField serverSokvag;
    
    @FXML
    private TextField attTaBort;



    // Reference to the main application
    private MainUi mainUi;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainUi mainUi) {
        this.mainUi = mainUi;
    }
    @FXML
    private void startStopKnapp(){
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Finns ej");
        alert.setContentText("Detta är inte än gjort så det funkar tyvär inte än automatiskt utan måste pushas manuelt :/ Här kommer lite info: \nSkapad av:\nLinus Ahlin Hamberg\nFör frågor kontakta mnig på ah.linus@gmail.com\nHoppas det funkar bra och mycket skoj och glädje!\nJa eller om du vill vara med och fixa detta :) ");

        alert.showAndWait();
    }
    @FXML
    private void handelPusha(){
    	try {
			sendPostTävling(serverSokvag.getText(), mainUi.getTävlingJson());
		} catch (Exception e) {
			PushInfo.appendText("Gick inte att pusha prova ändra sökvögen eller likander lycka till ");
			e.printStackTrace();
		} //response.toString());
    }
    
    
    
 // HTTP POST request
 		private void sendPostTävling(String strURL, String jsonTävling) throws Exception {

 			strURL += "/api/values/";//"http://localhost:6423/api/values/";
 			String postData = "x=val1&y=val2";
 			URL url = new URL(strURL);
 			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 			conn.setRequestMethod("POST");
 			conn.setRequestProperty("Content-Type","application/json"); 
 			conn.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));

 			
 			conn.setUseCaches(false);
 			conn.setDoInput(true);
 			conn.setDoOutput(true);
 			
 			// How to add postData as http body?
 			String str = jsonTävling;// "{\"datum\":\"kalaskuldatum\",\"loppena\":[{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:37\",\"klass\":\"H14\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"1\",\"banorna\":[]},{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:40\",\"klass\":\"H14\",\"typ\":\"försök\",\"typNummer\":\"2\",\"loppNummer\":\"2\",\"banorna\":[]},{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:41\",\"klass\":\"H16\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"3\",\"banorna\":[{\"namn\":\"Klas G�ran\",\"klubb\":\"närkets pk\",\"bana\":\"1\",\"tid\":\"12 min\"},{\"namn\":\"jonas lefson\",\"klubb\":\"närkets pk\",\"bana\":\"2\",\"tid\":\"9 min\"},{\"namn\":\"M�rten Frisk\",\"klubb\":\"Kalix paddelf�rening\",\"bana\":\"3\",\"tid\":\"12 min\"}]},{\"Datum\":\"12/4/2003\",\"distans\":\"1000m\",\"tid\":\"12:43\",\"klass\":\"D14\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"4\",\"banorna\":[]}]}";
 			byte[] outputInBytes = str.getBytes("UTF-8");
 			OutputStream os = conn.getOutputStream();
 			
 			os.write( outputInBytes );    
 			os.close();
 			
 			
 			int responseCode = conn.getResponseCode();
 			PushInfo.appendText("Sending 'POST' request to URL : " + url + "\n");
 			PushInfo.appendText("Response Code : " + responseCode + "\n");
 			
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
 			PushInfo.appendText("Det som servern la till: "+response.toString() + "\n---------------------\n");
 		}
    @FXML
    private void handleTaBort(){
    	try {
    		removeHelaTävling(serverSokvag.getText());
		} catch (Exception e) {
			PushInfo.appendText("Gick inte att ta bort prova ändra sökvägen eller likander lycka till ");
			e.printStackTrace();
		}
    }
    
    private void removeHelaTävling(String strURL) throws Exception {
			strURL += "/api/values/";//"http://localhost:6423/api/values/";
			String postData = "x=val1&y=val2";
			URL url = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type","application/json"); 
			conn.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));

			
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			// How to add postData as http body?
//			String str = jsonTävling;// "{\"datum\":\"kalaskuldatum\",\"loppena\":[{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:37\",\"klass\":\"H14\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"1\",\"banorna\":[]},{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:40\",\"klass\":\"H14\",\"typ\":\"försök\",\"typNummer\":\"2\",\"loppNummer\":\"2\",\"banorna\":[]},{\"Datum\":\"12/4/2003\",\"distans\":\"500m\",\"tid\":\"12:41\",\"klass\":\"H16\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"3\",\"banorna\":[{\"namn\":\"Klas G�ran\",\"klubb\":\"närkets pk\",\"bana\":\"1\",\"tid\":\"12 min\"},{\"namn\":\"jonas lefson\",\"klubb\":\"närkets pk\",\"bana\":\"2\",\"tid\":\"9 min\"},{\"namn\":\"M�rten Frisk\",\"klubb\":\"Kalix paddelf�rening\",\"bana\":\"3\",\"tid\":\"12 min\"}]},{\"Datum\":\"12/4/2003\",\"distans\":\"1000m\",\"tid\":\"12:43\",\"klass\":\"D14\",\"typ\":\"försök\",\"typNummer\":\"1\",\"loppNummer\":\"4\",\"banorna\":[]}]}";
//			byte[] outputInBytes = str.getBytes("UTF-8");
//			OutputStream os = conn.getOutputStream();
//			
//			os.write( outputInBytes );    
//			os.close();
			
			
			int responseCode = conn.getResponseCode();
			PushInfo.appendText("Sending 'Delete' request to URL : " + url + "\n");
			PushInfo.appendText("Response Code : " + responseCode + "\n");
			
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
			PushInfo.appendText("Det som servern la till: "+response.toString() + "\n---------------------\n");

    }
    

    /**
     * Opens an about dialog.
     * @author Linus Ahlin-Hamberg
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Lopp App");
        alert.setHeaderText("About");
        alert.setContentText("Detta är inte än gjort så det funkar tyvär inte än automatiskt utan måste pushas manuelt :/ Här kommer lite info: \nSkapad av:\nLinus Ahlin Hamberg\nFår frågor kontakta mnig på ah.linus@gmail.com\nHoppas det funkar bra och mycket skoj och glädje!\nJa eller om du vill vara med och fixa detta :) ");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}