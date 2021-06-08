package tester;

import entity.Camera;
import graphics.*;
import mesh.*;

public class Main {

	public static void main(String[] args) {
		Window window = new Window(1280, 720, "micro3D");
		Camera camera = new Camera(window.getAspectRatio(), 60, 0, 100);
		
		Mesh mesh = MeshLoader.loadMesh("res/cube.obj");
		
		window.mesh = mesh;
		window.camera = camera;
		
		while (window.isOpen()) {
			window.update();
		}
		
		window.close();
	}

}
