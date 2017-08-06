package uiProgram;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import loadDatabasParts.ParseDatabasToT‰vling;
import paresePdf.T‰vling;

public class DatabasLoderController {

private MainUi mainUi;
    
    @FXML
    ToggleButton on;
    @FXML
    ToggleButton of;
    
    @FXML
    RadioButton replace;
    @FXML
    RadioButton skipp;
    
    @FXML
    Button readDatabas;
    private File databasFile = null;
    private ToggleGroup onOfGrop = new ToggleGroup();
    
    private ParseDatabasToT‰vling pdb = null;
    
    public void setMainUi(MainUi mainUi) {
		this.mainUi = mainUi;
    }
	
	@FXML
    private void initialize() {
		on.setToggleGroup(onOfGrop);
		of.setToggleGroup(onOfGrop);
		//add change listner for typ of the replace or skip button
	}
	
	/**
	 * Handle datafilieShoser button
	 */
	final FileChooser fileChooser = new FileChooser();
	@FXML
	private void handleShoseDatabas(){
		System.out.println("selecting file");
//		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".mdb","Acess databas files (*.mdb)", "*.accdr", "*.accdt","old but working (*.accdb)" );
//		fileChooser.getExtensionFilters().add(extFilter);
		databasFile = fileChooser.showOpenDialog(null);
		
		
		if(databasFile !=null){
			pdb = new ParseDatabasToT‰vling(databasFile.getAbsolutePath());
			readDatabas.setDisable(false);
		}
	}
	
	@FXML
	private void loadDatabas(){
		System.out.println("databas being loded");
		T‰vling t = pdb.parserDatbas();
		
		if(replace.isSelected())
			MainUi.addLoppErs‰tt(t);
		else
			MainUi.addLoppHoppa÷ver(t);
	}
}
