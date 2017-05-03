import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6478341416311163042L;

	public static void main(String[] args) {
		Main window = new Main();
		// window.setUndecorated(true);
		window.add(new MainPanel());
		//window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(screen.width / 4, screen.height / 4);
		window.setVisible(true);

	}

	public Main() {
		super("Genetic algorithm test");
	}
}