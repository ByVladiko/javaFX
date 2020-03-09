package lab.second.view.model;

import airship.model.Airship;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class AirshipFX {
    private StringProperty id;
    private StringProperty model;
    private LongProperty numberOfSeat;

    public AirshipFX(StringProperty id, StringProperty model, LongProperty numberOfSeat) {
        this.id = id;
        this.model = model;
        this.numberOfSeat = numberOfSeat;
    }

    public AirshipFX(Airship airship) {
        id = new SimpleStringProperty(airship.getId().toString());
        model = new SimpleStringProperty(airship.getModel());
        numberOfSeat = new SimpleLongProperty(airship.getNumberOfSeat());
    }

    public UUID getId() {
        return UUID.fromString(id.getValue());
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public long getNumberOfSeat() {
        return numberOfSeat.get();
    }

    public LongProperty numberOfSeatProperty() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(long numberOfSeat) {
        this.numberOfSeat.set(numberOfSeat);
    }
}
