package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;

public abstract class Entity {
	private int posX;
	private int posY;
	
	public Entity( int x, int y ) {
		this.posX = x;
		this.posY = y;
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public void setPosX( int x ) {
		this.posX = x;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public void setPosY( int y ) {
		this.posY = y;
	}
	
	public boolean reincarnate( Player pl ) {
		return false;
	}
	
	public abstract void render( Graphics2D g, int cameraX, int cameraY );
	public abstract void update( int deltaTime );
}
