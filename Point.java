package topology;

public class Point{
	public int i,j;		//the coordinates of the point
	BoundingBox bb;		//bb is the bounding box to which the point belongs
	public Point(BoundingBox bb,int i,int j){
		this.bb=bb;
		this.i=i;
		this.j=j;
	}
	/*
	 * Method: isEqualTo
	 * Definition: true iff the point given in the argument and the element share 
	 * the same boundingbox and have the same coordinates
	 */
	public boolean isEqualTo(Point p){
		if(this.i==p.i && this.j==p.j)
			return true;
		return false;
	}
	
	/*
	 * Method: onCorner
	 * Definition: true iff the point is located on a corner of his bounding box
	 */
	public boolean onCorner(){
		if((this.bb.bb[0]==this.i&&this.bb.bb[1]==this.j)||(this.bb.bb[0]==this.i&&this.bb.bb[3]==this.j)||(this.bb.bb[2]==this.i&&this.bb.bb[1]==this.j)||(this.bb.bb[2]==this.i&&this.bb.bb[3]==this.j))
			return true;
		return false;
	}
	
	/*
	 * Method: onBorder
	 * Definition: true iff the point is located on a border of his bounding box
	 */
	public boolean onBorder(){
		if(this.i==this.bb.bb[0]){
			if(this.j>this.bb.bb[1] && this.j<this.bb.bb[3])
				return true;
		}else if(this.i==this.bb.bb[2]){
			if(this.j>this.bb.bb[1] && this.j<this.bb.bb[3])
				return true;
		}else if(this.j==this.bb.bb[1]){
			if(this.i>this.bb.bb[0] && this.j<this.bb.bb[2])
				return true;
		}else if(this.j==this.bb.bb[3]){
			if(this.i>this.bb.bb[0] && this.j<this.bb.bb[2])
				return true;
		}
		return false;
	}
	@Override public String toString(){
		String text;
		String text1="("+this.i+", "+this.j+")"+" appartient à Bounding Box bb["+this.bb.bb[0]+", "+this.bb.bb[1]+", "+this.bb.bb[2]+", "+this.bb.bb[3]+"]";
		String text2="This point is on border: "+String.valueOf(this.onBorder());
		String text3="This point is on corner: "+String.valueOf(this.onCorner());
		text=text1+text2+text3;
		return text;
	}
}
