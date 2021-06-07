package graphics;

import javax.swing.JFrame;

import math.*;
import mesh.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window {
	
	JFrame frame;
	Image buffer;
	boolean isRunning;
	
	float deltaTime;
	float currTime, prevTime;
	
	public Mesh mesh;
	
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
		
		// Fullscreen
		//frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		// Borderless
		//frame.setUndecorated(true);
		
		frame.setBackground(Color.BLACK);
		frame.setForeground(Color.WHITE);
		
		frame.setVisible(true);
		
		currTime = prevTime = System.nanoTime() / 1000000000f;
		isRunning = true;
	}
	float theta = 0;
	void drawTriangle(Graphics g, Triangle t) {
		Matrix4 pm = new Camera(getAspectRatio(), 60, 0, 10).getProjectionMatrix();
		Vector3 offset = new Vector3(1, 1, 0);
		Vector3 scale = new Vector3(getWidth() * 0.5f, getHeight() * 0.5f, 1);
		
		Vector3 trans = new Vector3(0, 0, 5);
		theta += deltaTime * 0.1f;
		Matrix4 rot = new Matrix4();
		rot.set(0, 0, 1);
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
		
		Vector3 a = new Vector3(t.a().position).mul(rot).add(trans).mul(pm).add(offset).mul(scale);
		Vector3 b = new Vector3(t.b().position).mul(rot).add(trans).mul(pm).add(offset).mul(scale);
		Vector3 c = new Vector3(t.c().position).mul(rot).add(trans).mul(pm).add(offset).mul(scale);
		
		g.drawLine((int) a.x(), (int) a.y(), (int) b.x(), (int) b.y());
		g.drawLine((int) b.x(), (int) b.y(), (int) c.x(), (int) c.y());
		g.drawLine((int) c.x(), (int) c.y(), (int) a.x(), (int) a.y());
	}
	
	public boolean isOpen() {
		//System.out.println("running");
		return isRunning;
	}
	
	void render() {
		buffer = frame.createImage(getWidth(), getHeight());
		Graphics g = buffer.getGraphics();
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		for (Triangle t : mesh.triangles) {
			drawTriangle(g, t);
		}
		
		frame.getGraphics().drawImage(buffer, 0, 0, frame);
	}
	
	public void update() {
		currTime = System.nanoTime() / 1000000000f;
		deltaTime = currTime - prevTime;
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
}
