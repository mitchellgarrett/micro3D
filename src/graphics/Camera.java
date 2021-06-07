package graphics;

import math.Mathf;
import math.Matrix4;

public class Camera {
	
	float aspect, fov;
	float near, far;
	
	public Camera(float aspect, float fov, float near, float far) {
		this.aspect = aspect;
		this.fov = fov;
		this.near = near;
		this.far = far;
	}
	
	public Matrix4 getProjectionMatrix() {
		float f = (1f / Mathf.tan(fov * 0.5f / 180f * Mathf.PI));
		Matrix4 mat = new Matrix4();
		
		mat.set(0, 0, aspect * f);
		mat.set(1, 1, f);
		mat.set(2, 2, far / (far - near));
		mat.set(3, 2, (-far * near) / (far - near));
		mat.set(2, 3, 1);
		mat.set(3, 3, 0);
		
		return mat;
	}
}
