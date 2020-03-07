package lab.second.view.controllers.route;

import airship.model.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lab.second.view.controllers.AlertDialog;
import lab.second.view.controllers.MainControl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddRouteController extends MainControl implements Initializable {

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private Button saveRouteButton;

    @FXML
    private Button routesMainButton;

    @FXML
    private Button ticketsMainButton;

    @FXML
    private Button clientsMainButton;

    @FXML
    void saveRouteButtonAction(ActionEvent event) {
        String regex = "^[a-zA-Z0-9А-Яа-я._-]{3,}$";
        if(!fromTextField.getText().matches(regex) || !toTextField.getText().matches(regex)) {
            AlertDialog.showAlert("Incorrect input");
            return;
        }
        try {
            daoProvider.getRouteDAO().add(new Route(fromTextField.getText(), toTextField.getText()));
            toScene("route/list_routes.fxml", "List Routes", event);
        } catch (IOException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
