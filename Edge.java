package topology;

public class Edge {
	private static int[][] v={{0,1},{1,0}};
	private int direction,i,j;
	public int orientation;
	public int label;
	private BoundingBox bb;
	public Edge(BoundingBox bb,int direction,int i,int j,int orientation){
		this.bb=bb;
		this.i=i;
		this.j=j;
		this.orientation=orientation;
	}	
	public Point[] border(){
		Point pointDepart = new Point(bb, i, j);
		int iplus=orientation*v[direction][0];
		int jplus=orientation*v[direction][1];
		Point pointFinal = new Point(bb, iplus, jplus);
		Point[] pointTable = {pointDepart, pointFinal};
		return pointTable;
	}
		
	@Override
	public String toString(){
		String res;
		res="Coordonnees du point depart: "+this.border()[0].toString()+"\n";
		res+="Coordonnees du point final: "+this.border()[1].toString()+"\n";
		return res;
	}
	
}
