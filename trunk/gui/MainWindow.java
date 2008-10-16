/**
 * @(#)MainWindow.java
 *
 *
 * @Danilo Lutz
 * @version 1.00 14/10/2008
 */
package gui;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.plaf.metal.*;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
	private JPanel jpMainContainer;
	//private String imgPath = new String("C:/Documents and Settings/Danilo/Meus documentos/JCreator Pro/MyProjects/Batalha Naval/src/gui/images");
	private String imgPath = new String("E:/Batalha Naval/src/gui/images");
	private StatusBar statusBar;
    public MainWindow() {
    	CreateMainWindow();
    	CreateMenu();
    	this.setVisible(true);
    }
    
    private void CreateMainWindow() {
    	this.setTitle(Info.getTitle() + " " + Info.getVersion());
    	this.setIconImage(new ImageIcon(imgPath + "/shipicon.png").getImage());
    	this.jpMainContainer = new JPanel(new GridBagLayout());
    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		statusBar = new StatusBar();
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		statusBar.setMessage("Pronto! Seja bem-vindo.");
    }
    
    private void CreateMenu() {
    	//Menu Layout.
    	JMenuBar mnuGeneral = new JMenuBar(); 
    	
    	JMenu mnuGame = new JMenu("Jogo");
    	JMenu mnuAbout = new JMenu("Sobre");
    	
    	ImageIcon imgCreateGame = new ImageIcon(imgPath + "/key.png");
    	ImageIcon imgJoinGame = new ImageIcon(imgPath + "/connect.png");
    	ImageIcon imgCancelGame = new ImageIcon(imgPath + "/disconnect.png");
    	ImageIcon imgExit = new ImageIcon(imgPath + "/door_open.png");
    	ImageIcon imgHelp = new ImageIcon(imgPath + "/help.png");
    	ImageIcon imgSubAbout = new ImageIcon(imgPath + "/information.png");
    	
    	JMenuItem mnuCreateGame = new JMenuItem("Criar jogo", imgCreateGame);
    	JMenuItem mnuJoinGame = new JMenuItem("Entrar no jogo", imgJoinGame);
    	
    	JMenuItem mnuCancelGame = new JMenuItem("Abandonar jogo", imgCancelGame);
    	JMenuItem mnuExit = new JMenuItem("Sair", imgExit);
    	
    	JMenuItem mnuHelp = new JMenuItem("Ajuda", imgHelp);
    	JMenuItem mnuSubAbout = new JMenuItem("Sobre", imgSubAbout);
    	
    	mnuGame.add(mnuCreateGame);
    	mnuGame.add(mnuJoinGame);
    	mnuGame.addSeparator();
    	mnuGame.add(mnuCancelGame);
    	mnuGame.addSeparator();
    	mnuGame.add(mnuExit);
    	
    	mnuAbout.add(mnuHelp);
    	mnuAbout.addSeparator();
    	mnuAbout.add(mnuSubAbout);
    	
    	mnuGeneral.add(mnuGame);
    	mnuGeneral.add(mnuAbout);
    	    	    	
    	//Menus Actions Listeners.
    	mnuCreateGame.addMouseListener(new MouseAdapter() {	
    		public void mouseMove(MouseEvent evt, int x, int y) {
    			statusBar.setText("OI");
    		}
    		
    		public void mouseExit(MouseEvent evt, int x, int y) {
    			statusBar.setText("");
    		}	
    	});
    	
    	
		mnuExit.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					System.exit(0);
			}  
		}); 
    	
    	mnuSubAbout.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					new AboutWindow();
			}  
		});  
		this.setJMenuBar(mnuGeneral);
    }
}