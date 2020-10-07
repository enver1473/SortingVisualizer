package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class ShellSort extends Sort {
	// final private int[] OriginalGaps = { 2048, 1024, 512, 256, 128, 64, 32, 16,
	// 8, 4, 2, 1 };
	// final private int[] PowTwoPlusOneGaps = { 2049, 1025, 513, 257, 129, 65, 33,
	// 17, 9, 5, 3, 1 };
	// final private int[] PowTwoMinusOneGaps = { 4095, 2047, 1023, 511, 255, 127,
	// 63, 31, 15, 7, 3, 1 };
	// final private int[] ThreeSmoothGaps = { 3888, 3456, 3072, 2916, 2592, 2304,
	// 2187, 2048, 1944, 1728, 1536, 1458,
	// 1296, 1152, 1024, 972, 864, 768, 729, 648, 576, 512, 486, 432, 384, 324, 288,
	// 256, 243, 216, 192, 162, 144,
	// 128, 108, 96, 81, 72, 64, 54, 48, 36, 32, 27, 24, 18, 16, 12, 9, 8, 6, 4, 3,
	// 2, 1 };
	// final private int[] SedgewickIncerpiGaps = { 1968, 861, 336, 112, 48, 21, 7,
	// 3, 1 };
	// final private int[] SedgewickGaps = { 1073, 281, 77, 23, 8, 1 };
	// final private int[] OddEvenSedgewickGaps = { 3905, 2161, 929, 505, 209, 109,
	// 41, 19, 5, 1 };
	// final private int[] GonnetBaezaYatesGaps = { 1861, 846, 384, 174, 79, 36, 16,
	// 7, 3, 1 };
	// final private int[] TokudaGaps = { 2660, 1182, 525, 233, 103, 46, 20, 9, 4, 1
	// };
	// final private int[] CiuraGaps = { 1750, 701, 301, 132, 57, 23, 10, 4, 1 };
	final private int[] PowersOfThreeGaps = { 3280, 1093, 364, 121, 40, 13, 4, 1 };
	final private int[] ExtendedCiuraGaps = { 8861, 3938, 1750, 701, 301, 132, 57, 23, 10, 4, 1 };

	public ShellSort() {
		this.setLabels("Shell Sort", "Shell");
	}

	private void shellSort(int[] array, int length) {
		int incs[] = ExtendedCiuraGaps;

		for (int k = 0; k < incs.length; k++) {
			if (incs == PowersOfThreeGaps) {
				if (incs[k] < length / 3) {
					for (int h = incs[k], i = h; i < length; i++) {

						int v = array[i];
						int j = i;

						this.highlights.markArray(1, j);
						this.highlights.markArray(2, j - h);

						while (j >= h && this.reads.compare(array[j - h], v) == 1) {
							this.writes.write(array, j, array[j - h], 0.25);
							j -= h;

							this.highlights.markArray(1, j);

							if (j - h >= 0) {
								this.highlights.markArray(2, j - h);
							} else {
								this.highlights.clearMark(2);
							}

						}
						this.writes.write(array, j, v, 0.25);

					}
				}
			} else {
				if (incs[k] < length) {
					for (int h = incs[k], i = h; i < length; i++) {

						int v = array[i];
						int j = i;

						this.highlights.markArray(1, j);
						this.highlights.markArray(2, j - h);

						while (j >= h && this.reads.compare(array[j - h], v) == 1) {
							this.writes.write(array, j, array[j - h], 0.25);
							j -= h;

							this.highlights.markArray(1, j);

							if (j - h >= 0) {
								this.highlights.markArray(2, j - h);
							} else {
								this.highlights.clearMark(2);
							}

						}
						this.writes.write(array, j, v, 0.25);

					}
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
		this.shellSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}
