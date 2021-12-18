package math;

public class Matrix4 {
	
	float[][] values;
	
	public Matrix4() {
		values = new float[4][4];
	}
	
	public float get(int r, int c) { return values[r][c]; }
	public void set(int r, int c, float val) { values[r][c] = val; }
	
	public Matrix4 identity() {
		set(0, 0, 1);
		set(1, 1, 1);
		set(2, 2, 1);
		set(3, 3, 1);
		return this;
	}
	
	public Matrix4 mul(Matrix4 val) {
		float[][] result = new float[4][4];
		for (int r = 0; r < 4; ++r) {
			for (int c = 0; c < 4; ++c) {
				for (int i = 0; i < 4; ++i) {
					result[r][c] += get(r, i) * val.get(i, c);
				}
			}
		}
		values = result;
		return this;
	}
}
