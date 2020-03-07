package lab.second.view.controllers;

import airship.model.Client;
import airship.model.Route;
import airship.model.Ticket;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

abstract public class MainControl {

    protected DAOProvider daoProvider = new DAOProvider();

    protected static Client selectedClient;
    protected static Ticket selectedTicket;
    protected static Route selectedRoute;

    @FXML
    private void mainRoutesButtonAction(ActionEvent event) {
        clearSelecting();
        toScene("route/list_routes.fxml", "List Routes", event);
    }

    @FXML
    private void mainTicketsButtonAction(ActionEvent event) {
        clearSelecting();
        toScene("ticket/list_tickets.fxml", "List Tickets", event);
    }

    @FXML
    private void mainClientsButtonAction(ActionEvent event) {
        clearSelecting();
        toScene("client/list_clients.fxml", "List Clients", event);
    }

    public void toScene(String path, String title, Event event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.FXML + path));
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            mainStage.setTitle(title);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearSelecting() {
        selectedClient = null;
        selectedRoute = null;
        selectedTicket = null;
    }
}
