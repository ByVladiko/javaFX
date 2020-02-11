package second;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;
    private Socket client;

    public Server() {
        try {
            server = new ServerSocket(3345);
            beginDialog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void beginDialog() throws IOException {

        System.out.println("Waiting for a client...");

        client = server.accept();
        System.out.println("Connection accepted.");

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        String input;

        System.out.println("Waiting for messages...");

        try {
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) break;
                System.out.println(input);
            }
        } catch (Exception e) {
            out.close();
            in.close();
            client.close();
            server.close();
            System.out.println("Connection closed");
        }

        out.close();
        in.close();
        client.close();
        server.close();
    }
}

