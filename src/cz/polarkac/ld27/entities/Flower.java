package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import cz.polarkac.ld27.graphics.Bitmap;

public class Flower extends Entity {
	private int flowerImage = 0;
	private int time;
	private boolean wasMoved = false;
	
	public Flower( int x, int y ) {
		super( x, y );
		Random rnd = new Random();
		flowerImage = rnd.nextInt( 3 );
		this.time = rnd.nextInt( 1000 );
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		switch ( this.flowerImage ) {
		case 0: g.drawImage( Bitmap.flower1, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		case 1: g.drawImage( Bitmap.flower2, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		case 2: g.drawImage( Bitmap.flower3, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		} 
		
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
