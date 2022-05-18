package controllers.loaders;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.saves.SavingStrategy;

import java.util.List;

public class SavingStrategiesLoader extends AbstractTableViewDataLoader<SavingStrategy> implements EventHandler<MouseEvent> {

    public SavingStrategiesLoader(TableView<SavingStrategy> context) {
        super(context, SavingStrategy.class);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        List<SavingStrategy> savingStrategyList = this.getDataRepository().getAll();

        if(!savingStrategyList.isEmpty()) {
            this.getContext().getItems().clear();
            ObservableList<TableColumn<SavingStrategy,?>> columns = this.getContext().getColumns();
            columns.get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            columns.get(1).setCellValueFactory(new PropertyValueFactory<>("pattern"));
            columns.get(2).setCellValueFactory(new PropertyValueFactory<>("savingBy"));
            this.getContext().getItems().addAll(savingStrategyList);
        }else{
            Alert noSavingStrategies = new Alert(Alert.AlertType.INFORMATION);
            noSavingStrategies.setContentText("You do not have any saving strategies in data base");
            noSavingStrategies.show();
        }

    }
}
