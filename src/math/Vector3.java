package math;

public class Vector3 {

	float[] values;
	
	public Vector3() {
		values = new float[3];
		values[0] = 0;
		values[1] = 0;
		values[2] = 0;
	}
	
	public Vector3(float x, float y, float z) {
		values = new float[3];
		values[0] = x;
		values[1] = y;
		values[2] = z;
	}
	
	public Vector3(Vector3 val) {
		values = new float[3];
		values[0] = val.x();
		values[1] = val.y();
		values[2] = val.z();
	}
	
	public float x() { return values[0]; }
	public float y() { return values[1]; }
	public float z() { return values[2]; }
	
	public float x(float val) { return values[0] = val; }
	public float y(float val) { return values[1] = val; }
	public float z(float val) { return values[2] = val; }
	
	public Vector3 add(Vector3 val) {
		values[0] += val.x();
		values[1] += val.y();
		values[2] += val.z();
		return this;
	}
	
	public Vector3 mul(float val) {
		values[0] *= val;
		values[1] *= val;
		values[2] *= val;
		return this;
	}
	
	public Vector3 mul(Vector3 val) {
		values[0] *= val.x();
		values[1] *= val.y();
		values[2] *= val.z();
		return this;
	}
	
	public Vector3 mul(Matrix4 mat) {
		float x = x() * mat.get(0, 0) + y() * mat.get(1, 0) + z() * mat.get(2, 0) + mat.get(3, 0);
		float y = x() * mat.get(0, 1) + y() * mat.get(1, 1) + z() * mat.get(2, 1) + mat.get(3, 1); 
		float z = x() * mat.get(0, 2) + y() * mat.get(1, 2) + z() * mat.get(2, 2) + mat.get(3, 2);
		
		values[0] = x;
		values[1] = y;
		values[2] = z;
		
		float w = x() * mat.get(0, 3) + y() * mat.get(1, 3) + z() * mat.get(2, 3) + mat.get(3, 3);
		if (w != 0) {
			values[0] /= w;
			values[1] /= w;
			values[2] /= w;
		}
		return this;
	}
}
