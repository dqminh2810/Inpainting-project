package topology;
import java.util.ArrayList;
/**
	List of all the connected components of the boundary of a Mask
*/
public class Components {
	/**
		Array of all the connected components of the boundary of the mash
	*/
	public ArrayList<Component> components;
	/**
		Number of connected components
	*/
	public int size() {
		return components.size();}
	/**
		Construct the list of all connected components of the given mask
	*/
	public Components(Boundary boundary) {
		components=new ArrayList<Component>();
		Tag tag=new Tag(boundary);
		Point seedPoint=tag.SeedPoint();
		while(seedPoint!=null) {
			Component newComponent=new Component(tag,seedPoint);
			components.add(newComponent);
			seedPoint=tag.SeedPoint();}}}