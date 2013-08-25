package cz.polarkac.ld27;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		JPanel panel = new JPanel( new BorderLayout() );
		Game game = new Game();
		panel.add( game, BorderLayout.CENTER );

		this.setContentPane( panel );
		this.pack();
		this.setResizable( false );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		game.start();
	}

	public static void main( String[] args ) {
		MainWindow w = new MainWindow();
		w.setVisible( true );
	}
}
