package topology;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;



public class Matrix extends BoundingBox{
		/**
		val is an array of size this.width x this.height
		containing the color of the pixels.
		val[i][j] is the color of the pixel (i,j)
	*/
	public Color[][] val;
	/**
		Save the image encoded by the array val in a file at the format .bmp 3x(8bits) BGR
	*/
	public void save(String fileName)throws IOException 
	{
		BufferedImage image =new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		int k=0;
		for(int j=0;j<height;j++)
			for(int i=0;i<width;i++)
				for(int c=0;c<3;c++)
					pixels[k++]=val[i][j].val[c];
		File outputfile = new File(fileName);
		ImageIO.write(image,"BMP",outputfile);
	}
	/**
		Construct a Matrix
			BoundingBox is of the same size than the image defined by the fileName. Its upper left corner is chosen
			to be (0,0).
	
			each color val[i][j] is setted to the color of the pixel of the imaged defined by fileName
			@param FileName = file name of an image in the format .bmp, 2x(8bits) BGR
	*/
	public Matrix(String fileName) throws IOException 
	{
		super(fileName);
		BufferedImage image = ImageIO.read(new File(fileName));
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		val=new Color[width][height];
		int k=0;
		for(int j=0;j<height;j++)
			for(int i=0;i<width;i++){
				byte r=pixels[k++];
				byte g=pixels[k++];
				byte b=pixels[k++];
				val[i][j]=new Color(r,g,b);
				//val[i][j]=new Color(pixels[k++],pixels[k++],pixels[k++]);
			}
				/*
				if (color.isequalto(new Color(pixels[k++],pixels[k++],pixels[k++])))
				{
					val[i][j]=1;
				} else val[i][j]=0;
				*/
	}
	
	/*
	 * Apply a Mask to the image Set every pixel such that Mask.val[i][j]=True to black color
	 * Mask - = mask to apply
	 */
	public void applyMask(Mask mask){
		Color black = new Color(0,0,0);
		for (int i = 0; i < val.length; i++) {
		    for (int j = 0; j < val[i].length; j++) {
		    	if(mask.val[i][j]==true)
		    		this.val[i][j].set(black);
		    }
		}
	}
}

