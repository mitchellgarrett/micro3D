package micro3d.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import micro3d.component.MeshComponent;
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
	
	Image buffer;
	float[][] zBuffer;
	Graphics graphics;
	
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
		
		setBuffers();
	}
	
	void setBuffers() {
		buffer = window.frame.createImage(window.getWidth(), window.getHeight());
		zBuffer = new float[window.getHeight()][window.getWidth()];
		graphics = buffer.getGraphics();
	}
	
	void renderEntity(Entity entity) {
		Transform t = entity.transform();
		MeshComponent mc = entity.getComponent(MeshComponent.class);
		if (mc != null) {
			renderMesh(mc.mesh(), t.position(), t.rotation(), t.scale());
		} else {
			SpriteComponent sc = entity.getComponent(SpriteComponent.class);
			if (sc != null) {
				renderSprite(sc.sprite(), t.position(), t.rotation(), t.scale());
			}
		}
	}
	
	void renderSprite(Sprite sprite, Vector3 position, Vector3 rotation, Vector3 scale) {
		Matrix4 rot = Mathf.rotationMatrix(rotation);
		
		Vector3 pos = position.copy().mul(projection).add(windowOffset).mul(windowScale);
		
		graphics.drawImage(sprite.image, Math.round(pos.x()),  Math.round(pos.y()), 128, 128, null);
		
		/*for (int y = 0; y < sprite.image.getHeight(null); y++) {
			
		}*/
	}
	
	void renderMesh(Mesh mesh, Vector3 position, Vector3 rotation, Vector3 scale) {
		Matrix4 rot = Mathf.rotationMatrix(rotation);
		
		List<Triangle> triangles = new ArrayList<Triangle>();
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

				triangles.add(new Triangle(new Vertex(v0), new Vertex(v1), new Vertex(v2)));
				
				//fillTriangle(v0, v1, v2, color);
				//drawTriangle(v0, v1, v2, Color.white);
			}
		}
		
		triangles.sort(new Comparator<Triangle>() {
			@Override
			public int compare(Triangle a, Triangle b) {
				float za = (a.a().position.z() + a.b().position.z() + a.c().position.z()) / 3f;
				float zb = (b.a().position.z() + b.b().position.z() + b.c().position.z()) / 3f;
				if (za < zb) return -1;
				if (zb > za) return 1;
				return 0;
			}
		});
		
		for (Triangle t : triangles) {
			Vector3 lightDir = new Vector3(0, 0, -1).normalize();
			Vector3 n = Mathf.normal(t.a().position, t.b().position, t.c().position);
			float d = Mathf.clamp(n.dot(lightDir), 0, 1);
			Color color = new Color(d, d, d);
			
			t.a().position.mul(view).mul(projection).add(windowOffset).mul(windowScale);
			t.b().position.mul(view).mul(projection).add(windowOffset).mul(windowScale);
			t.c().position.mul(view).mul(projection).add(windowOffset).mul(windowScale);
			
			//fillTriangle(t.a().position, t.b().position, t.c().position, color);
			drawTriangle(t.a().position, t.b().position, t.c().position, Color.white);
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
	
	void drawPixel(Graphics g, int x, int y) {
		g.drawLine(x, y, x, y);
	}
	
	void resetDepth() {
		for (int r = 0; r < zBuffer.length; ++r) {
			for (int c = 0; c < zBuffer[r].length; ++c) {
				zBuffer[r][c]= Float.POSITIVE_INFINITY; 
			}
		}
	}
	
	public void render(Scene scene) {
		if (window.isRunning) {
			resetDepth();
			graphics.clearRect(0, 0, window.getWidth(), window.getHeight());
			graphics.setColor(Color.WHITE);
			
			graphics.drawString(Float.toString(Time.getFPS()), 10, 50);
			
			camera = scene.getCamera();
			camera.setAspect(window.getAspectRatio());
			
			Vector3 lookDir = new Vector3(0, 0, 1);
			Vector3 up = new Vector3(0, 1, 0);
			Vector3 target = camera.transform().position().copy().add(lookDir);
			
			view = Mathf.lookAtMatrix(camera.transform().position().copy(), target, up);
			projection = camera.getProjectionMatrix();
			
			windowOffset = new Vector3(1, 1, 0);
			windowScale = new Vector3(window.getWidth() * 0.5f, window.getHeight() * 0.5f, 1);
			
			Set<Entity> entities = scene.getEntities();
			for (Entity entity : entities) {
				renderEntity(entity);
			}
			
			window.frame.getGraphics().drawImage(buffer, 0, 0, window.frame);
		}
	}
}
