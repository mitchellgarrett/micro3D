package micro3d.entity;

public class Time {
	
	private static float prevSystemTime;
	private static float currSystemTime;
	private static float deltaTime;
	private static float engineTime;
	
	public static void setTime(float time) {
		engineTime = time;
		currSystemTime = System.nanoTime() / 1000000000f;
	}
	
	public static void update() {
		prevSystemTime = currSystemTime;
		currSystemTime = System.nanoTime() / 1000000000f;
		deltaTime = currSystemTime - prevSystemTime;
		engineTime += deltaTime;
	}
	
	public static float getTime() {
		return engineTime;
	}
	
	public static float getDeltaTime() {
		return deltaTime;
	}
	
	public static float getFPS() {
		return 1f / deltaTime;
	}
	
}
