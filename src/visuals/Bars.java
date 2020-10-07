package visuals;

import java.awt.Color;

import gui.CanvasPane;
import gui.Main;
import templates.Visual;
import util.Delays;
import util.Highlights;

public class Bars extends Visual {	
	public Bars(Main Main, CanvasPane canvas, Delays delays, Highlights highlights, int id, boolean colored, boolean variableLength) {
		super(Main, canvas, delays, highlights, id);
		
		this.colored = colored;
		this.variableLength = variableLength;
		
		if (variableLength) {
			if (colored) {
				this.setName("Color Bar Graph");
			} else {
				this.setName("Bar Graph");
			}
		} else {
			this.setName("Color Bars");
		}
	}

	@Override
	public void drawVisual(int[] array, Main mainFrame, Highlights Highlights) {
		this.mainRender.setColor(Color.BLACK);
    	this.mainRender.fillRect(0, 0, this.canvas.cw, this.canvas.ch);
    	
    	for (int i = 0; i < this.mainFrame.getCurrentLength(); i++) {
    		boolean highlighted = false;
			if (this.highlights.containsPosition(i)) {
				highlighted = true;
				if (this.colored) {
					this.mainRender.setColor(Color.WHITE);
				} else {
					this.mainRender.setColor(Color.RED);
				}
			} else {
	    		if (this.colored) {
	        		this.mainRender.setColor(getIntColor(array[i], this.mainFrame.getCurrentLength()));
	    		} else {
	        		this.mainRender.setColor(Color.WHITE);
	    		}
			}
			
			int x;
			int y;
			int width = (int)(this.barWidth * (i + 1)) - (int)(this.barWidth * (i));
			int height;
			
			// This makes sure that the highlighted bar is visible
			// unless blocked (in rare enough occasions) by one of the bars after it if their x and width happen to be the same
			if (width < 1 && highlighted) {
				width = 1;
			}
			
			if (variableLength) {
	    		x = (int)(i * this.barWidth) + leftOffset;
	    		y = (int)(this.canvas.ch - bottomOffset - array[i] * ((this.canvas.ch - totalYOffset) / (double) this.canvas.count));
	    		height = (int)(array[i] * ((this.canvas.ch - totalYOffset) / (double)this.canvas.count));
			} else {
	    		x = (int)(i * this.barWidth);
	    		y = 0;
	    		height = this.canvas.ch;
			}
    		this.mainRender.fillRect(x, y, width, height);
    	}
	}
}
