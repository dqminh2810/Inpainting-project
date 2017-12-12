package topology;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoundingBox {
		/** bb=[imin,jmin,imax,jmax] where 
		(imin,jmin) is the upper left corner
		(imax,jmax) is the lower right corner */
	public int[] bb;	
	/** width of the bouding box = imax-imin*/
	public int width;
	/** height of the bouding box = jmax-jmin */
	public int height;
	/** size of the bouding box = width*height */
	public int size;
	/** number of horizontal edges belonging to the bounding box */
	int nbEdgesHorizontal;
	/** number of vertical edges belonging to the bounding box */
	int nbEdgesVertical;
	/** number of edges belonging to the bounding box */
	int nbEdges;
	/** @return 
		A new bounding box, such that all patches 
		with origin point in the new boudning box
		are included in the current bounding box
	*/
	public BoundingBox crop(Patch patch)
	{
		return new BoundingBox(new int[] {bb[0]-patch.boundingBox.bb[0],
			bb[1]-patch.boundingBox.bb[1],
			bb[2]-patch.boundingBox.bb[2],
			bb[3]-patch.boundingBox.bb[3]});
	}
	/**
		Set all the internal instances using bb_
	*/
	public BoundingBox(int[] bb_)
	{
		bb=bb_;
		width=bb[2]-bb[0];
		height=bb[3]-bb[1];
		size=height*width;
		nbEdgesHorizontal=width*(height+1);
		nbEdgesVertical=(width+1)*height;
		nbEdges=nbEdgesHorizontal+nbEdgesVertical;
	}
	/**
		Define a bounding box of the same size than the image
	*/
	public BoundingBox(BufferedImage image )
	{
		this(new int[]{0,0,image.getWidth(),image.getHeight()});
	}
	/**
		Define a bounding box of the same size than the image given by a file
		@param fileName a BMP file (format = RGB 3x8bits)
	*/
	public BoundingBox(String fileName)  throws IOException 
	{
		this(ImageIO.read(new FileInputStream(fileName)));
//		BufferedImage image = null;						//Load image from file
//		 		try {
//		 			image=ImageIO.read(new File(fileName));
//		 			height=image.getHeight();
//		 			width=image.getWidth();
//		 			size=width*height;
//		 			nbEdgesHorizontal=height+1;			
//		 			nbEdgesVertical=width+1;			
//		 			nbEdges=nbEdgesHorizontal+nbEdgesVertical;
//					bb = new int[4];
//		 			bb[0]=image.getMinX();
//		 			bb[1]=image.getMinY();
//		 			bb[2]=width+bb[0];
//		 			bb[3]=height+bb[1];
//		 		} catch (Exception e) {
//		 			e.getMessage();
//		 		}
	}
	/**
		A hard copy of boundingBox
	*/
	public BoundingBox(BoundingBox boundingBox)
	{
		this(boundingBox.bb);
}
}
