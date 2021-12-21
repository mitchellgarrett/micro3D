package micro3d.math;

public class Vector4 {

	float[] values;
	
	public Vector4() {
		values = new float[4];
		values[0] = 0;
		values[1] = 0;
		values[2] = 0;
		values[3] = 0;
	}
	
	public Vector4(float x, float y, float z, float w) {
		values = new float[4];
		values[0] = x;
		values[1] = y;
		values[2] = z;
		values[3] = w;
	}
	
	public Vector4(Vector4 val) {
		values = new float[3];
		values[0] = val.x();
		values[1] = val.y();
		values[2] = val.z();
		values[3] = val.w();
	}
	
	public float x() { return values[0]; }
	public float y() { return values[1]; }
	public float z() { return values[2]; }
	public float w() { return values[3]; }
	
	public float x(float val) { return values[0] = val; }
	public float y(float val) { return values[1] = val; }
	public float z(float val) { return values[2] = val; }
	public float w(float val) { return values[3] = val; }
	
	public Vector4 copy() {
		return new Vector4(this);
	}
	
	public Vector4 neg() {
		values[0] *= -1;
		values[1] *= -1;
		values[2] *= -1;
		return this;
	}
	
	public Vector4 mul(Matrix4 mat) {
		return new Vector4(
				x() * mat.get(0, 0) + y() * mat.get(1, 0) + z() * mat.get(2, 0) + mat.get(3, 0), 
				x() * mat.get(0, 1) + y() * mat.get(1, 1) + z() * mat.get(2, 1) + mat.get(3, 1), 
				x() * mat.get(0, 2) + y() * mat.get(1, 2) + z() * mat.get(2, 2) + mat.get(3, 2), 
				x() * mat.get(0, 3) + y() * mat.get(1, 3) + z() * mat.get(2, 3) + mat.get(3, 3)
			);
	}
}
