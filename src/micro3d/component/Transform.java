package micro3d.component;

import micro3d.math.Vector3;

public class Transform extends Component {
	
	Vector3 position;
	Vector3 rotation;
	Vector3 scale;
	
	public Transform() {
		position = new Vector3();
		rotation = new Vector3();
		scale = new Vector3(1, 1, 1);
	}
	
	@Override
	public Component copy() {
		Transform copy = new Transform();
		copy.position = position.copy();
		copy.rotation = rotation.copy();
		copy.scale = scale.copy();
		return copy;
	}
	
	public Vector3 position() { return position; }
	public Vector3 rotation() { return rotation; }
	public Vector3 scale() { return scale; }
	
	public void position(Vector3 position) { this.position = position; }
	public void rotation(Vector3 rotation) { this.rotation = rotation; }
	public void scale(Vector3 scale) { this.scale = scale; }
}
