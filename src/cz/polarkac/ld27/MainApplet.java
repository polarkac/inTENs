package cz.polarkac.ld27;

import java.applet.Applet;
import java.awt.BorderLayout;

public class MainApplet extends Applet {
	private static final long serialVersionUID = 1L;
	
	private Game game = new Game();
	
	public void init() {
		this.setLayout( new BorderLayout() );
		this.add( game, BorderLayout.CENTER );
	}
	
	public void start() {
		game.start();
	}
	
	public void stop() {
		game.stop();
	}

}
