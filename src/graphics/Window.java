package graphics;

import javax.swing.JFrame;

import entity.Camera;
import entity.Time;
import math.*;
import mesh.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window {
	
	JFrame frame;
	Image buffer;
	float[][] zBuffer;
	Graphics graphics;
	boolean isRunning;
	
	public Mesh mesh;
	public Camera camera;
	
	float deltaTime, prevTime, currTime;
	
	public Window(int width, int height, String title) {
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setTitle(title);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				buffer = frame.createImage(getWidth(), getHeight());
			}

			@Override
			public void componentHidden(ComponentEvent e) { }

			@Override
			public void componentMoved(ComponentEvent e) { }

			@Override
			public void componentShown(ComponentEvent e) { }
		});
		
		
		// Fullscreen
		//frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		// Borderless
		//frame.setUndecorated(true);
		currTime = prevTime = System.nanoTime() / 1000000000f;
		frame.setBackground(Color.BLACK);
		frame.setForeground(Color.WHITE);
		
		frame.setVisible(true);
		
		buffer = frame.createImage(getWidth(), getHeight());
		zBuffer = new float[getHeight()][getWidth()];
		
		Time.setTime(0);
		isRunning = true;
	}
	
	void renderMesh(Mesh mesh) {
		camera.setAspect(getAspectRatio());
		Matrix4 pm = camera.getProjectionMatrix();
		
		Vector3 offset = new Vector3(1, 1, 0);
		Vector3 scale = new Vector3(getWidth() * 0.5f, getHeight() * 0.5f, 1);
		
		Vector3 trans = new Vector3(0, 0, 10);
		
		for (int t = 0; t < mesh.indices.size(); t += 3) {
			int i0 = mesh.indices.get(t + 0);
			int i1 = mesh.indices.get(t + 1);
			int i2 = mesh.indices.get(t + 2);
			
			Vector3 v0 = mesh.vertices.get(i0).copy();
			Vector3 v1 = mesh.vertices.get(i1).copy();
			Vector3 v2 = mesh.vertices.get(i2).copy();
			
			v0.add(trans);
			v1.add(trans);
			v2.add(trans);
			
			Vector3 n = mesh.normals.get(i0);
			n = Mathf.normal(v0, v1, v2);
			if (n.dot(v0.copy().sub(camera.transform().position())) < 0) {
				
				v0.mul(pm).add(offset).mul(scale);
				v1.mul(pm).add(offset).mul(scale);
				v2.mul(pm).add(offset).mul(scale);
				
				graphics.drawLine((int) v0.x(), (int) v0.y(), (int) v1.x(), (int) v1.y());
				graphics.drawLine((int) v1.x(), (int) v1.y(), (int) v2.x(), (int) v2.y());
				graphics.drawLine((int) v2.x(), (int) v2.y(), (int) v0.x(), (int) v0.y());
			}
		}
	}
	
	float theta = 0;
	void renderTriangle(Graphics g, Triangle t) {
		camera.setAspect(getAspectRatio());
		Matrix4 pm = camera.getProjectionMatrix();
		Vector3 offset = new Vector3(1, 1, 0);
		Vector3 scale = new Vector3(getWidth() * 0.5f, getHeight() * 0.5f, 1);
		
		Vector3 trans = new Vector3(0, 0, 10);
		theta += deltaTime * 0.1f;
		Matrix4 rot = new Matrix4();
		rot.identity();
		/*rot.set(0, 0, 1);
		rot.set(1, 1,  Mathf.cos(theta * 0.5f));
		rot.set(1, 2, Mathf.sin(theta * 0.5f));
		rot.set(2, 1, -Mathf.sin(theta * 0.5f));
		rot.set(2, 2, Mathf.cos(theta * 0.5f));
		rot.set(3, 3, 1);
		/*rot.set(0, 0, (float) Math.cos(theta));
		rot.set(0, 1, (float) Math.sin(theta));
		rot.set(1, 0, (float) -Math.sin(theta));
		rot.set(1, 1, (float) Math.cos(theta));
		rot.set(2, 2, 1);
		rot.set(3, 3, 1);*/
		
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
			
			a.mul(pm).add(offset).mul(scale);
			b.mul(pm).add(offset).mul(scale);
			c.mul(pm).add(offset).mul(scale);
			
			/* wireframe*/
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
	
	public boolean isOpen() {
		return isRunning;
	}
	
	void render() {
		if (isRunning) {
			resetDepth();
			graphics = buffer.getGraphics();
			graphics.clearRect(0, 0, getWidth(), getHeight());
			
			graphics.setColor(Color.WHITE);
			renderMesh(mesh);
			
			frame.getGraphics().drawImage(buffer, 0, 0, frame);
		}
	}
	
	public void update() {
		Time.update();
		currTime = System.nanoTime() / 1000000000f;
		deltaTime = currTime - prevTime;
		//System.out.println("FPS: " + Time.getDeltaTime() + ", " + deltaTime);
		render();
		prevTime = currTime;
	}
	
	public void close() {	
		if (isRunning) {
			isRunning = false;
			frame.setVisible(false);
			frame.dispose();
		}
	}
	
	public int getWidth() { return frame.getWidth(); }
	public int getHeight() { return frame.getHeight(); }
	public float getAspectRatio() { return (float) getWidth() / getHeight(); }
	public void setIcon(Image icon) {
		frame.setIconImage(icon);
	}
}
