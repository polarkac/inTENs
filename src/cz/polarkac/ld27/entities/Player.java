package cz.polarkac.ld27.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cz.polarkac.ld27.KeyboardListener;
import cz.polarkac.ld27.graphics.Bitmap;
import cz.polarkac.ld27.screens.GameScreen;
import cz.polarkac.ld27.sound.Sound;

public class Player extends CollableEntity {
	
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
	private final int SPEED = 3;
	private FacingSide facing = FacingSide.LEFT;
	private int health = 100;
	private int damageTime = 0;
	private int knockbackTime = 0;
	private FacingSide knockBackDir;

	public Player( GameScreen screen, int x, int y ) {
		super( x, y );
		this.xOffset = 10;
		this.yOffset = 49;
		this.setBoundingBox( new Rectangle( x + xOffset, y + yOffset, 44, 15 ) );
		this.gameScreen = screen;
		this.playerImage = Bitmap.player;
	}

	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		BufferedImage subImage = null;
		subImage = this.playerImage.getSubimage( (int) this.actualFrame * 16, this.facing.value * 16, 16, 16 );
		g.drawImage( subImage, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
		
		if ( this.damageTime > 0 ) {
			g.setColor( new Color( 255, 0, 0, 30 ) );
			g.fillRect( this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64 );
		}
	}

	@Override
	public void update( int deltaTime ) {
		KeyboardListener k = this.gameScreen.getGame().getKeyboardListener();
		int lastX = this.getPosX();
		int lastY = this.getPosY();
		int newX = lastX;
		int newY = lastY;
		if ( k.down.isDown ) {
			newY = lastY + SPEED;
			this.facing = FacingSide.DOWN;
		} 
		if ( k.up.isDown ) {
			newY = lastY - SPEED;
			this.facing = FacingSide.UP;
		}
		if ( k.right.isDown ) {
			newX = lastX + SPEED;
			this.facing = FacingSide.RIGHT;
		}
		if ( k.left.isDown ) {
			newX = lastX - SPEED;
			this.facing = FacingSide.LEFT;
		}
		
		if ( this.knockbackTime > 0 ) {
			if ( this.knockBackDir == FacingSide.DOWN ) {
				newY += 4;
			}
			if ( this.knockBackDir == FacingSide.UP ) {
				newY -= 4;
			}
			if ( this.knockBackDir == FacingSide.LEFT ) {
				newX -= 4;
			}
			if ( this.knockBackDir == FacingSide.RIGHT ) {
				newX += 4;
			}
			this.knockbackTime -= deltaTime;
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
					this.actualFrame = 0;
				}
				this.setPosition( lastX, newY );
				if ( this.isColliding( en ) ) {
					newY = lastY;
					this.actualFrame = 0;
				}
			}
			this.setPosition( newX, newY );
			this.actualFrame += 0.2;
			if ( this.actualFrame > 3 ) {
				this.actualFrame = 0;
			}
		} else {
			this.actualFrame = 0;
		}
		
		if ( this.damageTime > 0 ) {
			this.damageTime -= deltaTime;
		}
	}
	
	public void hurt( Entity en, int damage ) {
		this.health -= damage;
		this.damageTime  = 200;
		Sound.punch.play();
		if ( this.health <= 0 ) {
			this.gameScreen.getGame().switchToDeath();
		}
		int diffX = this.getPosX() - en.getPosX();
		int diffY = this.getPosY() - en.getPosY();
		if ( diffX < 0 ) {
			this.knockBackDir = FacingSide.LEFT;
		} else {
			this.knockBackDir = FacingSide.RIGHT;
		}
		if ( diffY < 0 ) {
			this.knockBackDir = FacingSide.UP;
		} else if ( diffY > 0 ){
			this.knockBackDir = FacingSide.DOWN;
		}
		this.knockbackTime = 200;
	}
	
	public int getHealth() {
		return this.health;
	}
}
