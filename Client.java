import java.io.*;
import java.net.Socket;

public class Client {
    public Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    //public String username;

    public String messageTosend = " ";



    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.username = username;

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String s,String username){
        messageTosend = s;
        try {
            //bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            //Scanner scanner = new Scanner(System.in);
            if (socket.isConnected()) {
                    //c.ChatDisplay.setText(c.ChatDisplay.getText() + "\n" + (username + ": " + messageToSend));
                    bufferedWriter.write(username + " : " + messageTosend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

            }


        }catch (IOException ioException) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }

        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
