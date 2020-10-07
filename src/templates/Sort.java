package templates;

import gui.CanvasPane;
import gui.Main;
import sorts.AndreySort;
import sorts.BinaryGnomeSort;
import sorts.BinaryInsertionSort;
import sorts.BottomUpMergeSort;
import sorts.BubbleSort;
import sorts.CircleSort;
import sorts.CocktailShakerSort;
import sorts.CombSort;
import sorts.DoubleSelectionSort;
import sorts.DualPivotQuickSort;
import sorts.GnomeSort;
import sorts.GrailSort;
import sorts.HeapSort;
import sorts.InsertionSort;
import sorts.LazyStableSort;
import sorts.MergeSort;
import sorts.OddEvenSort;
import sorts.QuickSortLL;
import sorts.QuickSortLR;
import sorts.RadixLSD;
import sorts.RotateMergeSort;
import sorts.SelectionSort;
import sorts.ShellSort;
import sorts.SmartBubbleSort;
import sorts.SmartCocktailSort;
import sorts.SmartGnomeSort;
import sorts.SmarterBubbleSort;
import sorts.SmarterCocktailSort;
import sorts.StoogeSort;
import sorts.TimSort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public abstract class Sort {
	public static volatile Sort currentSort;
	public static volatile int currentSortIndex = 0;
	public static volatile boolean sorting;
	public static volatile Sort[] sorts;
	public static volatile int sortCount;
	public static volatile boolean initialized = false;

	protected Main mainFrame;
	protected CanvasPane canvas;
	protected Reads reads;
	protected Writes writes;
	protected Delays delays;
	protected Highlights highlights;

	protected String selectLabel;
	protected String displayLabel;

	public Sort() {
		Sort.currentSort = null;
	}

	public void setLabels(String displayLabel, String selectLabel) {
		this.displayLabel = displayLabel;
		this.selectLabel = selectLabel;
	}

	public String getSelectLabel() {
		return this.selectLabel;
	}

	public String getDisplayLabel() {
		return this.displayLabel;
	}

	public static String getCurrentSort() {
		if (currentSort != null) {
			return currentSort.getDisplayLabel();
		} else {
			return "Select sort...";
		}
	}

	public abstract void run(Main mainFrame, CanvasPane canvas, Reads reads, Writes writes, Delays delays,
			Highlights highlights);

	// Static methods:

	public static void initSorts() {
		int idx = 0;
		sorts = new Sort[100];
		sorts[idx++] = new AndreySort();
		sorts[idx++] = new BinaryGnomeSort();
		sorts[idx++] = new BinaryInsertionSort();
		sorts[idx++] = new BottomUpMergeSort();
		sorts[idx++] = new BubbleSort();
		sorts[idx++] = new CircleSort();
		sorts[idx++] = new CocktailShakerSort();
		sorts[idx++] = new CombSort();
		sorts[idx++] = new DoubleSelectionSort();
		sorts[idx++] = new DualPivotQuickSort();
		sorts[idx++] = new GnomeSort();
		sorts[idx++] = new GrailSort();
		sorts[idx++] = new HeapSort(true);
		sorts[idx++] = new HeapSort(false);
		sorts[idx++] = new InsertionSort();
		sorts[idx++] = new LazyStableSort();
		sorts[idx++] = new MergeSort();
		sorts[idx++] = new OddEvenSort();
		sorts[idx++] = new QuickSortLL();
		sorts[idx++] = new QuickSortLR();
		sorts[idx++] = new RadixLSD(4);
		sorts[idx++] = new RotateMergeSort();
		sorts[idx++] = new SelectionSort();
		sorts[idx++] = new ShellSort();
		sorts[idx++] = new SmartBubbleSort();
		sorts[idx++] = new SmartCocktailSort();
		sorts[idx++] = new SmarterBubbleSort();
		sorts[idx++] = new SmarterCocktailSort();
		sorts[idx++] = new SmartGnomeSort();
		sorts[idx++] = new StoogeSort();
		sorts[idx++] = new TimSort();
		sortCount = idx;

		initialized = true;
	}

	public static void runSort(Main mainFrame, CanvasPane canvas, Reads reads, Writes writes, Delays delays,
			Highlights highlights) {
		currentSort.run(mainFrame, canvas, reads, writes, delays, highlights);
	}

	public static void setCurrentSort(String sort) {
		for (int i = 0; i < sortCount; i++) {
			if (sorts[i].getSelectLabel().equals(sort)) {
				currentSort = sorts[i];
				break;
			}
		}
	}

}
