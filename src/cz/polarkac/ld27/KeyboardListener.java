package cz.polarkac.ld27;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

	public class Key {
		public boolean isDown = false;
	}
	
	public Key left = new Key();
	public Key right = new Key();
	public Key up = new Key();
	public Key down = new Key();
	public Key space = new Key();
	public Key escape = new Key();
	public Key enter = new Key();
	
	public void toggleKey( KeyEvent key, boolean isPressed ) {
		Key settedKey = null;
		switch( key.getKeyCode() ) {
		case KeyEvent.VK_UP:
			settedKey = this.up; break;
		case KeyEvent.VK_DOWN:
			settedKey = this.down; break;
		case KeyEvent.VK_RIGHT:
			settedKey = this.right; break;
		case KeyEvent.VK_LEFT:
			settedKey = this.left; break;
		case KeyEvent.VK_SPACE:
			settedKey = this.space; break;
		case KeyEvent.VK_ESCAPE:
			settedKey = this.escape; break;
		case KeyEvent.VK_ENTER:
			settedKey = this.enter; break;
		default:
			return;			
		}
		
		settedKey.isDown = isPressed;		
	}
	
	@Override
	public void keyPressed( KeyEvent key ) {
		this.toggleKey( key, true );
	}

	@Override
	public void keyReleased( KeyEvent key ) {
		this.toggleKey( key, false );
	}

	@Override
	public void keyTyped( KeyEvent arg0 ) {}

}
