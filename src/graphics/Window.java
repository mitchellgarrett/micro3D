package graphics;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window {
	
	JFrame frame;
	boolean isRunning;
	
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
		
		isRunning = true;
	}
	
	public boolean isOpen() {
		return isRunning;
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
	public void setIcon(Image icon) { frame.setIconImage(icon); }
}
