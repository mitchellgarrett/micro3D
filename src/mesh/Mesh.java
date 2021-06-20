package mesh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import math.Vector3;

public class Mesh {
	
	//MeshTopology topology;
	
	public List<Vector3> vertices;
	public List<Vector3> normals;
	public List<Integer> indices;
	public List<Color> colors;
	
	public List<Triangle> triangles;
	
	public Mesh() {
		vertices = new ArrayList<Vector3>();
		normals = new ArrayList<Vector3>();
		indices = new ArrayList<Integer>();
		colors = new ArrayList<Color>();
		
		triangles = new ArrayList<Triangle>();
	}
	
	public Mesh(List<Triangle> triangles) {
		this.triangles = triangles;
	}
	
	public void addTriangle(Triangle triangle) {
		triangles.add(triangle);
	}
	
	public void calculateNormals() {
		normals.clear();
		for (int i = 0; i < vertices.size(); ++i) {
			normals.add(null);
		}
		
		for (int t = 0; t < indices.size(); t += 3) {
			int i0 = indices.get(t + 0);
			int i1 = indices.get(t + 1);
			int i2 = indices.get(t + 2);
			
			Vector3 v0 = vertices.get(i0);
			Vector3 v1 = vertices.get(i1);
			Vector3 v2 = vertices.get(i2);
			
			Vector3 normal = v1.copy().sub(v0).cross(v2.copy().sub(v0)).normalize();
			normals.set(i0, normal);
			normals.set(i1, normal);
			normals.set(i2, normal);
		}
	}
}
