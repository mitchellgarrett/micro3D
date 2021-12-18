package math;

public class Mathf {
	
	public static final float PI = (float) Math.PI;
	
	public static float toDegrees(float r) { return (float) Math.toDegrees(r); }
	public static float toRadians(float d) { return (float) Math.toRadians(d); }
	
	public static float sin(float r) { return (float) Math.sin(r); }
	public static float cos(float r) { return (float) Math.cos(r); }
	public static float tan(float r) { return (float) Math.tan(r); }
	
	public static float sqrt(float v) { return (float) Math.sqrt(v); }
	
	public static Vector3 normal(Vector3 a, Vector3 b, Vector3 c) {
		return b.copy().sub(a).cross(c.copy().sub(a)).normalize();
	}
	
	public static Matrix4 rotationMatrix(Vector3 rotation) {
		float cosx = Mathf.cos(Mathf.toRadians(rotation.x()));
		float sinx = Mathf.sin(Mathf.toRadians(rotation.x()));
		
		float cosy = Mathf.cos(Mathf.toRadians(rotation.y()));
		float siny = Mathf.sin(Mathf.toRadians(rotation.y()));
		
		float cosz = Mathf.cos(Mathf.toRadians(rotation.z()));
		float sinz = Mathf.sin(Mathf.toRadians(rotation.z()));
		
		Matrix4 x = new Matrix4();
		x.identity();
		x.set(1, 1, cosx);
		x.set(1, 2, -sinx);
		x.set(2, 1, sinx);
		x.set(2, 2, cosx);
		
		Matrix4 y = new Matrix4();
		y.identity();
		y.set(0, 0, cosy);
		y.set(0, 2, siny);
		y.set(2, 0, -siny);
		y.set(2, 2, cosy);
		
		Matrix4 z = new Matrix4();
		z.identity();
		z.set(0, 0, cosz);
		z.set(0, 1, -sinz);
		z.set(1, 0, sinz);
		z.set(1, 1, cosz);
		
		return x.mul(y).mul(z);
	}
}
