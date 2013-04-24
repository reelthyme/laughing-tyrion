package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.Arrays;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;

public class CollisionTest extends BasicGame{
	//this class is meant to test and develop a collision detection system for the game
	
	public CollisionTest() {
		super("CollisionTest [Testing Run time of methods]");
		// TODO Auto-generated constructor stub
	}
	
	Collidable[] rects;
	
	//settings
	static int screenSize=600;
	int numOfRects=1000;
	float maxSize=16;
	boolean useRandomizedSize=false;
	boolean motionOn = false;
	float speed = 1;
	//end of settings
	
	//debug switches
	boolean drawQuadTree = true;
	boolean drawLineTags = false;
	//end of debug switches
	@Override
	public void init(GameContainer container) throws SlickException {
		rects=new Collidable[numOfRects];
		
		Rectangle tempRect;
		
		for (int x=0;x<numOfRects;x++){
			if(useRandomizedSize){
			tempRect=new Rectangle((float)(Math.random()*(screenSize-maxSize)),
					(float)(Math.random()*(screenSize-maxSize)),
					(float)(Math.random()*maxSize),
					(float)(Math.random()*maxSize));
			rects[x]=new Collidable(tempRect);
			
			}else{
				tempRect=new Rectangle((float)(Math.random()*(screenSize-maxSize)),
						(float)(Math.random()*(screenSize-maxSize)),
						(float)(maxSize),
						(float)(maxSize));
				rects[x]=new Collidable(tempRect);
		}
			Collidable c1=rects[x];
			float angle = (float) (Math.random()*2*Math.PI);
			c1.setVx((float)Math.cos(angle)*speed);
			c1.setVy((float)Math.sin(angle)*speed);
		}
			
			
			
		
	}
	
	class RedFill implements ShapeFill {

		@Override
		public Color colorAt(Shape shape, float x, float y) {
			return Color.red;
		}

		@Override
		public Vector2f getOffsetAt(Shape shape, float x, float y) {
			return new Vector2f(0,0);
		}
	}
	class WhiteFill extends RedFill{
		@Override
		public Color colorAt(Shape shape, float x, float y) {
			return Color.white;
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		RedFill redfill = new RedFill();
		WhiteFill whitefill= new WhiteFill();
		Collidable c;
		for (int i=0;i<numOfRects;i++){
			c=rects[i];
			if(c.isColliding)
			{ShapeRenderer.draw(c.rect, redfill);
			}else{ShapeRenderer.draw(c.rect, whitefill);}
			
			
			//g.drawString(String.valueOf(quad.getIndex(c.rect)), c.rect.getCenterX(), c.rect.getCenterY());
		}
		if(drawLineTags==true){
		quad.render(g, "");}
		else if(this.drawQuadTree==true){
			quad.render();}
		
	}
	int TOTALRUNS =100;
	int timesLeft=TOTALRUNS;
	boolean printed =false;
	double totalTime =0;
	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
//		//start of time testing code
//		if(timesLeft>0){
//			long startTime = System.nanoTime();
//			doQuadTreeCollisionStuff(); //put stuff you want to test the execution time of here
//			long endTime = System.nanoTime();
//
//			double duration = endTime - startTime;
//			System.out.println(duration/1000000); //show miliseconds to complete
//			totalTime+=(duration/1000);
//		}else if(timesLeft==0){
//		double avgTime=totalTime/TOTALRUNS;
//		System.out.println("avgtime: " + avgTime + " microseconds");
//		System.out.println(collisionCount);
//		
//		//put anything else here you want to just run 1 time
//		}
//		timesLeft-=1;
//		//end of time testing code, start of regular code
		
		//doCollisionStuff(); 
		doQuadTreeCollisionStuff();
		for(int i=0;i<numOfRects;i++){
			rects[i].onEnterFrame();
			checkForWallCollisions(rects[i]);
		}
	}
	
	private void checkForWallCollisions(Collidable c){
		r1=c.rect;
		if(r1.getMaxX()>screenSize){c.setVx(-1*Math.abs(c.getVx()));}
		if(r1.getMinX()<0){c.setVx(Math.abs(c.getVx()));}
		if(r1.getMaxY()>screenSize){c.setVy(-1*Math.abs(c.getVy()));}
		if(r1.getMinY()<0){c.setVy(Math.abs(c.getVy()));}
	}
	//public void moveSquares()
	
	int collisionCount;
	
	private void doCollisionStuff(){
		 int i,j;
		 Rectangle r1;
		 Rectangle r2;
		for (i=0; i<numOfRects;i++){
			r1=rects[i].rect;
			for(j=0;j<numOfRects;j++){
				if(j>=i){break;}
				r2=rects[j].rect;
				 if (r1.getMinX() < r2.getMaxX() && r1.getMaxX() > r2.getMinX() &&
						 r1.getMinY() < r2.getMaxY() && r1.getMaxY() > r2.getMinY()){
					 rects[j].isColliding=true;
					 rects[j].isColliding=true;
					 collisionCount+=1;
					 
				 }
			}
		}
		
	}

	QuadTree<Collidable> quad = new QuadTree<Collidable>(screenSize/2, screenSize/2, screenSize, screenSize);
	private void doQuadTreeCollisionStuff(){ //runs quadtree broad-phase CD
		
		quad.clear();
		//System.out.println("making a new quad tree");
		//quad=new QuadTree<Collidable>(screenSize/2, screenSize/2, screenSize, screenSize);
		
		for (int i = 0; i < numOfRects; i++) {
		  rects[i].isColliding=false;
		  quad.insert(rects[i]);
		 
		}
		ArrayList<Collidable> bbCollidingRects=new ArrayList<Collidable>(0); 
		bbCollidingRects.ensureCapacity(numOfRects);
		Collidable c1;
		for(int j=0;j<numOfRects;j++){
			c1=rects[j];
			bbCollidingRects=new ArrayList<Collidable>(0);
			quad.retrieve(bbCollidingRects, c1.rect);
			//System.out.println(bbCollidingRects.size());
			for (Collidable c2 : bbCollidingRects){
				if(AABB(c1, c2)){
					
					handleCollisionLogic(c1, c2);
				}
			}
		}
	}
	
	private void handleCollisionLogic(Collidable c1,Collidable c2){
		if(AABB(c1, c2)&& c1!=c2){
		c1.isColliding=true;
		c2.isColliding=true;
		}
	}
	private Rectangle r1;
	private Rectangle r2;
	private boolean AABB(Collidable c1, Collidable c2){
		r1=c1.rect;
		r2=c2.rect;
		 return (r1.getMinX() < r2.getMaxX() && r1.getMaxX() > r2.getMinX() &&
				 r1.getMinY() < r2.getMaxY() && r1.getMaxY() > r2.getMinY());
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new CollisionTest());
			app.setMinimumLogicUpdateInterval(17); // about 60 frames per second
			app.setMaximumLogicUpdateInterval(17);
			app.setDisplayMode(screenSize+100, screenSize+100, false);

			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
