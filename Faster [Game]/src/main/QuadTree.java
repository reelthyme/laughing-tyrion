package main;

public class QuadTree<T extends Collidable> extends QuadTreeNode<Collidable> {
	   
	public QuadTree(float cx,float cy, float width, float height){
		super(cx, cy, width, height, null);
		
	}
	
	//pseudo code: Try to fit piece into node X
	//if there are more than 1 pieces in nodeX, and the depth of the node<MAXDEPTH, subdivide it.
	//try and place both of the objects in a sub-node (you only have to try once,try the sub-node that 
		//contains the center of the rectangle.
	//you can do a naive insert as well, testing all 4 points

}
