package mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import math.Vector3;

public class MeshLoader {

	public static Mesh loadMesh(String path) {
		try {
			Scanner reader = new Scanner(new File(path));
			
			List<Vector3> vertices = new ArrayList<Vector3>();
			List<Integer> indices = new ArrayList<Integer>();
			
			Vector3 v;
			int i;
			
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
					
				case "f":
					for (int j = 0; j < 3; ++j) {
						String[] is = data[1 + j].split("/");
						i = Integer.parseInt(is[0]) - 1;
						indices.add(i);
					}
					break;
				}
			}
			
			reader.close();
			
			Mesh mesh = new Mesh();
			//mesh.setVertices(vertices);
			//mesh.setIndices(indices);
			
			for (int j = 0; j < indices.size(); j += 3) {
				int i0 = indices.get(j + 0);
				int i1 = indices.get(j + 1);
				int i2 = indices.get(j + 2);
				
				Triangle t = new Triangle(new Vertex(vertices.get(i0)), new Vertex(vertices.get(i1)), new Vertex(vertices.get(i2)));
				mesh.addTriangle(t);
			}
			
			return mesh;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
