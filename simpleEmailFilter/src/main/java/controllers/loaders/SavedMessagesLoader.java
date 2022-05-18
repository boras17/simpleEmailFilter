package controllers.loaders;


import controllers.Bot;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.MessageSaved;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class SavedMessagesLoader extends AbstractTableViewDataLoader<MessageSaved> implements EventHandler<MouseEvent> {
    private Bot bot;
    private Consumer<MessageSaved> messageSavedConsumer;
    private static Thread loadingThread;
    public static void setWorkingThread(Thread thread) {
        loadingThread = thread;
    }
    public static Optional<Thread> getWorkingLoaderThread() {
        return Optional.of(loadingThread);
    }
    public SavedMessagesLoader(TableView<MessageSaved> context, Class<MessageSaved> dataType, Bot bot) {
        super(context, dataType);
        this.bot = bot;
    }
    public SavedMessagesLoader(TableView<MessageSaved> context,
                               Class<MessageSaved> dataType,
                               Bot bot,
                               Consumer<MessageSaved> messageSavedConsumer) {
        super(context, dataType);
        this.bot = bot;
        this.messageSavedConsumer = messageSavedConsumer;
        TableView<MessageSaved> messageSavedTableView = this.getContext();
        ObservableList<TableColumn<MessageSaved,?>> columns = messageSavedTableView.getColumns();
        columns.get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<>("author"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<>("title"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<>("sentDate"));

        TableColumn<MessageSaved,Integer> attachmentsColumn = (TableColumn<MessageSaved,Integer>)columns.get(4);
        attachmentsColumn.setCellValueFactory(column -> {
            MessageSaved messageSaved = column.getValue();
            int attachmentsSize = messageSaved.getAttachments().size();
            return new SimpleObjectProperty<>(attachmentsSize);
        });
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(this.messageSavedConsumer != null){
            this.dynamicLoad();
        }else{
            this.load(super.getContext());
        }

    }
    private void load(TableView<MessageSaved> tableView) {
        List<MessageSaved> messagesSaved = this.getDataRepository().getAll();
        if(messagesSaved.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No messages saved");
            alert.setContentText("You do not have any message in database. PLease try to use dynamic load");
            alert.show();
        }else{
            tableView.getItems().addAll(messagesSaved);
        }
    }

    private void dynamicLoad() {
        Thread loadingThread = this.bot.checkMessages(this.messageSavedConsumer);
        setWorkingThread(loadingThread);
    }
}
