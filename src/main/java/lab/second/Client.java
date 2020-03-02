package lab.second;

import airship.dao.FactoryDAO;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    private static final String REMOTE_SERVICE_PATH = "rmi://localhost:5555/FactoryDAO";

    private FactoryDAO factoryDAO;

    public Client() {
        try {
            factoryDAO = (FactoryDAO) Naming.lookup(REMOTE_SERVICE_PATH);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public FactoryDAO getFactoryDAO() {
        return factoryDAO;
    }
}
