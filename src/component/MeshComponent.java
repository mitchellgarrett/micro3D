package component;

import mesh.Mesh;

public class MeshComponent extends Component {
	
	private Mesh mesh;
	
	public MeshComponent() { }
	
	public MeshComponent(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public Mesh mesh() { return mesh; }
	public void mesh(Mesh mesh) { this.mesh = mesh; }
	
	@Override
	public Component copy() {
		MeshComponent copy = new MeshComponent();
		copy.mesh = mesh;
		return copy;
	}
}
