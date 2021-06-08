package mesh;

import java.util.ArrayList;
import java.util.List;

import math.Vector3;

public class Mesh {
	
	//MeshTopology topology;
	
	List<Vector3> vertices;
	List<Integer> indices;
	
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
	
	public void setVertices(List<Vector3> vertices) { this.vertices = vertices; }
	public void setIndices(List<Integer> indices) { this.indices = indices; }
}
