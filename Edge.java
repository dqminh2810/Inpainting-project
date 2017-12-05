//package topology;
//
//public class Edge {
//	private static int[][] v={{0,1},{1,0}};
//	private int direction,i,j;
//	public int orientation;
//	public int label;
//	private BoundingBox bb;
//	public Edge(BoundingBox bb,int direction,int i,int j,int orientation){
//		this.bb=bb;
//		this.i=i;
//		this.j=j;
//		this.orientation=orientation;
//	}	
//	public Point[] border(){
//		Point pointDepart = new Point(bb, i, j);
//		int iplus=orientation*v[direction][0];
//		int jplus=orientation*v[direction][1];
//		Point pointFinal = new Point(bb, iplus, jplus);
//		Point[] pointTable = {pointDepart, pointFinal};
//		return pointTable;
//	}
//		
//	@Override
//	public String toString(){
//		String res;
//		res="Coordonnees du point depart: "+this.border()[0].toString()+"\n";
//		res+="Coordonnees du point final: "+this.border()[1].toString()+"\n";
//		return res;
//	}
//	
//}
package topology;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;

public class Edge 			// Edge of a domain
{
	private static int[][] v={{1,0},{0,1}};
	private int direction,i,j;
	public int orientation;
	/** Each non oriented edge is endowed with a unique label */
	public int label;
	private BoundingBox bb;
	public Point[] border()
	{	// vertices of the oriented edge
		Point[] bd=new Point[2];
		bd[0]=new Point(bb,i,j);
		bd[1]=new Point(bb,i+orientation*v[direction][0],j+orientation*v[direction][1]);
		return bd;
	}
	@Override
	public String toString(){
		Point[] vertices=border();
		return vertices[0]+"-"+vertices[1];
	}
	public Edge(BoundingBox _bb,int _direction,int _i,int _j,int _orientation)
	{
		bb=_bb;
		direction=_direction;
		i=_i;
		j=_j;
		orientation=_orientation;
		/*
			We want a unique label between
			
			0 and  bb.nbEdges-1 (independent of the orientation).
			There is several ways to do it.
			
			First, we labeled unoriented 

			horizontal edges from
			0 to bb.nbEdgesHorinzontal-1

			veritcal edges from
			bb.nbEdgesHorizontal to bb.nbEgdes-1

			We considere the center of the edge (is does not depend on the orientation).

			It is

			(i+orientation/2*v[direction],j+orientation/2*v[direcation])

			The minimal value is (1/2,1/2). So we substract it to get

			(i+orientation/2*v[direction][0]-1/2,j+orientation/2*v[direcation][1]-1/2)

			Then for horinzontal edges, the first term (i+orientation/2*v[direction]-1/2) goes
			from 0 to bb.with, thus be get a unique label

			(i+orientation/2*v[direction][0]-1/2)+(j+orientation/2*v[direction][1]-1/2)*bb.width
			=
			(i+(orientation-1)/2*v[direction][0])+(j+(orientation-1)/2*v[direction][1])*bb.width

			A similar formula is obtained for vertical edges  switchinig the role of i and j.
		*/

		if(direction==0)
			{label=	 (i+(orientation-1)/2*v[direction][0])
					+(j+(orientation-1)/2*v[direction][1])*bb.width;}
		else
			{label=bb.nbEdgesHorizontal+
			 		 (i+(orientation-1)/2*v[direction][0])*bb.height
					+(j+(orientation-1)/2*v[direction][1]);}
	}	
	/*
		direction=0 => horizontal edges
		direction=1 => vertical edges	
		
		orientation=+/-1 
			postive orientation correspond to
				left to righBt for horizontal edges
				up to down for vertical edges
			(the converse for negative value)
	*/
	}