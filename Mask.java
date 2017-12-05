package topology;

import java.io.IOException;

public class Mask extends BoundingBox{
		/**
		val[i][j]=
			True is the pixel (i,j) is masked
			False is the pixle (i,j) is to be kept unchanged
	*/
	public boolean[][] val;
	/**
		@param point a  point of the BoundingBox
		@return True if on the pixel that as point for a corner belongs to the mask (4 pixels at most)
	*/
	public boolean touchedBy(Point point){
		int i=point.i;int j=point.j;
		boolean res=false;
		for(int I=Math.max(i-1,0);I<Math.min(i+1,width);I++)
			for(int J=Math.max(j-1,0);J<Math.min(j+1,height);J++)
				res=res||val[I][J];
		return res;
	}
	/**
		@param matrix contains the image that defines the mask
		@param color the color of the pixel belonging to the mask
		
		Construct a mask of same bounding box than the matrix and
		set val[i][j] to True if pixel (i,j) is equal to color
	*/
	public Mask(Matrix matrix,Color color) 
	{
		super(matrix);
		val=new boolean[width][height];
		int k=0;
		for(int j=0;j<height;j++)
			for(int i=0;i<width;i++)
				val[i][j]=matrix.val[i][j].isequalto(color);
	}
	/**
		@param fileName File Name of a bmp image 3x8bits that defines the mask
		@param color the color of the pixels that defines the mask
	*/
	public Mask(String fileName,Color color) throws IOException 
	{
		this(new Matrix(fileName),color);
	}
}
