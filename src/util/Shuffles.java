package util;

import gui.CanvasPane;
import templates.Sort;

public class Shuffles {
	public static final int RANDOM = 0;
	public static final int ALREADY_SORTED = 1;
	public static final int ALMOST_SORTED = 2;
	public static final int REVERSED = 3;
	public static final int ALMOST_REVERSED = 4;
	public static final int SCRAMBLED_TAIL = 5;

	public static int currentShuffle = RANDOM;
	public static int shuffleCount = 6;

	public static int[] shuffles;
	
	private int[] array;

	private CanvasPane canvas;
	private Timers timers;
	private Delays delays;
	private Reads reads;
	private Writes writes;
	private Highlights highlights;
	private Sounds sounds;

	public Shuffles(CanvasPane canvas) {
		this.canvas = canvas;
		
		this.array = canvas.getArray();
		
		this.timers = canvas.getTimers();
		this.delays = canvas.getDelays();
		this.reads = canvas.getReads();
		this.writes = canvas.getWrites();
		this.highlights = canvas.getHighlights();
		this.sounds = canvas.getSounds();
	}

	public static void initShuffles() {
		shuffles = new int[shuffleCount];
		for (int i = 0; i < shuffleCount; i++) {
			shuffles[i] = i;
		}
	}

	public static String getShuffleName(int shuffleId) {
		switch (shuffleId) {
		case 0:
			return "Random";
		case 1:
			return "Already sorted";
		case 2:
			return "Almost sorted";
		case 3:
			return "Reversed";
		case 4:
			return "Almost reversed";
		case 5:
			return "Scrambled tail";
		default:
			return "Undefined";
		}
	}

	public void shuffle() {
		setup();
		
		// We have to save the name of the current sort because the caption is set to shuffling, 
		// the user might select a different sort while the shuffle is happening and that name will then be displayed, which is incorrect
		String current = Sort.getCurrentSort();
		
		switch (currentShuffle) {
		case 0:
			random();
			break;
		case 1:
			break;
		case 2:
			almostSorted(true);
			break;
		case 3:
			reversed(true);
			break;
		case 4:
			almostReversed();
			break;
		case 5:
			scrambledTail();
			break;
		default:
			random();
			break;
		}
		this.canvas.setCaption(current);
		reset();
	}

	public void setup() {
		this.delays.setDelay(1.0);
		this.delays.setDelayFactor((double) (Delays.MULTIPLIER_FACTOR / (this.canvas.getCurrentLength() * 3)));
		this.writes.clearStats();
		this.reads.clearStats();
		this.timers.resetTimers();
	}

	public void reset() {
		this.writes.clearStats();
		this.reads.clearStats();
		this.timers.resetTimers();
		this.highlights.clearAllMarks();
		this.canvas.repaint();
		this.sounds.toggleSounds(false);
		this.sounds.mute();
		double previousDelayFactor = this.delays.getDelayFactor();
		this.delays.setDelayFactor(1.0);
		this.delays.sleep(500);
		this.sounds.toggleSounds(true);
		this.delays.setDelayFactor(previousDelayFactor);
	}

	public void random() {
		for (int i = 0; i < this.canvas.getCurrentLength(); i++) {
			int rndIdx = (int) (Math.random() * (this.canvas.getCurrentLength() - i) + i);
			this.writes.swap(this.array, i, rndIdx, 1);
			this.highlights.markArray(0, i);
			this.highlights.markArray(1, rndIdx);
			this.canvas.repaint();
			// this.delays.sleep(1);
			this.highlights.clearMark(0);
		}
	}

	public void almostSorted(boolean standaloneShuffle) {
		double ms = standaloneShuffle ? 10 : 2;

		for (int i = 0; i < this.canvas.getCurrentLength(); i++) {
			if (Math.random() > 0.9) {
				int rndIdx = (int) (Math.random() * (this.canvas.getCurrentLength() - i) + i);
				this.writes.swap(this.array, i, rndIdx, ms);
				this.highlights.markArray(0, i);
				this.highlights.markArray(1, rndIdx);
				this.canvas.repaint();
				// this.delays.sleep(10);
				this.highlights.clearMark(0);
			}
		}
	}

	public void reversed(boolean standaloneShuffle) {
		double ms = standaloneShuffle ? 2 : 1.5;

		for (int i = 0, j = this.canvas.getCurrentLength() - 1; i < j; i++, j--) {
			this.writes.swap(this.array, i, j, ms);
			this.highlights.markArray(0, i);
			this.highlights.markArray(1, j);
			this.canvas.repaint();
			// this.delays.sleep(2);
			this.highlights.clearMark(0);
		}
	}

	public void almostReversed() {
		reversed(false);
		almostSorted(false);
	}

	public void scrambledTail() {
		int currentLen = this.canvas.getCurrentLength();
		int[] array = this.canvas.getArray();

		int j = 0, k = currentLen;
		int[] temp = new int[currentLen];

		for (int i = 0; j < k; i++) {
			this.highlights.markArray(2, i);
			if (Math.random() < 1 / 8d)
				this.writes.write(temp, --k, array[i], 0, true, true);
			else
				this.writes.write(temp, j++, array[i], 0, true, true);
			this.delays.sleep(0.5);

			this.canvas.repaint();
		}
		this.highlights.clearMark(2);

		for (int i = 0; i < currentLen; i++) {
			this.writes.write(array, i, temp[i], 0.5, true, false);

			this.canvas.repaint();
		}

		for (int i = k; i < currentLen; i++) {
			int randomIndex = (int) (Math.random() * (currentLen - i)) + i;
			this.writes.swap(array, i, randomIndex, 1, true, false);

			this.canvas.repaint();
		}
	}
}
