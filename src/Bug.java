import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Bug {
	public double result;
	final int stepSize = 10;
	public boolean dead = false;
	public static int deadCount = 0;
	Random r;
	final int mutationChance = 10;
	int dna[] = null;

	int x = 300;
	int y = 300;

	int xDest = 305;
	int yDest = 305;

	int dnaIndex = 0;

	Color color;

	Ellipse2D oval = new Ellipse2D.Float(x, y, 10, 10);

	public Bug(int[] dna) {
		this.dna = dna;
		r = new Random();
		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}

	void Move(int direction) {
		switch (direction) {
		case 0:
			y--;
			break;
		case 1:
			x++;
			break;
		case 2:
			y++;
			break;
		case 3:
			x--;
			break;
		}
		dnaIndex++;
		if (dnaIndex > BugFactory.dnaLength - 1) {
			dead = true;
			Destroy();
		}
		oval.setFrame(x, y, 10, 10);
	}

	void Destroy() {
		result = Math.hypot(MainPanel.food.x - x, MainPanel.food.y - y);
	}

	void Update() {
		if (!dead) {
			Move(dna[dnaIndex]);
		}
	}

	void Draw(Graphics2D g) {
		g.setColor(color);
		g.fill(oval);
		g.setColor(Color.white);
	}

	void Reset() {
		dead = false;
		x = 300;
		y = 300;
		dnaIndex = 0;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i : dna)
			s += i;
		return s + "\n";
	}

	public int[] Mutate() {
		int[] newDna = new int[BugFactory.dnaLength];
		for (int i = 0; i < BugFactory.dnaLength; i++) {
			int chance = r.nextInt(100);
			if (chance <= mutationChance)
				newDna[i] = r.nextInt(4);
			else
				newDna[i] = dna[i];
		}
		return newDna;
	}
}
