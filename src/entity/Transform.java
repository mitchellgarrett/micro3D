package entity;

import math.Vector3;

public class Transform {
	
	Vector3 position;
	Vector3 rotation;
	Vector3 scale;
	
	public Transform() {
		position = new Vector3();
		rotation = new Vector3();
		scale = new Vector3(1, 1, 1);
	}
	
	public Vector3 position() { return position; }
	public Vector3 rotation() { return rotation; }
	public Vector3 scale() { return scale; }
	
	public void position(Vector3 position) { this.position = position; }
	public void rotation(Vector3 rotation) { this.rotation = rotation; }
	public void scale(Vector3 scale) { this.scale = scale; }
}
