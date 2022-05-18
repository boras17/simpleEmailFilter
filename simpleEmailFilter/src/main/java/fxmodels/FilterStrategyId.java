package fxmodels;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilterStrategyId {
    private final StringProperty filterId;
    private final StringProperty savingStrategyId;

    public FilterStrategyId() {
        filterId = new SimpleStringProperty();
        savingStrategyId = new SimpleStringProperty();
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

    public String getSavingStrategyId() {
        return savingStrategyId.get();
    }

    public StringProperty savingStrategyIdProperty() {
        return savingStrategyId;
    }

    public void setSavingStrategyId(String savingStrategyId) {
        this.savingStrategyId.set(savingStrategyId);
    }
}
