package uiProgram;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.GenericValidator;
import paresePdf.Lopp;

/**
 * @author Liuns men vissa rester efter en Marco Jakob k�d
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

    private static final String TIME24HOURS_PATTERN =
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";

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
            
            //TODO l�gga till s� den tittar s� det inte finns f�rut heller
        }
        if (distansLable.getText() == null || distansLable.getText().length() == 0) {
            errorMessage += "No valid distanse!\n"; 
        }
        if (klassLable.getText() == null || klassLable.getText().length() == 0) {
            errorMessage += "No valid class!\n"; 
        }
        if (startTidLable.getText() == null || startTidLable.getText().length() == 0 || !validateTime(startTidLable.getText())) {
            errorMessage += "No valid startn time! (foramat is \"HH:MM\")\n";
        }
        if (typAvLoppLable.getText() == null || typAvLoppLable.getText().length() == 0) {
            errorMessage += "No valid typ av lopp!\n";
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
        if (datumLable.getText() == null || datumLable.getText().length() == 0 || !validateDate(datumLable.getText()) ) {
            errorMessage += "No valid date fromat! (Format \"yyy-mm-dd\")\n";
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

    /**
     * Validate time in 24 hours format with regular expression
     * @param time time address for validation
     * @return true valid time fromat, false invalid time format
     * TODO maybe use commons-validator
     */
    public static boolean validateTime(final String time){
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();

    }

    /**
     * Validate date in YYYY-MM-DD format with apache commons-validator
     * @param date date address for validation
     * @return true valid date fromat, false invalid date format
     */
    public static boolean validateDate(final String date){
        return GenericValidator.isDate(date, "yyyy-MM-dd", true);
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