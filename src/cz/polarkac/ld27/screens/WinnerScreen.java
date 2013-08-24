package cz.polarkac.ld27.screens;

import cz.polarkac.ld27.Game;

public class WinnerScreen extends DeathScreen {

	public WinnerScreen( Game game, Screen parent ) {
		super( game, parent );
		this.deathMsg = "You win!";
	}

}
