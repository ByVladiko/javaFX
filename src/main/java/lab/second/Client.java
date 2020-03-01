package lab.second;

import airship.dao.DAO;
import airship.dao.FactoryDAO;
import airship.model.Airship;
import airship.model.Route;
import airship.model.Ticket;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String REMOTE_HOSTNAME = "java.rmi.server.hostname";
    private static final String REMOTE_SERVICE_PATH = "rmi://localhost:5555/FactoryDAO";

    private FactoryDAO factoryDAO;

    public Client() {
        try {
            System.setProperty(REMOTE_HOSTNAME, LOCALHOST_IP);
            factoryDAO = (FactoryDAO) Naming.lookup(REMOTE_SERVICE_PATH);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public DAO<Airship> getAirshipDAO() throws RemoteException {
        return factoryDAO.getAirshipDAO();
    }

    public DAO<airship.model.Client> getClientDAO() throws RemoteException {
        return factoryDAO.getClientDAO();
    }

    public DAO<Route> getRouteDAO() throws RemoteException {
        return factoryDAO.getRouteDAO();
    }

    public DAO<Ticket> getTicketDAO() throws RemoteException {
        return factoryDAO.getTicketDAO();
    }
}
