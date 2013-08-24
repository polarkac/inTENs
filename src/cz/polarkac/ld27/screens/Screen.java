package cz.polarkac.ld27.screens;

import java.awt.Graphics2D;

import cz.polarkac.ld27.Game;

public abstract class Screen {
	private Game game;
	
	public Screen( Game game ) {
		this.game = game;
	}

	public abstract void render( Graphics2D g );
	public abstract void update( int deltaTime );
	
	public Game getGame() {
		return this.game;
	}


}
