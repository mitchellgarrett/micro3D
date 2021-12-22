package micro3d.entity;

public enum KeyCode {
	A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
	
	static KeyCode[] keys = new KeyCode[] { A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z };
	
	public static KeyCode getKeyCode(char value) {
		return keys[Character.toLowerCase(value) - 'a'];
	}
}
