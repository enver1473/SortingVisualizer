package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CanvasPane extends JPanel {
	
	public CanvasPane() {
		super();
	}
/*
	public CanvasPane() {
		this.tet = tetris;
		String tip = tetris.getTip();
		int sirina = tetris.getSirina();
		int visina = tetris.getVisina();
		buttons = new JButton[visina][sirina];
		int brojSljedecih = tetris.getBrojSljedecih();
		this.setLayout(new GridLayout(visina, sirina));
		for (int i = 0; i < visina; i++) {
			for (int j = 0; j < sirina; j++) {
				JButton jb = new JButton();
				jb.addKeyListener(new MojKeyListener());
				jb.setBackground(mojaBoja(tet.stanjeTabele()[visina - 1 - i][j]));
				jb.setSize(30, 30);
				this.add(jb);
				buttons[i][j] = jb;
			}
		}
	//	rotateThread rt = new rotateThread(tet, this);			//
	//	rotateThread.addListeners(rt);							//
	}

	public Color mojaBoja(int i) {
		if (i == 0)
			return Color.WHITE;
		if (i == 1 || i == 4)
			return Color.RED;
		if (i == 2 || i == 6)
			return Color.BLUE;
		if (i == 3 || i == 7 || i == 5)
			return Color.GREEN;
		return Color.BLACK;
	}
	public void osvjeziEkran() {
		int[][] tabela = tet.tabela();
		for (int i = tet.getVisina() - 1; i >= 0 ; i--) {
			for (int j = 0; j < tet.getSirina(); j++) {
				buttons[tet.getVisina() - 1 - i][j].setBackground(mojaBoja(tet.stanjeTabele()[i][j]));
			}
		}
	}
	
	public MoveThread napraviThread(int smjer) {
		MoveThread mt = new MoveThread(tet, this, smjer);
		return mt;
	}

	public class MojKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			performAction(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {}
		
		public void performAction(KeyEvent e) {
			if (!tet.kraj()) {
				//MoveThread mt = null;
				if (e.getKeyCode() == TETRIS_PANE_LIJEVO) {
					tet.pomjeriFiguru(Tetris.DIREKCIJA_LIJEVO);
					//mt = napraviThread(Tetris.DIREKCIJA_LIJEVO);
				} else {
					if(e.getKeyCode() == TETRIS_PANE_DESNO) {
						tet.pomjeriFiguru(Tetris.DIREKCIJA_DESNO);
						//mt = napraviThread(Tetris.DIREKCIJA_DESNO);
					} else {
						if (e.getKeyCode() == TETRIS_PANE_SPUSTI_SKROZ) {
							tet.pomjeriFiguru(Tetris.DIREKCIJA_DOLE);
							//mt = napraviThread(Tetris.DIREKCIJA_DOLE);
						}
					}
				}
				//  if (mt != null) {
				//	  mt.start();
				//  }
				if (e.getKeyCode() == TETRIS_PANE_ROTIRAJ_LIJEVO) {
					tet.rotirajFiguru(-1);
				} else if (e.getKeyCode() == TETRIS_PANE_ROTIRAJ_DESNO) {
					tet.rotirajFiguru(1);
				}
				osvjeziEkran();
			}
		}
		public void osvjeziEkran() {
			for (int i = tet.getVisina() - 1; i >= 0 ; i--) {
				for (int j = 0; j < tet.getSirina(); j++) {
					buttons[tet.getVisina() - 1 - i][j].setBackground(mojaBoja(tet.stanjeTabele()[i][j]));
				}
			}
		}
		
	}*/
}
