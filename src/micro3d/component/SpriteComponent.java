package micro3d.component;

import micro3d.graphics.Sprite;

public class SpriteComponent extends RenderComponent {
	
	private Sprite sprite;
	
	public SpriteComponent() { }
	
	public SpriteComponent(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite sprite() { return sprite; }
	public void sprite(Sprite sprite) { this.sprite = sprite; }
	
	@Override
	public Component copy() {
		SpriteComponent copy = new SpriteComponent();
		copy.sprite = sprite;
		return copy;
	}
}
