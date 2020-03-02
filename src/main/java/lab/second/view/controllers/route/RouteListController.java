package lab.second.view.controllers.route;

import airship.model.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lab.second.view.controllers.MainControl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RouteListController extends MainControl implements Initializable {

    private ObservableList<Route> tableRoutes  = FXCollections.observableArrayList();

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TableView<Route> tableViewRoutes;

    @FXML
    private TableColumn<Route, Integer> tableRoutesColumnId;

    @FXML
    private TableColumn<Route, String> tableRoutesColumnFrom;

    @FXML
    private TableColumn<Route, String> tableRoutesColumnTo;

    @FXML
    private Button addRouteButton;

    @FXML
    private Button editRouteButton;

    @FXML
    private Button deleteRouteButton;

    @FXML
    private Button mainRoutesButton;

    @FXML
    private Button mainTicketsButton;

    @FXML
    private Button mainClientsButton;

    @FXML
    void InputTextFieldKeyReleased(KeyEvent event) {
        try {
            List<Route> routes = util.getRouteDAO().getList().stream()
                    .filter(it -> it.getId().toString().toLowerCase().trim().contains(idTextField.getText().trim()))
                    .filter(it -> it.getStartPoint().toLowerCase().trim().contains(fromTextField.getText().trim()))
                    .filter(it -> it.getEndPoint().toLowerCase().trim().contains(toTextField.getText().trim()))
                    .collect(Collectors.toList());
            tableRoutes.setAll(routes);
            tableViewRoutes.setItems(tableRoutes);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addRouteButtonAction(ActionEvent event) {
        toScene("route/new_route.fxml", "New Route", event);
    }

    @FXML
    public void deleteRouteButtonAction(ActionEvent event) {
        if (tableViewRoutes.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            util.getRouteDAO().remove(tableViewRoutes.getSelectionModel().getSelectedItem());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    @FXML
    void editRouteButtonAction(ActionEvent event) {
        if(tableViewRoutes.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        EditRouteController.editRoute = tableViewRoutes.getSelectionModel().getSelectedItem();
        toScene("route/edit_route.fxml", "List Routes", event);
    }

    private void refreshTable() {
        try {
            tableRoutes.setAll(util.getRouteDAO().getList());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tableViewRoutes.setItems(tableRoutes);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableRoutesColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableRoutesColumnFrom.setCellValueFactory(new PropertyValueFactory<>("startPoint"));
        tableRoutesColumnTo.setCellValueFactory(new PropertyValueFactory<>("endPoint"));

        refreshTable();
    }
}

