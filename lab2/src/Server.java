import java.net.*;
import java.util.ArrayList;
import java.io.*;
 
public class Server
{
   private ArrayList<Socket> socketList = new ArrayList<Socket>();
    private ServerSocket        server   = null;
 
    public Server(int port)
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            while (true) {
                Socket newClient = server.accept();
                this.ConnectClient(newClient);
                
                ClientHandler handler = new ClientHandler(newClient);
                Thread thread = new Thread(handler);
                thread.start();
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public void SendMessage(ArrayList<Client> toSend, Client sender, String message) {

    }

    public void ConnectClient(Socket newClient) {
        System.out.println("Connected new client: " + newClient.getInetAddress().getHostAddress());
        this.socketList.add(newClient);
    }

    public void DisconnectClient(Client pesPatron) {

    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}