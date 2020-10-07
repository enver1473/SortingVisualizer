package util;

import gui.CanvasPane;

public class Delays {
	public static final double MULTIPLIER_FACTOR = 3000.0;

	private volatile double delayFactor;
	public static volatile double userDelayFactor = 1;
	
	private volatile double delay;
	private volatile double currentDelay;

	public volatile boolean skipped = false;
	
	private CanvasPane canvas;
    private Sounds sounds;
	
	public Delays(CanvasPane canvas) {
		this.delay = 1.0;
		this.skipped = false;
		this.delayFactor = 1;
		this.canvas = canvas;
		this.sounds = canvas.getSounds();
	}
	
	public void toggleSkipped(boolean skipped) {
		this.skipped = skipped;
	}
	
	public void setDelay(double ms) {
		this.delay = ms;
	}
	
	public void setDelayFactor(double factor) {
		this.delayFactor = factor;
	}
	
	public double getDelayFactor() {
		return this.delayFactor;
	}
	
	public void sleep(double ms) {
		if (ms <= 0) return;
		
		this.delay += (ms * this.delayFactor * userDelayFactor);
        this.currentDelay = (ms * this.delayFactor * userDelayFactor);
        
        this.sounds.changeNoteDelayAndFilter((int) this.currentDelay);

		try {
			if (!this.skipped) {
				this.canvas.toggleDisplayVisuals(false);
				while (this.delay >= 1) {
					Thread.sleep(1);
					this.delay--;
				}
				this.canvas.toggleDisplayVisuals(true);
			} else {
				this.delay = 0.0;
			}
		} catch (InterruptedException ex) {
            ex.printStackTrace();
		}
		
        this.currentDelay = 0;
	}
}
