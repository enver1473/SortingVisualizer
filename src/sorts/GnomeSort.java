package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class GnomeSort extends Sort {
	public GnomeSort() {
		super();

		this.setLabels("Gnome Sort", "Gnome");
	}

	public void gnomeSort(int[] array, int length) {
		for (int i = 1; i < length;) {
			if (this.reads.compare(array[i], array[i - 1]) >= 0) {
				i++;
				this.highlights.markArray(1, i);
				this.delays.sleep(0.04);
			} else {
				this.writes.swap(array, i, i - 1, 0.02);

				this.highlights.clearAllMarks();

				if (i > 1) {
					i--;
					this.highlights.markArray(1, i);
					this.delays.sleep(0.02);
				}
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
		gnomeSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}