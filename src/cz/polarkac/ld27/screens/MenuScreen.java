package cz.polarkac.ld27.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import cz.polarkac.ld27.Game;
import cz.polarkac.ld27.KeyboardListener;

public class MenuScreen extends Screen {
	private Screen parentScreen;
	private String[] menuOptions = { "Play game", "About", "Quit" };
	private int selectedOption = 0;
	private int time = 0;
	private Font font;

	public MenuScreen( Game game, Screen parentScreen ) {
		super( game );
		this.parentScreen = parentScreen;
		this.font = new Font( Font.SANS_SERIF, Font.PLAIN, 36 );
	}

	@Override
	public void render( Graphics2D g ) {
		this.parentScreen.render( g );
		g.setColor( new Color( 0, 0, 0, 75 ) );
		g.fillRect( 50, 50, 700, 500 );
		
		g.setColor( Color.BLUE );
		g.setFont( this.font );
		for ( int a = 0; a < this.menuOptions.length; a++ ) {
			g.drawString( this.menuOptions[a], 65, 80 + a * 50 );
		}
		g.setColor( Color.GREEN );
		g.setStroke( new BasicStroke( 3 ) );
		g.drawLine( 65, 86 + this.selectedOption * 50, 600, 86 + this.selectedOption * 50 );
		g.drawLine( 65, 86 + this.selectedOption * 50, 45, 70 + this.selectedOption * 50 );
	}

	@Override
	public void update( int deltaTime ) {
		KeyboardListener k = this.getGame().getKeyboardListener();
		if ( time <= 0 ) {
			if ( k.down.isDown ) {
				this.selectedOption++;
				time = 150;
			}
			if ( k.up.isDown ) {
				this.selectedOption--;
				time = 150;
			}
		} else {
			time -= deltaTime;
		}
		
		if ( k.enter.isDown ) {
			switch ( this.selectedOption ) {
			case 0:
				this.getGame().resumeGame(); 
				this.menuOptions[0] = "Resume game"; break;
			case 1:
				this.getGame().switchToAbout(); 
				this.menuOptions[0] = "Resume game"; break;
			case 2:
				this.getGame().stop(); break;
			}
		}
		
		if ( selectedOption > this.menuOptions.length - 1 ) {
			this.selectedOption = 0;
		} else if ( selectedOption < 0 ) {
			this.selectedOption = this.menuOptions.length - 1;
		}
	}
	
	public void setGameScreen( GameScreen game ) {
		this.parentScreen = game;
	}

}
