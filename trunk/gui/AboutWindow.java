package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.net.*;
import java.io.*;

public class AboutWindow extends JDialog{

	public AboutWindow(){
		this.buildWindow();
		this.setSize(729,462);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void buildWindow(){

		JLabel jlImage = new JLabel(new ImageIcon("gui/imagens/Padrao/about.jpg"));
		JLabel jlAbout = new JLabel("<html><br><br><br><br><br><br>Sobre o Batalha Naval<br><br> Por Danilo Lutz, Marcos Paulo Penteado, Thomas Kapp.<br><br> Este projeto consiste em uma demonstração de aprendizado em Sistemas Distribuídos.<br><br> Faculdades Logatti <br><br>CAP. Nascimento (vulgo, Prof. Osvaldo)<br><br>2008(c), versão 1.0.0.0</html>");

		JButton btnOk = new JButton("Ok");

		btnOk.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					dispose();
			}  
		});  

		this.setTitle("Sobre o Batalha Naval");
		this.setLayout(new GridBagLayout()); //2,2,10,10
		
		this.add(jlImage, this.componentPostion(1, 4, 0, 0));
		this.add(jlAbout, this.componentPostion(4, 2, 1, 0));

		this.add(btnOk, this.componentPostion(1, 1, 3, 3));
	}

	private GridBagConstraints componentPostion(int width, int height, int column, int line){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = column;
		gbc.gridy = line;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		return gbc; 
	}
}