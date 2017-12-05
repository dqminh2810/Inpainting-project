package topology;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Boundary{
	/**  
		 *  List of the edge of the Boundary
		 */  
	public ArrayList<Edge> edges;
	/**
	 * Bounding Box containing the Boundary
	 */
	BoundingBox bb;
	/**
	* @return a String with the list of all edges of the boundary
	*/
	@Override
	public String toString()
	{
		String s="";
		for(Edge edge:edges) s+=edge+";";
		return s;
	}
	/*
	* Compute the list of edges of the boundary (no order)
	*/
	public Boundary(Mask mask)
	{
		bb=mask;
		edges=new ArrayList<Edge>();
		// vertical edges T|F and F|T set to +1 and - 1 respectively
		for(int i=0;i<mask.width-1;i++)
			for(int j=0;j<mask.height;j++)
			{
				if((mask.val[i][j]) && (!mask.val[i+1][j]))
					edges.add(new Edge(mask,1,i+1,j,1));
				if((!mask.val[i][j]) && (mask.val[i+1][j]))
					edges.add(new Edge(mask,1,i+1,j+1,-1));
			}
		/*
		  horizontal edges 
		 	T   F 
		 	-   -   set to -1 and +1 respectively
		 	F   T
		 */
		for(int i=0;i<mask.width;i++)
			for(int j=0;j<mask.height-1;j++)
			{
				if((mask.val[i][j]) && (!mask.val[i][j+1]))
					edges.add(new Edge(mask,0,i+1,j+1,-1));
				if((!mask.val[i][j]) && (mask.val[i][j+1]))
					edges.add(new Edge(mask,0,i,j+1,1));
			}
		}
}