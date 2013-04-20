package main;

import java.util.ArrayList;

public class GameGraphics { //contains settings for the drawing of the main menu or Level
						//also actually draws the main menu or Level
	
boolean visibleAll = false; //options for controlling whether to show or hide all items (overriding their indiviual settings)
boolean hideAll = false;
boolean drawFlashAll=false;

ArrayList<Drawable> Drawables;

public void run() {
	if(hideAll)
	{return;}
	for(Drawable d: Drawables)
	{
		if(d.visible){ //code for looking up the properties and actually drawing
			if(drawFlashAll || d.isFlash())
			{
				d.getImage().drawFlash(d.getXInt(), d.getYInt());
				break; //done drawing this object
			}
			d.getImage().draw(d.getXInt(), d.getYInt());
			break;
		}
	}
}




}
