package lab.second.view.controllers;

import airship.dao.DAO;
import airship.model.Airship;
import airship.model.Route;
import airship.model.Ticket;
import javafx.scene.control.Alert;
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

    public void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Attention");
        a.setHeaderText("Ops!");
        String version = System.getProperty("java.version");
        String content = String.format(message, version);
        a.setContentText(content);
        a.showAndWait();
    }

}
