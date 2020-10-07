package templates;

import java.awt.Color;
import java.awt.Graphics2D;

import gui.CanvasPane;
import gui.Main;
import util.Delays;
import util.Highlights;
import visuals.Bars;
import visuals.Circle;

public abstract class Visual {
	public static final int              BAR_GRAPH = 0;
	public static final int      COLORED_BAR_GRAPH = 1;
	public static final int             COLOR_BARS = 2;
	public static final int           COLOR_CIRCLE = 3;
	public static final int       DISPARITY_CIRCLE = 4;
	public static final int COLOR_DISPARITY_CIRCLE = 5;
	public static final int         DISPARITY_DOTS = 6;
	public static final int   COLOR_DISPARITY_DOTS = 7;

	public static final int visualCount = 8;
	public static int currentVisual = BAR_GRAPH;
	private static boolean initialized = false;
	
	public static Visual[] visuals;
	
	public static int leftOffset = 5;
	public static int rightOffset = 5;
	public static int totalXOffset = leftOffset + rightOffset;
	
    public static int topOffset = 55;
    public static int bottomOffset = 5;
    public static int totalYOffset = topOffset + bottomOffset;
	
	protected Main mainFrame;
    protected Graphics2D mainRender;
    protected CanvasPane canvas;
    protected Delays delays;
    protected Highlights highlights;
    
    protected String name;
    protected int id;
	protected double barWidth;
	protected boolean colored;
	protected boolean variableLength;
	protected boolean dots;
    
    public Visual(Main Main, CanvasPane canvas, Delays delays, Highlights highlights, int id) {
        this.mainFrame = Main;
        this.canvas = canvas;
        this.delays = delays;
        this.highlights = highlights;
        this.updateRender();
    }
    
    public static void initializeVisuals(CanvasPane canvas) {
    	Main mainFrame = canvas.getMainFrame();
    	Delays delays = canvas.getDelays();
    	Highlights highlights = canvas.getHighlights();
    	
		visuals = new Visual[Visual.visualCount];
		visuals[0] = new Bars        (mainFrame, canvas, delays, highlights, 0, false, true);         // Bar Graph
		visuals[1] = new Bars        (mainFrame, canvas, delays, highlights, 1, true,  true);         // Color Bar Graph
		visuals[2] = new Bars		 (mainFrame, canvas, delays, highlights, 2, true,  false);        // Color Bars
		visuals[3] = new Circle      (mainFrame, canvas, delays, highlights, 3, true,  false, false); // Color Circle
		visuals[4] = new Circle      (mainFrame, canvas, delays, highlights, 4, false, true,  false); // Disparity Circle
		visuals[5] = new Circle      (mainFrame, canvas, delays, highlights, 5, true,  true,  false); // Color Disparity Circle
		visuals[6] = new Circle      (mainFrame, canvas, delays, highlights, 6, false, true,  true);  // Disparity dots
		visuals[7] = new Circle      (mainFrame, canvas, delays, highlights, 7, true,  true,  true);  // Color Disparity dots
		initialized = true;
    }

    public static void updateMainRender() {
    	for (int i = 0; i < Visual.visualCount; i++) {
    		visuals[i].updateRender();
    	}
    }
    
    private void updateRender() {
        this.mainRender = this.mainFrame.getMainRender();
        if (id < 3) {
    		this.updateBarWidth();
        }
    }
	
	private void updateBarWidth() {
		if (colored && !variableLength) {
			this.barWidth = (this.canvas.getWidth()) / (double)this.canvas.count;
		} else {
			this.barWidth = (this.canvas.getWidth() - Visual.totalXOffset) / (double)this.canvas.count;
		}
	}
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public static void drawCurrentVisual(CanvasPane canvas) {
    	if (!initialized) {
    		initializeVisuals(canvas);
    	}
    	visuals[currentVisual].drawVisual(canvas.getArray(), canvas.getMainFrame(), canvas.getHighlights());
    }
    
    public static Color getIntColor(int i, int length) {
        return Color.getHSBColor(((float) i / length), 1.0F, 0.8F);
    }
    
    public static void markBar(Graphics2D bar, boolean color, boolean rainbow, boolean analysis) {
        if(color || rainbow) {
            if(analysis) bar.setColor(Color.LIGHT_GRAY);
            else         bar.setColor(Color.WHITE);
        }
        else if(analysis)    bar.setColor(Color.BLUE);
        else                 bar.setColor(Color.RED);
    }
    private static void markBarFancy(Graphics2D bar, boolean color, boolean rainbow) {
        if(!color && !rainbow) bar.setColor(Color.RED);
        else                   bar.setColor(Color.BLACK);
    }
    
    public static void setRectColor(Graphics2D rect, boolean color, boolean analysis) {
        if(color) rect.setColor(Color.WHITE);
        else if(analysis) rect.setColor(Color.BLUE);
        else rect.setColor(Color.RED);
    }
    
    @SuppressWarnings("fallthrough")
    //The longer the array length, the more bars marked. Makes the visual easier to see when bars are thinner.
    public static void colorMarkedBars(int logOfLen, int index, Highlights Highlights, Graphics2D mainRender, boolean colorEnabled, boolean rainbowEnabled, boolean analysis) {
        switch(logOfLen) {
        case 15: if(Highlights.containsPosition(index - 15)) { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 14)) { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 13)) { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 12)) { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 11)) { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
        case 14: if(Highlights.containsPosition(index - 10)) { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 9))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 8))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
        case 13: if(Highlights.containsPosition(index - 7))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 6))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 5))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
        case 12: if(Highlights.containsPosition(index - 4))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
                 if(Highlights.containsPosition(index - 3))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
        case 11: if(Highlights.containsPosition(index - 2))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
        case 10: if(Highlights.containsPosition(index - 1))  { markBar(mainRender, colorEnabled, rainbowEnabled, analysis); break; }
        default: if(Highlights.containsPosition(index))        markBar(mainRender, colorEnabled, rainbowEnabled, analysis);
        }
    }
    
    @SuppressWarnings("fallthrough")
    public static void drawFancyFinish(int logOfLen, int index, int position, Graphics2D mainRender, boolean colorEnabled, boolean rainbowEnabled) {
        switch(logOfLen) {
        case 15: if(index == position - 14) { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 14: if(index == position - 13) { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 13: if(index == position - 12) { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 12: if(index == position - 11) { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 11: if(index == position - 10) { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 10: if(index == position - 9)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 9:  if(index == position - 8)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 8:  if(index == position - 7)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 7:  if(index == position - 6)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 6:  if(index == position - 5)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 5:  if(index == position - 4)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 4:  if(index == position - 3)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 3:  if(index == position - 2)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        case 2:  if(index == position - 1)  { markBarFancy(mainRender, colorEnabled, rainbowEnabled); break; }
        default: if(index == position)        markBarFancy(mainRender, colorEnabled, rainbowEnabled);
        }
    }
    
    public abstract void drawVisual(int[] array, Main mainFrame, Highlights Highlights);
}