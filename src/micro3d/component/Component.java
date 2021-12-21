package micro3d.component;

import micro3d.entity.Entity;

public abstract class Component {
	
	Entity entity;
	
	public Entity entity() { return entity; }
	public void entity(Entity entity) { this.entity = entity; }
	
	public abstract Component copy();
}
