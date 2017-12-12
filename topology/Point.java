package topology;

public class Point{
		/**
		(i,j) are the coordinates of the point
	*/
	public int i,j;
	/**
		bb is the bounding box to which the point belongs
	*/
	BoundingBox bb;
	/**
		@return true iff the point given in the argument and the element share the same boundingbox and have the same coordinates
		@param point Another point
	*/
	public boolean isEqualTo(Point point){
		return ((i==point.i) && (j==point.j));
	}
	/**
		@return true iff the point is located on a corner of his bounding box
	*/
	public boolean onCorner(){
		return ((i==0)||(i==bb.width)) && ((j==0)||(j==bb.height));
	}
	/**
		@return true iff the point is located on a border of his bounding box
	*/
	public boolean onBorder(){
		return ((i==0)||(j==0)||(i==bb.width)||(j==bb.height));
	}
	/**
		@return the set of Edges that have the point as a starting point
	*/
	public Edge[] outerEdges(){
		int length=4;
		if(onBorder()) length=3;
		if(onCorner()) length=2;
		Edge[] listEdges=new Edge[length];
		int k=0;
		if(i<bb.width)	listEdges[k++]=new Edge(bb,0,i,j,1);
		if(j<bb.height)	listEdges[k++]=new Edge(bb,1,i,j,1);
		if(i>0)			listEdges[k++]=new Edge(bb,0,i,j,-1);
		if(j>0)			listEdges[k++]=new Edge(bb,1,i,j,-1);
		return listEdges;
	}
	/**
		@return the coordinates (i,j) of the point as a string
	*/	
	
	@Override
	public String toString(){
		return "("+i+","+j+")";
	}
	public Point(BoundingBox _bb,int _i,int _j){
		bb=_bb;i=_i;j=_j;
	}
}
