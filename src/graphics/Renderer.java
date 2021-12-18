package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Set;

import component.MeshComponent;
import component.Transform;
import entity.Camera;
import entity.Entity;
import entity.Scene;
import math.Mathf;
import math.Matrix4;
import math.Vector3;
import mesh.Mesh;
import mesh.Triangle;

public class Renderer {
	
	Window window;
	
	Image buffer;
	float[][] zBuffer;
	Graphics graphics;
	
	Camera camera;
	Vector3 windowOffset, windowScale;
	Matrix4 projection;
	
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
		MeshComponent mc = entity.getComponent(MeshComponent.class);
		if (mc != null) {
			Transform t = entity.transform();
			renderMesh(mc.mesh(), t.position(), t.rotation(), t.scale());
		}
	}
	
	void renderMesh(Mesh mesh, Vector3 position, Vector3 rotation, Vector3 scale) {
		Matrix4 rot = Mathf.rotationMatrix(rotation);
		
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
			
			Vector3 n = mesh.normals.get(i0);
			n = Mathf.normal(v0, v1, v2);
			if (n.dot(v0.copy().sub(camera.transform().position())) < 0) {
				
				v0.mul(projection).add(windowOffset).mul(windowScale);
				v1.mul(projection).add(windowOffset).mul(windowScale);
				v2.mul(projection).add(windowOffset).mul(windowScale);
				
				graphics.drawLine(Math.round(v0.x()), Math.round(v0.y()), Math.round(v1.x()), Math.round(v1.y()));
				graphics.drawLine(Math.round(v1.x()), Math.round( v1.y()), Math.round(v2.x()), Math.round( v2.y()));
				graphics.drawLine(Math.round(v2.x()), Math.round(v2.y()), Math.round(v0.x()), Math.round(v0.y()));
			}
		}
	}
	
	void renderTriangle(Graphics g, Triangle t) {
		
		Vector3 trans = new Vector3(0, 0, 10);
		Matrix4 rot = new Matrix4();
		rot.identity();
		
		Vector3 a = new Vector3(t.a().position).mul(rot).add(trans);
		Vector3 b = new Vector3(t.b().position).mul(rot).add(trans);
		Vector3 c = new Vector3(t.c().position).mul(rot).add(trans);
		
		Vector3 normal, line1, line2;
		
		line1 = new Vector3(b).sub(a);
		line2 = new Vector3(c).sub(a);
		
		normal = line1.cross(line2).normalize();
		
		if (
				normal.x() * (a.x() - camera.transform().position().x()) +
				normal.y() * (a.y() - camera.transform().position().y()) +
				normal.z() * (a.z() - camera.transform().position().z()) < 0
				) {
		
			//Vector3 light = new Vector3(0, 0, -1).normalize();
			//float dp = light.dot(normal);
			
			a.mul(projection).add(windowOffset).mul(windowScale);
			b.mul(projection).add(windowOffset).mul(windowScale);
			c.mul(projection).add(windowOffset).mul(windowScale);
			
			// Wireframe
			g.drawLine((int) a.x(), (int) a.y(), (int) b.x(), (int) b.y());
			g.drawLine((int) b.x(), (int) b.y(), (int) c.x(), (int) c.y());
			g.drawLine((int) c.x(), (int) c.y(), (int) a.x(), (int) a.y());
		}
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
			
			camera = scene.getCamera();
			camera.setAspect(window.getAspectRatio());
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
