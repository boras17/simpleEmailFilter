package fxmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailFilterFX {
    private StringProperty emailFilterName = new SimpleStringProperty();

    public String getEmailFilterName() {
        return emailFilterName.get();
    }

    public StringProperty emailFilterNameProperty() {
        return emailFilterName;
    }

    public void setEmailFilterName(String emailFilterName) {
        this.emailFilterName.set(emailFilterName);
    }
}
