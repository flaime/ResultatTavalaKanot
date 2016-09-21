package uiProgram;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import paresePdf.Bana;
import paresePdf.Lopp;

public class LoppVisarenKontroller {

	@FXML
    private TableView<Lopp> loppTable;
    @FXML
    private TableColumn<Lopp, String> loppNummerColumn;
    @FXML
    private TableColumn<Lopp, String> KlassColumn;

    @FXML
    private Label loppNummerLable;
    @FXML
    private Label distansLable;
    @FXML
    private Label klassLable;
    @FXML
    private Label startTidLable;
    @FXML
    private Label typAvLoppLable;
    @FXML
    private Label typAvLoppNummer;
    @FXML
    private Label datumLable;
    
    @FXML
    private TableView<Bana> banaTable;
    @FXML
    private TableColumn<Bana, String> BanaNummer;
    @FXML
    private TableColumn<Bana, String> BanaNamn;
    @FXML
    private TableColumn<Bana, String> BanaKlubb;
    @FXML
    private TableColumn<Bana, String> BanaTid;
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteLopp() {
    	int selectedIndex = loppTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	loppTable.getItems().remove(selectedIndex);
        	Lopp radera = loppTable.getSelectionModel().getSelectedItem();
            mainUi.removeLopp(radera);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainUi.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Inget lopp �r markerat");//No Person Selected
            alert.setContentText("V�nligan markera ett lopp i tabellen.");//Please select a person in the table.

            alert.showAndWait();
        }
//        int selectedIndex = loppTable.getSelectionModel().getSelectedIndex();
//        Lopp radera = loppTable.getSelectionModel().getSelectedItem();
        
    }

    // Reference to the main application.
    private MainUi mainUi;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoppVisarenKontroller() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
        // Initialize the person table with the two columns.
    	loppNummerColumn.setCellValueFactory(cellData -> cellData.getValue().getloppnummerStringProperty());
    	KlassColumn.setCellValueFactory(cellData -> cellData.getValue().getKlassStringProperty());

    	
    	// Clear person details.
    	visaLoppDetails(null);

        // Listen for selection changes and show the person details when changed.
    	loppTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> visaLoppDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainUi mainApp) {
        this.mainUi = mainApp;

        // Add observable list data to the table
        loppTable.setItems(mainApp.getLoppData());
        System.out.println(mainApp.getLoppData());
        
    }
    
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    /*
     *  @FXML
    private TableColumn<Lopp, String> BanaNummer;
    @FXML
    private TableColumn<Lopp, String> BanaNamn;
    @FXML
    private TableColumn<Lopp, String> BanaKlubb;
    @FXML
    private TableColumn<Lopp, String> BanaTid;
     */
    private void visaLoppDetails(Lopp lopp) {
        if (lopp != null) {
            // Fill the labels with info from the person object.
            loppNummerLable.setText(lopp.getloppnummer());
            distansLable.setText(lopp.getDistans());
            klassLable.setText(lopp.getKlass());
            startTidLable.setText(lopp.getTid());
            typAvLoppLable.setText(lopp.getTyp());
            typAvLoppNummer.setText(lopp.getTypNummer());
            datumLable.setText(lopp.getDatum());

            // TODO: We need a way to convert the birthday into a String! 
            // birthdayLabel.setText(...);
            //todo impelmentra det exemplet p� b�ttre s�tt att andv�nda "riktig" dataklass och sen k�ra konventering ist�llet ....
            
            System.out.println("--------------------------------||||||||||||||||||||||||||||||||||-------------------\nlopp.getBanorna = " + lopp.getBanorna());
            banaTable.setItems(lopp.getBanorna());//mainApp.getLoppData())
            BanaNummer.setCellValueFactory(cellData -> cellData.getValue().getBanaNummer());
            BanaNamn.setCellValueFactory(cellData -> cellData.getValue().getNamn());
            BanaKlubb.setCellValueFactory(cellData -> cellData.getValue().getKlubb());
            BanaTid.setCellValueFactory(cellData -> cellData.getValue().getTid());
            
            
            
            Callback<TableColumn, TableCell> cellFactory =
                    new Callback<TableColumn, TableCell>() {
                        public TableCell call(TableColumn p) {
                           return new EditingCell();
                        }
                    };
                    
                    
            /**
             * nu blir det lite "repetetivt" men f�r att man ska kunna editera alla kolumner/detajler p� en bana...
             */
            BanaNummer.setCellFactory(TextFieldTableCell.forTableColumn());
            BanaNummer.setOnEditCommit(//setOnEditCommit(
                new EventHandler<CellEditEvent<Bana, String>>() {
                    @Override
                    public void handle(CellEditEvent<Bana, String> t) {
                    	if(lopp.FinnsBanaMedNummer(t.getNewValue())==false){
                        ((Bana) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setBanNummer(t.getNewValue());
                    	}{
                    		if(villDuForts�tta�venOm("Bana", t.getNewValue())){
                    			((Bana) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                        ).setBanNummer(t.getNewValue());
                    		}else{
                    			//beh�ller det gamla v�rdet
                    			//g�r det jag g�r nedan f�r att den ska uppdatera och m�rka att v�rdet inte �ndrades...
                                      t.getTableColumn().setVisible(false);
                                      t.getTableColumn().setVisible(true);
                    		}
                    			
                    	}
                    }
                }
            );
            
            BanaNamn.setCellFactory(TextFieldTableCell.forTableColumn());
            BanaNamn.setOnEditCommit(//setOnEditCommit(
                new EventHandler<CellEditEvent<Bana, String>>() {
                    @Override
                    public void handle(CellEditEvent<Bana, String> t) {
                    	if(lopp.FinnsBanaMedNamnet(t.getNewValue())==false){
                        ((Bana) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setBanNummer(t.getNewValue());
                    	}{
                    		if(villDuForts�tta�venOm("namn", t.getNewValue())){
                    			((Bana) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                        ).setPersonNamn(t.getNewValue());
                    		}else{
                    			//beh�ller det gamla v�rdet
                    			//g�r det jag g�r nedan f�r att den ska uppdatera och m�rka att v�rdet inte �ndrades...
                                      t.getTableColumn().setVisible(false);
                                      t.getTableColumn().setVisible(true);
                    		}
                    			
                    	}
                    }
                }
            );
            
            BanaKlubb.setCellFactory(TextFieldTableCell.forTableColumn());
            BanaKlubb.setOnEditCommit(//setOnEditCommit(
                new EventHandler<CellEditEvent<Bana, String>>() {
                    @Override
                    public void handle(CellEditEvent<Bana, String> t) {
                    	if(lopp.FinnsBanaMedKLubben(t.getNewValue())==false){
                        ((Bana) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setBanNummer(t.getNewValue());
                    	}{
                    		if(villDuForts�tta�venOm("klubb", t.getNewValue())){
                    			((Bana) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                        ).setKlubb(t.getNewValue());
                    		}else{
                    			//beh�ller det gamla v�rdet
                    			//g�r det jag g�r nedan f�r att den ska uppdatera och m�rka att v�rdet inte �ndrades...
                                      t.getTableColumn().setVisible(false);
                                      t.getTableColumn().setVisible(true);
                    		}
                    			
                    	}
                    }
                }
            );
            
            BanaTid.setCellFactory(TextFieldTableCell.forTableColumn());
            BanaTid.setOnEditCommit(//setOnEditCommit(
                new EventHandler<CellEditEvent<Bana, String>>() {
                    @Override
                    public void handle(CellEditEvent<Bana, String> t) {
                    	Alert alert = new Alert(AlertType.CONFIRMATION);
                    	alert.setTitle("S�ker");
                    	alert.setHeaderText("�r du s�ker p� att det �r korekt format p� tiden?");
                    	alert.setContentText("Nu sparas det");

                    	Optional<ButtonType> result = alert.showAndWait();
                    	if (result.get() == ButtonType.OK){
                    		((Bana) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setTiden(t.getNewValue());
                    	} else {
                    		//beh�ller det gamla v�rdet
                			//g�r det jag g�r nedan f�r att den ska uppdatera och m�rka att v�rdet inte �ndrades...
                                  t.getTableColumn().setVisible(false);
                                  t.getTableColumn().setVisible(true);
                    	}
                    }
                }
            );
            
            
            
        } else {
            // Person is null, remove all the text.
            loppNummerLable.setText("");
            distansLable.setText("");
            klassLable.setText("");
            startTidLable.setText("");
            typAvLoppLable.setText("");
            typAvLoppNummer.setText("");
            datumLable.setText("");
        }
    }
    
    protected boolean villDuForts�tta�venOm(String omVad, String skaVi�ndraTillDetta) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Vill du forts�tta och spara " + omVad +" som nu �r " + skaVi�ndraTillDetta + " men redan finns?");
    	alert.setContentText("Ska vi �ndra " + omVad + " till den som redan finns?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    		return false;
    	}
		
	}

	/**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Lopp tempLopp = new Lopp("", "", "", "", "", -1, -1);
        boolean okClicked = mainUi.showPersonEditDialog(tempLopp);
        if (okClicked) {
            mainUi.getLoppData().add(tempLopp);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Lopp selectedPerson = loppTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
        	System.out.println("selected person = " + selectedPerson);
            boolean okClicked = mainUi.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                visaLoppDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainUi.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void visaDetaljer(){
    	Lopp selectedPerson = loppTable.getSelectionModel().getSelectedItem();
    	if(selectedPerson != null){
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(mainUi.getPrimaryStage());
        alert.setTitle("Detaljer");
        alert.setHeaderText("Loppdetaljer �r f�ljande");
        alert.setContentText(selectedPerson.toString());

        alert.showAndWait();
    	}else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainUi.getPrimaryStage());
            alert.setTitle("Inget valt");
            alert.setHeaderText("Ingen person �r vald");
            alert.setContentText("V�nligen v�lj ett lopp fr�n listan.");

            alert.showAndWait();
        }
    }
    class EditingCell extends TableCell<Bana, String> {
    	 
        private TextField textField;
 
        public EditingCell() {
        }
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
    private void g�rTabellernaEditerBara(){
    	
    }
}
