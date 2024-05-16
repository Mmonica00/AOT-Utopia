package game.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class WeaponLaneView implements Initializable {

	private ArrayList<Weapon> weapons;
	
	
	private int height = 60;
	private int width = 60;

	private ImageView weapon1Img;
	private ImageView weapon2Img;
	private ImageView weapon3Img;
	private ImageView weapon4Img;
	
	private int weapon1Count=0;
	private int weapon2Count=0;
	private int weapon3Count=0;
	private int weapon4Count=0;
	
	private GridPane gridPane = new GridPane();
	
	
	public WeaponLaneView(ArrayList<Weapon> weaponsInput) {
		gridPane.setMaxHeight(200);
		gridPane.setMaxWidth(200);
		
		this.weapons = weaponsInput;
		
		setWeaponCounts();
		
		Image w1 = new Image(getClass().getResourceAsStream("Weapon1.gif"));
		this.weapon1Img = new ImageView(w1);
		weapon1Img.setFitHeight(height);
		weapon1Img.setFitWidth(width);
		Image w2 = new Image(getClass().getResourceAsStream("Weapon2.gif"));
		this.weapon2Img = new ImageView(w2);
		weapon2Img.setFitHeight(height);
		weapon2Img.setFitWidth(width);
		Image w3 = new Image(getClass().getResourceAsStream("Weapon3.gif"));
		this.weapon3Img = new ImageView(w3);
		weapon3Img.setFitHeight(height);
		weapon3Img.setFitWidth(width);
		Image w4 = new Image(getClass().getResourceAsStream("Weapon4.gif"));
		this.weapon4Img = new ImageView(w4);
		weapon4Img.setFitHeight(height);
		weapon4Img.setFitWidth(width);
		
		VBox grpWeapon1 = new VBox();
		grpWeapon1.setMaxHeight(height);
		grpWeapon1.setMaxWidth(width);
		Label l1 = new Label(" Count: "+ weapon1Count);
		l1.setMaxSize(100, 25);	
		l1.setStyle("-fx-font-family: 'Times New Roman';" + "-fx-font-weight: bold;");
		grpWeapon1.getChildren().addAll(l1,weapon1Img);
		grpWeapon1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
		grpWeapon1.setOpacity(0.0);
		
		gridPane.add(grpWeapon1, 0, 0);
		
		VBox grpWeapon2 = new VBox();
		grpWeapon2.setMaxHeight(height);
		grpWeapon2.setMaxWidth(width);
		Label l2 = new Label(" Count: "+ weapon2Count);
		l2.setMaxSize(100, 25);	
		l2.setStyle("-fx-font-family: 'Times New Roman';" + "-fx-font-weight: bold;");
		grpWeapon2.getChildren().addAll(l2,weapon2Img);
		grpWeapon2.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
		grpWeapon2.setOpacity(0.0);
		
		gridPane.add(grpWeapon2, 1, 0);
		
		VBox grpWeapon3 = new VBox();
		grpWeapon3.setMaxHeight(height);
		grpWeapon3.setMaxWidth(width);
		Label l3 = new Label(" Count: "+ 10);
		l3.setMaxSize(100, 25);	
		l3.setStyle("-fx-font-family: 'Times New Roman';" + "-fx-font-weight: bold;");
		grpWeapon3.getChildren().addAll(l3,weapon3Img);
		grpWeapon3.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
		grpWeapon3.setOpacity(0.0);
		
		gridPane.add(grpWeapon3, 0, 1);
		
		VBox grpWeapon4 = new VBox();
		grpWeapon4.setMaxHeight(height);
		grpWeapon4.setMaxWidth(width);
		Label l4 = new Label(" Count: "+ weapon4Count);
		l4.setMaxSize(100, 25);	
		l4.setStyle("-fx-font-family: 'Times New Roman';" + "-fx-font-weight: bold;");
		grpWeapon4.getChildren().addAll(l4,weapon4Img);
		grpWeapon4.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
		grpWeapon4.setOpacity(0.0);
		
		gridPane.add(grpWeapon4, 1, 1);
		
		if(weapon1Count>0)
			grpWeapon1.setOpacity(1.0);
		if (weapon2Count>0)
			grpWeapon2.setOpacity(1.0);
		if(weapon3Count>0)
			grpWeapon3.setOpacity(1.0);
		if(weapon4Count>0)
			grpWeapon4.setOpacity(1.0);
		
	}

	public void setWeaponCounts() {
		for(Weapon currWeapon : weapons) {
			if(currWeapon instanceof PiercingCannon)
				weapon1Count++;
			if(currWeapon instanceof SniperCannon)
				weapon2Count++;
			if(currWeapon instanceof VolleySpreadCannon)
				weapon3Count++;
			if(currWeapon instanceof WallTrap)
				weapon4Count++;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	//---------------------------------------------------------------------
	//Getters & Setters
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public GridPane getGridPane() {
		return gridPane;
	}
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}



}
