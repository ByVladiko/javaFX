package second;

import dao.DAO;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private final Registry registry = LocateRegistry.getRegistry(2099);

    private Socket socket;
    private BufferedWriter bw;

    public Client() throws RemoteException {
        try {
            socket = new Socket("localhost", 3345);
            System.out.println("Connected!");
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String message) throws IOException, NotBoundException {

        if (socket.isConnected()) {
            if (bw != null) {
                bw.write(message + "\n");
                bw.flush();
                String UNIC_BINDING_NAME = "server.dao";
                DAO service = (DAO) registry.lookup(UNIC_BINDING_NAME);
                return service.toUpperCase(message);
            } else {
                System.out.println("write disconnected");
            }
        } else
            System.out.println("not connected");
        return "";
    }
}


