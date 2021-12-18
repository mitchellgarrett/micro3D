package graphics;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	Image image;
	
	private Sprite(Image image) {
		this.image = image;
	}
	
	public static Sprite load(String path) {
		try {
			return new Sprite(ImageIO.read(new File(path)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
