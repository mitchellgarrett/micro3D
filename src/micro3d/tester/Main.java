package micro3d.tester;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import micro3d.component.*;
import micro3d.entity.*;
import micro3d.graphics.*;
import micro3d.math.Vector3;
import micro3d.mesh.*;

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
		entity.transform().position().z(5);
		
		Entity sprite = new Entity();
		sprite.addComponent(new SpriteComponent(Sprite.load("res/dirt.png")));
		sprite.transform().position().z(5);
		//scene.addEntity(sprite);
		
		Time.setTime(0);
		while (window.isOpen()) {
			Time.update();
			Input.update();
			
			float speed = 10f;
			float rotSpeed = 90f;
			if (Input.getKey(KeyCode.W)) {
				camera.transform().position().add(camera.transform().forward().mul(Time.getDeltaTime() * speed));
			}
			if (Input.getKey(KeyCode.S)) {
				camera.transform().position().add(camera.transform().forward().mul(Time.getDeltaTime() * -speed));
			}
			if (Input.getKey(KeyCode.D)) {
				camera.transform().rotation().add(0, Time.getDeltaTime() * -rotSpeed, 0);
			}
			if (Input.getKey(KeyCode.A)) {
				camera.transform().rotation().add(0, Time.getDeltaTime() * rotSpeed, 0);
			}
			
			entity.transform().position().add(new Vector3(Time.getDeltaTime() * 0.5f, 0, 0));
			entity.transform().rotation().add(new Vector3(Time.getDeltaTime() * 10, 0, 0));
			entity.transform().rotation().add(new Vector3(0, Time.getDeltaTime() * 10, 0));
			
			renderer.render(scene);
		}
		
		window.close();
	}

}
