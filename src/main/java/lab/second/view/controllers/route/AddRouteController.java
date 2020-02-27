package lab.second.view.controllers.route;

import airship.model.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lab.second.view.controllers.MainControl;

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
    void saveRouteButtonAction(ActionEvent event) throws Exception {
        String regex = "^[a-zA-Z0-9А-Яа-я._-]{3,}$";
        if(!fromTextField.getText().matches(regex) || !toTextField.getText().matches(regex)) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Attention");
            a.setHeaderText("Ops!");
            String version = System.getProperty("java.version");
            String content = String.format("Incorrect input", version);
            a.setContentText(content);
            a.showAndWait();
            return;
        }
        RouteDAOImpl.getInstance().add(new Route(fromTextField.getText(), toTextField.getText()));
        toScene("route/list_routes.fxml", "List Routes", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
