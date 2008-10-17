/**
 * @(#)MainWindow.java
 * @author Danilo Lutz
 * @version 1.00 
 * @date 17/10/2008
 **/
/*
    This file is part of Batalha Naval.

    Batalha Naval is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Batalha Naval is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Batalha Naval.  If not, see <http://www.gnu.org/licenses/>.
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class MainWindow extends JFrame {
	private JPanel jpMainContainer;
	private String imgPath = new String("gui/images");
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
		mnuCancelGame.setEnabled(false); //Desabilita o menu enquanto, não for identificada uma conexão.
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
		mnuCreateGame.addMouseListener(new MenuStatusAction("Cria um novo jogo.", statusBar));
		mnuJoinGame.addMouseListener(new MenuStatusAction("Conecta em um jogo existente.", statusBar));
		mnuCancelGame.addMouseListener(new MenuStatusAction("Desconecta do jogo atual.", statusBar));
		mnuExit.addMouseListener(new MenuStatusAction("Sair do jogo retornando ao sistema operacional.", statusBar));
		mnuHelp.addMouseListener(new MenuStatusAction("Exibe o conteúdo da ajuda.", statusBar));
		mnuSubAbout.addMouseListener(new MenuStatusAction("Exibe informações sobre versão, autores e outros.", statusBar));
		
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