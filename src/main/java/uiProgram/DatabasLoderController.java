package uiProgram;

import java.io.File;

import hjälpprogram.ReloadDatabasAutomatic;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
//import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import loadDatabasParts.LoadDatabasInformation;
import loadDatabasParts.ParseDatabasToTävling;
import paresePdf.Tävling;

public class DatabasLoderController implements LoadDatabasInformation {

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
    @FXML
    TextField databasURL;
    
    @FXML
    Text databasInfo;
    
    
    private ReloadDatabasAutomatic rdb =null;
    private File databasFile = null;
    private ToggleGroup onOfGrop = new ToggleGroup();
    
    private ParseDatabasToTävling pdb = null;
    private boolean replaceCompetition = true;
    private Thread loadDatabasThred = null;
    public void setMainUi(MainUi mainUi) {
		this.mainUi = mainUi;
    }
	
	@FXML
    private void initialize() {
		on.setToggleGroup(onOfGrop);
		of.setToggleGroup(onOfGrop);
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
			pdb = new ParseDatabasToTävling(databasFile.getAbsolutePath(), this);
			readDatabas.setDisable(false);
			databasURL.setText(databasFile.getAbsolutePath());
			on.setDisable(false);
			of.setDisable(false);
			if(rdb == null){
				rdb = new ReloadDatabasAutomatic(pdb, mainUi, replaceCompetition);
				rdb.setDaemon(true); 
				rdb.start();
				
//				loadDatabasThred = new Thread(rdb);
//				loadDatabasThred.run();
			}else
				rdb.setDatabasLoader(pdb);
			
		}else{
			on.setDisable(true);
			of.setDisable(true);
			databasURL.setText("");
			readDatabas.setDisable(true);
			pdb = null;
		}
			
	}
	
	@FXML
	private void loadDatabas(){
		System.out.println("databas being loded");
		Tävling t = pdb.parserDatbas();
		
		if(replace.isSelected()) {
			MainUi.addLoppErsätt(t);
			MainUi.addKlubbarErsätt(t);
		} else {
			MainUi.addLoppHoppaÖver(t);
			MainUi.addKlubbarHoppaÖver(t);
		}
	}
	
	
	
	@FXML
	public void autoLoadPdfOn(){
		System.out.println("load");
		readDatabas.setDisable(true);
		rdb.setRuning(true);
		System.out.println("load2");

//		rdb.setRuning(true);
//		rdb.putToSleep();
//		if(!loadDatabasThred.isAlive())
//		loadDatabasThred.start();
	}
	
	@FXML
	public void autoLoadPdfOf(){
		System.out.println("load3");

		readDatabas.setDisable(false);
		rdb.setRuning(false);
		System.out.println("load4");

//		rdb.putToSleep();
//		rdb.setRuning(false);
	}

	@Override
	public void addDatabasLoadInfo(String info) {
		databasInfo.setText(info);
	}
	
	@FXML
	public void replaceRadioButtonPressed(){
		rdb.replaseLopp(true);
	}
	
	@FXML
	public void skippRadioButtonPressed(){
		rdb.replaseLopp(false);
	}
}
