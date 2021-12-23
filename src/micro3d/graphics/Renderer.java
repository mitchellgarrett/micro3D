package micro3d.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;
import java.util.Set;

import micro3d.component.MeshComponent;
import micro3d.component.RenderComponent;
import micro3d.component.SpriteComponent;
import micro3d.component.Transform;
import micro3d.entity.Camera;
import micro3d.entity.Entity;
import micro3d.entity.Scene;
import micro3d.entity.Time;
import micro3d.math.Mathf;
import micro3d.math.Matrix4;
import micro3d.math.Vector3;
import micro3d.mesh.Mesh;
import micro3d.mesh.Triangle;
import micro3d.mesh.Vertex;

public class Renderer {
	
	Window window;
	
	BufferedImage buffer;
	float[][] depthBuffer;
	Graphics graphics;
	PriorityQueue<Triangle> triangles;
	
	Camera camera;
	Vector3 windowOffset, windowScale;
	Matrix4 view, projection;
	
	public Renderer(Window window) {
		this.window = window;
		
		window.frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				setBuffers();
			}

			@Override
			public void componentHidden(ComponentEvent e) { }

			@Override
			public void componentMoved(ComponentEvent e) { }

			@Override
			public void componentShown(ComponentEvent e) { }
		});
		
		triangles = new PriorityQueue<Triangle>();
		setBuffers();
	}
	
	void setBuffers() {
		buffer = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_ARGB);
		depthBuffer = new float[window.getHeight()][window.getWidth()];
		graphics = buffer.getGraphics();
	}
	
	void renderEntity(Entity entity) {
		Transform t = entity.transform();
		RenderComponent rc;
		if ((rc = entity.getComponent(MeshComponent.class)) != null) {
			renderMesh(((MeshComponent)rc).mesh(), t.position(), t.rotation(), t.scale());
		} else if ((rc = entity.getComponent(SpriteComponent.class)) != null) {
			renderSprite(((SpriteComponent)rc).sprite(), t.position(), t.rotation(), t.scale());
		}
	}
	
	void renderSprite(Sprite sprite, Vector3 position, Vector3 rotation, Vector3 scale) {
		Matrix4 rot = Matrix4.rotatation(rotation);
		
		Vector3 pos = position.copy().mul(projection).add(windowOffset).mul(windowScale);
		
		graphics.drawImage(sprite.image, Math.round(pos.x()),  Math.round(pos.y()), 128, 128, null);
		
		/*for (int y = 0; y < sprite.image.getHeight(null); y++) {
			
		}*/
	}
	
	void renderMesh(Mesh mesh, Vector3 position, Vector3 rotation, Vector3 scale) {
		Matrix4 rot = Matrix4.rotatation(rotation);
		
		for (int t = 0; t < mesh.indices.size(); t += 3) {
			int i0 = mesh.indices.get(t + 0);
			int i1 = mesh.indices.get(t + 1);
			int i2 = mesh.indices.get(t + 2);
			
			Vector3 v0 = mesh.vertices.get(i0).copy();
			Vector3 v1 = mesh.vertices.get(i1).copy();
			Vector3 v2 = mesh.vertices.get(i2).copy();
			
			v0.mul(rot).add(position);
			v1.mul(rot).add(position);
			v2.mul(rot).add(position);
			
			// Back-face culling
			Vector3 n = mesh.normals.get(i0);
			n = Mathf.normal(v0, v1, v2);
			if (n.dot(v0.copy().sub(camera.transform().position())) < 0) {
				v0.mul(view);
				v1.mul(view);
				v2.mul(view);
				
				triangles.add(new Triangle(new Vertex(v0), new Vertex(v1), new Vertex(v2)));
			}
		}
	}
	
	void rasterizeTriangles() {		
		while (!triangles.isEmpty()) {
			Triangle t = triangles.remove();
			t.a().position.mul(projection).add(windowOffset).mul(windowScale);
			t.b().position.mul(projection).add(windowOffset).mul(windowScale);
			t.c().position.mul(projection).add(windowOffset).mul(windowScale);
			
			//fillTriangle(t.a().position, t.b().position, t.c().position, Color.black);
			drawTriangle(t.a().position, t.b().position, t.c().position, Color.white);
		}
	}
	
	void rasterizeTriangle(Triangle triangle) {
		int[] xs = new int[] { Math.round(triangle.a().position.x()), Math.round(triangle.b().position.x()), Math.round(triangle.c().position.x()) };
		int[] ys = new int[] { Math.round(triangle.a().position.y()), Math.round(triangle.b().position.y()), Math.round(triangle.c().position.y()) };
		
		int xmin = Math.max(0, Math.min(xs[0], Math.min(xs[1], xs[2])));
		int xmax = Math.min(window.getWidth() - 1, Math.max(xs[0], Math.max(xs[1], xs[2])));
		int ymin = Math.max(0, Math.min(ys[0], Math.min(ys[1], ys[2])));
		int ymax = Math.min(window.getHeight() - 1, Math.max(ys[0], Math.max(ys[1], ys[2])));
		
		for (int y = ymin; y <= ymax; ++y) {
			for (int x = xmin; x <= xmax; ++x) {
				Vector3 point = new Vector3(x, y, 0);
				Vector3 bary = Mathf.bary(point, triangle.a().position, triangle.b().position, triangle.c().position);
				
				if (bary.x() < 0 || bary.x() > 1 || bary.y() < 0 || bary.y() > 1 || bary.z() < 0 || bary.z() > 1) continue;
				
				float z = bary.x() * triangle.a().position.z() + bary.y() * triangle.b().position.z() + bary.z() * triangle.c().position.z();
				if (z > depthBuffer[y][x]) continue;
				
				depthBuffer[y][x] = z;
				Color color = new Color(bary.x(), bary.y(), bary.z());
				setPixel(x, y, color);
			}
		}
	}
	
	void fillTriangle(Vector3 a, Vector3 b, Vector3 c, Color color) {
		int[] xs = new int[] { Math.round(a.x()), Math.round(b.x()), Math.round(c.x()) };
		int[] ys = new int[] { Math.round(a.y()), Math.round(b.y()), Math.round(c.y()) };
		graphics.setColor(color);
		graphics.fillPolygon(xs, ys, 3);
	}
	
	void drawTriangle(Vector3 a, Vector3 b, Vector3 c, Color color) {
		graphics.setColor(color);
		graphics.drawLine(Math.round(a.x()), Math.round(a.y()), Math.round(b.x()), Math.round(b.y()));
		graphics.drawLine(Math.round(b.x()), Math.round(b.y()), Math.round(c.x()), Math.round(c.y()));
		graphics.drawLine(Math.round(c.x()), Math.round(c.y()), Math.round(a.x()), Math.round(a.y()));
	}
	
	void setPixel(int x, int y) {
		buffer.setRGB(x, y, graphics.getColor().getRGB());
	}
	
	void setPixel(int x, int y, Color color) {
		buffer.setRGB(x, y, color.getRGB());
	}
	
	void resetDepthBuffer() {
		for (int r = 0; r < depthBuffer.length; ++r) {
			for (int c = 0; c < depthBuffer[r].length; ++c) {
				depthBuffer[r][c] = Float.POSITIVE_INFINITY; 
			}
		}
	}
	
	public void render(Scene scene) {
		if (window.isRunning) {
			//resetDepthBuffer();
			graphics.clearRect(0, 0, window.getWidth(), window.getHeight());
			graphics.setColor(Color.WHITE);
			
			graphics.drawString(Float.toString(Time.getFPS()), 10, 50);
			
			camera = scene.getCamera();
			camera.setAspect(window.getAspectRatio());
			
			Vector3 lookDir = new Vector3(0, 0, 1);
			Vector3 up = new Vector3(0, 1, 0);
			
			lookDir.mul(Matrix4.rotatation(camera.transform().rotation()));
			Vector3 target = camera.transform().position().copy().add(lookDir);
			
			view = Mathf.lookAt(camera.transform().position().copy(), target, up);
			projection = camera.getProjectionMatrix();
			
			windowOffset = new Vector3(1, 1, 0);
			windowScale = new Vector3(window.getWidth() * 0.5f, window.getHeight() * 0.5f, 1);
			
			triangles.clear();
			Set<Entity> entities = scene.getEntities();
			for (Entity entity : entities) {
				renderEntity(entity);
			}
			
			rasterizeTriangles();
			
			window.frame.getGraphics().drawImage(buffer, 0, 0, window.frame);
		}
	}
}
