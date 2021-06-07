package math;

public class Matrix4 {
	
	float[][] values;
	
	public Matrix4() {
		values = new float[4][4];
	}
	
	public float get(int r, int c) { return values[r][c]; }
	public void set(int r, int c, float val) { values[r][c] = val; }
}
