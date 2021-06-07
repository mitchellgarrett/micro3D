package mesh;

public class Triangle {
	
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
}
