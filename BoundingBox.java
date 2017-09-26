package topology;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoundingBox {
	public int[] bb;	//bb=[imin,jmin,imax,jmax] where (imin,jmin) is the upper left corner (imax,jmax) is the lower right corner
	public int width,height,size;
	public int nbEdges,nbEdgesHorizontal,nbEdgesVertical;
	
	/*Constructor: Set all the internal instances using bb*/
	public BoundingBox(int[] bb){		
		bb = new int[4];
		this.bb=bb;
		width=bb[2]-bb[0];
		height=bb[3]-bb[1];
		size=width*height;
		nbEdgesHorizontal=height+1;			//(bb[3]-bb[1]+1)
		nbEdgesVertical=width+1;			//(bb[2]-bb[0]+1)
		nbEdges=nbEdgesHorizontal+nbEdgesVertical;
	}
	
	/*Constructor: Define a bounding box of the same size than the image*/
	public BoundingBox(BufferedImage image){
		height=image.getHeight();
		width=image.getWidth();
		size=width*height;
		nbEdgesHorizontal=height+1;			
		nbEdgesVertical=width+1;			
		nbEdges=nbEdgesHorizontal+nbEdgesVertical;
		bb = new int[4];
		bb[0]=image.getMinX();
		bb[1]=image.getMinY();
		bb[2]=width+bb[0];
		bb[3]=height+bb[1];
	}
	
	/*Constructor: Define a bounding box of the same size than the image given by a file*/
	public BoundingBox(String file)  throws IOException{
		BufferedImage image = null;						//Load image from file
		try {
			image=ImageIO.read(new File(file));
			height=image.getHeight();
			width=image.getWidth();
			size=width*height;
			nbEdgesHorizontal=height+1;			
			nbEdgesVertical=width+1;			
			nbEdges=nbEdgesHorizontal+nbEdgesVertical;
			bb = new int[4];
			bb[0]=image.getMinX();
			bb[1]=image.getMinY();
			bb[2]=width+bb[0];
			bb[3]=height+bb[1];
		} catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	/*Constructor: A hard copy of boundingBox*/
	public BoundingBox(BoundingBox b){
		bb = new int[4];
		this.bb=b.bb;
		this.width=b.width;
		this.height=b.height;
		this.size=b.size;
		this.nbEdges=b.nbEdges;
		this.nbEdgesHorizontal=b.nbEdgesHorizontal;
		this.nbEdgesVertical=b.nbEdgesVertical;
	}
}
