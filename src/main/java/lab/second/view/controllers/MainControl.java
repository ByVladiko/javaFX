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

import java.io.IOException;

public class MainControl {

    protected Util util = new Util();

    @FXML
    private void mainRoutesButtonAction(ActionEvent event) {
        toScene("route/list_routes.fxml", "List Routes", event);
    }


    @FXML
    private void mainTicketsButtonAction(ActionEvent event) {
        TicketListController.client = null;
        toScene("ticket/list_tickets.fxml", "List Tickets", event);
    }

    @FXML
    private void mainClientsButtonAction(ActionEvent event) {
        toScene("client/list_clients.fxml", "List Clients", event);
    }

    public void toScene(String path, String title, Event event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Util.path + path));
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            mainStage.setTitle(title);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
