package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.polarkac.ld27.graphics.Bitmap;

public class Grass extends Entity {
	private BufferedImage grassImage = Bitmap.spritesheet.getSubimage( 0, 0, 16, 16 ); 
	
	public Grass( int x, int y ) {
		super( x, y );
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		g.drawImage( this.grassImage, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
	}

	@Override
	public void update( int deltaTime ) {
	}

}
