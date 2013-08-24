package cz.polarkac.ld27.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import cz.polarkac.ld27.Game;
import cz.polarkac.ld27.KeyboardListener;

public class DeathScreen extends Screen {
	private Screen parentScreen;
	private Font font;
	private FontMetrics metrics = null;
	private String[] options = { "Play again", "Quit game" };
	protected String deathMsg = "You were killed!";
	private int selectedOption = 0;
	private int lastKeyTime = 0;

	public DeathScreen( Game game, Screen parent ) {
		super( game );
		this.parentScreen = parent;
		this.font = new Font( Font.SANS_SERIF, Font.PLAIN, 56 );
	}

	@Override
	public void render( Graphics2D g ) {
		this.parentScreen.render( g );
		
		if ( this.metrics == null ) {
			this.metrics = g.getFontMetrics( this.font );
		}
		
		g.setFont( this.font );
		int width = this.metrics.stringWidth( this.deathMsg );
		g.setColor( new Color( 255, 0, 0, 50 ) );
		g.fillRect( 800 / 2 - width / 2 - 20, 43, width + 20, 70 );
		g.setColor( Color.WHITE );
		g.drawString( this.deathMsg, 800 / 2 - width / 2, 96 );
		
		for ( int a = 0; a < 2; a++ ) {
			int w = this.metrics.stringWidth( this.options[a] );
			g.drawString( this.options[a], 800 / 2 - w / 2, 300 + a * 80 );
		}
		g.setColor( Color.GRAY );
		g.setStroke( new BasicStroke( 3 ) );
		int w = this.metrics.stringWidth( this.options[this.selectedOption] );
		g.drawLine( 800 / 2 - w / 2, 305 + this.selectedOption * 80, 800 / 2 + w / 2, 305 + this.selectedOption * 80 );
		
	}

	@Override
	public void update( int deltaTime ) {
		KeyboardListener k = this.getGame().getKeyboardListener();
		if ( k.up.isDown && this.lastKeyTime == 0 ) {
			this.selectedOption--;
			this.lastKeyTime = 150;
		}
		if ( k.down.isDown && this.lastKeyTime == 0 ) {
			this.selectedOption++;
			this.lastKeyTime = 150;
		}
		
		if ( this.lastKeyTime > 0 ) {
			this.lastKeyTime -= deltaTime;
		} else {
			this.lastKeyTime = 0;
		}		
		
		if ( this.selectedOption > 1 ) {
			this.selectedOption = 0;
		} else if ( this.selectedOption < 0 ){
			this.selectedOption = 1;
		}
		
		if ( k.enter.isDown ) {
			switch ( this.selectedOption ) {
			case 0:
				this.getGame().restart(); break;
			case 1: 
				this.getGame().stop(); break;
			}
		}
	}
	
	public void setGameScreen( GameScreen gameScreen ) {
		this.parentScreen = gameScreen;
	}

}
