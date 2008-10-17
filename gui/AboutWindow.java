/**
 * @(#)AboutWindow.java
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
	
	
	This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 
*/
  
package gui;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Font;
import java.awt.BorderLayout;

import java.util.Calendar;

public class AboutWindow extends JDialog {
	private String imgPath = new String("gui/images");
	private Calendar calYear = Calendar.getInstance();
	
    public AboutWindow() {
    	CreateWindow();
    }
    
    private void CreateWindow() {
    	this.setTitle("Sobre o " + Info.getTitle() + " " + Info.getVersion());
		this.setSize(729, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.setModal(true);
		
		JPanel pnlImage = new JPanel();
		JLabel jlImage = new JLabel(new ImageIcon(imgPath + "/about.jpg"));
		pnlImage.add(jlImage);
		
		JPanel pnlTexts = new JPanel();
		JLabel jlAbout = new JLabel("<html><h2>Sobre o " + Info.getTitle() + " " + Info.getVersion() + "</h2><br><h3>Créditos</h3> " + Info.getCredits() + "<br><br> Descrição do Projeto.<br><br>" + calYear.get(Calendar.YEAR) + "©, Versão " + Info.getVersion() + "</html>");	
		pnlTexts.add(jlAbout);
		JTextArea txtLicense = new JTextArea("This program is free software; you can redistribute it and/or \nmodify it under the terms of the GNU General Public License as published by \nthe Free Software Foundation; either version 3 of the License, or \n(at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program. If not, see <http://www.gnu.org/licenses/>.\n\n\n\n", 6, 45);
        txtLicense.setFont(new Font("Tahoma", Font.PLAIN, 10));
        txtLicense.setLineWrap(true);   		
		JScrollPane scrollPane = new JScrollPane(txtLicense);
		pnlTexts.add(scrollPane);
		
		JPanel pnlButton = new JPanel();
		JButton btnOk = new JButton("   OK   ");
		pnlButton.add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					dispose();
			}  
		});
		
		this.add(pnlImage, BorderLayout.WEST);
		this.add(pnlTexts, BorderLayout.CENTER);
		this.add(pnlButton, BorderLayout.SOUTH);
		
		this.setVisible(true);
    }
}