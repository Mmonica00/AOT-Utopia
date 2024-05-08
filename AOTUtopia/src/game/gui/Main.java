package game.gui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;



public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			double screenWidth = 1180; //1280 Org size
		    double screenHeight = 620; //720 Org size
		    
			Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
			Scene scene = new Scene(root,screenWidth,screenHeight);
			scene.getStylesheets().add(getClass().getResource("game.gui.css").toExternalForm());
			
			primaryStage.setTitle("Attack On Titan: Utopia");
			Image icon= new Image(getClass().getResourceAsStream("gameIcon1.png"));
			primaryStage.getIcons().add(icon);
			
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
