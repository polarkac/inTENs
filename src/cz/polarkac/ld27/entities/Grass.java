package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;

import cz.polarkac.ld27.graphics.Bitmap;

public class Grass extends Entity {
	
	public Grass( int x, int y ) {
		super( x, y );
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		g.drawImage( Bitmap.grass, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
	}

	@Override
	public void update( int deltaTime ) {
	}

}
