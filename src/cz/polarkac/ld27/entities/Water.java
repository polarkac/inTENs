package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cz.polarkac.ld27.graphics.Bitmap;

public class Water extends CollableEntity {
	private int actualFrame = 0;
	private int time = 0;
	
	public Water( int x, int y ) {
		super( x, y );
		Random rnd = new Random();
		this.time = rnd.nextInt( 500 );
		this.setBoundingBox( new Rectangle( x, y, 64, 64 ) );
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		switch ( this.actualFrame ) {
		case 0: g.drawImage( Bitmap.water1, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		case 1: g.drawImage( Bitmap.water2, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		case 2: g.drawImage( Bitmap.water3, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		} 
	}

	@Override
	public void update( int deltaTime ) {
		this.time += deltaTime;
		if ( this.time >= 500 ) {
			this.actualFrame++;
			if ( this.actualFrame > 2) {
				this.actualFrame = 0;
			}
			time = 0;
		}
	}

}

