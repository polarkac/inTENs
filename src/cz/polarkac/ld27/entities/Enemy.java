package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.Random;

import cz.polarkac.ld27.graphics.Bitmap;
import cz.polarkac.ld27.screens.GameScreen;

public class Enemy extends CollableEntity {

	public enum FacingSide {
		LEFT(0), RIGHT(1), UP(2), DOWN(3);
		
		public int value;
		private FacingSide( int value ) {
			this.value = value;
		}
		
	}
	
	private GameScreen gameScreen;
	private BufferedImage playerImage = null;
	private float actualFrame = 0;
	private final int SPEED = 1;
	private FacingSide facing = FacingSide.LEFT;
	private int targetPointX;
	private int targetPointY;
	private Random rnd = new Random();
	private int lastHurtTime = 0;

	public Enemy( GameScreen screen, int x, int y ) {
		super( x, y );
		this.xOffset = 10;
		this.yOffset = 49;
		this.setBoundingBox( new Rectangle( x + this.xOffset, y + this.yOffset, 44, 15 ) );
		this.gameScreen = screen;
		if ( rnd.nextInt( 100 ) > 50 ) {
			this.playerImage = Bitmap.enemy1;
		} else {
			this.playerImage = Bitmap.enemy2;
		}
		this.targetPointX = 0;
		this.targetPointY = 0;
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		BufferedImage subImage = null;
		try {
			subImage = this.playerImage.getSubimage( (int) this.actualFrame * 16, this.facing.value * 16, 16, 16 );
			g.drawImage( subImage, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
		} catch( RasterFormatException e ) {
			e.printStackTrace();
		}
		
		//g.drawRect( this.getBoundingBox().x, this.getBoundingBox().y, this.getBoundingBox().width, this.getBoundingBox().height );
	}

	@Override
	public void update( int deltaTime ) {
		Player pl = this.gameScreen.getPlayer();
		this.targetPointX = pl.getPosX();
		this.targetPointY = pl.getPosY();
		int lastX = this.getPosX();
		int lastY = this.getPosY();
		int newX = lastX;
		int newY = lastY;
		int diffX = this.targetPointX - lastX;
		int diffY = this.targetPointY - lastY;
		if ( diffX < 0 ) {
			newX -= SPEED;
			this.facing = FacingSide.LEFT;
		} else if ( diffX > 0 ) {
			newX += SPEED;
			this.facing = FacingSide.RIGHT;
		}
		if ( diffY < 0 ) {
			newY -= SPEED;
			this.facing = FacingSide.UP;
		} else if ( diffY > 0 ) {
			newY += SPEED;
			this.facing = FacingSide.DOWN;
		}
		
		if ( lastX != newX || lastY != newY ) {
			ArrayList<Entity> entities = this.gameScreen.getEntities();
			for ( Entity e : entities ) {
				CollableEntity en = null;
				if ( !( e instanceof CollableEntity ) || e == this) {
					continue;
				}
				en = (CollableEntity) e;
				this.setPosition( newX, lastY );
				if ( this.isColliding( en ) ) {
					newX = lastX;
					if ( en instanceof Player && this.lastHurtTime == 0 ) {
						( (Player) en ).hurt( 20 );
						this.lastHurtTime = 500;
					}
				}
				this.setPosition( lastX, newY );
				if ( this.isColliding( en ) ) {
					newY = lastY;
					if ( en instanceof Player && this.lastHurtTime == 0 ) {
						( (Player) en ).hurt( 20 );
						this.lastHurtTime = 500;
					}
				}
			}
			
			if ( this.lastHurtTime > 0 ) {
				this.lastHurtTime -= deltaTime;
			} else if ( this.lastHurtTime != 0 ){
				this.lastHurtTime = 0;
			}
			this.setPosition( newX, newY );
			this.actualFrame += 0.2;
			if ( this.actualFrame > 3 ) {
				this.actualFrame = 0;
			}
		} else {
			this.actualFrame = 0;
		}
	}
	
	public void setPosition( int x, int y ) {
		this.setPosX( x );
		this.setPosY( y );
		this.getBoundingBox().x = x + this.xOffset;
		this.getBoundingBox().y = y + this.yOffset;
	}

}
