import java.net.*;
import java.util.List;
import java.io.*;

public class ServerHandler implements Runnable {
    DataInputStream in;
    Socket serverSocket;
    Client client;

    public ServerHandler(Client client, Socket serverSocket) {
        try {
            this.client = client;
            this.serverSocket = serverSocket;
            this.in = new DataInputStream(new BufferedInputStream(serverSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line = "";
        while (!line.equals("Over"))
        {
            try
            {
                line = in.readUTF();
                if (line.startsWith("/command_list")) {
                    this.client.updateClientList(line);
                }
                if (line.startsWith("/message")) {
                    this.client.getMessage(line);
                }
                System.out.println(line);
                
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
        System.out.println("Closing connection");
     
        try {
            this.serverSocket.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
