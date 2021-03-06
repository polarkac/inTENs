package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cz.polarkac.ld27.graphics.Bitmap;
import cz.polarkac.ld27.screens.GameScreen;

public class Gate extends CollableEntity {
	private int actualFrame = 0;
	private int time = 0;
	private int direction = 1;
	private GameScreen gameScreen;
	
	public Gate( GameScreen game, int x, int y ) {
		super( x, y );
		Random rnd = new Random();
		this.time = rnd.nextInt( 500 );
		this.xOffset = 0;
		this.yOffset = 50;
		this.setBoundingBox( new Rectangle( x + this.xOffset, y + this.yOffset, 64, 14 ) );
		this.gameScreen = game;
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		switch ( this.actualFrame ) {
		case 0: g.drawImage( Bitmap.gate1, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		case 1: g.drawImage( Bitmap.gate2, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		case 2: g.drawImage( Bitmap.gate3, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null ); break;
		} 
	}

	@Override
	public void update( int deltaTime ) {
		this.time += deltaTime;
		if ( this.time >= 100 ) {
			this.actualFrame += this.direction;
			if ( this.actualFrame > 1) {
				this.direction = -1;
			} else if ( this.actualFrame < 1 ) {
				this.direction = 1;
			}
			time = 0;
		}
		
		ArrayList<Entity> entities = this.gameScreen.getEntities();
		for ( Entity e : entities ) {
			if ( e instanceof Player ) {
				Rectangle plR = new Rectangle( e.getPosX(), e.getPosY() + 32, 64, 32 );
				Rectangle enR = new Rectangle( this.getPosX() - 1, this.getPosY() + 45, 66, 19 );
				if ( plR.intersects( enR ) ) {
					this.gameScreen.getGame().switchToWiner();
				}
			}
		}
	}

}
