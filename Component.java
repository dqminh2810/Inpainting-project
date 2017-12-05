package topology;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;

/**
	List of successive vertices of a connected component of the boundary of the mask.
	The edges are ordrered clockwise.
*/
public class Component {
	/**
		Array of the vertices of the connected component ordered clockwise
	*/
	public ArrayList<Point> points;
	/**
		Construct the connected component strating from the seedPoint,
		of the tzgged edges
	*/
	public Component(Tag tag,Point seedPoint) {
		points=new ArrayList<Point>();
		Point currentPoint=seedPoint;
		int k=tag.indexActiveOuterEdge(currentPoint);
		while(k!=-1) {
			points.add(tag.boundary.edges.get(k).border()[0]);
			tag.active[k]=false;
			tag.nbActive-=1;
			currentPoint=tag.boundary.edges.get(k).border()[1];
			k=tag.indexActiveOuterEdge(currentPoint);}
		if(!(seedPoint.isEqualTo(currentPoint))) points.add(currentPoint);}
	/**
		Display the sequence of vertices of the connected component
	*/
	@Override
	public String toString() {
		String s="(";
		if(points.size()>0){
			s+=points.get(0);
			for(int k=1;k<points.size();k++) s+=","+points.get(k);}
		s+=")";
		return s;}}