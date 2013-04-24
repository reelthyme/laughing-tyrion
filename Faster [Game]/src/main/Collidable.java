package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;

public class Collidable extends Movable {
	
	
	final String boxType; // type of shape the object uses for collision
							// detection

	// uncomment these as we implment them. These only apply to narrow-phase collision detection.
	// double hp;
	// boolean isInvincible; //true for non-destructible tiles, and false for
	// basically everything else.
	// boolean isFixed; //false for players and practically all bullets
	// (excepting lasers), true for tiles and fixed terrain.
	// boolean isKnockbackable=true; //true for players, false for most bullets
	// double mass; //for the lovely physics engine in determining knockback
	// only required if unit isKnockbackable. If two unKnockbackable objects
	// collide, they freeze (maybe marginal forward movement)
	// and keep colliding every frame, until they one or both of them dissipates
	// (think 2 heavy energy waves)and terrain.
	// boolean collidesWithNormalCollidables = true;
	boolean isColliding=false; //is true if the object's rect. bounding box is colliding with another
		//a debug variable. I will do things like draw all colliding boxes red, for example
	Polygon tri;
	Rectangle rect;
	Circle cir;
	Shape hitBox;

	public Collidable(Circle c) { //all the constructors are meant to make new copies of the given shape
		cir = new Circle(c.getCenterX(), c.getCenterY(), c.radius);
		rect = new Rectangle(
				c.getCenterX()-c.radius,
				c.getCenterY()-c.radius,
				c.radius,
				c.radius);
		boxType = "circle";
		setX(c.getCenterX());
		setY(c.getCenterY());

	}

	public Collidable(Rectangle r) {
		rect = new Rectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
		boxType = "rect";
		setX(r.getCenterX());
		setY(r.getCenterY());

	}

	public Collidable(Polygon t) {
		assert (t.getPointCount() == 3);
		tri = new Polygon(t.getPoints().clone());
		boxType = "tri";
	}

	class RedFill implements ShapeFill {

		@Override
		public Color colorAt(Shape shape, float x, float y) {
			// TODO Auto-generated method stub
			return Color.red;
		}

		@Override
		public Vector2f getOffsetAt(Shape shape, float x, float y) {
			return new Vector2f(0,0);
		}

	};
	class WhiteFill extends RedFill {

		@Override
		public Color colorAt(Shape shape, float x, float y) {
			// TODO Auto-generated method stub
			return Color.white;
		}


	};

	public Shape getCollisionShape() {
		if (boxType == "circle") {
			return cir;
		}
		if (boxType == "rect") {
			return rect;
		}
		if (boxType == "tri") {
			return tri;
		}
		assert (false);
		return null;
	}

	public void render() { // can be overridden by subclasses, such as units
		if (isColliding == true) {

			ShapeRenderer.draw(getCollisionShape(), new RedFill());

		} else {
			ShapeRenderer.draw(getCollisionShape(), new WhiteFill());
		}
	}
	@Override
	public void onEnterFrame(){
		super.onEnterFrame();
		setX(x);
		setY(y);
		
	}
	
	@Override
	public void setX(float x){
		this.x=x;
		this.getCollisionShape().setCenterX(x);
		this.rect.setCenterX(x);
	}
	@Override
	public void setY(float y){
		this.y=y;
		this.getCollisionShape().setCenterY(y);
		this.rect.setCenterY(y);
	}
	


	
}
