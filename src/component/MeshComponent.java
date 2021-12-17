package component;

import mesh.Mesh;

public class MeshComponent extends Component {
	
	private Mesh mesh;
	
	public Mesh mesh() { return mesh; }
	public void mesh(Mesh mesh) { this.mesh = mesh; }
}
