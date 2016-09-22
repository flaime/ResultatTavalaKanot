package uiProgram;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import paresePdf.Lopp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

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
    
    /**
     * @author ahlin
     */
    @FXML
    private void HandelläsInInställning(){
//    	System.out.println();
    	mainUi.showInställningsDialog();
//
//        } else {
//            // Nothing selected.
//            Alert alert = new Alert(AlertType.WARNING);
//            alert.initOwner(mainUi.getPrimaryStage());
//            alert.setTitle("No Selection");
//            alert.setHeaderText("No Person Selected");
//            alert.setContentText("Please select a person in the table.");
//
//            alert.showAndWait();
//        }
    }
    
    /**
     * @author ahlin
     */
    @FXML
    private void HandelPushaFonster(){
    	mainUi.showPushaDialog();
    }

//    /**
//     * Creates an empty address book.
//     */
//    @FXML
//    private void handleNew() {
//        mainUi.getPersonData().clear();
//        mainUi.setPersonFilePath(null);
//    }
//
//    /**
//     * Opens a FileChooser to let the user select an address book to load.
//     */
//    @FXML
//    private void handleOpen() {
//        FileChooser fileChooser = new FileChooser();
//
//        // Set extension filter
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                "XML files (*.xml)", "*.xml");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        // Show save file dialog
//        File file = fileChooser.showOpenDialog(mainUi.getPrimaryStage());
//
//        if (file != null) {
//            mainUi.loadPersonDataFromFile(file);
//        }
//    }
//
//    /**
//     * Saves the file to the person file that is currently open. If there is no
//     * open file, the "save as" dialog is shown.
//     */
//    @FXML
//    private void handleSave() {
//        File personFile = mainUi.getPersonFilePath();
//        if (personFile != null) {
//            mainUi.savePersonDataToFile(personFile);
//        } else {
//            handleSaveAs();
//        }
//    }
//
//    /**
//     * Opens a FileChooser to let the user select a file to save to.
//     */
//    @FXML
//    private void handleSaveAs() {
//        FileChooser fileChooser = new FileChooser();
//
//        // Set extension filter
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                "XML files (*.xml)", "*.xml");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        // Show save file dialog
//        File file = fileChooser.showSaveDialog(mainUi.getPrimaryStage());
//
//        if (file != null) {
//            // Make sure it has the correct extension
//            if (!file.getPath().endsWith(".xml")) {
//                file = new File(file.getPath() + ".xml");
//            }
//            mainUi.savePersonDataToFile(file);
//        }
//    }

    /**
     * Opens an about dialog.
     * @author Linus Ahlin-Hamberg
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Lopp App");
        alert.setHeaderText("About");
        alert.setContentText("Skapad av:\nLinus Ahlin Hamberg\nFör frågor kontakta mig på ah.linus@gmail.com\nHoppas det funkar bra och mycket skoj och glädje!");

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