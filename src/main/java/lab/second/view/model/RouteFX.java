package lab.second.view.model;

import airship.model.Route;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class RouteFX {
    private StringProperty id;
    private StringProperty startPoint;
    private StringProperty endPoint;

    public RouteFX(StringProperty id, StringProperty startPoint, StringProperty endPoint) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public RouteFX(Route route) {
        id = new SimpleStringProperty(route.getId().toString());
        startPoint = new SimpleStringProperty(route.getStartPoint());
        endPoint = new SimpleStringProperty(route.getEndPoint());
    }

    public UUID getId() {
        return UUID.fromString(id.getValue());
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint.get();
    }

    public StringProperty startPointProperty() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint.set(startPoint);
    }

    public String getEndPoint() {
        return endPoint.get();
    }

    public StringProperty endPointProperty() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint.set(endPoint);
    }
}
