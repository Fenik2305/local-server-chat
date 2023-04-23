import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GuiMain {
    private JFrame frame;
    private JTextArea chatTextArea;
    private JTextField messageTextField;
    private JList<String> nameList;
    private DefaultListModel<String> listModel;

    public GuiMain() {
        //Client client = new Client();
        frame = new JFrame("Multi-Select Chat Name List App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);

        messageTextField = new JTextField();
        messageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextField.getText();
                chatTextArea.append("You: " + message + "\n");
                messageTextField.setText("");
            }
        });

        listModel = new DefaultListModel<>();
        listModel.addElement("John");
        listModel.addElement("Jane");
        listModel.addElement("Alice");
        listModel.addElement("Bob");
        listModel.addElement("Charlie");

        nameList = new JList<>(listModel);
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

        JScrollPane nameListScrollPane = new JScrollPane(nameList);

        frame.setLayout(new BorderLayout());
        frame.add(nameListScrollPane, BorderLayout.WEST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(messageTextField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
