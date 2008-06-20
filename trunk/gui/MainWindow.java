package gui;
import conn.SocketClient;
import conn.SocketServer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.accessibility.AccessibleContext;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	private boolean orientacao = true;  // true = Horizontal.
	private int barcoSelecionado = -1;
	private JPanel jpEsquerda;
	public JPanel jpDireita;
	private JPanel jpFrota;
	private JPanel jpStatus;
	private JPanel jpContainer;
	public StatusBar stbar;
	private int barcosInseridos = 0;
	public int contPontos = 0;
	public JLabel jlJogador1;
	public JLabel jlJogador2;
	public JLabel jlPtJogador1;
	public JLabel jlPtJogador2;

	private JMenuItem mnuItem0;
	private JMenuItem mnuItem1;
	private JMenuItem mnuItem2;
	private JMenuItem mnuItem4;

	//Dados de quando for Servidor
	public String serverAddress = "";
	public String serverPort = "";
	public String serverNick = "";
	
	//Dados de quando for Cliente
	public String clientServer = "";
	public String clientPort = "";
	public String clientNick = "";
	
	private JLabel jlf0;
	private JLabel jlf1;
	private JLabel jlf4;
	private JLabel jlf2;
	private JLabel jlf3;
	private JLabel jlf5;
	private JLabel jlf6;
	private JLabel jlf7;
	private JLabel jlf8;
	private JLabel jlf9; 
	private JLabel jlf10;
	private JLabel jlf11;
	private JLabel jlf12;
	private JLabel jlf13;
	private JLabel jlf14;
	private JLabel jlf15;
	private JLabel jlf16;
	
	private SocketServer objServer;
	private SocketClient objCliente;
	
	private String tpCon;
	public boolean myTurn = false;
	
	public MainWindow(){
		this.buildWindow();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void buildWindow(){
		this.setTitle("Batalha Naval");
		this.setIconImage(new ImageIcon("gui/imagens/Padrao/ship_256.png").getImage());

		this.jpContainer = new JPanel(new GridBagLayout());

		this.buildMenu();
	
		this.buildFrota(5, 5);
		this.buildStatus(3, 2);
		this.buildEsquerda(10, 10);
		this.buildDireita(10, 10);
		
		jpContainer.add(jpEsquerda, this.componentPostion(1, 1, 0, 0));
		jpContainer.add(jpDireita, this.componentPostion(1, 1, 1, 0));
		jpContainer.add(jpFrota, this.componentPostion(1, 1, 0, 1));
		jpContainer.add(jpStatus, this.componentPostion(2, 2, 1, 1));
		this.getContentPane().add(jpContainer,BorderLayout.CENTER);
		
		stbar = new StatusBar();
		this.getContentPane().add(stbar,BorderLayout.SOUTH);
		stbar.setMessage("Clique em: Jogo -> Criar um Jogo, para iniciar a partida.");
	}

	private void buildMenu() {

		JMenuBar mnuBar = new JMenuBar();		

		JMenu mnu = new JMenu("Jogo");
		JMenu mnu1 = new JMenu("Sobre");

		mnuItem0 = new JMenuItem("Criar um jogo");
		mnuItem1 = new JMenuItem("Entrar em um jogo");
		mnuItem4 = new JMenuItem("Cancelar jogo (desconectar)");
		mnuItem2 = new JMenuItem("Sair");

		mnuItem0.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					AbrirServerWindow();
			}  
		});

		mnuItem1.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					AbrirClientWindow();
			}  
		});

		mnuItem4.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					Desconectar();
			}  
		});

		mnuItem2.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					System.exit(0);
			}  
		});  

		mnuItem4.setEnabled(false);

		mnu.add(mnuItem0);
		mnu.add(mnuItem1);
		mnu.addSeparator();
		mnu.add(mnuItem4);
		mnu.addSeparator();
		mnu.add(mnuItem2);

		JMenuItem mnuItem3 = new JMenuItem("Sobre");

		mnuItem3.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent evt) {  
					new AboutWindow();
			}  
		});  

		mnu1.add(mnuItem3);

		mnuBar.add(mnu);
		mnuBar.add(mnu1);
		this.setJMenuBar(mnuBar);
	}

	private void Desconectar() {
		orientacao = true; // true = Horizontal.
		barcoSelecionado = -1;
		jpEsquerda = null;
		barcosInseridos = 0;
		contPontos = 0;
		
		if (tpCon == "Server") {
			objServer.stop();
		}
		else
		{
			objCliente.stop();
		}
		
		//Limpa os dados.
		serverAddress = "";
		serverPort = "";
		serverNick = "";
		clientServer = "";
		clientPort = "";
		clientNick = "";
		
		buildWindow();
	}

	private boolean Conectar(String Ip, String Porta, String Nome) {
		//Aqui será aberta a conexão entre ServerSocket e ClientSocket.
		if (tpCon == "Server") {
			objServer = new SocketServer(this, Porta);
			objServer.start();
			myTurn = true;
		}
		else
		{
			objCliente = new SocketClient(this, Ip, Porta);
			objCliente.start();
		}
		
		return true;
	}
	
	private void AbrirServerWindow () {
		ServerWindow objWin = new ServerWindow();
		
		serverAddress = objWin.getIp();
		serverPort = objWin.getPorta();
		serverNick = objWin.getApelido();
		
		tpCon = "Server";
		if (Conectar(serverAddress, serverPort, serverNick)) {
			mnuItem0.setEnabled(false);
			mnuItem1.setEnabled(false);
			mnuItem4.setEnabled(true);
			
			jlJogador1.setText(serverNick);
			objServer.EnviarNome(serverNick);
		}
	}

	private void AbrirClientWindow() {
		ClientWindow objWin = new ClientWindow();
			
		clientServer = objWin.getIp();
		clientPort = objWin.getPorta();
		clientNick = objWin.getApelido();
		
		tpCon = "Client";
		if (Conectar(clientServer, clientPort, clientNick)) {
			mnuItem0.setEnabled(false);
			mnuItem1.setEnabled(false);
			mnuItem4.setEnabled(true);

			jlJogador2.setText(clientNick);
			objCliente.EnviarNome(clientNick);
		}
	}

	private void buildStatus(int linhas, int colunas){
		this.jpStatus = new JPanel(new GridLayout(linhas, colunas));
		this.jpStatus.setPreferredSize(new Dimension(160, 160));

		jlJogador1 = new JLabel("nome do jogador 1"); //Recuperado via socket, quando conectado.
		jlJogador2 = new JLabel("nome do jogador 2"); //Recuperado via socket, quando conectado.
	
		jlPtJogador1 = new JLabel("0");
		jlPtJogador2 = new JLabel("0");
	
		this.jpStatus.add(jlJogador1);
		this.jpStatus.add(jlPtJogador1);

		this.jpStatus.add(jlJogador2);
		this.jpStatus.add(jlPtJogador2);
	}

	private void buildFrota(int linhas, int colunas){
		this.jpFrota = new JPanel(new GridLayout(linhas, colunas));
		this.jpFrota.setPreferredSize(new Dimension(160, 160));
		this.jpFrota.setBackground(Color.WHITE);

		//Imagens do barco de cinco quadros (Tiles).
		jlf0 = new JLabel(new ImageIcon("gui/imagens/Padrao/SubpopaH.jpg"));
		jlf1 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
		jlf4 = new JLabel(new ImageIcon("gui/imagens/Padrao/ProaH.jpg"));
		jlf2 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
		jlf3 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));

		jpFrota.add(jlf0);
		jpFrota.add(jlf1);
		jpFrota.add(jlf2);
		jpFrota.add(jlf3);
		jpFrota.add(jlf4);
	
		jlf0.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 5);
					}
				});

		jlf1.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 5);
					}
				});

		jlf2.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 5);
					}
				});

		jlf3.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 5);
					}
				});

		jlf4.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 5);
					}
				});

		//Imagens dos quadros da água.
		JLabel jla0 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));

		jpFrota.add(jla0);

		//Imagens do barco de quatro Tiles.
		jlf5 = new JLabel(new ImageIcon("gui/imagens/Padrao/SubpopaH.jpg"));
		jlf6 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
		jlf7 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
		jlf8 = new JLabel(new ImageIcon("gui/imagens/Padrao/ProaH.jpg"));

		jpFrota.add(jlf5);
		jpFrota.add(jlf6);
		jpFrota.add(jlf7);
		jpFrota.add(jlf8);

		jlf5.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 4);
					}
				});

		jlf6.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 4);
					}
				});

		jlf7.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 4);
					}
				});

		jlf8.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 4);
					}
				});

		//Imagens dos quadros da água.
		JLabel jla1 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));
		JLabel jla2 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));

		jpFrota.add(jla1);
		jpFrota.add(jla2);

		//Imagens do barco de três Tiles.
		jlf9 = new JLabel(new ImageIcon("gui/imagens/Padrao/SubpopaH.jpg"));
		jlf10 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
		jlf11 = new JLabel(new ImageIcon("gui/imagens/Padrao/ProaH.jpg"));

		jpFrota.add(jlf9);
		jpFrota.add(jlf10);
		jpFrota.add(jlf11);

		jlf9.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 31);
					}
				});

		jlf10.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 31);
					}
				});

		jlf11.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 31);
					}
				});

		//Imagens dos quadros da água.
		JLabel jla3 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));
		JLabel jla4 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));

		jpFrota.add(jla3);
		jpFrota.add(jla4);

		//Imagens do barco de três Tiles.
		jlf12 = new JLabel(new ImageIcon("gui/imagens/Padrao/SubpopaH.jpg"));
		jlf13 = new JLabel(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
		jlf14 = new JLabel(new ImageIcon("gui/imagens/Padrao/ProaH.jpg"));

		jpFrota.add(jlf12);
		jpFrota.add(jlf13);
		jpFrota.add(jlf14);

		jlf12.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 32);
					}
				});

		jlf13.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 32);
					}
				});

		jlf14.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 32);
					}
				});

		//Imagens dos quadros da água.
		JLabel jla5 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));
		JLabel jla6 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));
		JLabel jla7 = new JLabel(new ImageIcon("gui/imagens/Padrao/Agua32.jpg"));

		jpFrota.add(jla5);
		jpFrota.add(jla6);
		jpFrota.add(jla7);

		//Imagens do barco de duas Tiles.
		jlf15 = new JLabel(new ImageIcon("gui/imagens/Padrao/SubpopaH.jpg"));
		jlf16 = new JLabel(new ImageIcon("gui/imagens/Padrao/ProaH.jpg"));

		jpFrota.add(jlf15);
		jpFrota.add(jlf16);

		jlf15.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 2);
					}
				});

		jlf16.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evt) {
						selBarco(evt, 2);
					}
				});
	}
	
	private void buildEsquerda(int linhas, int colunas){
		this.jpEsquerda = new JPanel(new GridLayout(linhas, colunas));
		this.jpEsquerda.setPreferredSize(new Dimension(330, 330));
		this.jpEsquerda.setBackground(Color.WHITE);
		
		this.addImages(jpEsquerda, linhas, colunas, "32");
	}
	
	private void buildDireita(int linhas, int colunas){
		
		this.jpDireita = new JPanel(new GridLayout(linhas, colunas));
		this.jpDireita.setPreferredSize(new Dimension(170, 170));
		this.jpDireita.setBackground(Color.WHITE);
		
		this.addImages(jpDireita, linhas, colunas, "16");
	}

	private void addImages(JPanel jp, int linhas, int colunas, String tipo){
		JLabel jl;
		for(int ln = 0; ln < linhas; ln++)
			for(int cl = 0; cl < colunas; cl++){
				jl = new JLabel(new ImageIcon("gui/imagens/Padrao/agua"+tipo+".jpg"));
				jl.setName(ln + "," + cl);
				jl.setToolTipText(ln + "," + cl);
				
				if (tipo.equals("32")) {
					jl.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt) {
							jlMouseClickGrid(evt, jpEsquerda);
						}
					});
				} else if (tipo.equals("16")){
					jl.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt) {
							jlMouseClickGridAttack(evt, jpDireita);
						}
					});
				}
				
				jp.add(jl);
			}
	}

	private void jlMouseClickGridAttack(MouseEvent evt, JPanel jpnl){
		if (SwingUtilities.isLeftMouseButton(evt)) {
			if (myTurn)
			{
				int shottedIndex = 0;
				AccessibleContext obj = ((JLabel)evt.getSource()).getAccessibleContext();
				shottedIndex = obj.getAccessibleIndexInParent();
				
				//Envia para o socket o Index(inteiro) do objeto no qual foi dado o tiro, 
				//após testar qual o socket a aplicação está utilizando.
				if (tpCon == "Server"){
					objServer.EnviarTiro(shottedIndex);
				}
				else
				{
					objCliente.EnviarTiro(shottedIndex);
				}
			}
		}
	}
	
	public void VerificaPontos(boolean Acertou, int indexObj) {	
		if (Acertou) { 
			contPontos++;
			
			if (tpCon == "Server"){
				jlPtJogador2.setText(String.valueOf(contPontos));
			}
			else
			{
				jlPtJogador1.setText(String.valueOf(contPontos));
			}
		}
	}
	
	private void jlMouseClickGrid(MouseEvent evt, JPanel jpnl){
		if (SwingUtilities.isLeftMouseButton(evt)) {
			if (this.barcoSelecionado == -1) {
				JOptionPane.showMessageDialog(this, "Selecione um barco antes de escolher a posição!", "Batalha Naval", JOptionPane.WARNING_MESSAGE);		
			} 
			else 
			{
				int varIndexObj = 0;
		
				AccessibleContext obj = ((JLabel)evt.getSource()).getAccessibleContext();
				varIndexObj = obj.getAccessibleIndexInParent();

				if (this.orientacao) { 
					int linha = Integer.valueOf(  ((JLabel)jpnl.getComponent(varIndexObj)).getName().split(",")[0]).intValue();

					//Posiciona os barcos na horizontal.
					if ((varIndexObj+this.barcoSelecionado - 1) > (linha * 10) + 9) {
						JOptionPane.showMessageDialog(this, "Posição inválida", "Batalha Naval", JOptionPane.WARNING_MESSAGE);		
						return;
					}
					String comp = "";
					for (int i = varIndexObj; i < varIndexObj+this.barcoSelecionado; i++) {
						comp = ((JLabel)jpnl.getComponent(i)).getIcon().toString().split("/")[3];
						if (!comp.equals(String.valueOf("agua32.jpg"))) {					
							JOptionPane.showMessageDialog(this, "Posição inválida, barco já posicionado no local", "Batalha Naval", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}

					for (int i = varIndexObj; i < varIndexObj+this.barcoSelecionado; i++) {

						if (i == varIndexObj) {
							((JLabel)jpnl.getComponent(i)).setIcon(new ImageIcon("gui/imagens/Padrao/popaH.jpg"));
						} else if (i == varIndexObj+this.barcoSelecionado - 1) {
							((JLabel)jpnl.getComponent(i)).setIcon(new ImageIcon("gui/imagens/Padrao/proaH.jpg"));
						} else {
							((JLabel)jpnl.getComponent(i)).setIcon(new ImageIcon("gui/imagens/Padrao/meioH.jpg"));
						}
					}
				}
				else 
				{
					//Posiciona os barcos na vertical
					if (varIndexObj+10*(this.barcoSelecionado-1) > 99) {
						JOptionPane.showMessageDialog(this, "Posição inválida", "Batalha Naval", JOptionPane.WARNING_MESSAGE);
						return;
					}

					String comp = "";
					for (int i = 0; i < this.barcoSelecionado; i++) {
						comp = ((JLabel)jpnl.getComponent(varIndexObj+i*10)).getIcon().toString().split("/")[3];
						if (!comp.equals(String.valueOf("agua32.jpg"))) {
							JOptionPane.showMessageDialog(this, "Posição inválida, barco já posicionado no local", "Batalha Naval", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}

					for (int i = 0; i < this.barcoSelecionado; i++) {
						if (i == 0) {
							((JLabel)jpnl.getComponent(varIndexObj+i*10)).setIcon(new ImageIcon("gui/imagens/Padrao/popaV.jpg"));
						} else if (i == this.barcoSelecionado - 1) {
							((JLabel)jpnl.getComponent(varIndexObj+i*10)).setIcon(new ImageIcon("gui/imagens/Padrao/proaV.jpg"));
						} else {
							((JLabel)jpnl.getComponent(varIndexObj+i*10)).setIcon(new ImageIcon("gui/imagens/Padrao/meioV.jpg"));
						}
					}
				}

				if (barcosInseridos == 5){
					for (int i = 0; i < 100; i++) {		
						((JLabel)jpnl.getComponent(i)).removeMouseListener(((JLabel)jpnl.getComponent(i)).getMouseListeners()[1]);
					}

					jpFrota.setVisible(false);
					stbar.setMessage("Iniciando o jogo...");
				}
				this.barcoSelecionado = -1; 
			}
		}
		else
		{
			mouseClicked(evt);
		}
	}

	private void selBarco(MouseEvent evt, int varBarco) {
		if (this.barcoSelecionado == -1) 
		{
			this.barcoSelecionado = varBarco;
			this.barcosInseridos++;
			
			if (varBarco == 5) {
				jlf0.setEnabled(false);
				jlf0.removeMouseListener(jlf0.getMouseListeners()[0]);
				jlf1.setEnabled(false);
				jlf1.removeMouseListener(jlf1.getMouseListeners()[0]);
				jlf2.setEnabled(false);
				jlf2.removeMouseListener(jlf2.getMouseListeners()[0]);
				jlf3.setEnabled(false);
				jlf3.removeMouseListener(jlf3.getMouseListeners()[0]);
				jlf4.setEnabled(false);
				jlf4.removeMouseListener(jlf4.getMouseListeners()[0]);

			} else if (varBarco == 4) {
				jlf5.setEnabled(false);
				jlf5.removeMouseListener(jlf5.getMouseListeners()[0]);
				jlf6.setEnabled(false);
				jlf6.removeMouseListener(jlf6.getMouseListeners()[0]);
				jlf7.setEnabled(false);
				jlf7.removeMouseListener(jlf7.getMouseListeners()[0]);
				jlf8.setEnabled(false);
				jlf8.removeMouseListener(jlf8.getMouseListeners()[0]);

			} else if (varBarco == 31) {
				jlf9.setEnabled(false);
				jlf9.removeMouseListener(jlf9.getMouseListeners()[0]);
				jlf10.setEnabled(false);
				jlf10.removeMouseListener(jlf10.getMouseListeners()[0]);
				jlf11.setEnabled(false);
				jlf11.removeMouseListener(jlf11.getMouseListeners()[0]);
				this.barcoSelecionado = 3;
			} else if (varBarco == 32) {
				jlf12.setEnabled(false);
				jlf12.removeMouseListener(jlf12.getMouseListeners()[0]);
				jlf13.setEnabled(false);
				jlf13.removeMouseListener(jlf13.getMouseListeners()[0]);
				jlf14.setEnabled(false);
				jlf14.removeMouseListener(jlf14.getMouseListeners()[0]);
				this.barcoSelecionado = 3;
			} else if (varBarco == 2) {
				jlf15.setEnabled(false);
				jlf15.removeMouseListener(jlf15.getMouseListeners()[0]);
				jlf16.setEnabled(false);
				jlf16.removeMouseListener(jlf16.getMouseListeners()[0]);
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Posicione o barco já selecionado, depois escolha o outro", "Batalha Naval", JOptionPane.WARNING_MESSAGE);
		}
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

	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			this.orientacao = !this.orientacao;
		}
		if (this.orientacao) {
			this.stbar.setMessage("Os barcos serão posicionados na HORIZONTAL.");
		} else {
			this.stbar.setMessage("Os barcos serão posicionados na VERTICAL.");
		} 
	}
  
	public void mousePressed(MouseEvent e) {
	}
  
	public void mouseReleased(MouseEvent e) {
	}
  
	public void mouseEntered(MouseEvent e) {
	}
  
	public void mouseExited(MouseEvent e) {
	}

	public boolean ValidaTiro(int indexObj) {
		// Valida se o tiro foi dado na água ou foi um acerto.
		if (((JLabel)jpEsquerda.getComponent(indexObj)).getIcon().toString().split("/")[3].equals("agua32.jpg")) {
			((JLabel)jpEsquerda.getComponent(indexObj)).setIcon(new ImageIcon("gui/imagens/Padrao/ExplosaoAgua32.jpg"));
			return false;
		} 
		else {
			((JLabel)jpEsquerda.getComponent(indexObj)).setIcon(new ImageIcon("gui/imagens/Padrao/Explosao32.jpg"));
			return true;
		}
	}
	
	public void MostrarTiro(boolean Acertou, int indexObj) {
		if (Acertou) {
			((JLabel)jpDireita.getComponent(indexObj)).setIcon(new ImageIcon("gui/imagens/Padrao/aguapo16.jpg"));
			((JLabel)jpDireita.getComponent(indexObj)).removeMouseListener(((JLabel)jpDireita.getComponent(indexObj)).getMouseListeners()[1]);
		} 
		else {
			((JLabel)jpDireita.getComponent(indexObj)).setIcon(new ImageIcon("gui/imagens/Padrao/aguapx16.jpg"));
			((JLabel)jpDireita.getComponent(indexObj)).removeMouseListener(((JLabel)jpDireita.getComponent(indexObj)).getMouseListeners()[1]);
		}
	}	
	
	public void EsperarTurno(boolean opt)
	{
		for (int i = 0; i < 100; i++) {
			((JLabel)jpDireita.getComponent(i)).setEnabled(opt);
		}
	}
	
	public void MostrarVitoria()
	{
		JOptionPane.showMessageDialog(this, "Você venceu o jogo!", "Batalha Naval", JOptionPane.INFORMATION_MESSAGE);
		this.buildWindow();
	}
	
	public void MostrarDerrota()
	{
		JOptionPane.showMessageDialog(this, "Você foi derrotado!", "Batalha Naval", JOptionPane.INFORMATION_MESSAGE);
		this.buildWindow();
	}
}