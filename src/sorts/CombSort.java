package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class CombSort extends Sort {
	public static final double SHRINK_FACTOR = 1.3;

	public CombSort() {
		super();

		this.setLabels("Comb Sort", "Comb");
	}

	public void combSort(int[] array, int length, boolean hybrid) {
		// insertSorter = new InsertionSort(this.arrayVisualizer);
		double shrink = SHRINK_FACTOR;

		boolean swapped = false;
		int gap = length;

		while ((gap > 1) || swapped) {
			this.highlights.clearMark(1);

			if (gap > 1) {
				gap = (int) (gap / shrink);
			}

			swapped = false;

			for (int i = 0; (gap + i) < length; ++i) {
				/*
				 * if(hybrid && (gap <= Math.min(8, length * 0.03125))) { gap = 0;
				 * 
				 * insertSorter.customInsertSort(array, 0, length, 0.5, false); break; }
				 */

				if (this.reads.compare(array[i], array[i + gap]) == 1) {
					this.writes.swap(array, i, i + gap, 0.5);
					swapped = true;
				}
				this.highlights.markArray(0, i);
				this.highlights.markArray(1, i + gap);

				this.delays.sleep(0.2);
				this.highlights.clearMark(0);

			}
		}
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
		this.combSort(this.canvas.getArray(), this.canvas.count, false);
		this.highlights.clearAllMarks();

	}
}