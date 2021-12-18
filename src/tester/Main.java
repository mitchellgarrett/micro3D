package tester;

import entity.*;
import graphics.*;
import math.Vector3;
import mesh.*;
import component.*;

public class Main {

	public static void main(String[] args) {
		
		Window window = new Window(1280, 720, "micro3D");
		window.setIcon(Sprite.load("res/icon.png"));
		//window.setFullscreen(true);
		//window.setBorderless(true);
		
		Renderer renderer = new Renderer(window);
		
		Camera camera = new Camera(window.getAspectRatio(), 60, 0, 100);
		Scene scene = new Scene(camera);
		
		Mesh mesh = MeshLoader.loadMesh("res/cube.obj");
		//mesh.calculateNormals();
		
		Entity entity = new Entity();
		entity.addComponent(new MeshComponent(mesh));
		
		entity.transform().position().z(10);
		entity.transform().position().x(-5);
		
		scene.addEntity(entity);
		scene.addEntity(entity.copy());
		
		Time.setTime(0);
		while (window.isOpen()) {
			Time.update();
			entity.transform().position().add(new Vector3(Time.getDeltaTime() * 0.5f, 0, 0));
			entity.transform().rotation().add(new Vector3(Time.getDeltaTime() * 10, 0, 0));
			entity.transform().rotation().add(new Vector3(0, Time.getDeltaTime() * 10, 0));
			renderer.render(scene);
		}
		
		window.close();
	}

}
