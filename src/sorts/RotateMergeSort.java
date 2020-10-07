package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class RotateMergeSort extends Sort {

	public RotateMergeSort() {
		this.setLabels("Rotate Merge Sort", "Rotate Merge");
	}

	private void grailSwap(int[] arr, int a, int b) {
		this.writes.swap(arr, a, b, 1, true, false);

	}

	private void grailMultiSwap(int[] arr, int a, int b, int swapsLeft) {
		while (swapsLeft != 0) {
			this.grailSwap(arr, a++, b++);
			swapsLeft--;
		}
	}

	protected void grailRotate(int[] array, int pos, int lenA, int lenB) {
		while (lenA != 0 && lenB != 0) {
			if (lenA <= lenB) {
				this.grailMultiSwap(array, pos, pos + lenA, lenA);
				pos += lenA;
				lenB -= lenA;
			} else {
				this.grailMultiSwap(array, pos + (lenA - lenB), pos + lenA, lenB);
				lenA -= lenB;
			}

		}
	}

	// boolean argument determines direction
	private int grailBinSearch(int[] arr, int pos, int len, int keyPos, boolean isLeft) {
		int left = -1, right = len;
		while (left < right - 1) {
			int mid = left + ((right - left) >> 1);
			if (isLeft) {
				if (this.reads.compare(arr[pos + mid], arr[keyPos]) >= 0) {
					right = mid;
				} else {
					left = mid;
				}
			} else {
				if (this.reads.compare(arr[pos + mid], arr[keyPos]) > 0) {
					right = mid;
				} else
					left = mid;
			}
			this.highlights.markArray(1, pos + mid);

		}

		return right;
	}

	// cost: min(len1, len2)^2 + max(len1, len2)
	private void grailMergeWithoutBuffer(int[] arr, int pos, int len1, int len2) {
		if (len1 < len2) {
			while (len1 != 0) {
				// Binary Search left
				int loc = this.grailBinSearch(arr, pos + len1, len2, pos, true);
				if (loc != 0) {
					this.grailRotate(arr, pos, len1, loc);
					pos += loc;
					len2 -= loc;
				}
				if (len2 == 0)
					break;
				do {
					pos++;
					len1--;

				} while (len1 != 0 && this.reads.compare(arr[pos], arr[pos + len1]) <= 0);

			}
		} else {
			while (len2 != 0) {
				// Binary Search right
				int loc = this.grailBinSearch(arr, pos, len1, pos + (len1 + len2 - 1), false);
				if (loc != len1) {
					this.grailRotate(arr, pos + loc, len1 - loc, len2);
					len1 = loc;
				}
				if (len1 == 0)
					break;
				do {
					len2--;

				} while (len2 != 0 && this.reads.compare(arr[pos + len1 - 1], arr[pos + len1 + len2 - 1]) <= 0);

			}
		}
	}

	private void grailInPlaceMerge(int[] arr, int pos, int len1, int len2) {
		if (len1 < 3 || len2 < 3) {
			this.grailMergeWithoutBuffer(arr, pos, len1, len2);
			return;
		}

		int midpoint;
		if (len1 < len2)
			midpoint = len1 + len2 / 2;
		else
			midpoint = len1 / 2;

		// Left binary search
		int len1Left, len1Right;
		len1Left = len1Right = this.grailBinSearch(arr, pos, len1, pos + midpoint, true);

		// Right binary search
		if (len1Right < len1 && this.reads.compare(arr[pos + len1Right], arr[pos + midpoint]) == 0) {
			len1Right = this.grailBinSearch(arr, pos + len1Left, len1 - len1Left, pos + midpoint, false) + len1Left;
		}

		int len2Left, len2Right;
		len2Left = len2Right = this.grailBinSearch(arr, pos + len1, len2, pos + midpoint, true);

		if (len2Right < len2 && this.reads.compare(arr[pos + len1 + len2Right], arr[pos + midpoint]) == 0) {
			len2Right = this.grailBinSearch(arr, pos + len1 + len2Left, len2 - len2Left, pos + midpoint, false)
					+ len2Left;
		}

		if (len1Left == len1Right)
			this.grailRotate(arr, pos + len1Right, len1 - len1Right, len2Right);
		else {
			this.grailRotate(arr, pos + len1Left, len1 - len1Left, len2Left);

			if (len2Right != len2Left) {
				this.grailRotate(arr, pos + (len1Right + len2Left), len1 - len1Right, len2Right - len2Left);
			}
		}

		this.grailInPlaceMerge(arr, pos + (len1Right + len2Right), len1 - len1Right, len2 - len2Right);
		this.grailInPlaceMerge(arr, pos, len1Left, len2Left);

	}

	protected void grailInPlaceMergeSort(int[] arr, int start, int len) {
		for (int dist = start + 1; dist < len; dist += 2) {
			if (this.reads.compare(arr[dist - 1], arr[dist]) > 0)
				this.grailSwap(arr, dist - 1, dist);

		}
		for (int part = 2; part < len; part *= 2) {
			int left = start, right = len - 2 * part;

			while (left <= right) {
				this.grailInPlaceMerge(arr, left, part, part);
				left += 2 * part;

			}

			int rest = len - left;
			if (rest > part)
				this.grailInPlaceMerge(arr, left, part, rest - part);

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

		this.grailInPlaceMergeSort(this.canvas.getArray(), 0, this.canvas.count);
		this.highlights.clearAllMarks();

	}

}
