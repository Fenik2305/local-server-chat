import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private ArrayList<String> clientList = null;
    private String nickname = null;
 
    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            ServerHandler serverHandler = new ServerHandler(this, socket);
            Thread thread = new Thread(serverHandler);
            thread.start();
 
            // takes input from terminal
            input = new DataInputStream(System.in);
 
            // sends output to the socket
            out = new DataOutputStream(
                socket.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }
 
        // string to read message from input

        String line = "";
              // keep reading until "Over" is input
              //while (!line.equals("Over")) {
              //  try {
              //      line = input.readLine();
              //     out.writeUTF(line);
               // }
               // catch (IOException i) {
               //     System.out.println(i);
              // }
          //  }
     
            // close the connection
           // try {
           //     input.close();
            //    out.close();
           //     socket.close();
           // }
          //  catch (IOException i) {
           //     System.out.println(i);
           // }
  
    }

    public void updateClientList(String clientList) {
        this.clientList = new ArrayList(Arrays.asList(clientList.split("\n")));
        this.clientList.remove(0);
    }

    public ArrayList<String> getClientList() {
        return this.clientList;
    }
 
    public static void main(String args[])
    {
        Client client = new Client("192.168.1.138", 5000);
    }
}

