package component;

import mesh.Mesh;

public class MeshComponent extends Component {
	
	private Mesh mesh;
	
	public MeshComponent() {
		super();
	}
	
	public Mesh mesh() { return mesh; }
	public void mesh(Mesh mesh) { this.mesh = mesh; }
}
