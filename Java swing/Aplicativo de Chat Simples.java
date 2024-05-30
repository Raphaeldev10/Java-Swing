import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatApp extends JFrame implements ActionListener {
    private JTextField textField;
    private JTextArea textArea;
    private JButton sendButton;
    private String userName;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ChatApp(String userName) {
        this.userName = userName;

        setTitle("Chat - " + userName);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField(30);
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        try {
            socket = new Socket("localhost", 5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(userName);
            new Thread(new IncomingReader()).start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        String userName = JOptionPane.showInputDialog("Enter your username:");
        if (userName != null && !userName.isEmpty()) {
            SwingUtilities.invokeLater(() -> new ChatApp(userName));
        } else {
            JOptionPane.showMessageDialog(null, "Username cannot be empty. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String message = textField.getText().trim();
            if (!message.isEmpty()) {
                writer.println(message);
                textField.setText("");
            }
        }
    }

    private void appendMessage(String message) {
        textArea.append(message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    class IncomingReader implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    appendMessage(message);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error reading message: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
