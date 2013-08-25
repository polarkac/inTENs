package cz.polarkac.ld27.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import cz.polarkac.ld27.graphics.Bitmap;

public class Tree extends CollableEntity {
	private BufferedImage treeImage = Bitmap.spritesheet.getSubimage( 16, 0, 16, 16 );
	
	public Tree( int x, int y ) {
		super( x, y );
		this.xOffset = 20;
		this.yOffset = 50;
		this.setBoundingBox( new Rectangle( x + this.xOffset, y + this.yOffset, 64 - this.xOffset * 2, 64 - this.yOffset ) );
	}
	
	@Override
	public void render( Graphics2D g, int cameraX, int cameraY ) {
		g.drawImage( this.treeImage, this.getPosX() - cameraX, this.getPosY() - cameraY, 64, 64, null );
	}

	@Override
	public void update( int deltaTime ) {
		
	}

}
