package topology;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.Arrays;
import org.apache.commons.cli.*;
//import org.apache.commons.cli.*;

/**
	Inpainting is a class that restore an image
	partially obstructed by a mask
*/
public class Inpainting {
	/** "tres grande valeur" */
	private static  int tgv=3*255*255+1;		
	/** Matrix of the image to treat */
	private Matrix image;
	/** Mask applied to the image */
	private Mask m;
	/** Penalization of the pixels of the initial Mask */
	private int[][] penMask;
	/** Working window */
	private BoundingBox window;
	/** 
		Define  an impainting problem of on image
		obstructed by a mask
	*/
	public Inpainting(Matrix _image,Mask _m){
		assert(Arrays.equals(_image.bb,_m.bb)):"Image and Mask do not have the same dimension";
		image=_image;
		m=_m;
		window=_m;
		penMask=new int[window.width][window.height];
		for(int i=0;i<window.width;i++)
			for(int j=0;j<window.height;j++)
				if(_m.val[i][j]) penMask[i][j]=tgv;
	}
	/** 
		The image is updated by copying the patch at the best_point.
		The mask is updated : the restored pixels are removed. 
	*/
	private void copyPatch(Point best_point,Patch patch){
		int i=best_point.i;
		int j=best_point.j;
		for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
			for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++)
			{
				int I=patch.point.i+dx;
				int J=patch.point.j+dy;
				if(m.val[I][J])
					image.val[I][J].set(image.val[i+dx][j+dy]);
					m.val[I][J]=false;
			}
	}
	//tim i,j min, max bb1 + marge = bb res
	private BoundingBox searchingBox(Component c ,int marge){
//		int imin, imax, jmin, jmax;
//		imin=imax=c.points.get(0).i;
//		jmin=jmax=c.points.get(0).j;
//		for(int k=0; k<c.points.size(); k++){
//			if(imax<c.points.get(k).i)
//				imax=c.points.get(k).i;
//			if(imin>c.points.get(k).i)
//				imin=c.points.get(k).i;
//			if(jmax<c.points.get(k).j)
//				imax=c.points.get(k).j;
//			if(jmin>c.points.get(k).j)
//				imin=c.points.get(k).j;
//		}
//		if(imin-window.bb[0]>=marge){		//imin
//			imin-=marge;
//		}else
//			imin=window.bb[0];
//		if(window.bb[2]-imax>=marge){		//imax
//			imax+=marge;
//		}else
//			imax=window.bb[2];
//		if(jmin-window.bb[1]>=marge){		//jmin
//			jmin-=marge;
//		}else
//			jmin=window.bb[1];
//		if(window.bb[3]-jmax>=marge){		//jmax
//			jmax+=marge;
//		}else
//			jmin=window.bb[3];
//		
//		int[] bb_ = {imin, jmin, imax, jmax};
//		return new BoundingBox(bb_);
		Point pt0=c.points.get(0);
		int[] bb=new int[]{pt0.i,pt0.j,pt0.i,pt0.j};
		for(Point pt:c.points){
			bb[0]=Math.min(bb[0],pt.i);
			bb[1]=Math.min(bb[1],pt.j);
			bb[2]=Math.max(bb[2],pt.i);
			bb[3]=Math.max(bb[3],pt.j);
		};
		return new BoundingBox(new int[]{Math.max(bb[0]-marge,0),
			Math.max(bb[1]-marge,0),
			Math.min(bb[2]+marge,window.width),
			Math.min(bb[3]+marge,window.height)});
	}
	
	/**
	 *  @return the index [i,j] such that for all k and l,
	array[i][j] lower or equal to array[k][l] 
	*/
	private int[] argmin(double[][]tab){
		double min=tab[0][0];
		int i = 0,j = 0;
		for(int k=0; k<tab.length; k++)
			for(int l=0; l<tab[0].length; l++)
				if(min>tab[k][l]){
					min=tab[k][l];
					i=k;
					j=l;
				}
		return new int[] {i,j};
	}
	
	/** 
	@return the origin point of the best matching patch
	included in the bounding box Box
	@param patch The patch to match
	@param Box The searching box
	 */
	private Point best_match(Patch patch ,BoundingBox Box){
		if(Box==null)
			Box=new BoundingBox(new int[]{0,0,m.width,m.height});
		BoundingBox searchBox=Box.crop(patch);
		// We compute the difference on all the search box, then
		// look at the minimum.
		// It should be a little faster to do it all in one step
		double[][] norms=new double[searchBox.width][searchBox.height];	// filled with zero by default
		for(int i=0;i<searchBox.width;i++)
			for(int j=0;j<searchBox.height;j++) norms[i][j]=0;		// A priori inutile
		for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
			for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++){
				int I=patch.point.i+dx;			int J=patch.point.j+dy;
				int i_min=searchBox.bb[0]+dx; 	int i_max=searchBox.bb[2]+dx;
				int j_min=searchBox.bb[1]+dy;	int j_max=searchBox.bb[3]+dy;
				// penalization of patch intersectin the mask
				for(int k=0;k<searchBox.width;k++) for(int l=0;l<searchBox.height;l++)
						norms[k][l]+=penMask[i_min+k][j_min+l];
				
				// add contribution to points outside the mask
				if (!m.val[I][J])
					for(int k=0;k<searchBox.width;k++)  for(int l=0;l<searchBox.height;l++)
							norms[k][l]+=Color.dist(image.val[i_min+k][j_min+l],image.val[I][J]);
			}
		int[] bestIndex=argmin(norms);
		return new Point(m,bestIndex[0]+searchBox.bb[0],bestIndex[1]+searchBox.bb[1]);
	}
	
	/**
	Restore the image obstucted by the mask
	@param halfwidth half width of the patches
	@param searchWidth margin of the seraching box around the connected components.
		If searchWidth=0, the searchingBox is chosen as the whole window.
	 * @return 
	 */
	public void restore(int halfwidth ,int searchWidth)  throws IOException{
		Boundary b=new Boundary(m);
		
		Components C=new Components(b);
		while (C.size()!=0){
			int k=0;
			for(Component component:C.components){
				k+=1;
				BoundingBox searchBox=null;
				if(searchWidth!=0)
					searchBox=searchingBox(component,searchWidth);

				for(Point point:component.points)
				{ 
					if (m.touchedBy(point)){
						Patch patch=new Patch(point,halfwidth,window);
						Point best_point=best_match(patch,searchBox);
						copyPatch(best_point,patch);	//update image and mask
					}
				}
			}
			b=new Boundary(m);
			C=new Components(b);
			if(searchWidth!=0) searchWidth+=halfwidth;
		}
		System.out.println("Done");
	}
	public static void main(String[] args) throws IOException, URISyntaxException{
		
		//Créer un nouveau parser et options
		CommandLineParser parser = new BasicParser();
		Options options =  new Options();
		
		//Ajouter les paramètres possibles dans la table options
		options.addOption("p","p",true,"halfwidth");
		options.addOption("s","s",true,"searchwidth");
		options.addOption("m","m",true,"mask.bmp");
		options.addOption("i","i",true,"imageToRestore.bmp");
		options.addOption("o","o",true,"restoredImage.bmp");
		
		try{
			CommandLine commandLine = parser.parse(options, args);
			
			//Obtener les paramètres en utilisant la méthode getOptionValue
			int halfWidth=Integer.parseInt(commandLine.getOptionValue("p"));
			int searchWidth=Integer.parseInt(commandLine.getOptionValue("s"));
			String chemin_img_origin="C:\\Users\\dqminh\\Documents\\workspace\\Inpainting\\src\\"+commandLine.getOptionValue("i");
			String chemin_mask="C:\\Users\\dqminh\\Documents\\workspace\\Inpainting\\src\\"+commandLine.getOptionValue("m");
			String chemin_img_saved="C:\\Users\\dqminh\\Documents\\workspace\\Inpainting\\src\\"+commandLine.getOptionValue("o");
			
			//Transmettre les paramètres pour exécuter le programme
			Inpainting test = new Inpainting(new Matrix(chemin_img_origin), new Mask(chemin_mask, new Color(0, 0, 0)));
			test.restore(halfWidth, searchWidth);
			test.image.save(chemin_img_saved);
			
			System.out.println(commandLine.getOptionValue("i"));
			System.out.println(commandLine.getOptionValue("m"));
			System.out.println(commandLine.getOptionValue("p"));
			System.out.println(commandLine.getOptionValue("s"));
			
		}catch(ParseException e){
			e.printStackTrace();
		}
		
	}
}