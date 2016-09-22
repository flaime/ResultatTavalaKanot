package uiProgram;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import paresePdf.Bana;
import paresePdf.Lopp;
import paresePdf.PDFToText;
import paresePdf.ParseToObjekt;
import paresePdf.T�vling;
import paresePdf.WrongFormatPDFParseException;

public class MainUi extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		this.primaryStage.setTitle("AddressApp");

		initRootLayout();

		showPersonOverview();
	}

	// loader.setLocation(MainUi.class.getResource("RotUi.fxml"));

	// /**
	// * Initializes the root layout.
	// */
	// public void initRootLayout() {
	// try {
	//
	// // Load root layout from fxml file.
	// FXMLLoader loader = new FXMLLoader();
	// loader.setLocation(MainUi.class.getResource("RotUi.fxml"));
	// rootLayout = (BorderPane) loader.load();
	//
	// // Show the scene containing the root layout.
	// Scene scene = new Scene(rootLayout);
	// primaryStage.setScene(scene);
	// primaryStage.show();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Initializes the root layout and tries to load the last opened person
	 * file.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainUi.class.getResource("RotUi.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			System.out.println(controller + "---------------------------------------------------------");
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// // Try to load last opened person file.
		// File file = getPersonFilePath();
		// if (file != null) {
		// loadPersonDataFromFile(file);
		// }
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showPersonOverview() {
		System.err.println("----------------");
		try {
			// Load person overview.

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainUi.class.getResource("LoppVisaren.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			LoppVisarenKontroller controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	private static T�vling t = new T�vling("tomt");

	public static void main(String[] args) {
		ParseToObjekt p = new ParseToObjekt();

		// try {
		// t =
		// p.ParseToT�vling(PDFToText.getTextFromPDF("C:\\ResultatLista_KanotSM_M�ndag_FM_F�rs�k_500m.pdf"));//StartLista_SprintSM_2015_M�ndag.pdf"));//startlista_20160719_tis_v2.pdf"));//ResultatLista_KanotSM_M�ndag_FM_F�rs�k_500m.pdf"));//("C:\\besattningsregattan-2016.pdf"));
		// } catch (WrongFormatPDFParseException e) {
		// System.out.println("Det var fel i PDF:en som inte s�g ut som
		// standarden b�r, s� filen gick inte att l�sa tyv�r :/");
		// e.printStackTrace();
		// }
		launch(args);
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Lopp lopp) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainUi.class.getResource("LoppEditDialog.fxml"));
			System.out.println("Det kommer inte ett till");
			AnchorPane page = (AnchorPane) loader.load();
			System.out.println("ett till d�?");

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Lopp");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			LoppEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setLopp(lopp);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showLoppInls�ningDialog(Lopp lopp) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainUi.class.getResource("LoadPDFUi.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Lopp inl�sning");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			LoppEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setLopp(lopp);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * �ppnar en dialog med inst�llningar med inl�sning av filer samt f�r att
	 * s�ta p� automatisk inl�sning.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	private Stage dialogStage = new Stage();
	private Stage stage = new Stage();
	private boolean visatInfoTidigare = false;

	public void showInst�llningsDialog() { // var boolean f�rut
		try {
			if (visatInfoTidigare == false) {

				// // Load the fxml file and create a new stage for the popup
				// dialog.
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainUi.class.getResource("LoadPDFUi.fxml"));
				AnchorPane page = (AnchorPane) loader.load();
				//
				Scene scene = new Scene(page);

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadPDFUi.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				stage.setTitle("L�sa in Filer");
				stage.setScene(scene);// new Scene(root1
				stage.show();
				scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

					@Override
					public void handle(WindowEvent event) {
//						System.out.println("nu ska den inte st�ngas tack");
						event.consume();
						stage.hide();
						visatInfoTidigare = true;
					}
				});

				LopadPDFController controller = loader.getController();
				controller.setMainUi(this);

			} else if (visatInfoTidigare == true) {
				stage.show();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean visatPushaF�rut = false;
	private Stage stagePushaFonster = new Stage();
	public void showPushaDialog() { // var boolean f�rut
		try {
			if (visatPushaF�rut == false) {

				// // Load the fxml file and create a new stage for the popup
				// dialog.
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainUi.class.getResource("PushTillSIdaUi.fxml"));
				AnchorPane page = (AnchorPane) loader.load();
				//
				Scene scene = new Scene(page);

//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PushTillSIdaUi.fxml"));
//				Parent root1 = (Parent) fxmlLoader.load();
				stagePushaFonster.setTitle("Pusha data till server");
				stagePushaFonster.setScene(scene);// new Scene(root1
				stagePushaFonster.show();
				scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

					@Override
					public void handle(WindowEvent event) {
//						System.out.println("nu ska den inte st�ngas tack");
						event.consume();
						stagePushaFonster.hide();
						visatPushaF�rut = true;
					}
				});

				PushaController controller = loader.getController();
				controller.setMainApp(this);

			} else if (visatPushaF�rut == true) {
				stagePushaFonster.show();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Lopp> getLoppData() {
		return t.getLopp();
	}

	public boolean removeLopp(Lopp radera) {
		return t.remove(radera);

	}

	public static void addLoppErs�tt(T�vling t2) {
		// System.out.println("�r t null = " + (t == null));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// customer.setF setFirstName(rs.getString("FirstName"));
				// etc
				t.addLoppErs�tt(t2.getLopp());
			}
		});

	}

	public static void addLoppHoppa�ver(T�vling t2) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// customer.setF setFirstName(rs.getString("FirstName"));
				// etc
				t.addLoppHoppa�ver(t2.getLopp());
			}
		});

	}

	public String getT�vlingJson() {
		return t.getJsonString();
	}

	public boolean finnsLoppMedNummer(String loppnummer) {
		for(Lopp lopp: t.getLopp()){
			if(lopp.getloppnummer().trim().equalsIgnoreCase(loppnummer.trim()))
				return true;
		}
		return false;
	}

	public boolean showBanaEditDialog(Bana Bana) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainUi.class.getResource("BanaEditDialog.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit bana");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			BanaEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setBana(Bana, this);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
