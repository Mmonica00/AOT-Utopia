package game.gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.engine.base.Wall;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class WallView extends HBox implements Initializable{

	private static ProgressBar healthBar = new ProgressBar();
	private static ImageView wallIcon;
	private static int currHealth;
	private static int orgHealth;
	private Wall laneWall;
	private HBox wallBox = new HBox();
	
	public WallView(Wall laneWall) {
		this.laneWall = laneWall;
		this.currHealth = laneWall.getCurrentHealth();
		this.orgHealth = laneWall.getBaseHealth();
		
		healthBar.setMaxHeight(25);
		healthBar.setMinWidth(150);
		healthBar.setRotate(-90.0);
		healthBar.setStyle("-fx-accent: green;");
		double progress = (double) currHealth/orgHealth;
		healthBar.setProgress(progress);
		healthBar.setTranslateY(65);
		
		Image wallImg = new Image(getClass().getResourceAsStream("wall.png"));
        wallIcon = new ImageView(wallImg);
        wallIcon.setFitHeight(150);
        wallIcon.setFitWidth(50);
        
        
        wallBox.setMaxHeight(100);
        wallBox.setMaxWidth(75);
        wallBox.getChildren().addAll(healthBar,wallIcon);
		wallBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
		
		
		
	}
	
	public static void updateWall(Wall newWall) {
		currHealth=newWall.getCurrentHealth();
		double progress = (double) currHealth/orgHealth;
		healthBar.setProgress(progress);
		if(newWall.isDefeated())
			wallIcon.setOpacity(0.0);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//initialize location with starting x position from anchorpane
		
	}
	
	
	public ProgressBar getHealthBar() {
		return healthBar;
	}
	public void setHealthBar(ProgressBar healthBar) {
		this.healthBar = healthBar;
	}
	
	public ImageView getWallIcon() {
		return wallIcon;
	}

	public void setWallIcon(ImageView wallIcon) {
		this.wallIcon = wallIcon;
	}

	public int getCurrHealth() {
		return currHealth;
	}
	public void setCurrHealth(int currHealth) {
		this.currHealth = currHealth;
	}
	public int getOrgHealth() {
		return orgHealth;
	}
	public void setOrgHealth(int orgHealth) {
		this.orgHealth = orgHealth;
	}
	public Wall getLaneWall() {
		return laneWall;
	}
	public void setLaneWall(Wall laneWall) {
		this.laneWall = laneWall;
	}

	public HBox getWallBox() {
		return wallBox;
	}

	public void setWallBox(HBox wallBox) {
		this.wallBox = wallBox;
	}
	
	
	
}
