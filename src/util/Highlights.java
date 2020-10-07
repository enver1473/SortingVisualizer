package util;

import java.util.Arrays;

public class Highlights {
    private volatile int[] highlights;
    private volatile int maxIndexMarked;   
    private volatile int markCount;
    
    public Highlights(int maximumLength) {
        this.highlights = new int[maximumLength * maximumLength];
        this.maxIndexMarked = 0;
        this.markCount = 0;
        
        Arrays.fill(highlights, -1);
    }
    
    public int getMaxIndex() {
        return this.maxIndexMarked;
    }
    
    public int getMarkCount() {
        return this.markCount;
    }
    
    public int[] getHighlightsArray() {
        return this.highlights;
    }
    
    public boolean containsPosition(int arrayPosition) {
        for(int i = 0; i <= this.maxIndexMarked; i++) {
            if(this.highlights[i] == -1) {
                continue;
            }
            else if(this.highlights[i] == arrayPosition) {
                return true;
            }
        }

        return false;
    }
    
    public void markArray(int markerIdx, int markPosition) {
        try {
            if(markPosition < 0) {
               if(markPosition == -1) throw new Exception("highlights.markArray(): Invalid position! Index -1 is reserved for the clearMark method.");
               else throw new Exception("highlights.markArray(): Invalid position!");
            } else {
            	if (this.highlights[markerIdx] == -1) {
                    this.markCount++;
            	}
            	this.highlights[markerIdx] = markPosition;
                
                if (markerIdx > this.maxIndexMarked) {
                    this.maxIndexMarked = markerIdx;
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int markNext(int markPosition) {
    	int tmpMarkCount = this.markCount + 2;
    	markArray(this.markCount + 2, markPosition);
    	return tmpMarkCount;
    }
    
    public void clearMark(int markerIdx) {
    	if (this.highlights[markerIdx] != -1) {
            this.markCount--;
    	}
    	this.highlights[markerIdx] = -1;
        
        if(markerIdx == this.maxIndexMarked) {
            this.maxIndexMarked = 0;
            
            for(int i = markerIdx - 1; i >= 0; i--) {
                if(this.highlights[i] != -1) {
                    this.maxIndexMarked = i;
                    break;
                }
            }
        }
    }
    public void clearAllMarks() {
        Arrays.fill(this.highlights, -1);
        this.maxIndexMarked = 0;
        this.markCount = 0;
    }
}
