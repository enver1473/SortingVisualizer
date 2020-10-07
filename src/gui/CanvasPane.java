package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JPanel;

import templates.Visual;
import util.Highlights;
import util.Reads;
import util.Shuffles;
import util.Sounds;
import util.Timers;
import util.Writes;
import util.Delays;

public class CanvasPane extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_LEN = 32768;
	public static final int FONT_SIZE = 18;
	public static final double RESIZE_FACTOR = 800.0;
	public static final double TXT_DISTANCE_FACTOR = 1.75;
	
	public static boolean displayStats = true;
	
	
	private Image offscreenImg;
	private Graphics2D offscreen;
	private volatile boolean displayVisuals;
	
	private int[] array;

	private DecimalFormat formatter;
    private DecimalFormatSymbols symbols;

	private Main mainFrame;
	private Timers timers;
	private Reads reads;
	private Highlights highlights;
	private Delays delays;
	private Writes writes;
	private Shuffles shuffles;
	private Sounds sounds;
	
	private String caption;
	
	private Font typeFace;
	
	public int cw;
	public int ch;
	
	public int widthDifference;
	public int heightDifference;
	
	public int count;
	private int uniqueCount;

    public double speedFactor = 1d;
	
	public CanvasPane(Main mainFrame) {
		super();
		this.mainFrame = mainFrame;
		
		this.widthDifference = 212;
		this.heightDifference = 38;
		int width = mainFrame.getWidth() - this.widthDifference;
		int height = mainFrame.getHeight() - this.heightDifference;
		
		this.setSize(width, height);
		this.cw = width;
		this.ch = height;
		
		Image img = this.mainFrame.createVolatileImage(width, height);
		this.setImage(img);
		
		this.offscreen = (Graphics2D) this.getImage().getGraphics();
		
		this.mainFrame.setMainRender(this.offscreen);
		
		this.count = 512;
		this.uniqueCount = this.count;
	
		int[] array = new int[MAX_LEN];
		this.array = array;
		
		this.formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		this.symbols = this.formatter.getDecimalFormatSymbols();
        this.formatter.setRoundingMode(RoundingMode.HALF_UP);
        this.symbols.setGroupingSeparator(',');
        this.formatter.setDecimalFormatSymbols(this.symbols);
        
        // JOptionPane.showMessageDialog
        
        this.timers = new Timers(this);
		this.sounds = new Sounds(this);
		this.delays = new Delays(this);
		this.delays.setDelayFactor((double)(Delays.MULTIPLIER_FACTOR / count));
		
		this.highlights = new Highlights(10);
        this.reads = new Reads(this);
		this.writes = new Writes(this);
		
		this.caption = "Select sort...";
		this.shuffles = new Shuffles(this);
		
		this.mainFrame.setArray(array);
		
	    this.typeFace = new Font("Times New Roman", Font.PLAIN, FONT_SIZE);

		Visual.initializeVisuals(this);
		initializeArray();
	}
	
    public void paint(Graphics g) {
    	if (this.cw != this.mainFrame.getWidth() - this.widthDifference || this.ch != this.mainFrame.getHeight() - this.heightDifference) {
        	this.setSize(this.mainFrame.getWidth() - this.widthDifference, this.mainFrame.getHeight() - this.heightDifference);
        	
    		Image img = this.createVolatileImage(this.getWidth(), this.getHeight());
    		this.setImage(img);
    		this.offscreen = (Graphics2D) img.getGraphics();
    		
    		this.cw = this.getWidth();
    		this.ch = this.getHeight();

    		this.mainFrame.setMainRender(this.offscreen);

    		this.typeFace = new Font("Times New Roman", Font.PLAIN, (int) (FONT_SIZE * (this.cw / RESIZE_FACTOR)) + 3);
    		
    		Visual.topOffset = (int) (55 * (this.cw / RESIZE_FACTOR));
    		Visual.totalYOffset = Visual.topOffset + Visual.bottomOffset;
    		Visual.updateMainRender();
    	}
    	
    	Visual.drawCurrentVisual(this);

        if (displayStats) {
        	Font f = this.offscreen.getFont();
            this.offscreen.setFont(this.typeFace);
			this.offscreen.setColor(Color.BLACK);
			this.offscreen.drawString(this.caption,                     17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)* 15)+31);
	        this.offscreen.drawString(this.timers.getCurrentSortTime(), 17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)* 55)+31);
			this.offscreen.drawString(this.getElementCount(),           17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*115)+31);
			this.offscreen.drawString(this.getVisualizationSpeed(),     17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*155)+31);
	        this.offscreen.drawString(this.writes.getMainWrites(),      17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*215)+31);
	        this.offscreen.drawString(this.writes.getAuxWrites(),       17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*255)+31);
	        this.offscreen.drawString(this.reads.getComparisons(),      17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*315)+31);
	        this.offscreen.drawString(this.writes.getSwaps(),           17, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*355)+31);
	    	this.offscreen.setColor(Color.WHITE);
	    	this.offscreen.drawString(this.caption,                     15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)* 15)+30);
	        this.offscreen.drawString(this.timers.getCurrentSortTime(), 15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)* 55)+30);
			this.offscreen.drawString(this.getElementCount(),           15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*115)+30);
			this.offscreen.drawString(this.getVisualizationSpeed(),     15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*155)+30);
	    	this.offscreen.drawString(this.writes.getMainWrites(),      15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*215)+30);
	        this.offscreen.drawString(this.writes.getAuxWrites(),       15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*255)+30);
	        this.offscreen.drawString(this.reads.getComparisons(),      15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*315)+30);
	    	this.offscreen.drawString(this.writes.getSwaps(),           15, (int) (this.cw/(RESIZE_FACTOR * TXT_DISTANCE_FACTOR)*355)+30);
	        this.offscreen.setFont(f);
        }

    	g.drawImage(this.offscreenImg, 0, 0, null);
    }
    
    public void initializeArray() {
    	int uniqueItems = this.uniqueCount;
		for (int i = 1; i < uniqueItems + 1; i++) {
			for (int j = 0; j < this.count / uniqueItems + 1; j++) {
				this.array[((i - 1) * (this.count) / uniqueItems) + j] = (int) (i * ((double)(this.count) / uniqueItems)) + 1;
			}
        }
		Visual.updateMainRender();
		this.delays.setDelayFactor((double)(Delays.MULTIPLIER_FACTOR / count));
		repaint();
    }

	public Image getImage() {
		return this.offscreenImg;
	}
	
	public void setImage(Image img) {
		this.offscreenImg = img;
	}

	public int[] getArray() {
		return this.array;
	}
	
	public void setCurrentLength(int length) {
		this.count = length;
	}
	
	public int getCurrentLength() {
		return this.count;
	}
    
    public int getLogBaseTwoOfLength() {
    	return (int) getLogBaseTwoOf(this.count);
    }
	
	public String getElementCount() {
		return "Element count: " + this.formatter.format(this.count);
	}
	
	public Main getMainFrame() {
		return this.mainFrame;
	}
	
	public Timers getTimers() {
		return this.timers;
	}
	
	public Reads getReads() {
		return this.reads;
	}
	
	public Highlights getHighlights() {
		return this.highlights;
	}
	
	public Delays getDelays() {
		return this.delays;
	}
	
	public Writes getWrites() {
		return this.writes;
	}
	
	public Shuffles getShuffles() {
		return this.shuffles;
	}
	
	public Sounds getSounds() {
		return this.sounds;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public Font getFont() {
		return this.typeFace;
	}
	
	public DecimalFormat getFormatter() {
		return this.formatter;
	}
	
	public void toggleDisplayVisuals(boolean flag) {
		this.displayVisuals = flag;
	}
	
	public boolean shouldVisualize() {
		return this.displayVisuals;
	}
    
    public void changeVisual(int nextVisual) {
    	Visual.currentVisual = nextVisual;
    	repaint();
    }
    
    public void setVisualizationSpeed(double speedFactor) {
    	this.speedFactor = speedFactor; 
    	this.repaint();
    }
    
    public String getVisualizationSpeed() {
    	double currentSpeed = (this.speedFactor * 100) >= 100 ? Math.floor(this.speedFactor * 100) : this.speedFactor * 100;
    	return "Visualization speed: " + this.formatter.format(currentSpeed) + "%";
    }
    
    public int canvasHalfWidth() {
    	return this.getWidth() / 2;
    }
    
    public int canvasHalfHeight() {
    	return this.getHeight() / 2;
    }
    
    public int getDotSize() {
    	return (int) (6 * Math.log(MAX_LEN / this.count) / 3) + (int) (getLogBaseTwoOf(this.cw) / 2);
    }
    
    public static double getLogBaseTwoOf(double value) {
        return Math.log(value) / Math.log(2); 
    }
    
    public int getUniqueCount() {
    	return this.uniqueCount;
    }
    
    public void setUniqueCount(int uniqueCount) {
    	this.uniqueCount = uniqueCount;
    	initializeArray();
    }
}
