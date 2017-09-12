package topology;

public class test {
	public static void main(String[] args){
		Color c1 = new Color(132, 132, 132);
		Color c2 = new Color(132, 132, 132);
		System.out.println("c1"+c1.toString()+"\n"+"Distance entre c1 et c2: "+Color.dist(c1,c2));
		System.out.println("c1 et c2 ont même couleur? "+c1.isequalto(c2));
		
	}
}
