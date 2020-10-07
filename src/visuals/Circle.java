package visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Stroke;

import gui.CanvasPane;
import gui.Main;
import templates.Visual;
import util.Delays;
import util.Highlights;

public class Circle extends Visual {
    final private static double CIRC_HEIGHT_RATIO = (9/8.0843731432) * (16/9d);
    final private static double CIRC_WIDTH_RATIO = (16/8.5843731432) * (16/9d);
    final private static int HEIGHT_DIFFERENCE = 96; // default: 96
    final private static int WIDTH_DIFFERENCE = 64; // default: 64

	public Circle(Main Main, CanvasPane canvas, Delays delays, Highlights highlights, int id, boolean colored, boolean variableLength, boolean dots) {
		super(Main, canvas, delays, highlights, id);
		
		this.colored = colored;
		this.variableLength = variableLength;
		this.dots = dots;
		
		if (variableLength) {
			if (colored) {
				if (dots) {
					this.setName("Color disparity dots");
				} else {
					this.setName("Color disparity circle");
				}
			} else {
				if (dots) {
					this.setName("Disparity dots");
				} else {
					this.setName("Disparity circle");
				}
			}
		} else {
			if (colored) {
				this.setName("Color circle");
			}
		}
	}
    
    private static double getSinOfDegrees(double d, int halfCirc) {
        return Math.sin((d * Math.PI) / halfCirc);
    }
    
    private static double getCosOfDegrees(double d, int halfCirc) {
        return Math.cos((d * Math.PI) / halfCirc);
    }
    
    private int halfCircle() {
    	return this.canvas.getCurrentLength() / 2;
    }

	@Override
	public void drawVisual(int[] array, Main mainFrame, Highlights Highlights) {
        this.mainRender.setColor(Color.BLACK);
    	this.mainRender.fillRect(0, 0, this.canvas.cw, this.canvas.ch);
    	
        for (int i = 0; i < this.canvas.getCurrentLength(); i++) {
            boolean highlighted = false;
            
        	if (colored) {
                this.mainRender.setColor(getIntColor(array[i], this.canvas.getCurrentLength()));
        	} else {
                this.mainRender.setColor(Color.WHITE);
        	}
        	
        	if (dots) {
    			highlighted = this.highlights.containsPosition(i);
        	} else {
                colorMarkedBars(this.canvas.getLogBaseTwoOfLength(), i, this.highlights, this.mainRender, colored, colored, false);
        	}
        	
            if (variableLength) {
            	double distanceFactor = calculateDistance(i, array[i]);
    			if (dots) {
    				int x;
    				int y;
    				int dotWidth = this.canvas.getDotSize();
    				int dotHeight = dotWidth;
    				if (colored) { // Color Disparity Dots
    					x = this.canvas.canvasHalfWidth()  + (int) (Circle.getSinOfDegrees(i, this.halfCircle()) * ((this.canvas.getWidth()  - WIDTH_DIFFERENCE) / CIRC_WIDTH_RATIO) * distanceFactor);
    					y = this.canvas.canvasHalfHeight() - (int) (Circle.getCosOfDegrees(i, this.halfCircle()) * ((this.canvas.getHeight() - HEIGHT_DIFFERENCE) / CIRC_HEIGHT_RATIO) * distanceFactor);
    					
    					this.mainRender.fillRect(x, y, dotWidth, dotHeight);
    					
    					this.mainRender.setColor(Color.WHITE);
    				} else { // Disparity Dots
    					x = this.canvas.canvasHalfWidth()  + (int) (Circle.getSinOfDegrees(i, this.halfCircle()) * ((this.canvas.getWidth()  - WIDTH_DIFFERENCE) / CIRC_WIDTH_RATIO) * distanceFactor);
    					y = this.canvas.canvasHalfHeight() - (int) (Circle.getCosOfDegrees(i, this.halfCircle()) * ((this.canvas.getHeight() - HEIGHT_DIFFERENCE) / CIRC_HEIGHT_RATIO) * distanceFactor);
    					
    					this.mainRender.fillRect(x, y, dotWidth, dotHeight);

    					this.mainRender.setColor(Color.RED);
    				}
    				
					if (highlighted) {
						int strokeThickness = 6;
						
						int markerWidth  = strokeThickness * 2 + dotWidth * 2;
						int markerHeight = markerWidth;

						int markerX = x -  dotWidth / 2 - strokeThickness;
						int markerY = y - dotHeight / 2 - strokeThickness;
						
						this.mainRender.setStroke(new BasicStroke(strokeThickness));
						this.mainRender.drawRect(markerX, markerY, markerWidth, markerHeight);
						this.mainRender.setStroke(defaultStroke());
					}
    			} else {
    	            Polygon p = new Polygon();

    	            p.addPoint(this.canvas.canvasHalfWidth(),
    	            		this.canvas.canvasHalfHeight());

					p.addPoint(this.canvas.canvasHalfWidth()  + (int) (Circle.getSinOfDegrees(i, this.halfCircle()) * ((this.canvas.getWidth()  - WIDTH_DIFFERENCE) / CIRC_WIDTH_RATIO) * distanceFactor),
							   this.canvas.canvasHalfHeight() - (int) (Circle.getCosOfDegrees(i, this.halfCircle()) * ((this.canvas.getHeight() - HEIGHT_DIFFERENCE) / CIRC_HEIGHT_RATIO) * distanceFactor));

					p.addPoint(this.canvas.canvasHalfWidth()  + (int) (Circle.getSinOfDegrees(i + 1, this.halfCircle()) * ((this.canvas.getWidth()  - WIDTH_DIFFERENCE) / CIRC_WIDTH_RATIO) * distanceFactor),
							   this.canvas.canvasHalfHeight() - (int) (Circle.getCosOfDegrees(i + 1, this.halfCircle()) * ((this.canvas.getHeight() - HEIGHT_DIFFERENCE) / CIRC_HEIGHT_RATIO) * distanceFactor));
					
    	            this.mainRender.fillPolygon(p);
    			}
    		} else {
	            Polygon p = new Polygon();

	            p.addPoint(this.canvas.canvasHalfWidth(),
	            		this.canvas.canvasHalfHeight());

	            p.addPoint(this.canvas.canvasHalfWidth()  + (int) (Circle.getSinOfDegrees(i, this.halfCircle()) * ((this.canvas.getWidth()  - WIDTH_DIFFERENCE) / CIRC_WIDTH_RATIO)),
	            		this.canvas.canvasHalfHeight() - (int) (Circle.getCosOfDegrees(i, this.halfCircle()) * ((this.canvas.getHeight() - HEIGHT_DIFFERENCE) / CIRC_HEIGHT_RATIO)));

	            p.addPoint(this.canvas.canvasHalfWidth()  + (int) (Circle.getSinOfDegrees(i + 1, this.halfCircle()) * ((this.canvas.getWidth()  - WIDTH_DIFFERENCE) / CIRC_WIDTH_RATIO)),
	            		this.canvas.canvasHalfHeight() - (int) (Circle.getCosOfDegrees(i + 1, this.halfCircle()) * ((this.canvas.getHeight() - HEIGHT_DIFFERENCE) / CIRC_HEIGHT_RATIO)));

	            this.mainRender.fillPolygon(p);
    		}
            

            /*
            if(ArrayVisualizer.pointerActive()) {
                if(Highlights.containsPosition(i)) {
                    if(ArrayVisualizer.analysisEnabled()) {
                        this.extraRender.setColor(Color.GRAY);
                    }
                    else {
                        this.extraRender.setColor(Color.WHITE);
                    }

                    //Create new Polygon for the pointer
                    Polygon pointer = new Polygon();

                    //Calculate radians
                    double degrees = 360 * ((double) i / ArrayVisualizer.getCurrentLength());
                    double radians = Math.toRadians(degrees);

                    int pointerWidthRatio  = (int) (ArrayVisualizer.windowHalfWidth()  / CIRC_WIDTH_RATIO);
                    int pointerHeightRatio = (int) (ArrayVisualizer.windowHalfHeight() / CIRC_HEIGHT_RATIO);

                    //First step: draw a triangle
                    int[] pointerXValues = {pointerWidthRatio - 10,
                                            pointerWidthRatio,
                                            pointerWidthRatio + 10};

                    int[] pointerYValues = {pointerHeightRatio - 10,
                                            pointerHeightRatio + 10,
                                            pointerHeightRatio - 10};

                    //Second step: rotate triangle (https://en.wikipedia.org/wiki/Rotation_matrix)
                    for(int j = 0; j < pointerXValues.length; j++) {
                        double x = pointerXValues[j] - pointerWidthRatio;
                        double y = pointerYValues[j] - pointerHeightRatio;

                        pointerXValues[j] = (int) (pointerWidthRatio
                                                + x*Math.cos(radians)
                                                - y*Math.sin(radians));
                        pointerYValues[j] = (int) (pointerHeightRatio
                                                + x*Math.sin(radians)
                                                + y*Math.cos(radians));
                    }

                    for(int j = 0; j < pointerXValues.length; j++) {
                        pointer.addPoint(pointerXValues[j], pointerYValues[j]);
                    }

                    this.extraRender.fillPolygon(pointer);                        
                }
            }
            else */ 
        }
	}

	private double calculateDistance(int index, int number) {
		return (1 + Math.cos((Math.PI * (number - index)) / (this.canvas.count * 0.5))) * 0.5;
	}
	
	private Stroke defaultStroke() {
		return new BasicStroke((float) (3 * this.canvas.cw / 1280d));
	}
	
}
