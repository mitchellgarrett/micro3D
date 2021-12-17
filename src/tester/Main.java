package tester;

import entity.*;
import graphics.*;
import mesh.*;
import component.*;

public class Main {

	public static void main(String[] args) {
		
		Window window = new Window(1280, 720, "micro3D");
		Camera camera = new Camera(window.getAspectRatio(), 60, 0, 100);
		
		Renderer renderer = new Renderer(window);
		
		Mesh mesh = MeshLoader.loadMesh("res/cube.obj");
		//mesh.calculateNormals();
		
		Entity entity = new Entity();
		MeshComponent mc = entity.addComponent(new MeshComponent());
		mc.mesh(mesh);
		
		Scene scene = new Scene(camera);
		scene.addEntity(entity);
		
		Time.setTime(0);
		while (window.isOpen()) {
			Time.update();
			renderer.render(scene);
		}
		
		window.close();
	}

}
