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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fruit)) return false;

        Fruit fruit = (Fruit) o;

        if (radius != fruit.radius) return false;
        if (locationInScreen != null ? !locationInScreen.equals(fruit.locationInScreen) : fruit.locationInScreen != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationInScreen != null ? locationInScreen.hashCode() : 0;
        result = 31 * result + radius;
        return result;
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
