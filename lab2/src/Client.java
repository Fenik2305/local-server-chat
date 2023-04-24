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
    private GUI gui = null;
 
    // constructor to put ip address and port
    public Client(String address, int port, GUI gui)
    {
        // establish a connection
        try {
            this.gui = gui;
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
    }
    public void sendMessage(String message, List<String> receivers) {
        if (!message.equals("Over")) {
            String command = "/message\n" + message + "\n" + String.join("\n", receivers);
            try {
                out.writeUTF(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                out.writeUTF("Over");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateClientList(String clientList) {
        this.clientList = new ArrayList(Arrays.asList(clientList.split("\n")));
        this.clientList.remove(0);
        this.gui.updateClientList();
    }

    public ArrayList<String> getClientList() {
        return this.clientList;
    }
    
    public void getMessage(String message){
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(message.split("\n"))); // Split input by newline character
        this.gui.addMessageToChat(lines.get(1),lines.get(2));
    }
}

