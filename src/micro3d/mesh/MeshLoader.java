package micro3d.mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import micro3d.math.Vector3;

public class MeshLoader {

	public static Mesh loadMesh(String path) {
		try {
			Scanner reader = new Scanner(new File(path));
			
			List<Vector3> vertices = new ArrayList<Vector3>();
			List<Vector3> normals = new ArrayList<Vector3>();
			List<Integer> indices = new ArrayList<Integer>();
			List<Integer> normalIndices = new ArrayList<Integer>();
			
			Vector3 v;
			
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split(" ");
				
				switch (data[0]) {
				
				case "v":
					v = new Vector3();
					v.x(Float.parseFloat(data[1]));
					v.y(Float.parseFloat(data[2]));
					v.z(Float.parseFloat(data[3]));
					vertices.add(v);
					break;
				
				case "vn":
					v = new Vector3();
					v.x(Float.parseFloat(data[1]));
					v.y(Float.parseFloat(data[2]));
					v.z(Float.parseFloat(data[3]));
					normals.add(v);
					break;
					
				case "f":
					for (int j = 0; j < 3; ++j) {
						String[] is = data[1 + j].split("/");
						indices.add(Integer.parseInt(is[0]) - 1);
						
						if (is.length >= 3) {
							normalIndices.add(Integer.parseInt(is[2]) - 1);
						}
					}
					break;
				}
			}
			
			reader.close();
			
			if (normalIndices.size() == indices.size()) {
				Vector3[] ns = new Vector3[vertices.size()];
				
				for (int i = 0; i < normalIndices.size(); i += 3) {
					int n0 = normalIndices.get(i + 0);
					int n1 = normalIndices.get(i + 1);
					int n2 = normalIndices.get(i + 2);
					
					int i0 = indices.get(i + 0);
					int i1 = indices.get(i + 1);
					int i2 = indices.get(i + 2);
					
					ns[i0] = normals.get(n0);
					ns[i1] = normals.get(n1);
					ns[i2] = normals.get(n2);
				}
				
				normals.clear();
				for (int i = 0; i < ns.length; i++) {
					normals.add(ns[i]);
				}
			}
			
			Mesh mesh = new Mesh();
			
			mesh.vertices = vertices;
			mesh.indices = indices;
			mesh.normals = normals;
			
			//mesh.setVertices(vertices);
			//mesh.setIndices(indices);
			
			return mesh;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
