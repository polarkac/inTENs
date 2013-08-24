package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cz.polarkac.ld27.graphics.Bitmap;

public class Water extends CollableEntity {
	private ArrayList<BufferedImage> waterAnim = new ArrayList<BufferedImage>();
	private int actualFrame = 0;
	private int time = 0;
	
	public Water( int x, int y ) {
		super( x, y );
		this.waterAnim.add( Bitmap.spritesheet.getSubimage( 0 * 16, 1 * 16, 16, 16 ) );
		this.waterAnim.add( Bitmap.spritesheet.getSubimage( 1 * 16, 1 * 16, 16, 16 ) );
		this.waterAnim.add( Bitmap.spritesheet.getSubimage( 2 * 16, 1 * 16, 16, 16 ) );
		Random rnd = new Random();
		this.time = rnd.nextInt( 500 );
		this.setBoundingBox( new Rectangle( x, y, 64, 64 ) );
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		g.drawImage( this.waterAnim.get( this.actualFrame ), this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
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

