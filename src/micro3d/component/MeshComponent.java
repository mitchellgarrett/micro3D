package micro3d.component;

import micro3d.mesh.Mesh;

public class MeshComponent extends RenderComponent {
	
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
