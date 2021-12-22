package micro3d.mesh;

public class Triangle implements Comparable<Triangle> {
	
	Vertex[] vertices;
	
	public Triangle(Vertex a, Vertex b, Vertex c) {
		vertices = new Vertex[3];
		vertices[0] = a;
		vertices[1] = b;
		vertices[2] = c;
	}
	
	public Vertex a() { return vertices[0]; }
	public Vertex b() { return vertices[1]; }
	public Vertex c() { return vertices[2]; }
	
	@Override
	public int compareTo(Triangle other) {
		float za = (a().position.z() + b().position.z() + c().position.z());
		float zb = (other.a().position.z() + other.b().position.z() + other.c().position.z());
		if (za < zb) return -1;
		if (zb > za) return 1;
		return 0;
	}
	
	@Override
	public String toString() {
		return "(" + a() + ", " + b() + ", " + c() + ")";
	}
}
