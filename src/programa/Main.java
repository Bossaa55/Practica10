package programa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
//import javafx.scene.layout.Pane;

public class Main extends Application {
	public void start(Stage teatre) {
		try {		
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("decorat.fxml"));
	        Parent decorat = fxml.load();//Pane decorat=(Pane)loader.load();
	        Scene escenari = new Scene(decorat, 800, 600);
	        //escenari.getStylesheets().add(getClass().getResource("Decorat.css").toExternalForm());
			teatre.setScene(escenari);
			teatre.setTitle("Teatre-Escenari-Decorat");
			//teatre.setMaximized(true);
			teatre.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    
	public static void main(String[] args) {
		launch(args);
	}
}