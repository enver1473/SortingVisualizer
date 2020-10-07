package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;

final public class SmarterCocktailSort extends Sort {
	public SmarterCocktailSort() {
		super();

		this.setLabels("More Optimized Cocktail Shaker Sort", "Smarter Cocktail");
	}

	public void smarterCocktailShaker(int[] array, int length) {
		for (int start = 0, end = length - 1; start < end;) {
			int consecSorted = 1;
			for (int i = start; i < end; i++) {
				if (this.reads.compare(array[i], array[i + 1]) == 1) {
					this.writes.swap(array, i, i + 1, 0.075, true, false);
					consecSorted = 1;
				} else
					consecSorted++;

				this.highlights.markArray(1, i);
				this.highlights.markArray(2, i + 1);
				this.delays.sleep(0.025);
			}
			end -= consecSorted;

			consecSorted = 1;
			for (int i = end; i > start; i--) {
				if (this.reads.compare(array[i - 1], array[i]) == 1) {
					this.writes.swap(array, i - 1, i, 0.075, true, false);
					consecSorted = 1;
				} else
					consecSorted++;

				this.highlights.markArray(1, i - 1);
				this.highlights.markArray(2, i);
				this.delays.sleep(0.025);
			}
			start += consecSorted;
		}
	}

	@Override
	public void run(Main mainFrame, CanvasPane canvas, util.Reads reads, util.Writes writes, util.Delays delays,
			util.Highlights highlights) {
		this.mainFrame = mainFrame;
		this.canvas = canvas;
		this.reads = reads;
		this.writes = writes;
		this.delays = delays;
		this.highlights = highlights;

		this.canvas.getShuffles().shuffle();
		smarterCocktailShaker(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}