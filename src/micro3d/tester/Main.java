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
		
		Entity sprite = new Entity();
		sprite.addComponent(new SpriteComponent(Sprite.load("res/dirt.png")));
		sprite.transform().position().z(5);
		scene.addEntity(sprite);
		
		window.addListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				switch (e.getKeyChar()) {
				case 'w':
					camera.transform().position().add(0, Time.getDeltaTime() * 10, 0);
					break;
				case 's':
					camera.transform().position().add(0, Time.getDeltaTime() * -10, 0);
					break;
				case 'd':
					camera.transform().position().add(Time.getDeltaTime() * 10, 0, 0);
					break;
				case 'a':
					camera.transform().position().add(Time.getDeltaTime() * -10, 0, 0);
					break;

				default:
					break;
				}
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Time.setTime(0);
		while (window.isOpen()) {
			Time.update();
			Input.update();
			
			float speed = 10f;
			if (Input.getKey(KeyCode.W)) {
				camera.transform().position().add(0, Time.getDeltaTime() * speed, 0);
			}
			if (Input.getKey(KeyCode.S)) {
				camera.transform().position().add(0, Time.getDeltaTime() * -speed, 0);
			}
			if (Input.getKey(KeyCode.D)) {
				camera.transform().position().add(Time.getDeltaTime() * speed, 0, 0);
			}
			if (Input.getKey(KeyCode.A)) {
				camera.transform().position().add(Time.getDeltaTime() * -speed, 0, 0);
			}
			
			entity.transform().position().add(new Vector3(Time.getDeltaTime() * 0.5f, 0, 0));
			entity.transform().rotation().add(new Vector3(Time.getDeltaTime() * 10, 0, 0));
			entity.transform().rotation().add(new Vector3(0, Time.getDeltaTime() * 10, 0));
			
			renderer.render(scene);
		}
		
		window.close();
	}

}
