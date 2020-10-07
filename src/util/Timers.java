package util;

import java.text.DecimalFormat;

import gui.CanvasPane;

public class Timers {
	public long sortTime;
	public double sortTimeMillis;
	private long currentStartTime;
	
	// private CanvasPane canvas;
	private DecimalFormat formatter;
	
	public Timers(CanvasPane canvas) {
		this.formatter = new DecimalFormat("#.##");
		this.sortTime = 0;
		this.sortTimeMillis = 0d;
		this.currentStartTime = 0;
	}
	
	public void startTimer() {
		this.currentStartTime = System.nanoTime();
	}
	
	public void stopTimer(long endTime) {
		this.sortTime += (endTime - this.currentStartTime);
		this.sortTimeMillis = this.sortTime / 1_000_000d;
	}
	
	public String getCurrentSortTime() {
		if (this.sortTimeMillis < 1000) {
			return "Estimated real time: " + this.formatter.format(this.sortTimeMillis) + " ms";
		} else {
			return "Estimated real time: " + this.formatter.format((int) (this.sortTimeMillis / 1000)) + "s : " + this.formatter.format(this.sortTimeMillis % 1000) + " ms";
		}
	}
	
	public void resetTimers() {
		this.sortTime = 0;
		this.sortTimeMillis = 0d;
		this.currentStartTime = 0;
	}

}
