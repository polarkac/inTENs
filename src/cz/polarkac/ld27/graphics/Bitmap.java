package cz.polarkac.ld27.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cz.polarkac.ld27.Game;

public class Bitmap {
	public static BufferedImage spritesheet = Bitmap.loadBitmap( "/spritesheet.png" );
	public static BufferedImage player = Bitmap.loadBitmap( "/player.png" );
	public static BufferedImage enemy1 = Bitmap.loadBitmap( "/enemy1.png" );
	public static BufferedImage enemy2 = Bitmap.loadBitmap( "/enemy2.png" );
	
	public static BufferedImage loadBitmap( String filename ) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read( Game.class.getResourceAsStream( filename ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return tempImage;
	}

}
