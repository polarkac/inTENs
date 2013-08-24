package cz.polarkac.ld27.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cz.polarkac.ld27.Game;
import cz.polarkac.ld27.KeyboardListener;

public class AboutScreen extends Screen {
	private String[] aboutStrings = { "Programming: Lukas Pohlreich", "Graphics: Lukas Pohlreich", 
			"Game was made for Ludum Dare 27." };
	private Font font;
	private int activeColor;
	private int activeString;
	private ArrayList<Color> colors;
	private int time;
	private FontMetrics metrics = null;
	
	public AboutScreen( Game game ) {
		super( game );
		this.font = new Font( Font.SANS_SERIF, Font.PLAIN, 36 );
		this.activeColor = 0;
		this.colors = new ArrayList<Color>();
		for ( int a = 50; a > 0; a-- ) {
			this.colors.add( new Color( 255, 255, 255, 255 / 50 * a) );
		}
	}

	@Override
	public void render( Graphics2D g ) {
		if ( this.metrics == null) {
			this.metrics = g.getFontMetrics( this.font );
		}
		g.setFont( this.font );
		g.setColor( Color.GREEN );
		g.drawString( "10 SECONDS", 800 / 2 - 140, 36 );
		
		int width = this.metrics.stringWidth( this.aboutStrings[this.activeString] );
		g.setColor( this.colors.get( this.activeColor ) );
		g.drawString( this.aboutStrings[this.activeString], 800 / 2 - width / 2, 300 );
	}

	@Override
	public void update( int deltaTime ) {
		time += deltaTime;
		KeyboardListener k = this.getGame().getKeyboardListener();
		if ( k.escape.isDown ) {
			this.getGame().resumeGame();
		}
		
		if ( time >= 60 ) {
			time = 0;
			this.activeColor++;
			if ( this.activeColor >= 50 ) {
				this.activeColor = 0;
				this.activeString++;
				if ( this.activeString > this.aboutStrings.length - 1 ) {
					this.activeString = 0;
				}
			}
		}
	}

}
