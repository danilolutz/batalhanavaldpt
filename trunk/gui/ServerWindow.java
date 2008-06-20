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

public class ServerWindow extends JDialog{
	private JTextField txtIp;
	private JTextField txtPorta;
	private JTextField txtApelido;

	public ServerWindow(){
		this.buildWindow();
		this.setSize(300,150);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void buildWindow(){

		JLabel jlIp = new JLabel("Seu IP:");
		JLabel jlPorta = new JLabel("Sua Porta:");
		JLabel jlApelido = new JLabel("Seu Apelido:");

		InetAddress inAd = null;

		try {
			inAd = InetAddress.getLocalHost();
		} catch	(Exception e){
			JOptionPane.showMessageDialog(this, "Problemas na placa de rede local, não foi pssível conectar", "Batalha Naval", JOptionPane.WARNING_MESSAGE);
		}

		txtIp = new JTextField(inAd.getHostAddress());
		txtIp.setEnabled(false);
	
		txtPorta = new JTextField();
		txtApelido = new JTextField();

		JButton btnOk = new JButton("Ok");
		JButton btnCancelar = new JButton("Cancelar");

		btnOk.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					Validar();
			}  
		});
	
		btnCancelar.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					dispose();
			}  
		});

		this.setTitle("Configurações do Servidor");
		this.setLayout(new GridLayout(4, 2, 10, 10));
		
		this.add(jlIp);
		this.add(txtIp);

		this.add(jlPorta);
		this.add(txtPorta);

		this.add(jlApelido);
		this.add(txtApelido);

		this.add(btnOk);
		this.add(btnCancelar);
	}

	public String getIp() {
		return txtIp.getText();
	}

	public String getPorta() {
		return txtPorta.getText();
	}

	public String getApelido() {
		return txtApelido.getText();
	}

	private void Validar() {
		String msg = "";

		if (txtPorta.getText().equals("")) {
			msg += "• Por favor, informe a Porta\n";
		}

		if (txtApelido.getText().equals("")) {
			msg += "• Por favor, informe o seu Apelido\n";
		}

		if (!msg.equals("")) {
			msg = "Foram encontrados os seguintes erros:\n\n" + msg;
			JOptionPane.showMessageDialog(this, msg, "Batalha Naval", JOptionPane.WARNING_MESSAGE);
			return;
		}

		dispose();
	}
}
