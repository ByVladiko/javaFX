package lab.second.view.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.second.view.controllers.ticket.TicketListController;

public class MainControl {

    Stage mainStage;

    @FXML
    private void mainRoutesButtonAction(ActionEvent event) throws Exception {
        toScene("route/list_routes.fxml", "List Routes", event);
    }

    @FXML
    private void mainClientsButtonAction(ActionEvent event) throws Exception {
        toScene("client/list_clients.fxml", "List Clients", event);
    }

    @FXML
    private void mainTicketsButtonAction(ActionEvent event) throws Exception {
        TicketListController.client = null;
        toScene("ticket/list_tickets.fxml", "List Tickets", event);
    }

    public void toScene(String path, String title, Event event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(Util.path + path));
        mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        mainStage.setTitle(title);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
