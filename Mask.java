package topology;

import java.io.IOException;

public class Mask extends BoundingBox{
	/*
	 *  True if the pixel (i,j) is masked and False if the pixle (i,j) is to be kept unchanged 
	 */
	public boolean[][] val;	
	/*
	 * matrix - contains the image that defines the mask
	 * color - the color of the pixel belonging to the mask Construct 
	 * a mask of same bounding box than the matrix and set val[i][j] 
	 * to True if pixel (i,j) is equal to color
	 * */
	public Mask(Matrix m ,Color c){
		super(m);
		for(int i=0; i<m.val.length; i++){
			for (int j = 0; j < val[i].length; j++) {
				if(m.val[i][j].equals(c))
					this.val[i][j]=true;
				else
					this.val[i][j]=false;
			}
		}
		
	}
	public Mask(String fileName,Color c) throws IOException{
		super(fileName);
		Matrix m = new Matrix(fileName);
		for(int i=0; i<m.val.length; i++){
			for (int j = 0; j < val[i].length; j++) {
				if(m.val[i][j].equals(c))
					this.val[i][j]=true;
				else
					this.val[i][j]=false;
			}
		}
	}
	/*
	 * True if on the pixel that as point for a corner belongs to the mask (4 pixels at most)
	 */
	public boolean touchedBy(Point p){
			if(p.onCorner()){								//Point onCorner
				if(p.i==0){
					if(val[p.j-1][p.i]==true)
						return true;
					else
						return false;
				}else if(p.j==0){
					if(val[p.j][p.i-1]==true)
						return true;
					else 
						return false;
				}else{
					if(val[p.j-1][p.i-1]==true)
						return true;
					else 
						return false;
				}
			}else if(p.onBorder()){							//Point onBorder
				if(p.i==p.bb.bb[0]||p.i==p.bb.bb[2]){
					if(p.i==0){
						if(val[p.j-1][p.i]==true||val[p.j][p.i]==true)
							return true;
						else
							return false;
					}else{
						if(val[p.j-1][p.i-1]==true||val[p.j][p.i-1]==true)
							return true;
						else
							return false;
					}
				}else if(p.i==p.bb.bb[1]||p.i==p.bb.bb[3]){
					if(p.j==0){
						if(val[p.j][p.i-1]==true||val[p.j][p.i]==true)
							return true;
						else
							return false;
					}else{
						if(val[p.j-1][p.i-1]==true||val[p.j-1][p.i]==true)
							return true;
						else
							return false;
					}	
				}
			}else{										//Point in table
				if(val[p.j-1][p.i]==true||val[p.j][p.i]==true||val[p.j-1][p.i-1]==true||val[p.j][p.i-1]==true)
					return true;
			}	
			return false;		
	}
	
}
