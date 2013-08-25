package cz.polarkac.ld27;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import cz.polarkac.ld27.screens.AboutScreen;
import cz.polarkac.ld27.screens.DeathScreen;
import cz.polarkac.ld27.screens.GameScreen;
import cz.polarkac.ld27.screens.MenuScreen;
import cz.polarkac.ld27.screens.Screen;
import cz.polarkac.ld27.screens.WinnerScreen;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	private Screen activeScreen;
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private AboutScreen aboutScreen;
	private KeyboardListener keyboardListener;
	private int lastScreenChange = 0;
	private DeathScreen deathScreen;
	private WinnerScreen winnerScreen;

	public Game() {
		this.setSize( 800, 600 );
		this.gameScreen = new GameScreen( this );
		this.menuScreen = new MenuScreen( this, this.gameScreen );
		this.deathScreen = new DeathScreen( this, this.gameScreen );
		this.winnerScreen = new WinnerScreen( this, this.gameScreen );
		this.aboutScreen = new AboutScreen( this );
		this.activeScreen = this.gameScreen;
		this.keyboardListener = new KeyboardListener();
		this.addKeyListener( this.keyboardListener );
		this.requestFocusInWindow();
	}

	@Override
	public void run() {
		this.requestFocus();
		
		int FPS = 0;
		int updates = 0;

		long timer = System.nanoTime();
		double unprocessedUpdates = 0;
		double nsPerUpdate = Math.pow( 10, 9 ) / 60.0;
		long lastUpdate = System.currentTimeMillis();

		while ( this.isRunning ) {
			long now = System.nanoTime();
			unprocessedUpdates += (now - timer) / nsPerUpdate;
			timer = now;

			while ( unprocessedUpdates >= 1 ) {
				this.update( (int) (System.currentTimeMillis() - lastUpdate) );
				lastUpdate = System.currentTimeMillis();
				updates++;
				unprocessedUpdates--;
			}

			FPS++;
			render();

			if ( updates >= 60 ) {
				System.out.println( "FPS: " + FPS + ", updates: " + updates );
				FPS = 0;
				updates = 0;
			}
		}
	}

	private void render() {
		BufferStrategy buffer = this.getBufferStrategy();
		if ( buffer == null ) {
			this.createBufferStrategy( 3 );
			return;
		}

		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		
		g.setColor( Color.BLACK );
		g.fillRect( 0, 0, 800, 600 );

		this.activeScreen.render( g );

		g.dispose();
		buffer.show();

	}

	private void update( int deltaTime ) {
		if ( !this.hasFocus() ) {
			this.activeScreen = this.menuScreen;
		}
		
		if ( this.keyboardListener.escape.isDown && this.lastScreenChange <= 0) {
			if ( this.activeScreen == this.gameScreen ) {
				this.activeScreen = this.menuScreen;
				this.menuScreen.setGameScreen( this.gameScreen );
			} else {
				this.activeScreen = this.gameScreen;
			}
			this.lastScreenChange = 150;
		}
		if ( this.lastScreenChange > 0 )
			this.lastScreenChange -= deltaTime;
			
		this.activeScreen.update( deltaTime );
	}

	public KeyboardListener getKeyboardListener() {
		return this.keyboardListener;
	}

	public void start() {
		this.isRunning = true;
		Thread t = new Thread( this );
		t.start();
	}

	public void stop() {
		this.isRunning = false;
		System.exit( 0 );
	}

	public void resumeGame() {
		this.activeScreen = this.gameScreen;
	}
	
	public void switchToAbout() {
		this.activeScreen = this.aboutScreen;
	}

	public void switchToDeath() {
		this.activeScreen = this.deathScreen;
	}

	public void restart() {
		this.gameScreen = new GameScreen( this );
		this.deathScreen.setGameScreen( this.gameScreen );
		this.winnerScreen.setGameScreen( this.gameScreen );
		this.activeScreen = this.gameScreen;
	}

	public void switchToWiner() {
		this.activeScreen = this.winnerScreen;
	}
}
