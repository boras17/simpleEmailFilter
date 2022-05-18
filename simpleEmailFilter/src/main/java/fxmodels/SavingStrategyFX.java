package fxmodels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.saves.SavingBy;

public class SavingStrategyFX {
    private ObjectProperty<SavingBy> saveBy = new SimpleObjectProperty();
    private StringProperty pattern = new SimpleStringProperty();


    public String getPattern() {
        return pattern.get();
    }

    public StringProperty patternProperty() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern.set(pattern);
    }

    public SavingBy getSaveBy() {
        return saveBy.get();
    }

    public ObjectProperty<SavingBy> saveByProperty() {
        return saveBy;
    }

    public void setSaveBy(SavingBy saveBy) {
        this.saveBy.set(saveBy);
    }
}
