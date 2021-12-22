package micro3d.graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import micro3d.entity.Input;
import micro3d.entity.KeyCode;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			
			@Override
			public void keyReleased(KeyEvent e) {
				Input.setKeyUp(KeyCode.getKeyCode(e.getKeyChar()));
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Input.setKeyDown(KeyCode.getKeyCode(e.getKeyChar()));
			}
		});
		
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					Input.setMouseButtonUp(0);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					Input.setMouseButtonUp(1);
				} else if (SwingUtilities.isMiddleMouseButton(e)) {
					Input.setMouseButtonUp(2);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					Input.setMouseButtonDown(0);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					Input.setMouseButtonDown(1);
				} else if (SwingUtilities.isMiddleMouseButton(e)) {
					Input.setMouseButtonDown(2);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		
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
	
	public void setFullscreen(boolean fullscreen) { 
		if (fullscreen) frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		else frame.setExtendedState(Frame.NORMAL);
	}
	
	public void setBorderless(boolean borderless) { 
		frame.dispose();
		frame.setUndecorated(borderless); 
		frame.setVisible(true);
	}
	
	public void addListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}
	
	public void addListener(MouseListener listener) {
		frame.addMouseListener(listener);
	}

	public int getWidth() { return frame.getWidth(); }
	public int getHeight() { return frame.getHeight(); }
	public float getAspectRatio() { return (float) getWidth() / getHeight(); }
	public void setIcon(Sprite icon) { frame.setIconImage(icon.image); }
}
