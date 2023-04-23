import java.net.*;
import java.io.*;

public class ServerHandler implements Runnable {
    DataInputStream in;
    Socket serverSocket;

    public ServerHandler(Socket serverSocket) {
        try {
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
