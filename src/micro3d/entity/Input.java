package micro3d.entity;

import java.util.EnumSet;

public class Input {
	
	static EnumSet<KeyCode> keysHeld = EnumSet.noneOf(KeyCode.class);
	static EnumSet<KeyCode> keysDown = EnumSet.noneOf(KeyCode.class);
	static EnumSet<KeyCode> keysUp = EnumSet.noneOf(KeyCode.class);
	
	static final int MOUSE_BUTTONS = 3;
	static boolean[] mouseHeld = new boolean[MOUSE_BUTTONS];
	static boolean[] mouseDown = new boolean[MOUSE_BUTTONS];
	static boolean[] mouseUp = new boolean[MOUSE_BUTTONS];
	
	public static boolean getKey(KeyCode key) {
		return keysHeld.contains(key);
	}
	
	public static boolean getKeyDown(KeyCode key) {
		return keysDown.contains(key);
	}
	
	public static boolean getKeyUp(KeyCode key) {
		return keysUp.contains(key);
	}
	
	public static void setKeyDown(KeyCode key) {
		keysDown.add(key);
		keysHeld.add(key);
	}
	
	public static void setKeyUp(KeyCode key) {
		keysHeld.remove(key);
		keysUp.add(key);
	}
	
	public static boolean getMouseButton(int button) {
		return mouseHeld[button];
	}
	
	public static boolean getMouseButtonDown(int button) {
		return mouseDown[button];
	}
	
	public static boolean getMouseButtonUp(int button) {
		return mouseUp[button];
	}
	
	public static void setMouseButtonDown(int button) {
		mouseHeld[button] = true;
		mouseDown[button] = true;
	}
	
	public static void setMouseButtonUp(int button) {
		mouseHeld[button] = false;
		mouseUp[button] = true;
	}
	
	public static void update() {
		keysDown.clear();
		keysUp.clear();
		
		for (int i = 0; i < MOUSE_BUTTONS; ++i) {
			mouseDown[i] = false;
			mouseUp[i] = false;
		}
	}
}
