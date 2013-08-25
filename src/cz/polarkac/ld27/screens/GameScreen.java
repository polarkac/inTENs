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
	public static int mapSizeX = 800 / 24 * 64;
	public static int mapSizeY = 600 / 24 * 64;
	private Random rnd = new Random();

	public GameScreen( Game game ) {
		super( game );
		this.pl =  new Player( this, this.getRandomPos( GameScreen.mapSizeX - 128 ), this.getRandomPos( GameScreen.mapSizeY - 128 ) );
		this.cam.setPosition( this.pl.getPosX(), this.pl.getPosY() );
		this.entites.add( pl ); 
		int xx = this.getRandomPos( GameScreen.mapSizeX );
		int yy = this.getRandomPos( GameScreen.mapSizeY );
		this.entites.add( new Stone( xx, yy - 64) );
		this.entites.add( new Stone( xx + 64, yy) );
		this.entites.add( new Stone( xx - 64, yy) );
		this.entites.add( new Gate( this, xx, yy ) );
		
		for ( int a = 0; a < 50; a++ ) {
			this.entites.add( new Flower( this.getRandomPos( GameScreen.mapSizeX - 128 ), this.getRandomPos( GameScreen.mapSizeY - 128 ) ) );
			this.entites.add( new Tree( this.getRandomPos( GameScreen.mapSizeX - 128 ), this.getRandomPos( GameScreen.mapSizeY - 128 ) ) );
		}
		for ( int a = 0; a < 30; a++ ) {
			this.entites.add( new Enemy( this, this.getRandomPos( GameScreen.mapSizeX - 128 ), this.getRandomPos( GameScreen.mapSizeY - 128 ) ) );
		}
		
		for ( int y = 0; y < 600 / 24; y++ ) {
			for ( int x = 0; x < 800 / 24; x++ ) {
				if ( y >= 1 && y <= 600 / 24 - 2 && x >= 1 && x <= 800 / 24 - 2 ) {
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
		Rectangle entR = new Rectangle();
		entR.width = 64;
		entR.height = 64;
		for ( Entity e : this.groundEntites ) {
			entR.x = e.getPosX() - this.cam.getPosX();
			entR.y = e.getPosY() - this.cam.getPosY();
			if ( screenR.intersects( entR ) ) {
				e.render( g, this.cam.getPosX(), this.cam.getPosY() );
			}
		}

		for ( Entity e : this.entites ) {
			entR.x = e.getPosX() - this.cam.getPosX();
			entR.y = e.getPosY() - this.cam.getPosY();
			if ( screenR.intersects( entR ) ) {
				e.render( g, this.cam.getPosX(), this.cam.getPosY() );
			}
		}

		for ( int a = this.pl.getHealth() / 20; a > 0; a-- ) {
			g.drawImage( Bitmap.hearth, 795 - a * 50, -10, 64, 64, null );
		}

		if ( this.reincarnationTime <= 5000 ) {
			String msg = "Reincarnation in " + (this.reincarnationTime / 1000)
					+ " seconds.";
			g.setColor( Color.RED );
			g.setFont( this.font );
			int width = g.getFontMetrics( this.font ).stringWidth( msg );
			g.drawString( msg, 800 / 2 - width / 2, 80 );
		}
	}

	@Override
	public void update( int deltaTime ) {
		Rectangle screenR = new Rectangle( 0, 0, 800, 600 );
		Rectangle entR = new Rectangle();
		entR.width = 64;
		entR.height = 64;
		for ( Entity e : this.groundEntites ) {
			entR.x = e.getPosX() - this.cam.getPosX();
			entR.y = e.getPosY() - this.cam.getPosY();
			if ( screenR.intersects( entR ) ) {
				e.update( deltaTime );
			}
		}
		Collections.sort( this.entites, this.comparator );
		for ( Entity e : this.entites ) {
			e.update( deltaTime );
		}
		this.cam.setPosition( this.pl.getPosX(), this.pl.getPosY() );

		this.reincarnationTime -= deltaTime;
		if ( this.reincarnationTime <= 0 ) {
			Random rnd = new Random();
			boolean isReincarnated = false;
			do {
				Entity en = this.entites
						.get( rnd.nextInt( this.entites.size() ) );
				isReincarnated = en.reincarnate( this.pl );
			} while ( !isReincarnated );
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
	
	private int getRandomPos( int max ) {
		int randomPos = 0;
		do {
			randomPos = this.rnd.nextInt( max );
		} while ( randomPos < 64 );
		return randomPos;
	}
}

class Camera {
	private int cameraX;
	private int cameraY;

	public void setPosition( int x, int y ) {
		this.cameraX = x - 400 + 8;
		this.cameraY = y - 300 + 8;
		if ( this.cameraX < 0 ) {
			this.cameraX = 0;
		}
		if ( this.cameraY < 0 ) {
			this.cameraY = 0;
		}
		if ( this.cameraX > GameScreen.mapSizeX - 800 ) {
			this.cameraX = GameScreen.mapSizeX - 800;
		}
		if ( this.cameraY > GameScreen.mapSizeY - 600 ) {
			this.cameraY = GameScreen.mapSizeY - 600;
		}
	}

	public int getPosX() {
		return this.cameraX;
	}

	public int getPosY() {
		return this.cameraY;
	}
}
