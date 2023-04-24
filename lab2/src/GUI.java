import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.NameList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JTextArea chatTextArea;
    private JTextField messageTextField;
    private JList<String> nameList;
    private DefaultListModel<String> listModel;
    private Client client;

    public GUI() {
        frame = new JFrame("Multi-Select Chat Name List App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);

        messageTextField = new JTextField();

        this.listModel = new DefaultListModel<>();
    
        this.client = new Client("192.168.1.138", 5000, this); 
        Client temp = this.client;

        //this.updateClientList();

        nameList = new JList<>(this.listModel);
        nameList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        nameList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    JList<String> source = (JList<String>) e.getSource();
                    List<String> selectedNames = source.getSelectedValuesList();
                    chatTextArea.append("Selected names: " + String.join(", ", selectedNames) + "\n");
                }
            }
        });

        

        messageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextField.getText();
                chatTextArea.append("You: " + message + "\n");
                messageTextField.setText("");
                temp.sendMessage(message, nameList.getSelectedValuesList());
            }
        });

        JScrollPane nameListScrollPane = new JScrollPane(nameList);

        frame.setLayout(new BorderLayout());
        frame.add(nameListScrollPane, BorderLayout.WEST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(messageTextField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void addMessageToChat(String text, String sender) {
        chatTextArea.append(sender + ": " + text + "\n");
    }

    public void updateClientList() {
        this.listModel.clear();
        for (String client : this.client.getClientList()) {
            this.listModel.addElement(client);
        }
    } 
}