package game.gui;

import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class TitanView extends VBox implements Initializable{
	
	private ProgressBar healthBar = new ProgressBar();
	private ImageView titanIcon;
	private int currHealth;
	private int orgHealth;
	private int location;
	private Titan titan;
	private VBox titanBox = new VBox();
	
	
	
	public TitanView(Titan titan) {
		this.titan = titan;
		this.currHealth = titan.getCurrentHealth();
		this.orgHealth = titan.getBaseHealth();
		titanBox.setMaxWidth(100);
		titanBox.setMaxHeight(150);
		
		Label l1 = new Label("   HP:"+titan.getCurrentHealth());
		l1.setMaxSize(100, 25);
		l1.setStyle("-fx-font-family: 'Times New Roman';" +
                "-fx-font-size: 22;" +
                "-fx-font-weight: bold;");
		
		//set healthBar 
		healthBar.setPrefHeight(25);
		healthBar.setPrefWidth(100);
		healthBar.setStyle("-fx-accent: green;");
		double progress = (double) orgHealth/currHealth;
		healthBar.setProgress(progress);
		
		//Set titan Image
		Image titanImg;
		if(titan instanceof ArmoredTitan) 
			titanImg = new Image("Titan4.gif");
		else if (titan instanceof ColossalTitan) 
			titanImg = new Image("Titan2.gif");
		else if (titan instanceof AbnormalTitan) 
			titanImg = new Image("Titan3.gif");
		else 
			titanImg = new Image("Titan1.gif");
		
		titanIcon = new ImageView(titanImg);
		titanIcon.setFitHeight(100);
		titanIcon.setFitWidth(100);
		

		titanBox.getChildren().addAll(l1,healthBar,titanIcon);
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//initialize location with starting x position from anchorpane
		 
		
	}
	
	public void moveTitan() {
		setTranslateX(getTranslateX() - titan.getSpeed()*10); //factor will be edited upon gameplay
	}
	
	//-------------------------------------------------------------
	// Getters and setters
	public ProgressBar getHealthBar() {
		return healthBar;
	}
	public void setHealthBar(ProgressBar healthBar) {
		this.healthBar = healthBar;
	}
	public ImageView getTitanIcon() {
		return titanIcon;
	}
	public void setTitanIcon(ImageView titanIcon) {
		this.titanIcon = titanIcon;
	}
	
	public int getCurrHealth() {
		return currHealth;
	}

	public void setCurrHealth(int currHealth) {
		this.currHealth = currHealth;
	}

	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public Titan getTitan() {
		return titan;
	}
	public void setTitan(Titan titan) {
		this.titan = titan;
	}
	
	
	
}
