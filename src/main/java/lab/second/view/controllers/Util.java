package lab.second.view.controllers;

import airship.dao.DAO;
import airship.model.Airship;
import airship.model.Route;
import airship.model.Ticket;
import lab.second.Client;

import java.rmi.RemoteException;

public class Util {

    public static String path = "../../../../../fxml/";

    private Client client = new Client();

    public DAO<Airship> getAirshipDAO() throws RemoteException {
        return client.getFactoryDAO().getAirshipDAO();
    }

    public DAO<airship.model.Client> getClientDAO() throws RemoteException {
        return client.getFactoryDAO().getClientDAO();
    }

    public DAO<Route> getRouteDAO() throws RemoteException {
        return client.getFactoryDAO().getRouteDAO();
    }

    public DAO<Ticket> getTicketDAO() throws RemoteException {
        return client.getFactoryDAO().getTicketDAO();
    }

}
