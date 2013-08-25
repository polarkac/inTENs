package cz.polarkac.ld27.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static final Sound punch = new Sound( "/punch.wav" );
	
	private AudioClip clip;
	
	public Sound( String filename ) {
		this.clip =  Applet.newAudioClip( Sound.class.getResource( filename ) );
	}
	
	public void play() {
		new Thread() {
			public void run() {
				clip.play();
			}
		}.start();
	}

}
