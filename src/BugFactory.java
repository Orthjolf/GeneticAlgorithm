import java.util.ArrayList;
import java.util.Random;

public class BugFactory {
	Random r;

	int amount;
	public final static int dnaLength = 700;

	ArrayList<Bug> currentGeneration = new ArrayList<>();

	public BugFactory(int amount) {
		this.amount = amount;
	}

	ArrayList<Bug> GenerateSelection() {
		for (int j = 0; j < amount; j++) {
			r = new Random();
			int[] dna = new int[dnaLength];
			int t;
			for (int i = 0; i < dnaLength; i++) {
				t = r.nextInt(4);
				if (i != 0) {
					if (t == dna[i - 1])
						t = r.nextInt(4);					
				}
				dna[i] = t;
			}
			currentGeneration.add(new Bug(dna));
		}
		return currentGeneration;
	}

	ArrayList<Bug> Mutate(Bug bug) {
		bug.Reset();
		currentGeneration = new ArrayList<>();
		currentGeneration.add(bug);
		for (int i = 0; i < amount - 1; i++) {
			int[] newDna = bug.Mutate();
			currentGeneration.add(new Bug(newDna));
		}
		return currentGeneration;
	}
}
