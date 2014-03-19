package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure;

import android.graphics.Point;

/* 
 * Data structure for fruits
 * Can be modified to implement your own version of the game
 */
public class Fruit {

	private Point locationInScreen; //Where the fruit is located
	private int radius; // How big is the fruit
	
	public Fruit(Point location, int rad){
		locationInScreen = location;
		radius = rad;
	}
	
	public void setLocation(Point p){
		locationInScreen = p;
	}
	
	public Point getLocationInScreen() {
		return locationInScreen;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}
}
