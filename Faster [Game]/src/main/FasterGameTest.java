package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Animation;
//import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;


public class FasterGameTest extends BasicGame {
	public FasterGameTest(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init(GameContainer arg0) throws SlickException {
		yukari = new Image("img/yakumo_yukari.png");
		
		String tenshiFolder = "img/tenshi/";
		Image[] tenshi_run_frames = {
		new Image(tenshiFolder +"Tenshi_Runf0.png"),
		new Image(tenshiFolder + "Tenshi_Runf1.png"),
		new Image(tenshiFolder + "Tenshi_Runf2.png"),
		new Image(tenshiFolder + "Tenshi_Runf3.png"),
		};
		
		Image[] tenshi_stance_frames = {
				new Image(tenshiFolder + "Tenshi_Stancef0.png"),
				new Image(tenshiFolder + "Tenshi_Stancef1.png"),
				new Image(tenshiFolder + "Tenshi_Stancef2.png"),
				new Image(tenshiFolder + "Tenshi_Stancef3.png"),
				new Image(tenshiFolder + "Tenshi_Stancef4.png"),
				new Image(tenshiFolder + "Tenshi_Stancef5.png"),
				new Image(tenshiFolder + "Tenshi_Stancef6.png"),
				new Image(tenshiFolder + "Tenshi_Stancef7.png"),
				};
		
		tenshi_run = new Animation(tenshi_run_frames, 100, true);
		tenshi_stance = new Animation(tenshi_stance_frames, 120, true);
	}
	
	
	double tenshiX=100;
	Image yukari;
	Animation tenshi_stance;
	Animation tenshi_run;
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		yukari.draw(Math.round(elapsedTime*0.01), 0);
		
		tenshi_run.draw(-1000,-1000);
		tenshi_stance.draw(-1000,-1000); //draws the animation off-screen, due to a library shortcoming this is needed to update the currentFrame
										//I can fix this if I can modify the source and recompile it.
		tenshi_stance.getCurrentFrame().draw(50, 50);
		 
		if(isKeyDown[Input.KEY_LEFT] || isKeyDown[Input.KEY_RIGHT])
		{
		tenshi_run.getCurrentFrame().getFlippedCopy(!facingRight, false).draw(Math.round(tenshiX), 300);
		}
		else{tenshi_stance.draw(Math.round(tenshiX), 300);}
		//tenshi_run.draw();
		
/*		tenshi_run.draw(Math.round(elapsedTime*0.15), 100);
		tenshi_stance.draw(200,200);
		yukari.drawFlash(200, 300);
		tenshi_stance.getCurrentFrame().drawFlash(300,300);
		tenshi_run.getCurrentFrame().getFlippedCopy(true, false).draw(Math.round(500-elapsedTime*0.15), 100);*/
		
	}


	private static final int DELAY = 1000; // 1 second
	private int elapsedTime; // The time that has passed, reset to 0 after +-1 sec.
	@Override
	public void update(GameContainer arg0, int delta) throws SlickException {
		
		
		  elapsedTime += delta;
		  if(facingRight)
		  {tenshiX+=2;} 
		  else{tenshiX-=2;}
		 /* if (elapsedTime >= DELAY) {
		    elapsedTime = 0;
		    trigger();  // The trigger method would be called every second
	
	}*/	
	}
	private static void trigger(){
		System.out.println("FasterGame: triggered");
	}
	
	
	public static void main(String[] args)
	{
        try
        {
            AppGameContainer app = new AppGameContainer(new FasterGameTest("Faster [Game]"));
            app.setMinimumLogicUpdateInterval(17); //about 60 frames per second
            app.setMaximumLogicUpdateInterval(17);
            app.setDisplayMode(500, 400, false);	
            
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
	}

	boolean facingRight = true;
	
	boolean[] isKeyDown= new boolean[500]; //I assume there are less than 500 keys
	public void keyPressed(int key, char c) {
		String message = "You pressed key code "+key+" (character = "+c+")";
		System.out.println(message);
		if(key==Input.KEY_LEFT)
		{facingRight=false;}
		if(key==Input.KEY_RIGHT)
		{facingRight=true;}
		isKeyDown[key]=true;
		
	}
	public void keyReleased(int key, char c){
		isKeyDown[key]=false;
	}

}
