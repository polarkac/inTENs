package cz.polarkac.ld27.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cz.polarkac.ld27.Game;
import cz.polarkac.ld27.ZComparator;
import cz.polarkac.ld27.entities.Enemy;
import cz.polarkac.ld27.entities.Entity;
import cz.polarkac.ld27.entities.Flower;
import cz.polarkac.ld27.entities.Gate;
import cz.polarkac.ld27.entities.Grass;
import cz.polarkac.ld27.entities.Player;
import cz.polarkac.ld27.entities.Stone;
import cz.polarkac.ld27.entities.Tree;
import cz.polarkac.ld27.entities.Water;
import cz.polarkac.ld27.graphics.Bitmap;

public class GameScreen extends Screen {
	private ArrayList<Entity> entites = new ArrayList<Entity>();
	private ZComparator comparator = new ZComparator();
	private Camera cam = new Camera();
	private Player pl;
	private ArrayList<Entity> groundEntites = new ArrayList<Entity>();
	private int reincarnationTime = 10000;
	private Font font = new Font( Font.SANS_SERIF, Font.PLAIN, 36 );
	
	public GameScreen( Game game ) {
		super( game );
		this.pl =  new Player( this, 350, 350 );
		this.cam.setPosition( this.pl.getPosX(), this.pl.getPosY() );
		this.entites.add( pl ); 
		this.entites.add( new Tree( 80, 20 ) );
		this.entites.add( new Tree( 100, 100 ) );
		this.entites.add( new Tree( 200, 200 ) );
		this.entites.add( new Stone( 250 + 64, 250) );
		this.entites.add( new Gate( this, 250, 250) );
		
		for ( int a = 0; a < 20; a++ ) {
			this.entites.add( new Flower( 0, 0 ) );
		}
		for ( int a = 0; a < 3; a++ ) {
			this.entites.add( new Enemy( this, 30 + a * 30, 30 + a * 60 ) );
		}
		
		for ( int y = -20; y < 600 / 32; y++ ) {
			for ( int x = -20; x < 800 / 32; x++ ) {
				if ( y >= -19 && y <= 16 && x >= -19 && x <= 23 ) {
					this.groundEntites.add( new Grass( x * 64, y * 64 ) );
				} else {
					this.groundEntites.add( new Water( x * 64, y * 64 ) );
				}
			}
		}
	}

	@Override
	public void render( Graphics2D g ) {	
		Rectangle screenR = new Rectangle( 0, 0, 800, 600 );
		Rectangle entR = new Rectangle( );
		entR.width = 64;
		entR.height = 64;
		for ( Entity e : this.groundEntites ) {
			entR.x = e.getPosX() - this.cam.getPosX();
			entR.y = e.getPosY() - this.cam.getPosY();
			if ( screenR.intersects( entR ) ) {
				e.render( g, this.cam.getPosX(), this.cam.getPosY() );
			}
		}
		Collections.sort( this.entites, this.comparator );
		for ( Entity e : this.entites ) {
			entR.x = e.getPosX() - this.cam.getPosX();
			entR.y = e.getPosY() - this.cam.getPosY();
			if ( screenR.intersects( entR ) ) {
				e.render( g, this.cam.getPosX(), this.cam.getPosY() );
			}
		}
		
		for ( int a = this.pl.getHealth() / 20; a > 0; a-- ) {
			g.drawImage( Bitmap.spritesheet.getSubimage( 6 * 16, 0, 16, 16 ), 795 - a * 50, -10, 64, 64, null );
		}
		
		if ( this.reincarnationTime <= 5000 ) {
			String msg = "Reincarnation in " + ( this.reincarnationTime / 1000 ) + " seconds.";
			g.setColor( Color.RED );
			g.setFont( this.font );
			int width = g.getFontMetrics( this.font ).stringWidth( msg );
			g.drawString( msg, 800 / 2 - width / 2, 80 );
		}
	}

	@Override
	public void update( int deltaTime ) {
		for ( Entity e : this.groundEntites ) {
			e.update( deltaTime );
		}
		for ( Entity e : this.entites ) {
			e.update( deltaTime );
		}
		this.cam.setPosition( this.pl.getPosX(), this.pl.getPosY() );
		
		this.reincarnationTime -= deltaTime;
		if ( this.reincarnationTime <= 0 ) {
			Random rnd = new Random();
			boolean isReincarnated = false;
			do {
				Entity en = this.entites.get( rnd.nextInt( this.entites.size() ) );
				isReincarnated = en.reincarnate( this.pl );
			} while( !isReincarnated );
			this.reincarnationTime = 10000;
		}
	}
	
	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> tempArr = new ArrayList<Entity>();
		tempArr.addAll( this.entites );
		tempArr.addAll( this.groundEntites );
		return tempArr;
	}
	
	public Player getPlayer() {
		return this.pl;
	}
}

class Camera {
	private int cameraX;
	private int cameraY;
	
	public void setPosition( int x, int y ) {
		this.cameraX = x - 400 + 8;
		this.cameraY = y - 300 + 8;
	}
	
	public int getPosX() {
		return this.cameraX;
	}
	
	public int getPosY() {
		return this.cameraY;
	}
}
