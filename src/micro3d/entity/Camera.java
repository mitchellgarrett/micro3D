package micro3d.entity;

import micro3d.math.Mathf;
import micro3d.math.Matrix4;

public class Camera extends Entity {
	
	float aspect, fov;
	float near, far;
	
	public Camera(float aspect, float fov, float near, float far) {
		super();
		this.aspect = aspect;
		this.fov = fov;
		this.near = near;
		this.far = far;
	}
	
	public Matrix4 getProjectionMatrix() {
		float f = (1f / Mathf.tan(Mathf.toRadians(fov * 0.5f)));
		Matrix4 mat = new Matrix4();
		
		mat.set(0, 0, f);
		mat.set(1, 1, aspect * f);
		mat.set(2, 2, far / (far - near));
		mat.set(3, 2, (-far * near) / (far - near));
		mat.set(2, 3, 1);
		mat.set(3, 3, 0);
		
		return mat;
	}
	
	public void setAspect(float aspect) { this.aspect = aspect; }
}
