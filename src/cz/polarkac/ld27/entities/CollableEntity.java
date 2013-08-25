package cz.polarkac.ld27.entities;

import java.awt.Rectangle;

public abstract class CollableEntity extends Entity {
	private Rectangle boundingBox;
	protected int xOffset = 0;
	protected int yOffset = 0;
	
	public CollableEntity( int x, int y ) {
		super( x, y );
	}
	
	public boolean isColliding( CollableEntity e ) {
		if ( e.getBoundingBox().intersects( this.boundingBox ) ) { 
			return true;
		}
		
		return false;
	}
	
	public boolean isColliding( CollableEntity e, int x, int y ) {
		Rectangle r = new Rectangle( this.boundingBox );
		r.x -= x;
		r.y -= y;
		r.width += x;
		r.height += y;
		
		if ( e.getBoundingBox().intersects( r ) ) { 
			return true;
		}
		
		return false;
	}
	
	public void setBoundingBox( Rectangle r ) {
		this.boundingBox = r;
	}
	
	public Rectangle getBoundingBox() {
		return this.boundingBox;
	}
	
	public void setPosition( int x, int y ) {
		this.setPosX( x );
		this.setPosY( y );
		this.getBoundingBox().x = x + this.xOffset;
		this.getBoundingBox().y = y + this.yOffset;
	}
}
