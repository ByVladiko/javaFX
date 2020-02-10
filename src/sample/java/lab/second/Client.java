package lab.second;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private OutputStream os;
    private OutputStreamWriter osw;
    private BufferedWriter bw;

    public Client() {
        try {
            socket = new Socket("localhost", 3345);
            System.out.println("Connected!");
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {

        if (socket.isConnected()) {
            if (bw != null) {
                bw.write(message + "\n");
                bw.flush();
            } else {
                System.out.println("write disconnected");
            }
        } else
            System.out.println("not connected");
    }
}


