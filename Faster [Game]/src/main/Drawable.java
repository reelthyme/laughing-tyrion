package main;

import org.newdawn.slick.Image;


public class Drawable {
	boolean visible = true;
	boolean isFlippedHor = false;
	boolean isFlash = false;

	float x;
	float y;
	Image image;
	
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isFlippedHor() {
		return isFlippedHor;
	}
	public void setFlippedHor(boolean isFlippedHor) {
		this.isFlippedHor = isFlippedHor;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getXInt() {
		return (int) Math.round(x);
	}
	public int getYInt() {
		return (int) Math.round(y);
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public boolean isFlash() {
		return isFlash;
	}
	public void setFlash(boolean isFlash) {
		this.isFlash = isFlash;
	}
}
