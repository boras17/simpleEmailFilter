package fxmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailFilterFilteredEmailId {
    private final StringProperty filterId;
    private final StringProperty filteredEmailId;

    public EmailFilterFilteredEmailId() {
        this.filteredEmailId = new SimpleStringProperty();
        this.filterId = new SimpleStringProperty();
    }

    public String getFilterId() {
        return filterId.get();
    }

    public StringProperty filterIdProperty() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId.set(filterId);
    }

    public String getFilteredEmailId() {
        return filteredEmailId.get();
    }

    public StringProperty filteredEmailIdProperty() {
        return filteredEmailId;
    }

    public void setFilteredEmailId(String filteredEmailId) {
        this.filteredEmailId.set(filteredEmailId);
    }
}
