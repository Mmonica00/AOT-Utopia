package game.gui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			double screenWidth = 1280; //1280 Org size
		    double screenHeight = 720; //720 Org size
		    primaryStage.getMaxHeight();
		    
			Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
			Scene scene = new Scene(root,screenWidth,screenHeight);
			scene.getStylesheets().add(getClass().getResource("game.gui.css").toExternalForm());
			
			primaryStage.setTitle("Attack On Titan: Utopia");
			Image icon= new Image(getClass().getResourceAsStream("gameIcon1.png"));
			primaryStage.getIcons().add(icon);
			
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				logout(primaryStage);	
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void logout(Stage stage){	
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setResizable(false);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to close the game? ");
		
		if (alert.showAndWait().get() == ButtonType.OK){
			System.out.println("User logged out");
			stage.close();
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
