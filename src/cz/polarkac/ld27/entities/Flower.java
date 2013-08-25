package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import cz.polarkac.ld27.graphics.Bitmap;

public class Flower extends Entity {
	private BufferedImage flowerImage = null;
	private int time;
	private boolean wasMoved = false;
	
	public Flower( int x, int y ) {
		super( x, y );
		Random rnd = new Random();
		if ( x == 0 && y == 0) {
			int xx = rnd.nextInt( 800 );
			int yy = rnd.nextInt( 600 );
			this.setPosX( xx );
			this.setPosY( yy );
		}
		flowerImage = Bitmap.spritesheet.getSubimage( ( rnd.nextInt( 3 ) + 3 ) * 16, 0, 16, 16 );
		this.time = rnd.nextInt( 1000 );
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		g.drawImage( this.flowerImage, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
	}

	@Override
	public void update( int deltaTime ) {
		time += deltaTime;
		if ( time > 500 && time < 1000 && !this.wasMoved ) {
			this.setPosX( this.getPosX() - 2 );
			this.wasMoved = true;
		} else if ( time > 1000 && time < 1500 && this.wasMoved ) {
			this.setPosX( this.getPosX() + 2 );
			this.wasMoved = false;
			time = 0;
		}
	}

}
