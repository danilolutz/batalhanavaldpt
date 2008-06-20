package conn;

import gui.MainWindow;
import java.net.*;
import java.io.*;
public class SocketServer extends Thread implements Runnable {
		
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private BufferedReader entrada;
	private PrintStream saida;
	private String varResposta;
	private MainWindow varMW;
	
	public void run () 
	{
		try {
			while (true) {		
				varResposta = entrada.readLine(); //recebe parametros das jogadas do outro jogador
					
				if (varResposta.substring(0, 2).equals("W:")) {
					varMW.MostrarVitoria();
				}
				
				if (varResposta.substring(0, 2).equals("P:")) {
					varMW.jlJogador2.setText(varResposta.substring(2));
					varMW.stbar.setText(varMW.jlJogador2.getText() + ", conectado.");
				}
				
				if (varResposta.substring(0, 2).equals("A:")) {
					varMW.jlPtJogador1.setText(varResposta.substring(2));
				}
				
				if (varResposta.substring(0, 2).equals("C:")) {
					varMW.MostrarTiro(true, Integer.parseInt(varResposta.substring(2)));
				}
				
				if (varResposta.substring(0, 2).equals("E:")) {
					varMW.MostrarTiro(false, Integer.parseInt(varResposta.substring(2)));
				}				
				
				if (varResposta.substring(0, 2).equals("T:")) {
					
					if (varMW.ValidaTiro(Integer.parseInt(varResposta.substring(2)))) {
						varMW.VerificaPontos(true, Integer.parseInt(varResposta.substring(2)));
						EnviarPontos("A:" + varMW.contPontos);
						EnviarMira("C:" + Integer.parseInt(varResposta.substring(2)));
					}
					else
					{
						varMW.VerificaPontos(false, Integer.parseInt(varResposta.substring(2)));
						EnviarMira("E:" + Integer.parseInt(varResposta.substring(2)));
					}
					if (varMW.contPontos == 17) 
					{
						EnviarVencedor("W:");
						varMW.MostrarDerrota();
					}
					varMW.myTurn = true;
					varMW.EsperarTurno(true);
					varMW.stbar.setText("Turno de " + varMW.jlJogador1.getText());
				}
				
				if (varResposta.substring(0, 2).equals("D:"))
				{
					clientSocket.close();
					return;
				}				
			}		
		} catch (IOException e) {
			System.out.println("Excessão de I/O: " + e);
			System.exit(-1);
		}			
	}
	
	public SocketServer(MainWindow mw, String port) {
		
		try {
			varMW = mw;
			serverSocket = new ServerSocket(Integer.parseInt(port));
			clientSocket = serverSocket.accept();	
			
			entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			saida = new PrintStream(clientSocket.getOutputStream());
			
		} catch (IOException e) {
			System.out.println("Excessão de I/O: " + e);
			System.exit(-1);
		}
	}
	
	public void EnviarTiro(int index)
	{
		saida.println("T:" + index);
		varMW.myTurn = false;
		varMW.EsperarTurno(false);
		varMW.stbar.setText("Turno de " + varMW.jlJogador2.getText());
	}
	
	public void EnviarNome(String NickName)
	{
		saida.println("P:" + NickName);
	}
	
	public void EnviarPontos(String msg)
	{
		saida.println(msg);
	}
	
	public void EnviarMira(String msg)
	{
		saida.println(msg);
	}	
	
	public void EnviarVencedor(String msg)
	{
		saida.println(msg);
	}
}