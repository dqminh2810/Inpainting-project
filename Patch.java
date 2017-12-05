package topology;

public class Patch {
	public BoundingBox boundingBox;
	public Point point;
	
	public Patch(Point point, int halfwidth, BoundingBox window){
		this.point=point;
		boundingBox=new BoundingBox(new int[]{Math.max(0,point.i-halfwidth)-point.i,
			Math.max(0,point.j-halfwidth)-point.j,
			Math.min(window.width,point.i+halfwidth)-point.i,
			Math.min(window.height,point.j+halfwidth)-point.j});
	}
}
