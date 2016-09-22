package uiProgram;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import paresePdf.Lopp;

/**
 * @author Liuns men vissa rester efter en Marco Jakob kåd
 */
public class LoppEditDialogController {
	
	@FXML
    private TextField loppNummerLable;
    @FXML
    private TextField distansLable;
    @FXML
    private TextField klassLable;
    @FXML
    private TextField startTidLable;
    @FXML
    private TextField typAvLoppLable;
    @FXML
    private TextField typAvLoppNummer;
    @FXML
    private TextField datumLable;


    private Stage dialogStage;
    private Lopp lopp;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
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
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setLopp(Lopp person) {
        this.lopp = person;

        if(lopp.getLoppNummerIntegerProperty().getValue() == -1)
        	loppNummerLable.setText("");
        else
        	loppNummerLable.setText(lopp.getloppnummer());
        
        distansLable.setText(lopp.getDistans());
        klassLable.setText(lopp.getKlass());
        startTidLable.setText(lopp.getTid());
        typAvLoppLable.setText(lopp.getTyp());
        if(lopp.getTypNummerIntegerProperty().getValue() == -1)
        	typAvLoppNummer.setText("");
        else
        	typAvLoppNummer.setText(lopp.getTypNummer());
        datumLable.setText(lopp.getDatum());
      
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
            lopp.setLoppNummer(Integer.parseInt(loppNummerLable.getText().trim()));
            lopp.setdistans(distansLable.getText());
            lopp.setKlass(klassLable.getText());
            lopp.setStartTid(startTidLable.getText());
            lopp.setTypAvLopp(typAvLoppLable.getText());
            lopp.setTypAvLoppNummer(Integer.parseInt(typAvLoppNummer.getText()));
            lopp.setDatum(datumLable.getText());

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

        if (loppNummerLable.getText() == null || !testOmDeÄrTal(loppNummerLable.getText())) {
            errorMessage += "No valid number of the race!\n";
            
            //TODO lägga till så den tittar så det inte finns förut heller
        }
        if (distansLable.getText() == null || distansLable.getText().length() == 0) {
            errorMessage += "No valid distanse!\n"; 
        }
        if (klassLable.getText() == null || klassLable.getText().length() == 0) {
            errorMessage += "No valid class!\n"; 
        }
        if (startTidLable.getText() == null || startTidLable.getText().length() == 0) {
            errorMessage += "No valid startn time!\n"; 
        }
        if (typAvLoppLable.getText() == null || typAvLoppLable.getText().length() == 0) {
            errorMessage += "No valid startn time!\n"; 
        }
        if (typAvLoppNummer.getText() == null || typAvLoppNummer.getText().length() == 0) {
            errorMessage += "No valid heat (lopp) number!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(typAvLoppNummer.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid heat (lopp) number (must be an integer)!\n"; 
            }
        }
        if (datumLable.getText() == null || startTidLable.getText().length() == 0) {
            errorMessage += "No valid startn time!\n"; 
        }
        

//        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
//            errorMessage += "No valid birthday!\n";
//        } else {
//            if (!DateUtil.validDate(birthdayField.getText())) {
//                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
//            }
//        }

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