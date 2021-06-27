package uiProgram;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import loadDatabasParts.DatabasClass;
import loadDatabasParts.LoadDatabasInformation;

import java.io.File;
import java.sql.SQLException;

public class SqlDatabasInterfaceController implements LoadDatabasInformation {

    @FXML
    Button send;

	@FXML
	Button selectdatabas;

    @FXML
    TextField databasURL;

	@FXML
	TextField sql;

    @FXML
    TextArea sqlOutput;
    
    @FXML
    Text databasInfo;

	DatabasClass databasClass = null;
    
    
    private File databaseFile = null;


	@FXML
    private void initialize() {
		send.setDisable(true);
	}

	
	/**
	 * Handle datafilieShoser button
	 */
	final FileChooser fileChooser = new FileChooser();
	@FXML
	private void handleShoseDatabas(){
		System.out.println("selecting file");
		databaseFile = fileChooser.showOpenDialog(null);
		
		
		if(databaseFile !=null){
			databasURL.setText(databaseFile.getAbsolutePath());
			try {
				databasClass = new DatabasClass(databasURL.getText());
				send.setDisable(false);
			} catch (Exception e) {
				e.printStackTrace();
				databasInfo.setText("could not connect to DB is the correct file? Logging error to terminal");
				databasURL.setText("");
				send.setDisable(true);
			}
		}else{
			databasURL.setText("");
			send.setDisable(true);
		}
	}

	@FXML
	private void runQuery(){
//		try {
	}
	@Override
	public void addDatabasLoadInfo(String info) {
		databasInfo.setText(info);
	}
}
