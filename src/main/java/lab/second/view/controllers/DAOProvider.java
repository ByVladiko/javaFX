package lab.second.view.controllers;

import airship.dao.DAO;
import airship.dao.FactoryDAO;
import airship.model.Airship;
import airship.model.Route;
import airship.model.Ticket;
import airship.model.Client;
import lab.second.view.AlertDialog;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class DAOProvider {

    private static final String REMOTE_SERVICE_PATH = "rmi://localhost:5555/FactoryDAO";

    private FactoryDAO factoryDAO;

    public DAOProvider() {
        try {
            this.factoryDAO = (FactoryDAO) Naming.lookup(REMOTE_SERVICE_PATH);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    public DAO<Airship> getAirshipDAO() throws RemoteException {
        return factoryDAO.getAirshipDAO();
    }

    public DAO<Client> getClientDAO() throws RemoteException {
        return factoryDAO.getClientDAO();
    }

    public DAO<Route> getRouteDAO() throws RemoteException {
        return factoryDAO.getRouteDAO();
    }

    public DAO<Ticket> getTicketDAO() throws RemoteException {
        return factoryDAO.getTicketDAO();
    }

}
