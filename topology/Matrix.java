package topology;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;



public class Matrix extends BoundingBox{
		/**
		val is an array of size this.width x this.height
		containing the color of the pixels.
		val[i][j] is the color of the pixel (i,j)
	*/
	public Color[][] val;
	public String fileName;
	/**
		Save the image encoded by the array val in a file at the format .bmp 3x(8bits) BGR
	*/
	public void save(String fileNameSave)throws IOException {
//		// Initialize BufferedImage, assuming Color[][] is already properly populated.
//		 		BufferedImage imageSaved = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
//		 		BufferedImage myImage = ImageIO.read(new File(fileName));
//		 		
//		 		// Set each pixel of the BufferedImage to the color RGB from the val[][].
//		 		for (int i = 0; i < val.length; i++) {
//		 		    for (int j = 0; j < val[i].length; j++) {
//		 		    	int clr=myImage.getRGB(i, j); 	//get color RGB at pixel val[i][j]
//		 				int red=(clr & 0x00ff0000) >> 16;		//get red int
//		 			 	int green = (clr & 0x0000ff00) >> 8;	//get green int
//		 			 	int blue  =  clr & 0x000000ff;			//get blue int
//		 		    	int bgr = (blue << 16 | green << 8 | red);	//get combine blue,green,red int
//		 		    	imageSaved.setRGB(i,j,bgr);			//set couleur rgb for bufferedimage at pixel [i][j] 
//		 		    }
//		 		}
//		 		//Save image with format bmp -- format color BRGB
//		 		ImageIO.write(imageSaved, "BMP", new File(fileNameSave+".bmp"));
		BufferedImage image =new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		int k=0;
		for(int j=0;j<height;j++)
			for(int i=0;i<width;i++)
				for(int c=0;c<3;c++)
					pixels[k++]=val[i][j].val[c];
		ImageIO.write(image, "BMP", new File(fileNameSave+".bmp"));
	}
	/**
		Construct a Matrix
			BoundingBox is of the same size than the image defined by the fileName. Its upper left corner is chosen
			to be (0,0).
	
			each color val[i][j] is setted to the color of the pixel of the imaged defined by fileName
			@param FileName = file name of an image in the format .bmp, 2x(8bits) BGR
	 * @throws URISyntaxException 
	*/
	public Matrix(String fileName) throws IOException, URISyntaxException 
	{
//		super(fileName);
//		this.fileName=fileName;
////		BufferedImage image = ImageIO.read(new File(getClass().getResource(fileName).toURI()));
//		BufferedImage image=ImageIO.read(new File(fileName));
//		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//		val=new Color[width][height];
//		int k=0;
//		for(int j=0;j<height;j++)
//			for(int i=0;i<width;i++){
//				byte r=pixels[k++];
//				byte g=pixels[k++];
//				byte b=pixels[k++];
//				val[i][j]=new Color(r,g,b);
//				//val[i][j]=new Color(pixels[k++],pixels[k++],pixels[k++]);
//			}
//				/*
//				if (color.isequalto(new Color(pixels[k++],pixels[k++],pixels[k++])))
//				{
//					val[i][j]=1;
//				} else val[i][j]=0;
//				*/
		super(fileName);
		 		this.fileName=fileName;
		  		BufferedImage image = null;						//Load image from file
		 		if(fileName.substring(fileName.length()-3).equals("bmp")==false) throw new IOException("Error format");
		 		else try {
		 				image=ImageIO.read(new File(fileName));
		 				height=image.getHeight();
		  				width=image.getWidth();
		 				size=width*height;
		 				nbEdgesHorizontal=height+1;			
		  				nbEdgesVertical=width+1;			
		 				nbEdges=nbEdgesHorizontal+nbEdgesVertical;
		 				bb[0]=image.getMinX();
		  				bb[1]=image.getMinY();
		 				bb[2]=width+bb[0];
		 				bb[3]=height+bb[1];
		  				val=new Color[width][height];
		  				for(int i=0; i<this.width; i++){
		  					for(int j=0; j<this.height; j++){
		  						int clr=image.getRGB(i, j);
		 						int red=(clr & 0x00ff0000) >> 16;
		 					 	int  green = (clr & 0x0000ff00) >> 8;
		  					 	int  blue  =  clr & 0x000000ff;
		 						val[i][j]=new Color(blue, green, red);;
		  					}
		  				}
		 			} catch (Exception e) {
		 				e.getMessage();
		 			}
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

