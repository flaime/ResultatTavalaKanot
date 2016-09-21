package uiProgram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.pdfclown.util.parsers.ParseException;

import hj�lpprogram.H�llaKollP�Mapp;
import hj�lpprogram.H�llaKollSave;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import paresePdf.PDFToText;
import paresePdf.ParseToObjekt;
import paresePdf.T�vling;
import paresePdf.WrongFormatPDFParseException;

public class LopadPDFController {
	
	@FXML
    private TextArea inlastaFiler;
    @FXML
    private CheckBox Autoinl�sning;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private RadioButton lasinErsatt;
    @FXML
    private RadioButton lasInHoppaOver;
    @FXML
    private RadioButton franMappErs�tt;
    @FXML
    private RadioButton franMappHoppaOver;
    @FXML
    private Label sokVagenLable;
    @FXML
    private CheckBox autoinlasning;
    private Stage dialogStage;
    
    private MainUi mainUi;
    
    
    public void setMainUi(MainUi mainUi) {
		this.mainUi = mainUi;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	System.out.println("hej p� dig");
    	autoinlasning.selectedProperty().addListener(new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> observably, Boolean oldValue, Boolean newValue){
				startStopAutoInlasning();
			}
    	});
    	
    	autoinlasning.selectedProperty().addListener(new ChangeListener<Boolean>() {
    	    @Override
    	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//    	    	autoinlasning.setSelected(!newValue);
    	    	System.out.println("testar lite tv p�");
    	    }
    	});
		
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
     * Called when the user clicks cancel.
     */
    final FileChooser fileChooser = new FileChooser();
    @FXML
    private void handleReadFile() {
    	System.out.println("hej");
    	java.util.List<File> list = fileChooser.showOpenMultipleDialog(dialogStage); //
            if (list != null) {
                for (File file : list) {
                    l�sPDFFil(file);
                    System.out.println(file);
                }
            }
    	
    	
    	
//    	 DirectoryChooser chooser = new DirectoryChooser();
//    	    chooser.setInitialDirectory(Datastore.getInstance().getDataDir().toFile());
//    	    File dir = chooser.showDialog(root.getScene().getWindow());
//    	    if (dir == null) {
//    	        return;
//    	    }
//    	    Datastore.getInstance().setDataDir(Paths.get(dir.getAbsolutePath()));
//        dialogStage.close();
    }
    
    private void l�sPDFFil(File file) {
    	T�vling t = null;
    	try {
			t = new ParseToObjekt().ParseToT�vling(PDFToText.getTextFromPDF(file.getAbsolutePath()));
			if(t!=null){
				if(lasinErsatt.isSelected())
					MainUi.addLoppErs�tt(t);
				else
					MainUi.addLoppHoppa�ver(t);
			}else
				System.err.println("den �r null filen = " + file);
    	} catch (WrongFormatPDFParseException e) {
    		 Alert alert = new Alert(AlertType.WARNING);
    	        alert.setTitle("Fel");
    	        alert.setHeaderText("Filen gick ej att l�sa felet var f�ljande:");
    	        alert.setContentText(e.getMessage());

    	        alert.showAndWait();
//			System.out.println("Det var fel i PDF:en som inte s�g ut som standarden b�r, s� filen gick inte att l�sa tyv�r :/");
////			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParseException e) {
			if(e.getMessage().equalsIgnoreCase("PDF header not found.")){
				Alert alert = new Alert(AlertType.WARNING);
    	        alert.setTitle("Fel");
    	        alert.setHeaderText("Filen gick ej att l�sa felet var f�ljande:");
    	        alert.setContentText("Icke en PDF (" + e.getMessage()+")");

    	        alert.showAndWait();
			}
		}
		
    	
	}
    private File mapp;
    @FXML
	private void handelSelectMapp() {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("V�lj mapp");//resourceBundle.getString("ResizeSelectOutputFolder"));
        File outputFolder = directoryChooser.showDialog(dialogStage);//outputSelectBrowseBtn.getScene().getWindow());

        if (outputFolder != null) {
//            outputFolderTextField.setText(outputFolder.getPath());
//            outputFolderSelected = true;
//            outputFolderPath = outputFolder.getPath();
//            updateStartBtnState();
        	System.out.println("vald mapp �r " + outputFolder.getPath());
        	sokVagenLable.setText(outputFolder.getPath());
        	mapp = outputFolder;
        	h�llaKoll = new H�llaKollP�Mapp(mapp, mainUi, lasinErsatt.isSelected(), this);
			try {
				Thread tr�d = new Thread(h�llaKoll);
				tr�d.setDaemon(true);
				tr�d.start();
//				h�llaKoll.run();
			}catch(ParseException e){
				System.out.println("f�ngade parseExaption");
				System.out.println("p� offsetten" + e.getPosition());
        	}catch (Exception e) {
				System.err.println("hejjjjjjjjjjjjjjjjjjjjj");
				e.printStackTrace();
			}
        	
			try {
				if(!autoinlasning.isSelected()){
//					h�llaKoll.wait();
					h�llaKoll.pausa(true);
					pausad = true;
				}else{
					h�llaKoll.pausa(false);
					pausad = false;
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.err.println("f�ngar �ven detta!");
			}
        }
    }

	public void addinl�stFil(String name) {
		inlastaFiler.appendText(name+"\n");
		System.out.println("inl�st fil: " + name);
		
	}
	
	ArrayList<String> felaktigaFiler = new ArrayList<>();
	public void addFelaktikFil(String filNamn){
		felaktigaFiler.add(filNamn);
		System.out.println("fil som inte gick att bearbeta: " + filNamn);
	}

	private boolean pausad = true;
	private H�llaKollP�Mapp h�llaKoll = null;
	
//	@FXML
	private void startStopAutoInlasning(){
		if(h�llaKoll != null){
			System.out.println("�r den pausad = " + pausad);
			if(pausad){
	//			h�llaKoll.notify();
				h�llaKoll.pausa(false);
				pausad = false;
			}else{
	//			try {
	//				h�llaKoll.wait();
					h�llaKoll.pausa(true);
					pausad = true;
	//			} catch (InterruptedException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
		
			}
		}
	}
	/*
	 * 
	@FXML
    private TextArea inlastaFiler;
    @FXML
    private CheckBox Autoinl�sning;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private RadioButton lasinErsatt;
    @FXML
    private RadioButton lasInHoppaOver;
    @FXML
    private RadioButton franMappErs�tt;
    @FXML
    private RadioButton franMappHoppaOver;
    @FXML
    private Label sokVagenLable;
    @FXML
    private CheckBox autoinlasning;
    private Stage dialogStage;
    
    private MainUi mainUi;
	 */
	
	public H�llaKollSave save(){
		H�llaKollSave sparade = new H�llaKollSave();
		sparade.addInl�staFilerTextArea( inlastaFiler.getText());
		
		
		return sparade;
	}
	
	public void load(H�llaKollSave save){
		inlastaFiler.setText(save.getInl�staFilerTextArea());
	}

	

}