package entity;

import java.util.HashSet;
import java.util.Set;

public class Scene {
	
	Camera camera;
	Set<Entity> entities;
	
	public Scene(Camera camera) {
		entities = new HashSet<Entity>();
		setCamera(camera);
	}
	
	public Camera getCamera() { return camera; }
	public void setCamera(Camera camera) { this.camera = camera; }
	
	public boolean addEntity(Entity entity) { return entities.add(entity); }
	public Set<Entity> getEntities() { return entities; }
}
