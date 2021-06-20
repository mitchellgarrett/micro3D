package component;

import entity.Entity;

public abstract class Component {
	
	Entity entity;
	
	public Entity entity() { return entity; }
}
