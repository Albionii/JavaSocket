import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    private BufferedReader bufferedReader;
    private JPanel Panel;
    public JTextField Text;
    public JButton button1;
    public JTextPane ChatDisplay;


    static String username;

    static Socket socket;

    Client client = new Client(socket, username);

    public String s;

    public static void start() throws IOException {
        socket = new Socket("localhost", 1234);
        Scanner sc = new Scanner(System.in);
        username = JOptionPane.showInputDialog("Username: ");
    }

    public Chat() {
        JFrame f = new JFrame();
        f.setContentPane(Panel);
        f.pack();
        f.setVisible(true);
        f.setSize(1280, 720);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChatDisplay.setEditable(false);
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(bufferedReader);
        }


        button1.addActionListener(e -> {
            s = Text.getText();
            client.sendMessage(s, username);
            ChatDisplay.setText(ChatDisplay.getText() + "\n" + username + ": " + s);
            Text.setText("");
        });
        Text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    s = Text.getText();
                    client.sendMessage(s, username);
                    ChatDisplay.setText(ChatDisplay.getText() + "\n" + username + ": " + s);
                    Text.setText("");
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Enter your username for the group chat: ");
        //String username = scanner.nextLine();
        start();
        //new Chat();
        Chat ListenForMessage = new Chat();
        ListenForMessage.listenForMessage();

    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        //System.out.println("Listening");
                        msgFromGroupChat = bufferedReader.readLine();
                        //c.ChatDisplay.setForeground(Color.blue);
                        //c.ChatDisplay.setText(c.ChatDisplay.getText() + "\n" + msgFromGroupChat);
                        System.out.println(msgFromGroupChat);
                        ChatDisplay.setText(ChatDisplay.getText() + "\n" + msgFromGroupChat);

                    } catch (IOException ioException) {
                        //closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

}
