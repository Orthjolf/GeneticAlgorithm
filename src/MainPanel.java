
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Runnable{

	public static int WIDTH = 820;
	public static int HEIGHT = 600;
	public static char keyValue;
	public static Point p = new Point();
	public static Rectangle r;
	public static int mouseButton;
	public static int mouseState;
	public static int xStart;
	public static int yStart;

	int tX = 50;
	int tY = HEIGHT;
	Rectangle tR = new Rectangle(10, 10, WIDTH - 20, HEIGHT - 20);

	// mouse states
	public static final int MOVED = 0;
	public static final int PRESSED = 0;
	public static final int RELEASED = 0;

	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	Font font = new Font("", Font.BOLD, 14);

	// image
	private BufferedImage image;
	private Graphics2D g;

	// game state manager

	// other
	private boolean recording = false;
	private int recordingCount = 0;
	private boolean screenshot;

	public MainPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	int kBugs = 100;
	BugFactory bf = new BugFactory(kBugs);
	ArrayList<Bug> bugs;

	public static Rectangle food = new Rectangle(300, 30, 25, 25);

	public static Rectangle r1 = new Rectangle(280, 60, 70, 20);

	private void init() throws Exception {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setFont(font);
		running = true;
		bugs = bf.GenerateSelection();
	}

	public void run() {
		try {
			init();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long start;
		long elapsed;
		long wait;

		// game loop
		while (running) {

			start = System.nanoTime();
			// System.out.println(p);

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	int generation = 1;
	double bestResult = 900;
	Bug bestBug;
	int kAlive = kBugs;

	private void update() {
		for (Bug bug : bugs) {
			bug.Update();
			if (bug.dead) {
				kAlive--;
				if (bug.result < bestResult) {
					bestResult = bug.result;
					bestBug = bug;
				}
			}
		}
		if (kAlive <= 0) {
			bugs = bf.Mutate(bestBug);
			kAlive = kBugs;
			generation++;
		}
	}

	private void draw() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (Bug bug : bugs)
			bug.Draw(g);

		g.setColor(Color.RED);
	//g.fill(food);
		g.drawString("Best result: " + (int) bestResult, 20, 20);
		g.drawString("Generation: " + generation, 20, 35);
		g.setColor(Color.BLACK);
		g.fill(r1);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
		if (screenshot) {
			screenshot = false;
			try {
				java.io.File out = new java.io.File("screenshot " + System.nanoTime() + ".gif");
				javax.imageio.ImageIO.write(image, "gif", out);
			} catch (Exception e) {
			}
		}

		if (!recording)
			return;
		try {
			java.io.File out = new java.io.File("C:\\out\\frame" + recordingCount + ".gif");
			javax.imageio.ImageIO.write(image, "gif", out);
			recordingCount++;
		} catch (Exception e) {
		}
	}

}
