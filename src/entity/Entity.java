package entity;

import java.util.HashMap;
import java.util.Map;

import component.Component;
import component.Transform;

public class Entity {

	Transform transform;
	
	Map<Class<?>, Component> components;
	
	public Entity() {
		transform = new Transform();
		components = new HashMap<Class<?>, Component>();
		components.put(transform.getClass(), transform);
	}
	
	public <T extends Component> T addComponent(T component) {
		if (components.containsKey(component.getClass())) return null;
		components.put(component.getClass(), component);
		component.entity(this);
		return component;
	}
	
	public <T extends Component> T getComponent(Class<T> type) {
		return type.cast(components.get(type));
	}
	
	public Transform transform() { return transform; }
}
