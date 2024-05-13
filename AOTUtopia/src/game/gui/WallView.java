package game.gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.engine.base.Wall;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class WallView extends HBox implements Initializable{

	private ProgressBar healthBar = new ProgressBar();
	private ImageView WallIcon;
	private int currHealth;
	private int orgHealth;
	private Wall laneWall;
	private HBox WallBox = new HBox();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public WallView(Wall laneWall) {
		this.laneWall = laneWall;
		this.currHealth = laneWall.getCurrentHealth();
		this.orgHealth = laneWall.getBaseHealth();
		
		
		
		healthBar.setPrefHeight(100);
		healthBar.setPrefWidth(25);
		healthBar.setStyle("-fx-accent: green;");
		double progress = (double) orgHealth/currHealth;
		healthBar.setProgress(progress);
	}
	
	public ProgressBar getHealthBar() {
		return healthBar;
	}
	public void setHealthBar(ProgressBar healthBar) {
		this.healthBar = healthBar;
	}
	public ImageView getWallIcon() {
		return WallIcon;
	}
	public void setWallIcon(ImageView wallIcon) {
		WallIcon = wallIcon;
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
	
	
}
