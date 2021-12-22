package micro3d.math;

import micro3d.mesh.Triangle;

public class Mathf {
	
	public static final float PI = (float) Math.PI;
	
	public static float toDegrees(float r) { return (float) Math.toDegrees(r); }
	public static float toRadians(float d) { return (float) Math.toRadians(d); }
	
	public static float sin(float r) { return (float) Math.sin(r); }
	public static float cos(float r) { return (float) Math.cos(r); }
	public static float tan(float r) { return (float) Math.tan(r); }
	
	public static float sqrt(float v) { return (float) Math.sqrt(v); }
	
	public static float clamp(float val, float min, float max) {
		if (val < min) return min;
		if (val > max) return max;
		return val;
	}
	
	public static Vector3 normal(Vector3 a, Vector3 b, Vector3 c) {
		return b.copy().sub(a).cross(c.copy().sub(a)).normalize();
	}
	
	public static Vector3 bary(Vector3 point, Vector3 a, Vector3 b, Vector3 c) {
		float area = (b.x() - a.x()) * (c.y() - a.y()) - (c.x() - a.x()) * (b.y() - a.y());
		
		float x = ((c.x() - b.x()) * (point.y() - b.y()) - (point.x() - b.x()) * (c.y() - b.y())) / area;
		float y = ((a.x() - c.x()) * (point.y() - c.y()) - (point.x() - c.x()) * (a.y() - c.y())) / area;
		float z = ((b.x() - a.x()) * (point.y() - c.y()) - (point.x() - a.x()) * (b.y() - a.y())) / area;
		
		return new Vector3(x, y, z);
	}
	
	public static Matrix4 pointAt(Vector3 position, Vector3 target, Vector3 up)  {
		
		Vector3 forward = position.copy().sub(target).normalize();
		up = up.copy().sub(forward.copy().mul(up.dot(forward))).normalize();
		Vector3 right = up.copy().cross(forward).normalize();
		
		Matrix4 mat = new Matrix4();
		
		mat.set(0, 0, forward.x());
		mat.set(0, 1, forward.y());
		mat.set(0, 2, forward.z());
		
		mat.set(1, 0, right.x());
		mat.set(1, 1, right.y());
		mat.set(1, 2, right.z());
		
		mat.set(2, 0, up.x());
		mat.set(2, 1, up.y());
		mat.set(2, 2, up.z());
		
		mat.set(3, 0, position.x());
		mat.set(3, 1, position.y());
		mat.set(3, 2, position.z());
		mat.set(3, 3, 1);
		
		return mat;
	}
	
	public static Matrix4 lookAt(Vector3 position, Vector3 target, Vector3 up)  {
		
		Vector3 forward = position.copy().sub(target).normalize();
		//up = up.copy().cross(forward).normalize();
		up = up.copy().sub(forward.copy().mul(up.dot(forward))).normalize();
		Vector3 right = up.copy().cross(forward).normalize();
		
		Matrix4 mat = new Matrix4();
		
		mat.set(0, 0, right.x());
		mat.set(1, 0, right.y());
		mat.set(2, 0, right.z());
		
		mat.set(0, 1, up.x());
		mat.set(1, 1, up.y());
		mat.set(2, 1, up.z());
		
		mat.set(0, 2, forward.x());
		mat.set(1, 2, forward.y());
		mat.set(2, 2, forward.z());
		
		mat.set(3, 0, -position.dot(right));
		mat.set(3, 1, -position.dot(up));
		mat.set(3, 2, -position.dot(forward));
		mat.set(3, 3, 1);
		
		return mat;
	}
	
	public static Vector3 intersectPlane(Vector3 plane, Vector3 normal, Vector3 start, Vector3 end) {
		normal.normalize();
		float d = -normal.dot(plane);
		float ad = start.dot(normal);
		float bd = end.dot(normal);
		float t = (-d - ad) / (bd - ad);
		Vector3 intersect = end.copy().sub(start).mul(t);
		return intersect.add(start);
	}
	
	public static float distanceToPlane(Vector3 plane, Vector3 normal, Vector3 point) {
		normal.normalize();
		return normal.dot(point) - normal.dot(plane);
	}
	
	public static int clipPlane(Vector3 plane, Vector3 normal, Triangle triangle, Triangle outA, Triangle outB) {
		normal.normalize();
		int numInside = 0;
		int numOutside = 0;
		Vector3[] inside = new Vector3[3];
		Vector3[] outside = new Vector3[3];
		
		float d0 = distanceToPlane(plane, normal, triangle.a().position);
		float d1 = distanceToPlane(plane, normal, triangle.b().position);
		float d2 = distanceToPlane(plane, normal, triangle.c().position);
		
		if (d0 >= 0) {
			inside[numInside++] = triangle.a().position;
		} else {
			outside[numOutside++] = triangle.a().position;
		}
		
		if (d1 >= 0) {
			inside[numInside++] = triangle.b().position;
		} else {
			outside[numOutside++] = triangle.b().position;
		}
		
		if (d2 >= 0) {
			inside[numInside++] = triangle.c().position;
		} else {
			outside[numOutside++] = triangle.c().position;
		}
		
		if (numInside == 0) {
			return 0;
		}
		if (numInside == 3) {
			outA.a().position = triangle.a().position;
			outA.b().position = triangle.b().position;
			outA.c().position = triangle.c().position;
		}
		if (numInside == 1 && numOutside == 2) {
			outA.a().position = inside[0];
			outA.b().position = intersectPlane(plane, normal, inside[0], outside[0]);
			outA.c().position = intersectPlane(plane, normal, inside[0], outside[1]);
			return 1;
		}
		if (numInside == 2 && numOutside == 1) {
			outA.a().position = inside[0];
			outA.b().position = inside[1];
			outA.c().position = intersectPlane(plane, normal, inside[0], outside[0]);
			
			outB.a().position = inside[1];
			outB.b().position = inside[2];
			outB.c().position = intersectPlane(plane, normal, inside[1], outside[0]);
			return 2;
		}
		
		return 0;
	}
}
