package topology;

public class Color {
	public byte[] val;
	
	//Constructor
	public Color(byte r,byte g,byte b){
		val = new byte[3];
		val[0]=r;
		val[1]=g;
		val[2]=b;
//		int i=(int)(b& 0xFF);
	}
	public Color(int r,int g,int b){
		val = new byte[3];
		val[0]=(byte)r;
		val[1]=(byte)g;
		val[2]=(byte)b;
//		val[0]=(byte) (r & 0xFF);
//		val[1]=(byte) (g & 0xFF);
//		val[2]=(byte) (b & 0xFF);
//		
	}
	
	//distance entre 2 couleurs
	public static double dist(Color c1,Color c2){
		return Math.sqrt(Math.pow((c2.val[0]-c1.val[0]),2)+Math.pow((c2.val[1]-c1.val[1]),2)+Math.pow((c2.val[1]-c1.val[1]),2));
	}
	
	//set COuleur 
	public void set(Color c){
		for(int i=0; i<3; i++){
			this.val[i]=c.val[i];
		}
	}
	
	//comparer même couleur ou pas
	boolean isequalto(Color c){
		return (this.val[0]==c.val[0]&&this.val[1]==c.val[1]&&this.val[2]==c.val[2]);
	}

	@Override public String toString(){
		String text;
		text="("+Byte.toString(val[0])+","+Byte.toString(val[1])+","+Byte.toString(val[2])+")";
		return text;
	}
}
