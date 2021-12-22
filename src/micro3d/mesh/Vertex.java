package micro3d.mesh;

import micro3d.math.Vector3;

public class Vertex {

	public Vector3 position;
	
	public Vertex(Vector3 position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return position.toString();
	}
}
