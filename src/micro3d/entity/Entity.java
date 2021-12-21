package micro3d.entity;

import java.util.HashMap;
import java.util.Map;

import micro3d.component.Component;
import micro3d.component.Transform;

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
	
	public Entity copy() {
		Entity copy = new Entity();
		copy.transform.position(transform.position().copy());
		copy.transform.rotation(transform.rotation().copy());
		copy.transform.scale(transform.scale().copy());
		for (Component component : components.values()) {
			copy.addComponent(component.copy());
		}
		return copy;
	}
	
	public static Entity instantiate() {
		return new Entity();
	}
	
	public static Entity instantiate(Entity entity) {
		return entity.copy();
	}
}
