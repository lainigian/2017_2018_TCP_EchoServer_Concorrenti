import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread implements Runnable
{
	private Socket connection;
	private InputStream input;
	private OutputStream out;
	
	public ClientThread(Socket connection) throws IOException
	{
		this.connection=connection;
		input=connection.getInputStream();
		out=connection.getOutputStream();
	}
	
	public void run()
	{
		byte[] bufferInput= new byte[1];
		byte[] bufferOutput=new byte[1];
		int n;
		
		try 
		{
			while ((n=input.read(bufferInput))!=-1)
			{
				if (n>0)
				{
					out.write(bufferInput,0,n);
					out.flush();
				}
			}
			
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			input.close();
			out.close();
			connection.shutdownInput();
			connection.shutdownOutput();
			connection.close();
			System.out.println("connessione chiusa");
		} 
		catch (IOException e) 
		{
		
		}
		
		
	}
	
}
