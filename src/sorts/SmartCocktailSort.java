package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class SmartCocktailSort extends Sort {
	public SmartCocktailSort() {
		super();

		this.setLabels("Optimized Cocktail Shaker Sort", "Smart Cocktail Shaker");
	}

	private void smartCocktailShaker(int[] array, int start, int end, double sleep) {
		int i = start;
		while (i < ((end / 2) + start)) {
			boolean sorted = true;

			for (int j = i; j < end + start - i - 1; j++) {
				if (this.reads.compare(array[j], array[j + 1]) == 1) {
					this.writes.swap(array, j, j + 1, sleep);
					sorted = false;
				}

				this.highlights.markArray(1, j);
				this.highlights.markArray(2, j + 1);

				this.delays.sleep(sleep / 2);

			}
			this.highlights.clearAllMarks();

			for (int j = end + start - i - 1; j > i; j--) {
				if (this.reads.compare(array[j], array[j - 1]) == -1) {
					this.writes.swap(array, j, j - 1, sleep);
					sorted = false;
				}

				this.highlights.markArray(1, j);
				this.highlights.markArray(2, j - 1);

				this.delays.sleep(sleep / 2);

			}
			this.highlights.clearAllMarks();

			if (sorted)
				break;
			else
				i++;
		}
	}

	public void customSort(int[] array, int start, int end) {
		this.smartCocktailShaker(array, start, end, 1);
	}

	@Override
	public void run(Main mainFrame, CanvasPane canvas, Reads reads, Writes writes, Delays delays,
			Highlights highlights) {
		this.mainFrame = mainFrame;
		this.canvas = canvas;
		this.reads = reads;
		this.writes = writes;
		this.delays = delays;
		this.highlights = highlights;

		this.canvas.getShuffles().shuffle();
		smartCocktailShaker(this.canvas.getArray(), 0, this.canvas.count, 0.1);
		this.highlights.clearAllMarks();

	}
}