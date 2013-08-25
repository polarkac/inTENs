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
	public static BufferedImage hearth = Bitmap.spritesheet.getSubimage( 6 * 16, 0, 16, 16 );
	public static BufferedImage flower1 = Bitmap.spritesheet.getSubimage( 3 * 16, 0, 16, 16 );
	public static BufferedImage flower2 = Bitmap.spritesheet.getSubimage( 4 * 16, 0, 16, 16 );
	public static BufferedImage flower3 = Bitmap.spritesheet.getSubimage( 5 * 16, 0, 16, 16 );
	public static BufferedImage grass = Bitmap.spritesheet.getSubimage( 0 * 16, 0, 16, 16 );
	public static BufferedImage water1 = Bitmap.spritesheet.getSubimage( 0 * 16, 1 * 16, 16, 16 );
	public static BufferedImage water2 = Bitmap.spritesheet.getSubimage( 1 * 16, 1 * 16, 16, 16 );
	public static BufferedImage water3 = Bitmap.spritesheet.getSubimage( 2 * 16, 1 * 16, 16, 16 );
	public static BufferedImage gate1 = Bitmap.spritesheet.getSubimage( 0 * 16, 2 * 16, 16, 16 );
	public static BufferedImage gate2 = Bitmap.spritesheet.getSubimage( 1 * 16, 2 * 16, 16, 16 );
	public static BufferedImage gate3 = Bitmap.spritesheet.getSubimage( 2 * 16, 2 * 16, 16, 16 );
	public static BufferedImage stone = Bitmap.spritesheet.getSubimage( 2 * 16, 0 * 16, 16, 16 );
	
	
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
