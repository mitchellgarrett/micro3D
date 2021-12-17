package entity;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	
	Camera camera;
	List<Entity> entities;
	
	public Scene(Camera camera) {
		entities = new ArrayList<Entity>();
		setCamera(camera);
	}
	
	public Camera getCamera() { return camera; }
	public void setCamera(Camera camera) { this.camera = camera; }
	
	public void addEntity(Entity entity) { entities.add(entity); }
	public List<Entity> getEntities() { return entities; }
}
