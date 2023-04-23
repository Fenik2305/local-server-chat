import java.net.*;
import java.io.*;
import java.util.ArrayList;
 
public class Server
{
    private ArrayList<Socket> socketList = new ArrayList<Socket>();
    private ServerSocket          server = null;
 
    public Server(int port)
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a clients...");

            while (true) {
                Socket newClient = server.accept();
                this.ConnectClient(newClient);
                
                ClientHandler handler = new ClientHandler(this, newClient);
                Thread thread = new Thread(handler);
                thread.start();
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public void SendClientList() {
        for (int i = 0; i < socketList.size(); i++) {
            try {
                DataOutputStream out = new DataOutputStream(socketList.get(i).getOutputStream());
                out.writeUTF("/command_list\n" + this.getUsers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUsers(){
        String line = new String();
        for (int i = 0; i < socketList.size(); i++) {
           line += socketList.get(i).getInetAddress().getHostAddress() + "\n";
        }
        return line;
    }

    public void SendMessage(ArrayList<Client> toSend, Client sender, String message) {
        
    }

    public void ConnectClient(Socket newClient) {
        System.out.println("Connected new client: " + newClient.getInetAddress().getHostAddress());
        this.socketList.add(newClient);
        this.SendClientList();
    }

    public void DisconnectClient(Socket pesPatron) {
        for (int i = 0; i < socketList.size(); i++) {
            if (socketList.get(i) == pesPatron) {
                socketList.remove(i);
                break;
            }
        }
        System.out.println("Disconnected client: " + pesPatron.getInetAddress().getHostAddress());
        this.SendClientList();
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}