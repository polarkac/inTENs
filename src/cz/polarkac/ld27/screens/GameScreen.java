package cz.polarkac.ld27.screens;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

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
	
	public GameScreen( Game game ) {
		super( game );
		this.pl =  new Player( this, 0, 0 );
		this.cam.setPosition( this.pl.getPosX(), this.pl.getPosY() );
		this.entites.add( pl ); 
		this.entites.add( new Tree( 80, 20 ) );
		this.entites.add( new Tree( 100, 100 ) );
		this.entites.add( new Tree( 200, 200 ) );
		//this.entites.add( new Stone( 250, 250) );
		this.entites.add( new Gate( this, 250, 250) );
		
		for ( int a = 0; a < 20; a++ ) {
			this.entites.add( new Flower( 0, 0 ) );
		}
		for ( int a = 0; a < 3; a++ ) {
			//this.entites.add( new Enemy( this, 30 + a * 30, 30 + a * 60 ) );
		}
		
		for ( int y = -4; y < 600 / 32; y++ ) {
			for ( int x = -4; x < 800 / 32; x++ ) {
				if ( y >= 0 && y <= 10 && x >= 0 && x <= 10 ) {
					this.groundEntites.add( new Grass( x * 64, y * 64 ) );
				} else {
					this.groundEntites.add( new Water( x * 64, y * 64 ) );
				}
			}
		}
	}

	@Override
	public void render( Graphics2D g ) {	
		for ( Entity e : this.groundEntites ) {
			e.render( g, this.cam.getPosX(), this.cam.getPosY() );
		}
		Collections.sort( this.entites, this.comparator );
		for ( Entity e : this.entites ) {
			e.render( g, this.cam.getPosX(), this.cam.getPosY() );
		}
		
		for ( int a = this.pl.getHealth() / 20; a > 0; a-- ) {
			g.drawImage( Bitmap.spritesheet.getSubimage( 6 * 16, 0, 16, 16 ), 795 - a * 50, -10, 64, 64, null );
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
