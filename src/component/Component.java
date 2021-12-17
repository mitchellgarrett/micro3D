package component;

import entity.Entity;

public abstract class Component {
	
	Entity entity;
	
	public Entity entity() { return entity; }
	public void entity(Entity entity) { this.entity = entity; }
}
