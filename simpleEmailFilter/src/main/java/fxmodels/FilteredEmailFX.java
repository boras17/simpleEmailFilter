package fxmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilteredEmailFX {
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty host = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
