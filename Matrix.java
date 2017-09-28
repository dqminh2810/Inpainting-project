package topology;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;



public class Matrix extends BoundingBox{
	public Color[][] val;
	public String fileName;
	
	public Matrix(String fileName) throws IOException{
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
	 * Save the image encoded by the array val in a file at the format .bmp 3x(8bits) BGR (Blue, Green, Red
	 */
	public void save(String fileNameSave) throws IOException{
		// Initialize BufferedImage, assuming Color[][] is already properly populated.
		BufferedImage imageSaved = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
		BufferedImage myImage = ImageIO.read(new File(fileName));
		
		// Set each pixel of the BufferedImage to the color RGB from the val[][].
		for (int i = 0; i < val.length; i++) {
		    for (int j = 0; j < val[i].length; j++) {
		    	int clr=myImage.getRGB(i, j); 	//get color RGB at pixel val[i][j]
				int red=(clr & 0x00ff0000) >> 16;		//get red int
			 	int green = (clr & 0x0000ff00) >> 8;	//get green int
			 	int blue  =  clr & 0x000000ff;			//get blue int
		    	int bgr = (blue << 16 | green << 8 | red);	//get combine blue,green,red int
		    	imageSaved.setRGB(i,j,bgr);			//set couleur rgb for bufferedimage at pixel [i][j] 
		    }
		}
		//Save image with format bmp -- format color BRGB
		ImageIO.write(imageSaved, "BMP", new File(fileNameSave+".bmp"));
	}
	/*
	 * Apply a Mask to the image Set every pixel such that Mask.val[i][j]=True to black color
	 * Mask - = mask to apply
	 */
	public void applyMask(Mask mask){
		Color black = new Color(0, 0, 0);
		for (int i = 0; i < val.length; i++) {
		    for (int j = 0; j < val[i].length; j++) {
		    	if(mask.val[i][j]==true)
		    		this.val[i][j].set(black);
		    }
		}
	}
}

