package entity;

public class Entity {

	Transform transform;
	
	public Entity() {
		transform = new Transform();
	}
	
	public Transform transform() { return transform; }
}
