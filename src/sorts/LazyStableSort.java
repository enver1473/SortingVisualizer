package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class LazyStableSort extends Sort {

	public LazyStableSort() {
		this.setLabels("Lazy Stable Sort", "Lazy Stable");
	}

	private void lazyStableSort(int[] arr, int pos, int len) {
		for (int dist = 1; dist < len; dist += 2) {
			if (this.reads.compare(arr[pos + dist - 1], arr[pos + dist]) > 0) {
				this.writes.swap(arr, pos + (dist - 1), pos + dist, 1, true, false);
			}
			this.highlights.markArray(3, pos + dist - 1);
			this.highlights.markArray(4, pos + dist);

		}
		this.highlights.clearMark(3);
		this.highlights.clearMark(4);

		for (int part = 2; part < len; part *= 2) {
			int left = 0;
			int right = len - 2 * part;

			while (left <= right) {
				this.grailMergeWithoutBuffer(arr, pos + left, part, part);
				left += 2 * part;

			}

			int rest = len - left;
			if (rest > part) {
				this.grailMergeWithoutBuffer(arr, pos + left, part, rest - part);
			}

		}
	}

	private void multiSwap(int[] arr, int a, int b, int swapsLeft) {
		while (swapsLeft != 0) {
			this.writes.swap(arr, a++, b++, 1, true, false);
			swapsLeft--;

		}
	}

	private void rotate(int[] array, int pos, int lenA, int lenB) {
		while (lenA != 0 && lenB != 0) {
			if (lenA <= lenB) {
				this.multiSwap(array, pos, pos + lenA, lenA);
				pos += lenA;
				lenB -= lenA;
			} else {
				this.multiSwap(array, pos + (lenA - lenB), pos + lenA, lenB);
				lenA -= lenB;
			}

		}
	}

	private int binSearch(int[] arr, int pos, int len, int keyPos, boolean isLeft) {
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

	private void grailMergeWithoutBuffer(int[] arr, int pos, int len1, int len2) {
		if (len1 < len2) {
			while (len1 != 0) {
				// Binary Search left
				int loc = this.binSearch(arr, pos + len1, len2, pos, true);
				if (loc != 0) {
					this.rotate(arr, pos, len1, loc);
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
				int loc = this.binSearch(arr, pos, len1, pos + (len1 + len2 - 1), false);
				if (loc != len1) {
					this.rotate(arr, pos + loc, len1 - loc, len2);
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
		this.lazyStableSort(this.canvas.getArray(), 0, this.canvas.count);
		this.highlights.clearAllMarks();

	}

}
