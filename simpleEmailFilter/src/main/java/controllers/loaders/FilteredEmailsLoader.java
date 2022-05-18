package controllers.loaders;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.filters.FilteredEmail;

import java.util.List;

public class FilteredEmailsLoader extends AbstractTableViewDataLoader<FilteredEmail> implements EventHandler<MouseEvent> {

    public FilteredEmailsLoader(TableView<FilteredEmail> context) {
        super(context, FilteredEmail.class);
    }


    @Override
    public void handle(MouseEvent mouseEvent) {

        List<FilteredEmail> filteredEmails = this.getDataRepository().getAll();
        if(filteredEmails.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You do not have any emails in data base");
        }else{
            TableView<FilteredEmail> view = this.getContext();
            view.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            view.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("email"));
            view.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("host"));
            view.setItems(FXCollections.observableList(filteredEmails));
        }
    }
}