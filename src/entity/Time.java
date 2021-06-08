package entity;

public class Time {
	
	private static float previousTime;
	private static float currentTime;
	private static float deltaTime;
	
	public static void setTime(float time) {
		currentTime = time;
		previousTime = currentTime;
	}
	
	public static void update() {
		previousTime = currentTime;
		currentTime = (float) System.nanoTime() / 1000000000f;
		deltaTime = currentTime - previousTime;
	}
	
	public static float getTime() {
		return currentTime;
	}
	
	public static float getDeltaTime() {
		return deltaTime;
	}
	
	public static float getFPS() {
		return 1f / deltaTime;
	}
	
}
