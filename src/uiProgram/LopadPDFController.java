package uiProgram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.pdfclown.util.parsers.ParseException;

import hjälpprogram.HållaKollPåMapp;
import hjälpprogram.HållaKollSave;
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
import paresePdf.Tävling;
import paresePdf.WrongFormatPDFParseException;

public class LopadPDFController {
	
	@FXML
    private TextArea inlastaFiler;
    @FXML
    private CheckBox Autoinläsning;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private RadioButton lasinErsatt;
    @FXML
    private RadioButton lasInHoppaOver;
    @FXML
    private RadioButton franMappErsätt;
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
    	System.out.println("hej på dig");
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
    	    	System.out.println("testar lite tv på");
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
                    läsPDFFil(file);
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
    
    private void läsPDFFil(File file) {
    	Tävling t = null;
    	try {
			t = new ParseToObjekt().ParseToTävling(PDFToText.getTextFromPDF(file.getAbsolutePath()));
			if(t!=null){
				if(lasinErsatt.isSelected())
					MainUi.addLoppErsätt(t);
				else
					MainUi.addLoppHoppaÖver(t);
			}else
				System.err.println("den är null filen = " + file);
    	} catch (WrongFormatPDFParseException e) {
    		 Alert alert = new Alert(AlertType.WARNING);
    	        alert.setTitle("Fel");
    	        alert.setHeaderText("Filen gick ej att läsa felet var följande:");
    	        alert.setContentText(e.getMessage());

    	        alert.showAndWait();
//			System.out.println("Det var fel i PDF:en som inte såg ut som standarden bör, så filen gick inte att läsa tyvär :/");
////			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParseException e) {
			if(e.getMessage().equalsIgnoreCase("PDF header not found.")){
				Alert alert = new Alert(AlertType.WARNING);
    	        alert.setTitle("Fel");
    	        alert.setHeaderText("Filen gick ej att läsa felet var följande:");
    	        alert.setContentText("Icke en PDF (" + e.getMessage()+")");

    	        alert.showAndWait();
			}
		}
		
    	
	}
    private File mapp;
    @FXML
	private void handelSelectMapp() {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Välj mapp");//resourceBundle.getString("ResizeSelectOutputFolder"));
        File outputFolder = directoryChooser.showDialog(dialogStage);//outputSelectBrowseBtn.getScene().getWindow());

        if (outputFolder != null) {
//            outputFolderTextField.setText(outputFolder.getPath());
//            outputFolderSelected = true;
//            outputFolderPath = outputFolder.getPath();
//            updateStartBtnState();
        	System.out.println("vald mapp är " + outputFolder.getPath());
        	sokVagenLable.setText(outputFolder.getPath());
        	mapp = outputFolder;
        	hållaKoll = new HållaKollPåMapp(mapp, mainUi, lasinErsatt.isSelected(), this);
			try {
				Thread tråd = new Thread(hållaKoll);
				tråd.setDaemon(true);
				tråd.start();
//				hållaKoll.run();
			}catch(ParseException e){
				System.out.println("fångade parseExaption");
				System.out.println("på offsetten" + e.getPosition());
        	}catch (Exception e) {
				System.err.println("hejjjjjjjjjjjjjjjjjjjjj");
				e.printStackTrace();
			}
        	
			try {
				if(!autoinlasning.isSelected()){
//					hållaKoll.wait();
					hållaKoll.pausa(true);
					pausad = true;
				}else{
					hållaKoll.pausa(false);
					pausad = false;
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.err.println("fångar även detta!");
			}
        }
    }

	public void addinlästFil(String name) {
		inlastaFiler.appendText(name+"\n");
		System.out.println("inläst fil: " + name);
		
	}
	
	ArrayList<String> felaktigaFiler = new ArrayList<>();
	public void addFelaktikFil(String filNamn){
		felaktigaFiler.add(filNamn);
		System.out.println("fil som inte gick att bearbeta: " + filNamn);
	}

	private boolean pausad = true;
	private HållaKollPåMapp hållaKoll = null;
	
//	@FXML
	private void startStopAutoInlasning(){
		if(hållaKoll != null){
			System.out.println("är den pausad = " + pausad);
			if(pausad){
	//			hållaKoll.notify();
				hållaKoll.pausa(false);
				pausad = false;
			}else{
	//			try {
	//				hållaKoll.wait();
					hållaKoll.pausa(true);
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
    private CheckBox Autoinläsning;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private RadioButton lasinErsatt;
    @FXML
    private RadioButton lasInHoppaOver;
    @FXML
    private RadioButton franMappErsätt;
    @FXML
    private RadioButton franMappHoppaOver;
    @FXML
    private Label sokVagenLable;
    @FXML
    private CheckBox autoinlasning;
    private Stage dialogStage;
    
    private MainUi mainUi;
	 */
	
	public HållaKollSave save(){
		HållaKollSave sparade = new HållaKollSave();
		sparade.addInlästaFilerTextArea( inlastaFiler.getText());
		
		
		return sparade;
	}
	
	public void load(HållaKollSave save){
		inlastaFiler.setText(save.getInlästaFilerTextArea());
	}

	

}