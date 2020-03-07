package lab.second.view.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertDialog {

    private AlertDialog() {}

    public static void showAlert(AlertType alertType, String title, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void showAlert(String title, String headerText, String message) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInfoAlert(String title, String headerText, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWarnAlert(String title, String headerText, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showErrorAlert(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error alert");
        alert.setHeaderText(e.getMessage());
        VBox dialogPaneContent = new VBox();
        Label label = new Label("Stack Trace:");
        String stackTrace = getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);
        dialogPaneContent.getChildren().addAll(label, textArea);
        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();
    }

    private static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
