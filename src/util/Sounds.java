package util;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JOptionPane;

import gui.CanvasPane;
import soundfont.SFXFetcher;
import templates.JErrorPane;

/*
 * 
MIT License

Copyright (c) 2019 w0rthy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class Sounds {
    private int[] array;
    
    private CanvasPane canvas;
    
    private Thread AudioThread;
    
    private Highlights highlights;
    
    private Synthesizer synth;
    private MidiChannel[] channels;
    
    private volatile int noteDelay;
    
    private volatile boolean SOUND;
    private boolean MIDI;
    private int NUMCHANNELS; //Number of Audio Channels
    private double PITCHMIN; //Minimum Pitch
    private double PITCHMAX; //Maximum Pitch
    private double SOUNDMUL;
    private boolean SOFTERSOUNDS;
    
    private final int REVERB = 100;
    public static volatile boolean audioStarted = false;
    
    public Sounds(CanvasPane canvas) {
        this.canvas = canvas;
        this.array = canvas.getArray();
        this.highlights = canvas.getHighlights();
        
        initialize();
    }
    
    public void reinitialize() {
    	initialize();
    }
    
    private void initialize() {
    	this.SOUND = true;
        this.MIDI = true;
        this.NUMCHANNELS = 16;
        this.PITCHMIN = 25d;
        this.PITCHMAX = 105d;
        this.SOUNDMUL = 0.01;
        
        this.noteDelay = 1;
        
        try {
            MidiSystem.getSequencer(false);
            this.synth = MidiSystem.getSynthesizer();
            this.synth.open();
        } catch (MidiUnavailableException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + ": The MIDI device is unavailable, possibly because it is already being used by another application. Sound is disabled.");
        }
        
        SFXFetcher sfxFetcher = new SFXFetcher();
        InputStream stream = sfxFetcher.getSFXFile();
        try {
            this.synth.loadAllInstruments(MidiSystem.getSoundbank(new BufferedInputStream(stream)));
        } catch (Exception e) {
            JErrorPane.invokeErrorMessage(e);
        }
        finally {
            try {
                stream.close();
            } catch (Exception e) {
                JErrorPane.invokeErrorMessage(e);
            }
        }
        this.channels = new MidiChannel[this.NUMCHANNELS];
        
        for(int i = 0; i < this.NUMCHANNELS; i++) {
            this.channels[i] = this.synth.getChannels()[i];
            this.channels[i].programChange(this.synth.getLoadedInstruments()[16].getPatch().getProgram()); // MIDI Instrument 16 from sfx.sf2 is a Rock Organ.
            // 231, 252, 256, 265
            this.channels[i].setChannelPressure(1);
        }
        if(this.channels[0].getProgram() == -1) {
            JOptionPane.showMessageDialog(null, "Could not find a valid MIDI instrument. Sound is disabled.");
        }
        
        setThread();
    }
    
    public synchronized void toggleSounds(boolean val) {
        this.SOUND = val;
    }
    
    public synchronized void toggleSound(boolean val) {
        this.MIDI = val;
    }
    
    public void toggleSofterSounds(boolean val) {
        this.SOFTERSOUNDS = val;
        
        if(this.SOFTERSOUNDS) this.SOUNDMUL = 0.01;
        else                  this.SOUNDMUL = 1;
    }
    
    public double getVolume() {
        return this.SOUNDMUL;
    }
    public void changeVolume(double val) {
        this.SOUNDMUL = val;
    }
    
    public void changeNoteDelayAndFilter(int noteFactor) {
        if(noteFactor != this.noteDelay) {
            if(noteFactor > 1) {
                this.noteDelay = noteFactor;
                this.SOUNDMUL = 1d / noteFactor;
            }
            else {
                this.noteDelay = 1;
                
                if(this.SOFTERSOUNDS) this.SOUNDMUL = 0.01;
                else                  this.SOUNDMUL = 1;
            }
        }
    }
    
    public void startAudioThread() {
    	if (this.highlights == null) this.highlights = this.canvas.getHighlights();
    	if (audioStarted) return;
    	audioStarted = true;
        AudioThread.start();
    }
    
    public void stopAudioThread() {
    	try {
        	AudioThread.interrupt();
    	} catch (Exception e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} finally {
    		reinitialize();
        	resetThread();
    	}
    }
    
    public void mute() {
        for(MidiChannel channel : channels) {
        	if (channel == null) continue;
            channel.allNotesOff();
        }
    }
    
    public void resetThread() {
    	setThread();
    }
    
    public void setThread() {
    	this.AudioThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    for(MidiChannel channel : channels) {
                    	if (channel == null) continue;
                        channel.allNotesOff();
                    }
                    if(SOUND == false || MIDI == false || JErrorPane.errorMessageActive) {
                        continue;
                    }

                    int noteCount = Math.min(highlights.getMarkCount(), NUMCHANNELS);
                    int voice = 0;

                    for(int i : highlights.getHighlightsArray()) {
                        try {
                            if(i != -1) {
                                int currentLen = canvas.getCurrentLength();

                                //PITCH
                                double pitch = Sounds.this.array[Math.min(Math.max(i, 0), currentLen - 1)] / (double) currentLen * (PITCHMAX - PITCHMIN) + PITCHMIN;
                                int pitchmajor = (int) pitch;
                                int pitchminor = (int)((pitch-((int)pitch))*8192d)+8192;

                                int vel = (int) (Math.pow(PITCHMAX - pitchmajor, 2d) * (Math.pow(noteCount, -0.25)) * 64d * SOUNDMUL) / 2; //I'VE SOLVED IT!!

                                if(SOUNDMUL >= 1 && vel < 256) {
                                    vel *= vel;
                                }

                                channels[voice].noteOn(pitchmajor, vel);
                                channels[voice].setPitchBend(pitchminor);
                                channels[voice].controlChange(REVERB, 10);
                                
                                if((++voice % Math.max(noteCount, 1)) == 0)
                                    break;
                            }
                        }
                        catch (Exception e) {
                            JErrorPane.invokeErrorMessage(e);
                        }
                    }
                    try {
                        for(int i = 0; i < Sounds.this.noteDelay; i++) {
                            sleep(1);
                        }
                    } catch(Exception e) {
                        JErrorPane.invokeErrorMessage(e);
                    }
                }
            }
        };
    }
}