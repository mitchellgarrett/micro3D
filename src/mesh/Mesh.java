package mesh;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
	
	public List<Triangle> triangles;
	
	public Mesh() {
		triangles = new ArrayList<Triangle>();
	}
	
	public Mesh(List<Triangle> triangles) {
		this.triangles = triangles;
	}
	
	public void addTriangle(Triangle triangle) {
		triangles.add(triangle);
	}
}
