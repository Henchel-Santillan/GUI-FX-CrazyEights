package gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.PerspectiveCamera;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import utility_deprecated.Rank;
import utility_deprecated.Suit;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardModelOld {
	
	public static final double MODEL_HEIGHT = 120.0d;
	public static final double MODEL_WIDTH = 80.0d;
	public static final double ARC_LENGTH = 15.0d;

	private final Rectangle casing;
	private final ImageView print;
	private final Group model;
	
	private final Rank card_rank;
	private final Suit card_suit;
	
	public boolean isClicked;
	private BooleanProperty showFront;
	
	public CardModelOld(Rank card_rank, Suit card_suit) {
		
		this.card_rank = card_rank;
		this.card_suit = card_suit;
		
		casing = new Rectangle(MODEL_WIDTH, MODEL_HEIGHT);
		
		casing.setFill(Color.WHITE);
		casing.setStroke(Color.BLACK);
		casing.setStrokeWidth(2.5d);
		casing.setArcWidth(ARC_LENGTH);
		casing.setArcHeight(ARC_LENGTH);
		
		print = new ImageView();
	
		print.setFitHeight(MODEL_HEIGHT);
		print.setFitWidth(MODEL_WIDTH);
		
		model = new Group();
		model.getChildren().addAll(casing, print);
		
		isClicked = false;
		showFront = new SimpleBooleanProperty(false);
	}
	
	public Group get() {
		return model;
	}
	
	public void set(double x, double y) {
		model.setTranslateX(x);
		model.setTranslateY(y);
	}
	
	public ImageView getImageView() {
		return print;
	}
	
	public Rectangle getCasing() {
		return casing;
	}
	
	public boolean isFrontShowing() {
		return showFront.get();
	}
	
	public void setFaceUp() {
		try {
			String path = this.card_rank.getID() + this.card_suit.getID() + ".png";
			
			print.setImage(new Image(
					getClass().getResource("/IMAGE/".concat(path)).toURI().toString()));
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CardModelOld.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModelOld.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
	
	public void setFaceDown() {
		try {
			print.setImage(new Image(
					getClass().getResource("/IMAGE/card_back.png").toURI().toString()));
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CardModelOld.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModelOld.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
	
	public void flip() {
		Image current_image = print.getImage();
		
		
	}
	
	public void onEnter() {
		casing.setStroke(Color.LIGHTSKYBLUE);
	}
	
	public void onExit() {
		casing.setStroke(Color.BLACK);
	}
	
	public void onClick() {
		if (!isClicked) {
			casing.setStroke(Color.RED);
			isClicked = true;
		} else {
			casing.setStroke(Color.BLACK);
			isClicked = false;
		}
	}
	
}
