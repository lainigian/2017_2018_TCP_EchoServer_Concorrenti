import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPechoServer extends Thread
{

	private ServerSocket server;
	private final int SERVER_PORT=7;
	
	public TCPechoServer() throws IOException
	{
		server= new ServerSocket(SERVER_PORT);
		server.setSoTimeout(1000);
	}
	
	public void run()
	{
		Socket connection = null;
		while(!interrupted())
		{
			try 
			{
				connection=server.accept();  //rimane in ascolto di nuve richiese di connessione da parte di client
				//genera un thread ClientConnection per la gestine di ogni richiesta di connessione
				ClientThread clientThread= new ClientThread(connection);	
				Thread nuovoThread= new Thread (clientThread);
				nuovoThread.start();
			}
			catch (SocketTimeoutException e) 
			{
				System.err.println("Timeout");
			}
			catch (IOException e) 
			{
			
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}
	
	
	
	public static void main(String[] args) 
	{
		ConsoleInput tastiera= new ConsoleInput();
		try 
		{
			TCPechoServer server= new TCPechoServer();
			server.start();
			tastiera.readLine();
			server.interrupt();
			server.join();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

}
