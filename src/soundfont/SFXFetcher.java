package soundfont;

import java.io.InputStream;

final public class SFXFetcher {
    private InputStream sfxFile;
    
    public SFXFetcher() {
        this.sfxFile = this.getClass().getResourceAsStream("yamaha_xg_sound_set.sf2");
    }
    
    public InputStream getSFXFile() {
        return this.sfxFile;
    }
}