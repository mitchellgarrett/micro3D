package micro3d.entity;

public enum KeyCode {
	None, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
	
	static KeyCode[] keys = new KeyCode[] { A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z };
	
	public static KeyCode getKeyCode(char value) {
		int index = Character.toLowerCase(value) - 'a';
		if (index < 0 || index >= keys.length) return KeyCode.None;
		return keys[index];
	}
}
