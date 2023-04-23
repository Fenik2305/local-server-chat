import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    DataInputStream in;
    Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        try {
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
        System.out.println("Closing connection");

                            
        try {
            this.clientSocket.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
