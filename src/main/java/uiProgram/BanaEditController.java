package uiProgram;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import paresePdf.Bana;


/**
 * @author Liuns
 */
public class BanaEditController {
	
	@FXML
    private TextField banaLable;
    @FXML
    private TextField namnLable;
    @FXML
    private TextField klubbLable;
    @FXML
    private TextField tidLable;


    private MainUi mainUi;
    private Stage dialogStage;
    private Bana bana;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sett vilket lopp som ska fyllas med info
     */
    public void setBana(Bana bana, MainUi mainUi) {
        this.bana =  bana;
        this.mainUi = mainUi;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            bana.setBanNummer(banaLable.getText());// .setLoppNummer(Integer.parseInt(loppNummerLable.getText().trim()));
            bana.setPersonNamn(namnLable.getText());
            bana.setKlubb(klubbLable.getText());
            bana.setTiden(tidLable.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (banaLable.getText() == null || !testOmDeÄrTal(banaLable.getText())) {
        	if(mainUi.finnsLoppMedNummer(banaLable.getText()))
        		errorMessage += "Loppnumret existerar redan välj ett annat.";
            errorMessage += "No valid number of the race!\n";
            
            //TODO lägga till så den tittar så det inte finns förut heller
        }
        if (namnLable.getText() == null || namnLable.getText().length() == 0) {
            errorMessage += "No valid namn(en)!\n"; 
        }
        if (klubbLable.getText() == null || klubbLable.getText().length() == 0) {
            errorMessage += "No valid klubb(ar)!\n"; 
        }
        if (tidLable.getText() == null || tidLable.getText().length() == 0) {
            errorMessage += "No valid tid!\n"; 
        }
        


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
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