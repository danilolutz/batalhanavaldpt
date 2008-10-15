package conn;
import gui.MainWindow;
import java.io.*;
import java.net.*;

public class SocketClient extends Thread implements Runnable {
	private Socket clientSocket;
	private BufferedReader entrada;
	private PrintStream saida;
	private String varResposta;
	private MainWindow varMW;
	
	public void run ()
	{
		try {
			
			while (true) {		
				varResposta = entrada.readLine();
				
				if (varResposta.substring(0, 2).equals("W:")) {
					varMW.MostrarVitoria();
				}
				
				if (varResposta.substring(0, 2).equals("P:")) {
					varMW.jlJogador1.setText(varResposta.substring(2));
					varMW.stbar.setText(varMW.jlJogador1.getText() + ", conectado.");
				}
				
				if (varResposta.substring(0, 2).equals("A:")) {
					varMW.jlPtJogador2.setText(varResposta.substring(2));
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
					varMW.stbar.setText("Turno de " + varMW.jlJogador2.getText());
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
	
	public SocketClient (MainWindow mw, String serverAddress, String serverPort) {
		try {
			varMW = mw;
			Socket serverConnection = new Socket(serverAddress, Integer.parseInt(serverPort));
			
			entrada = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
			saida = new PrintStream(serverConnection.getOutputStream());		
		
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
		varMW.stbar.setText("Turno de " + varMW.jlJogador1.getText());
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