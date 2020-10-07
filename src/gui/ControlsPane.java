package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sorts.TimSort;
import templates.JErrorPane;
import templates.Sort;
import templates.Visual;
import util.Shuffles;

public class ControlsPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 424012286950296970L;
	private static final float COMPONENT_ALIGNMENT_X = CENTER_ALIGNMENT;
	private static final float COMPONENT_ALIGNMENT_Y = CENTER_ALIGNMENT;
	
	private JButton stopSort;
	private JButton runSelectedSort;
	private JButton uniqueCountBtn;
	
	private JComboBox<String> sorts;
	private JComboBox<String> visualStyles;
	private JComboBox<String> shuffles;
	
	private SpeedPane speedPane;
	
	public CanvasPane canvas;
	
	public ControlsPane(CanvasPane canvas) {
		super();
		
		this.canvas = canvas;
		
		JPanel outerWrapper = new JPanel();
		outerWrapper.setLayout(new BoxLayout(outerWrapper, BoxLayout.Y_AXIS));

		JPanel innerWrapper1 = new JPanel();
		innerWrapper1.setLayout(new BoxLayout(innerWrapper1, BoxLayout.Y_AXIS));

		JPanel innerWrapper2 = new JPanel();
		innerWrapper2.setLayout(new BoxLayout(innerWrapper2, BoxLayout.X_AXIS));
		innerWrapper2.setBorder(new EmptyBorder(10, 20, 10, 20));
		innerWrapper2.setMaximumSize(new Dimension(200, 240));
		
		JPanel runSortBtnWrapper = new JPanel();
		runSortBtnWrapper.setLayout(new BoxLayout(runSortBtnWrapper, BoxLayout.Y_AXIS));
		runSortBtnWrapper.setBorder(new EmptyBorder(10, 0, 10, 0));

		JPanel stopSortBtnWrapper = new JPanel();
		stopSortBtnWrapper.setLayout(new BoxLayout(stopSortBtnWrapper, BoxLayout.Y_AXIS));
		stopSortBtnWrapper.setBorder(new EmptyBorder(10, 0, 10, 0));

		JPanel uniqueCountBtnWrapper = new JPanel();
		uniqueCountBtnWrapper.setLayout(new BoxLayout(uniqueCountBtnWrapper, BoxLayout.Y_AXIS));
		uniqueCountBtnWrapper.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		
		runSelectedSort = new JButton("Run Selected Sort");
		runSelectedSort.setSize(30, 30);
		runSelectedSort.setAlignmentX(COMPONENT_ALIGNMENT_X);
		runSelectedSort.setAlignmentY(COMPONENT_ALIGNMENT_Y);
		runSelectedSort.setEnabled(false);

		stopSort = new JButton("Stop");
		stopSort.setSize(30, 30);
		stopSort.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				canvas.getDelays().setDelayFactor(0.0);
				canvas.getDelays().skipped = true;
			}
		});
		
		stopSort.setEnabled(false);
		stopSort.setAlignmentX(COMPONENT_ALIGNMENT_X);
		stopSort.setAlignmentY(COMPONENT_ALIGNMENT_Y);
		
		
		uniqueCountBtn = new JButton("Unique elements");
		uniqueCountBtn.setSize(30, 30);
		uniqueCountBtn.setAlignmentX(COMPONENT_ALIGNMENT_X);
		uniqueCountBtn.setAlignmentY(COMPONENT_ALIGNMENT_Y);
		uniqueCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
	            boolean showPrompt = true;
	            while(showPrompt) {
	                try {
	                	int oldUniqueCount = canvas.getUniqueCount();
	                    String userInput = JOptionPane.showInputDialog(null, "Specify a number of unique items", oldUniqueCount);
	                    if(userInput == null) {
	                        showPrompt = false;
	                    }
	                    else {
	                        int newUniqueCount = Integer.parseInt(userInput);
	                        if (newUniqueCount < 1 || newUniqueCount > canvas.count) {
	                        	throw new Exception("Must be in the range (1, current_number_of_elements)");
	                        }
	                        canvas.setUniqueCount(newUniqueCount);
	                        showPrompt = false;
	                    }
	                }
	                catch(Exception e) {
	                    showPrompt = true;
	                    JOptionPane.showMessageDialog(null, "Not a number! (" + e.getMessage() + ")", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
			}
			
		});
		
		
		String[] visualStrings = new String[Visual.visualCount];

		for (int i = 0; i < Visual.visualCount; i++) {
			visualStrings[i] = Visual.visuals[i].getName();
		}

		DefaultComboBoxModel<String> visualsModel = new DefaultComboBoxModel<String>(visualStrings);
		
		visualStyles = new JComboBox<String>(visualsModel);
		visualStyles.setSelectedIndex(0);
		visualStyles.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {
		        @SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
		        int visual = cb.getSelectedIndex();
		        canvas.changeVisual(visual < Visual.visualCount ? visual : 0);
			}
		});
		visualStyles.setMaximumSize(new Dimension(300, 30));

        visualStyles.setBackground(Color.WHITE);
		
		JPanel visualComboWrapper = new JPanel();
		visualComboWrapper.setLayout(new BoxLayout(visualComboWrapper, BoxLayout.Y_AXIS));
		visualComboWrapper.setSize(200, 40);
		visualComboWrapper.add(visualStyles);
		visualComboWrapper.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		innerWrapper1.add(visualComboWrapper);

		Sort.initSorts();
		
		String[] sortStrings = new String[Sort.sortCount + 1];
		
		sortStrings[0] = "Select sort...";
		
		for (int i = 1; i < Sort.sortCount + 1; i++) {
			sortStrings[i] = String.valueOf(i) + ". " + Sort.sorts[i - 1].getSelectLabel();
		}

		DefaultComboBoxModel<String> sortsModel = new DefaultComboBoxModel<String>(sortStrings);
		
		sorts = new JComboBox<String>(sortsModel);
		sorts.setSelectedIndex(0);
		sorts.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {
		        @SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
		        String sort = (String) cb.getSelectedItem();
		        
		        Sort.setCurrentSort(stripOffNumber(sort));
		    	
		        if (!Sort.sorting) {
					runSelectedSort.setEnabled(true);

			    	canvas.setCaption(Sort.getCurrentSort());
			    	canvas.repaint();
		        }
			}
		});
		sorts.setMaximumSize(new Dimension(300, 30));
		
		sorts.setBackground(Color.WHITE);
		
		JPanel sortsComboWrapper = new JPanel();
		sortsComboWrapper.setLayout(new BoxLayout(sortsComboWrapper, BoxLayout.Y_AXIS));
		sortsComboWrapper.setSize(200, 40);
		sortsComboWrapper.add(sorts);
		sortsComboWrapper.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		innerWrapper1.add(sortsComboWrapper);
		
		Shuffles.initShuffles();
		Shuffles.currentShuffle = Shuffles.RANDOM;
		String[] shuffleStrings = new String[Shuffles.shuffleCount];
		for (int i = 0; i < Shuffles.shuffleCount; i++) {
			shuffleStrings[i] = Shuffles.getShuffleName(Shuffles.shuffles[i]);
		}

		DefaultComboBoxModel<String> shufflesModel = new DefaultComboBoxModel<String>(shuffleStrings);
		
		shuffles = new JComboBox<String>(shufflesModel);
		shuffles.setSelectedIndex(0);
		shuffles.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
		        @SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
		        int shuffle = cb.getSelectedIndex();
		        
		        Thread t = new Thread(new Runnable() {
				    public void run() {
				    	try {
				    		Shuffles.currentShuffle = shuffle;
				    	} catch (Exception e) {
				    		JErrorPane.invokeErrorMessage(e);
				    	}
				    }
				});

				t.start();
			}
		});
		shuffles.setMaximumSize(new Dimension(300, 30));
		
		shuffles.setBackground(Color.WHITE);
		
		JPanel shufflesComboWrapper = new JPanel();
		shufflesComboWrapper.setLayout(new BoxLayout(shufflesComboWrapper, BoxLayout.Y_AXIS));
		shufflesComboWrapper.setSize(200, 40);
		shufflesComboWrapper.add(shuffles);
		shufflesComboWrapper.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		runSelectedSort.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e) {
				if (Sort.currentSort == null) {
					return;
				}
		        
				Thread t = new Thread(new Runnable() {
				    public void run() {
				    	try {
					    	if (Sort.sorting) {
					    		throw new Exception("An algorithm is already running! Wait for the algorithm to finish or click Stop!");
					    	}
							canvas.getDelays().skipped = false;
					    	
					    	// TODO: Reconsider resetting visual speed to 100% after a sort is ran
					        // Delays.userDelayFactor = 1;
					        // canvas.setVisualizationSpeed(1d);
					        // speedPane.resetSpeedFactor();
					    	
					    	canvas.getSounds().startAudioThread();
					    	
					    	stopSort.setEnabled(true);
							runSelectedSort.setEnabled(false);
							uniqueCountBtn.setEnabled(false);
					    	Sort.sorting = true;
					    	canvas.setCaption("Shuffling...");
					    	
					        Sort.runSort(canvas.getMainFrame(), canvas, canvas.getReads(), canvas.getWrites(), canvas.getDelays(), canvas.getHighlights());
					        
					        if (Sort.currentSort == null) {
					        	Sort.currentSort = new TimSort();
					        }
					        
							canvas.repaint();
					        
							stopSort.setEnabled(false);
							runSelectedSort.setEnabled(true);
							uniqueCountBtn.setEnabled(true);
					    	Sort.sorting = false;
					    	
					    	canvas.getSounds().mute();
				    	} catch (Exception e) {
				    		JErrorPane.invokeErrorMessage(e);
				    	}
				    }
				});

				t.start();
			}
		});
		
		runSelectedSort.setEnabled(false);
		
		runSortBtnWrapper.add(runSelectedSort);
		stopSortBtnWrapper.add(stopSort);
		uniqueCountBtnWrapper.add(uniqueCountBtn);

		innerWrapper1.add(shufflesComboWrapper);
		innerWrapper1.add(runSortBtnWrapper);
		innerWrapper1.add(stopSortBtnWrapper);/*
		innerWrapper1.add(uniqueCountBtnWrapper);*/
		
		ArrayPane arrayPane = new ArrayPane(canvas.getArray(), canvas.getMainFrame(), canvas);
		innerWrapper2.add(arrayPane);
		
		
		this.speedPane = new SpeedPane(canvas.getArray(), canvas.getMainFrame(), canvas);
		innerWrapper2.add(speedPane);


        JCheckBox statsCheckbox = new JCheckBox("Display stats", true);  
        statsCheckbox.setBounds(100,100,50,50);
        statsCheckbox.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e) {
				CanvasPane.displayStats = statsCheckbox.isSelected();
				canvas.repaint();
			}
		});
        statsCheckbox.setAlignmentX(COMPONENT_ALIGNMENT_X);
		
		outerWrapper.add(innerWrapper1);
        outerWrapper.add(statsCheckbox);
		outerWrapper.add(innerWrapper2);
		
		this.add(outerWrapper);
		this.revalidate();
	}
	
	public String stripOffNumber(String label) {
		String sortName = "";
		boolean afterFirstSpace = false;
		
		for (int i = 0; i < label.length(); i++) {
			if (!afterFirstSpace && label.charAt(i) == ' ') {
				afterFirstSpace = true;
				continue;
			} else if (afterFirstSpace) {
				sortName += label.charAt(i);
			}
		}
		return sortName;
	}
	
	public void setCanvas(CanvasPane canvas) {
		this.canvas = canvas;
	}
}
