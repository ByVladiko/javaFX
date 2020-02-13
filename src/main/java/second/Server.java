package second;

import dao.DAOImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    final private DAOImpl daoImpl = new DAOImpl();

    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(3345);
            beginDialog();
        } catch (IOException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    private void beginDialog() throws IOException, AlreadyBoundException {

        System.out.println("Waiting for a client...");

        Socket client = server.accept();
        System.out.println("Connection accepted.");

        final Registry registry = LocateRegistry.createRegistry(2099);
        Remote stub = UnicastRemoteObject.exportObject(daoImpl, 0);
        String UNIC_BINDING_NAME = "server.dao";
        registry.bind(UNIC_BINDING_NAME, stub);

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
            System.out.println("Connection closed");
            beginDialog();
        }
        out.close();
        in.close();
        client.close();
        server.close();
    }
}