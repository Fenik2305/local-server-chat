import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    private DataInputStream in;
    private Server server;
    private Socket clientSocket;

    public ClientHandler(Server server, Socket clientSocket) {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            this.in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
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
                System.out.println(line);

            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
        this.server.DisconnectClient(this.clientSocket);
        //System.out.println("Closing connection");

                            
        try {
            this.clientSocket.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
