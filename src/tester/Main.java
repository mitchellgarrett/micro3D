package tester;

import graphics.*;
import math.*;
import mesh.*;

public class Main {

	public static void main(String[] args) {
		Window window = new Window(720, 720, "micro3D");
		Camera camera = new Camera(window.getAspectRatio(), 60, 0, 5);
		
		Mesh mesh = new Mesh();
		
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(0, 0, 0)), new Vertex(new Vector3(0, 1, 0)), new Vertex(new Vector3(1, 1, 0))));
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(0, 0, 0)), new Vertex(new Vector3(1, 1, 0)), new Vertex(new Vector3(1, 0, 0))));
		
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(1, 0, 0)), new Vertex(new Vector3(1, 1, 0)), new Vertex(new Vector3(1, 1, 1))));
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(1, 0, 0)), new Vertex(new Vector3(1, 1, 1)), new Vertex(new Vector3(1, 0, 1))));
		
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(1, 0, 1)), new Vertex(new Vector3(1, 1, 1)), new Vertex(new Vector3(0, 1, 1))));
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(1, 0, 1)), new Vertex(new Vector3(0, 1, 1)), new Vertex(new Vector3(0, 0, 1))));
		
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(0, 0, 1)), new Vertex(new Vector3(0, 1, 1)), new Vertex(new Vector3(0, 1, 0))));
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(0, 0, 1)), new Vertex(new Vector3(0, 1, 0)), new Vertex(new Vector3(0, 0, 0))));
		
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(0, 1, 0)), new Vertex(new Vector3(0, 1, 1)), new Vertex(new Vector3(1, 1, 1))));
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(0, 1, 0)), new Vertex(new Vector3(1, 1, 1)), new Vertex(new Vector3(1, 1, 0))));
		
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(1, 0, 1)), new Vertex(new Vector3(0, 0, 1)), new Vertex(new Vector3(0, 0, 0))));
		mesh.addTriangle(new Triangle(new Vertex(new Vector3(1, 0, 1)), new Vertex(new Vector3(0, 0, 0)), new Vertex(new Vector3(1, 0, 0))));
		
		window.mesh = mesh;
		
		while (window.isOpen()) {
			window.update();
		}
		
		window.close();
	}

}
